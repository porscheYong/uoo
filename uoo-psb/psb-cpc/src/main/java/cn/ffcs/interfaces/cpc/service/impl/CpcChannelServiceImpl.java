package cn.ffcs.interfaces.cpc.service.impl;
import java.util.Date;

import cn.ffcs.interfaces.cpc.constant.HandleChannelConstant;
import cn.ffcs.interfaces.cpc.constant.SystemConstant;
import cn.ffcs.interfaces.cpc.dao.*;
import cn.ffcs.interfaces.cpc.pojo.*;
import cn.ffcs.interfaces.cpc.service.*;
import cn.ffcs.interfaces.cpc.util.Json2MapUtil;
import cn.ffcs.uoo.base.common.tool.util.DateUtils;
import cn.ffcs.uoo.base.common.tool.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.*;

@Service
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class CpcChannelServiceImpl implements CpcChannelService {

    @Autowired
    private AmqpTemplate template;

    @Resource
    private TbOrgMapper tbOrgMapper;
    @Resource
    private CommonRegionMapper commonRegionMapper;
    @Resource
    private SystemtableMapper systemtableMapper;
    @Resource
    private ExpandocolumnMapper expandocolumnMapper;
    @Resource
    private ExpandorowMapper expandorowMapper;
    @Resource
    private ExpandovalueMapper expandovalueMapper;
    @Resource
    private AccountOrgRelMapper accountOrgRelMapper;
    @Resource
    private OrgTypeMapper orgTypeMapper;
    @Resource
    private TbOrgOrgtypeRelMapper tbOrgOrgtypeRelMapper;
    @Resource
    private TbPersonnelMapper tbPersonnelMapper;
    @Resource
    private TbCertMapper tbCertMapper;
    @Resource
    private TbContactMapper tbContactMapper;
    @Resource
    private TbAcctMapper tbAcctMapper;
    @Resource
    private TbSlaveAcctMapper tbSlaveAcctMapper;
    @Resource
    private AcctCrossRelMapper acctCrossRelMapper;

    @Resource
    private TbCpcLogMapper tbCpcLogMapper;

    @Autowired
    private SystemConstant systemConstant;

    private Long slaveAcctId = null;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public String handle(String json){
        logger.info("json:{}，date:{}",json,new Date());
        Map<String, Object> map = null;
        //1000是失败，0成功
        String result_code = "0";
        //结果描述
        StringBuffer result_msg = new StringBuffer();
        //流水号
        String TransactionID = "";
        //结果集
        Map<String, Object> rsMap = new HashMap<>();
        rsMap.put("result_code", result_code);
        try{
            map = Json2MapUtil.handle(json);
            if (map == null) {
                result_msg.append("json is null.");
            } else {
                try {
                    TransactionID = (String) map.get("TransactionID") == null ? "" : (String) map.get("TransactionID");
                    rsMap.put("TransactionID", TransactionID);
                    /*渠道*/
                    Map<String, Object> CHANNEL = (Map<String, Object>) map.get("CHANNEL");
                    /*员工*/
                    Map<String, Object> STAFF = (Map<String, Object>) map.get("STAFF");
                    /*员工渠道关系*/
                    List<Map<String, Object>> STAFF_CHANNEL_RELAS = (List<Map<String, Object>>) map.get("STAFF_CHANNEL_RELAS");

                    hand_CHANNEL(CHANNEL, rsMap);

                    hand_STAFF(STAFF, rsMap);

                    if(STAFF_CHANNEL_RELAS != null && STAFF_CHANNEL_RELAS.size() >0){
                        STAFF_CHANNEL_RELAS.forEach((temp)->{
                            hand_STAFF_CHANNEL_RELAS(temp,rsMap);
                        });
                    }

                    /*如果增加了从账号*/
                    if(slaveAcctId != null){
                        String commonRegionId = String.valueOf(STAFF.get("COMMON_REGION_ID"));
                        hand_STAFF2(slaveAcctId,commonRegionId,rsMap);
                    }
                }catch (Exception e){
                    logger.error("Exception:{}",e);
                    rsMap.put("result_code","1000");
                    rsMap.put("message", "处理时异常！");
                }

                //事务回滚
                if ("1000".equals(rsMap.get("result_code"))) {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                }
            }
        }catch (Exception e){
            logger.error("Exception:{},json:{}",e.toString(),json);
            rsMap.put("result_code","1000");
            rsMap.put("message", "处理不了的报文");
        }

        TbCpcLogWithBLOBs tbCpcLogWithBLOBs = new TbCpcLogWithBLOBs();
        tbCpcLogWithBLOBs.setReceive(json);
        tbCpcLogWithBLOBs.setRevert(JSON.toJSONString(rsMap));
        tbCpcLogWithBLOBs.setStatusCd(String.valueOf (rsMap.get("result_code")));
        tbCpcLogMapper.insert(tbCpcLogWithBLOBs);
        logger.info("rsMap:{}",rsMap);
        return JSON.toJSONString(rsMap);
    }

    private void hand_STAFF2(Long slaveAcctId, String commonRegionId,Map<String, Object> rsMap) {
        if ("1000".equals((String) (rsMap.get("result_code")))) {
            return;
        }
        TbSlaveAcct slaveAcct = tbSlaveAcctMapper.selectById(slaveAcctId);
        if(slaveAcct == null){
            rsMap.put("result_code","1000");
            logger.warn("从账号不存在！");
        }else{
            if(slaveAcct.getAcctOrgRelId() == null && slaveAcct.getAcctId() != null){
                //查询主账号与commonRegionId查询是否存在关系
                Long orgId =HandleChannelConstant.ORG_ID;
                /*Long orgId = tbAcctMapper.getOrgIdByCommonRegionId(commonRegionId);

                if(orgId == null){
                    orgId = HandleChannelConstant.ORG_ID;
                }*/

                AccountOrgRel accountOrgRel = new AccountOrgRel();
                accountOrgRel.setAcctId(slaveAcct.getAcctId());
                accountOrgRel.setStatusCd("1000");
                accountOrgRel.setOrgId(orgId);
                accountOrgRel.setOrgTreeId(HandleChannelConstant.ORG_TREE_ID);
                AccountOrgRel temp = accountOrgRelMapper.selectOne(accountOrgRel);

                if(temp == null){
                    //新增一个
                    accountOrgRel.setCreateDate(new Date());
                    accountOrgRel.setCreateUser(HandleChannelConstant.HANDLE_USER);
                    accountOrgRel.setUpdateDate(new Date());
                    accountOrgRel.setUpdateUser(HandleChannelConstant.HANDLE_USER);
                    accountOrgRel.setStatusDate(new Date());
                    accountOrgRel.setRelType("99");
                    accountOrgRelMapper.insert(accountOrgRel);
                }

                slaveAcct.setUpdateDate(new Date());
                slaveAcct.setAcctOrgRelId(accountOrgRel.getAcctOrgRelId());
                slaveAcct.setUpdateUser(HandleChannelConstant.HANDLE_USER);
                tbSlaveAcctMapper.updateById(slaveAcct);

            }
        }
    }

    /**
     * 渠道落入主数据组织，扩展表
     *
     * @param channel
     * @param rsMap
     */
    private void hand_CHANNEL(Map<String, Object> channel, Map<String, Object> rsMap) {
        if (null == channel || "1000".equals(String.valueOf(rsMap.get("result_code")))) {
            return;
        }
        String commoinRegionId = String.valueOf(channel.get("COMMON_REGION_ID"));
        String orgName = (String) channel.get("CHANNEL_NAME");
        String action = (String) channel.get("ACTION");
        String channelNbr = (String) channel.get("CHANNEL_NBR");
        String chnTypeCd = (String) channel.get("CHN_TYPE_CD");
        if (StringUtils.isEmpty(action) || StringUtils.isEmpty(channelNbr)) {
            rsMap.put("result_code", "1000");
            rsMap.put("message", "渠道必要信息为空");
            return;
        }

        try {
            switch (action) {
                case "ADD":
                    if (StringUtils.isEmpty(commoinRegionId) || StringUtils.isEmpty(orgName) || StringUtils.isEmpty(chnTypeCd)) {
                        rsMap.put("result_code", "1000");
                        rsMap.put("message", "渠道必要信息为空");
                        return;
                    }
                    addChannel(channel);
                    break;
                case "MOD":
                    if (StringUtils.isEmpty(commoinRegionId) || StringUtils.isEmpty(orgName) || StringUtils.isEmpty(chnTypeCd)) {
                        rsMap.put("result_code", "1000");
                        rsMap.put("message", "渠道必要信息为空");
                        return;
                    }
                    modChannel(channel);
                    break;
                case "DEL":
                    delChannel(channel);
                    break;
                default:{
                    rsMap.put("result_code", "1000");
                    rsMap.put("message", "错误的处理码！ACTION:"+ channel.get("ACTION"));
                }
            }
        } catch (Exception e) {
            rsMap.put("result_code", "1000");
            e.printStackTrace();
            rsMap.put("message", e.getStackTrace());
        }

    }

    /**
     * 渠道人员信息落入人员相关表
     *
     * @param staff
     * @param rsMap
     */
    private void hand_STAFF(Map<String, Object> staff, Map<String, Object> rsMap){

        if (null == staff || "1000".equals((String) (rsMap.get("result_code")))) {
            return;
        }
        String staffName = (String) staff.get("STAFF_NAME");
        String staffCode =(String) staff.get("STAFF_CODE");
        String idCard = (String) staff.get("CERT_NUMBER");
        String salesCode = (String) staff.get("SALES_CODE");
        if (StringUtils.isNotEmpty(staffCode)) {
            try {
                //ADD|MOD|DEL
                switch ((String) staff.get("ACTION")) {
                    case "ADD":
                    case "MOD": {
                        if (StringUtils.isNotEmpty(staffName) && StringUtils.isNotEmpty(idCard) && StringUtils.isNotEmpty(salesCode)) {
                            //根据CERT_TYPE 和 CERT_NUMBER 判断 该人是否已经存在
                            Long personnelId = tbCertMapper.checkExistCertTypeAndCertNumber(String.valueOf(staff.get("CERT_TYPE")), String.valueOf(staff.get("CERT_NUMBER")));
                            if (personnelId == null) {
                                //新增
                                // 插入TB_PERSONNEL表

                                //查询是否在人力存在
                                String ncCode = tbPersonnelMapper.checkExistNcCodeByIdCode(String.valueOf(staff.get("CERT_NUMBER")));

                                String psnCode = "";
                                if(ncCode != null && !"".equals(ncCode)){
                                    psnCode = ncCode.replaceAll("@ZJ","");
                                }else{
                                    psnCode = "H"+tbPersonnelMapper.getPsnCode();
                                }

                                TbPersonnel tbPersonnel = new TbPersonnel(staffName, psnCode, psnCode, idCard);
                                tbPersonnel.setNcCode(ncCode);
                                tbPersonnel.setCreateUser(1004040L);
                                tbPersonnel.setUpdateUser(1004040L);
                                tbPersonnelMapper.insertValueOfPersonnel(tbPersonnel);
                                // 插入TB_CERT
                                TbCert tbCert = new TbCert(tbPersonnel.getPersonnelId(), staffName,
                                        String.valueOf(staff.get("CERT_TYPE")), String.valueOf(staff.get("CERT_NUMBER")),
                                        UUID.randomUUID().toString().replaceAll("-","").toUpperCase(), "1", "1000",
                                        DateUtils.parseDate(DateUtils.getDateTime()));
                                tbCert.setCreateUser(1004040L);
                                tbCert.setUpdateUser(1004040L);
                                tbCertMapper.insert(tbCert);
                                // 插入TB_ACCT
                                TbAcct tbAcct = new TbAcct(String.valueOf(tbPersonnel.getPersonnelId()), psnCode, "1314",
                                        "0DB7DBB1F7EAF44CF5C077C9BC699A35", "1000",
                                        DateUtils.parseDate(DateUtils.getDateTime()), "2",
                                        DateUtils.parseDate("2019-01-01"), DateUtils.parseDate("2099-01-01"), "2");
                                tbAcct.setCreateUser(1004040L);
                                tbAcct.setUpdateUser(1004040L);
                                tbAcctMapper.insert(tbAcct);
                                // 插入TB_CONTACT
                                if (staff.get("MOBILE_PHONE") != null) {
                                    TbContact tbContact = new TbContact(tbPersonnel.getPersonnelId(), "1",
                                            String.valueOf(staff.get("MOBILE_PHONE")), UUID.randomUUID().toString().replaceAll("-","").toUpperCase(),
                                            "1000", DateUtils.parseDate(DateUtils.getDateTime()), Short.valueOf("1"));
                                    tbContact.setCreateUser(1004040L);
                                    tbContact.setUpdateUser(1004040L);
                                    tbContactMapper.insert(tbContact);
                                }
                                if (StringUtils.isNotEmpty((String) staff.get("E_MAIL"))) {
                                    TbContact tbContact = new TbContact(tbPersonnel.getPersonnelId(), "2",
                                            String.valueOf(staff.get("E_MAIL")), UUID.randomUUID().toString().replaceAll("-","").toUpperCase(),
                                            "1000", DateUtils.parseDate(DateUtils.getDateTime()), Short.valueOf("0"));
                                    tbContact.setCreateUser(1004040L);
                                    tbContact.setUpdateUser(1004040L);
                                    tbContactMapper.insert(tbContact);
                                }
                                //插入TB_ACCT_CROSS_REL
                                AcctCrossRel acctCrossRel = new AcctCrossRel(tbAcct.getAcctId(),
                                        String.valueOf(staff.get("SALES_CODE")), "100100102", "1000",
                                        DateUtils.parseDate(DateUtils.getDateTime()));
                                acctCrossRel.setCreateUser(1004040L);
                                acctCrossRel.setUpdateUser(1004040L);
                                acctCrossRelMapper.insert(acctCrossRel);
                                //插入TB_SLAVE_ACCT
                                if(StringUtils.isNotEmpty((String) staff.get("ACCOUNT"))){
                                    TbSlaveAcct tbSlaveAcct = new TbSlaveAcct(String.valueOf(staff.get("ACCOUNT")), "1314",
                                        "0DB7DBB1F7EAF44CF5C077C9BC699A35", "1", systemConstant.getCpcSystemId(),
                                        "1000", DateUtils.parseDate(DateUtils.getDateTime()), null,
                                        tbAcct.getAcctId(), DateUtils.parseDate("2019-01-01"), DateUtils.parseDate("2099-01-01"));
                                    tbSlaveAcct.setCreateUser(1004040L);
                                    tbSlaveAcct.setUpdateUser(1004040L);
                                    tbSlaveAcctMapper.insert(tbSlaveAcct);
                                    slaveAcctId = tbSlaveAcct.getSlaveAcctId();
                                    String msg = "{\"type\":\"person\",\"handle\":\"insert\",\"context\":{\"column\":\"slaveAcctId\",\"value\":"+tbSlaveAcct.getSlaveAcctId()+"}}";
                                    send(msg);
                                }
                            } else {
                                //修改人
                                TbPersonnel tbPersonnel = new TbPersonnel();
                                tbPersonnel.setPersonnelId(personnelId);
                                tbPersonnel.setStatusCd("1000");
                                //tbPersonnel.setPsnCode(psnCode);
                                //tbPersonnel.setPsnNbr(psnCode);
                                tbPersonnel.setUpdateDate(new Date());
                                tbPersonnel.setStatusDate(new Date());
                                tbPersonnel.setPsnName(staffName);
                                //tbPersonnel.setCreateUser(1004040L);
                                tbPersonnel.setUpdateUser(1004040L);
                                tbPersonnelMapper.updateById(tbPersonnel);
                                //修改 TB_CERT（不用修改）
                                //修改 TB_ACCT（有则修改，没有则需要生成）
                                //获取主账号
                                TbAcct tbAcct = tbAcctMapper.selectByPersonnelId(personnelId);
                                if (tbAcct == null) {
                                    // 插入TB_ACCT
                                    tbAcct = new TbAcct(String.valueOf(tbPersonnel.getPersonnelId()), tbPersonnel.getPsnCode(), "1314",
                                            "0DB7DBB1F7EAF44CF5C077C9BC699A35", "1000",
                                            DateUtils.parseDate(DateUtils.getDateTime()), "2",
                                            DateUtils.parseDate("2019-01-01"), DateUtils.parseDate("2099-01-01"), "2");
                                    //tbAcct.setCreateUser(1004040L);
                                    tbAcct.setUpdateUser(1004040L);
                                    tbAcctMapper.insert(tbAcct);
                                } else {
                                    tbAcct.setAcct(tbPersonnel.getPsnCode());
                                    //tbAcct.setCreateUser(1004040L);
                                    tbAcct.setUpdateUser(1004040L);
                                    tbAcctMapper.updateById(tbAcct);
                                }
                                //修改 TB_CONTACT 1.删除该人的所有的联系方式 2.插入新的联系方式
                                //tbContactMapper.deleteByPersonnelId(personnelId);
                                if (staff.get("MOBILE_PHONE") != null) {
                                    //判断手机号是不是已存在
                                   ;
                                    if(tbContactMapper.selectCount(new EntityWrapper<TbContact>()
                                            .eq("STATUS_CD","1000")
                                            .eq("CONTACT_TYPE","1")
                                            .eq("PERSONNEL_ID",personnelId)
                                            .eq("CONTENT", String.valueOf(staff.get("MOBILE_PHONE")))) ==0){
                                        TbContact tbContact = new TbContact(tbPersonnel.getPersonnelId(), "1",
                                             String.valueOf(staff.get("MOBILE_PHONE")), UUID.randomUUID().toString().replaceAll("-","").toUpperCase(),
                                             "1000", DateUtils.parseDate(DateUtils.getDateTime()), Short.valueOf("0"));
                                        //tbContact.setCreateUser(1004040L);
                                        tbContact.setUpdateUser(1004040L);
                                        tbContactMapper.insert(tbContact);
                                    }
                                }
                                if (StringUtils.isNotEmpty((String)(staff.get("E_MAIL")))) {
                                    if(tbContactMapper.selectCount(new EntityWrapper<TbContact>()
                                            .eq("STATUS_CD","1000")
                                            .eq("CONTACT_TYPE","2")
                                            .eq("PERSONNEL_ID",personnelId)
                                            .eq("CONTENT",staff.get("E_MAIL"))) ==0) {
                                        TbContact tbContact = new TbContact(tbPersonnel.getPersonnelId(), "2",
                                                String.valueOf(staff.get("E_MAIL")), UUID.randomUUID().toString().replaceAll("-", "").toUpperCase(),
                                                "1000", DateUtils.parseDate(DateUtils.getDateTime()), Short.valueOf("0"));
                                        //tbContact.setCreateUser(1004040L);
                                        tbContact.setUpdateUser(1004040L);
                                        tbContactMapper.insert(tbContact);
                                    }
                                }
                                //修改TB_ACCT_CROSS_REL1.删除该人的关系类型为'100100102'跨域账号 2.插入新的TB_ACCT_CROSS_REL1
                                acctCrossRelMapper.deleteByAcctIdAndRelaType(tbAcct.getAcctId(), "100100102");
                                AcctCrossRel acctCrossRel = new AcctCrossRel(tbAcct.getAcctId(),
                                        String.valueOf(staff.get("SALES_CODE")), "100100102", "1000",
                                        DateUtils.parseDate(DateUtils.getDateTime()));
                                //acctCrossRel.setCreateUser(1004040L);
                                acctCrossRel.setUpdateUser(1004040L);
                                acctCrossRelMapper.insert(acctCrossRel);
                                //插入TB_SLAVE_ACCT。判断该账号是否存在。
                                TbSlaveAcct tbSlaveAcct = tbSlaveAcctMapper.selectBySlaveAcctAndAcctId(String.valueOf(staff.get("ACCOUNT")), tbAcct.getAcctId(),systemConstant.getCpcSystemId());
                                if (staff.get("ACCOUNT") != null &&  tbSlaveAcct == null) {
                                     tbSlaveAcct = new TbSlaveAcct(String.valueOf(staff.get("ACCOUNT")), "1314",
                                            "0DB7DBB1F7EAF44CF5C077C9BC699A35", "1", systemConstant.getCpcSystemId(),
                                            "1000", DateUtils.parseDate(DateUtils.getDateTime()), null,
                                            tbAcct.getAcctId(), DateUtils.parseDate("2019-01-01"), DateUtils.parseDate("2099-01-01"));
                                    //tbSlaveAcct.setCreateUser(1004040L);
                                    tbSlaveAcct.setUpdateUser(1004040L);
                                    tbSlaveAcctMapper.insert(tbSlaveAcct);
                                    slaveAcctId = tbSlaveAcct.getSlaveAcctId();
                                    String msg = "{\"type\":\"person\",\"handle\":\"insert\",\"context\":{\"column\":\"slaveAcctId\",\"value\":"+tbSlaveAcct.getSlaveAcctId()+"}}";
                                    send(msg);
                                }else{
                                    String msg = "{\"type\":\"person\",\"handle\":\"update\",\"context\":{\"column\":\"slaveAcctId\",\"value\":"+tbSlaveAcct.getSlaveAcctId()+"}}";
                                    send(msg);
                                }
                            }
                        }else{
                            rsMap.put("result_code", "1000");
                            rsMap.put("message", "参数丢失。");
                        }
                    }
                    ;
                    break;
                    case "DEL": {
                        Long personnelId = acctCrossRelMapper.checkExistCrossRelTypeAndSalesCode("100100102",staffCode);
                        //Long personnelId = tbPersonnelMapper.checkExistPsnCode(psnCode);
                        if (personnelId == null) {
                            rsMap.put("result_code", "0");
                            //rsMap.put("message", "人员标识不存在。");
                            return;
                        } else {
                            TbAcct tbAcct = tbAcctMapper.selectByPersonnelId(personnelId);
                            if (tbAcct == null) {
                                rsMap.put("result_code", "1000");
                                rsMap.put("message", "主账号标识不存在。");
                                return;
                            }
                            List<TbSlaveAcct> slaveAcctList = tbSlaveAcctMapper.selectList(new EntityWrapper<TbSlaveAcct>()
                                    .eq("ACCT_ID",tbAcct.getAcctId())
                                    .eq("STATUS_CD","1000")
                                    .eq("SLAVE_ACCT_TYPE","1")
                                    .eq("RESOURCE_OBJ_ID",systemConstant.getCpcSystemId()));
                            
                            if(slaveAcctList != null){
                                for (TbSlaveAcct tbSlaveAcct : slaveAcctList) {
                                    String msg = "{\"type\":\"person\",\"handle\":\"delete\",\"context\":{\"column\":\"slaveAcctId\",\"value\":"+tbSlaveAcct.getSlaveAcctId()+"}}";
                                    send(msg);
                                }
                            }
                            tbSlaveAcctMapper.deleteByAcctId(tbAcct.getAcctId(),systemConstant.getCpcSystemId());
                        }
                    }
                    ;
                    break;
                    default:{
                        rsMap.put("result_code", "1000");
                        rsMap.put("message", "错误的处理码！ACTION:"+ staff.get("ACTION"));
                    }
                }


            } catch (NumberFormatException e) {
                e.printStackTrace();
                rsMap.put("result_code", "1000");
                rsMap.put("message", e.getStackTrace());
            }
        }else{
            rsMap.put("result_code", "1000");
            rsMap.put("message", "参数丢失。");
        }
    }

    /**
     * 渠道人员关系信息落入主账号组织关系
     *
     * @param staffChannelRel
     * @param rsMap
     */
    private void hand_STAFF_CHANNEL_RELAS(Map<String, Object> staffChannelRel, Map<String, Object> rsMap) {
        if (null == staffChannelRel || "1000".equals(rsMap.get("result_code"))) {
            return;
        }
        // 检查必要信息
        String salesCode = (String) staffChannelRel.get("SALES_CODE");
        String channelNBR = (String) staffChannelRel.get("CHANNEL_NBR");
        String relaType = (String) staffChannelRel.get("RELA_TYPE");
        String action = (String) staffChannelRel.get("ACTION");
        if (StringUtils.isEmpty(salesCode) || StringUtils.isEmpty(channelNBR) || StringUtils.isEmpty(action)) {
            rsMap.put("result_code", "1000");
            rsMap.put("message", "必要信息为空");
            return;
        }
        if (!"10".equals(relaType) && !"20".equals(relaType) && !"30".equals(relaType)) {
            rsMap.put("result_code", "1000");
            rsMap.put("message", "无效的人员关系类型RELA_TYPE");
            return;
        }
        try {
            switch (action) {
                case "ADD":
                    if (StringUtils.isEmpty(relaType)) {
                        rsMap.put("result_code", "1000");
                        rsMap.put("message", "必要信息关系类型为空");
                        return;
                    }
                    addStaffChannelRelas(staffChannelRel);
                    break;
                case "MOD":
                    if (StringUtils.isEmpty(relaType)) {
                        rsMap.put("result_code", "1000");
                        rsMap.put("message", "必要信息关系类型为空");
                        return;
                    }
                    modStaffChannelRelas(staffChannelRel);
                    break;
                case "DEL":
                    delStaffChannelRelas(staffChannelRel);
                    break;
                default:{
                    rsMap.put("result_code", "1000");
                    rsMap.put("message", "错误的处理码！ACTION:"+ staffChannelRel.get("ACTION"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            rsMap.put("result_code", "1000");
            rsMap.put("message", e.toString());
        }
    }

    /**
     * 新增组织
     */
    private void addChannel(Map<String, Object> channel) throws Exception {

        Expandovalue expandovalue = checkChannelNbr(channel);
        if (null != expandovalue) {
            return;
        }
        // 插入组织表
        TbOrg tbOrg = new TbOrg();
        String commoinRegionId = channel.get("COMMON_REGION_ID").toString();
        List<CommonRegion> commonRegions = commonRegionMapper.selectList(new EntityWrapper<CommonRegion>().
                eq("COMMON_REGION_ID", commoinRegionId.substring(0, 5) + "00")
                .eq("STATUS_CD", "1000"));
        if (commonRegions == null || commonRegions.size() == 0) {
            return;
        }
        tbOrg.setLocId(Long.valueOf(commoinRegionId));
        tbOrg.setAreaCodeId(commonRegions.get(0).getAreaCodeId());
        tbOrg.setOrgName(channel.get("CHANNEL_NAME").toString());
        tbOrgMapper.insertChannel(tbOrg);

        System.out.println("组织id--------" + tbOrg.getOrgId());

        // 插入扩展行
        Systemtable systemtable = new Systemtable();
        systemtable.setTableName(HandleChannelConstant.ORG_TABLE_NAME);
        systemtable.setStatusCd(HandleChannelConstant.VALID_STATE);
        Systemtable systemtableSelected = systemtableMapper.selectOne(systemtable);
        Expandorow expandorow = new Expandorow();
        expandorow.setTableId(systemtableSelected.getTableId());
        expandorow.setResourceId(HandleChannelConstant.RESOURCE_ID);
        expandorow.setRecordId(String.valueOf(tbOrg.getOrgId()));
        expandorow.setStatusCd(HandleChannelConstant.VALID_STATE);
        expandorow.setCreateDate(new Date());
        expandorow.setCreateUser(HandleChannelConstant.HANDLE_USER);
        expandorowMapper.insert(expandorow);


        Expandocolumn columnOfIsChannel = new Expandocolumn();
        columnOfIsChannel.setColumnName("IsChannel");
        columnOfIsChannel.setStatusCd(HandleChannelConstant.VALID_STATE);
        columnOfIsChannel = expandocolumnMapper.selectOne(columnOfIsChannel);
        Expandocolumn columnOfChannelNBR = new Expandocolumn();
        columnOfChannelNBR.setColumnName("channelNBR");
        columnOfChannelNBR.setStatusCd(HandleChannelConstant.VALID_STATE);
        columnOfChannelNBR = expandocolumnMapper.selectOne(columnOfChannelNBR);

        // 插入扩展值
        Expandovalue valueOfIsChannel = new Expandovalue();
        valueOfIsChannel.setResourceId(HandleChannelConstant.RESOURCE_ID);
        valueOfIsChannel.setTableId(systemtableSelected.getTableId());
        valueOfIsChannel.setColumnId(columnOfIsChannel.getColumnId());
        valueOfIsChannel.setRowId(expandorow.getRowId());
        valueOfIsChannel.setRecordId(String.valueOf(tbOrg.getOrgId()));
        valueOfIsChannel.setData("Y");
        valueOfIsChannel.setStatusCd(HandleChannelConstant.VALID_STATE);
        valueOfIsChannel.setCreateDate(new Date());
        valueOfIsChannel.setCreateUser(HandleChannelConstant.HANDLE_USER);

        Expandovalue valueOfChannelNBR = new Expandovalue();
        valueOfChannelNBR.setResourceId(HandleChannelConstant.RESOURCE_ID);
        valueOfChannelNBR.setTableId(systemtableSelected.getTableId());
        valueOfChannelNBR.setColumnId(columnOfChannelNBR.getColumnId());
        valueOfChannelNBR.setRowId(expandorow.getRowId());
        valueOfChannelNBR.setRecordId(String.valueOf(tbOrg.getOrgId()));
        valueOfChannelNBR.setData(channel.get("CHANNEL_NBR").toString());
        valueOfChannelNBR.setStatusCd(HandleChannelConstant.VALID_STATE);
        valueOfChannelNBR.setCreateDate(new Date());
        valueOfChannelNBR.setCreateUser(HandleChannelConstant.HANDLE_USER);
        expandovalueMapper.insert(valueOfIsChannel);
        expandovalueMapper.insert(valueOfChannelNBR);

        // 插入标准树

        // 插入组织类别
        String chnTypeCd = String.valueOf(channel.get("CHN_TYPE_CD"));
        TbOrgOrgtypeRel tbOrgOrgtypeRel = checkTbOrgOrgTypeRel(chnTypeCd, tbOrg.getOrgId());
        if (null == tbOrgOrgtypeRel) {
            return;
        }
        if (null == tbOrgOrgtypeRelMapper.selectOne(tbOrgOrgtypeRel)) {
            tbOrgOrgtypeRel.setStatusCd(HandleChannelConstant.VALID_STATE);
            tbOrgOrgtypeRel.setCreateDate(new Date());
            tbOrgOrgtypeRel.setCreateUser(HandleChannelConstant.HANDLE_USER);
            tbOrgOrgtypeRelMapper.insert(tbOrgOrgtypeRel);
        }

    }


    /**
     * 修改渠道对应信息
     *
     * @param channel
     */
    private void modChannel(Map<String, Object> channel) throws Exception {

        Expandovalue expandovalue = checkChannelNbr(channel);
        if (null == expandovalue) {
            addChannel(channel);
            return;
        }

        // 修改组织表
        TbOrg tbOrg = tbOrgMapper.selectById(expandovalue.getRecordId());
        tbOrg.setOrgName(String.valueOf(channel.get("CHANNEL_NAME")));
        tbOrg.setShortName(String.valueOf(channel.get("CHANNEL_NAME")));
        String commonRegionId = String.valueOf(channel.get("COMMON_REGION_ID"));
        List<CommonRegion> commonRegions = commonRegionMapper.selectList(new EntityWrapper<CommonRegion>().
                eq("COMMON_REGION_ID", commonRegionId.substring(0, 5) + "00")
                .eq("STATUS_CD", "1000"));
        if (commonRegions == null || commonRegions.size() == 0) {
            return;
        }
        tbOrg.setAreaCodeId(commonRegions.get(0).getAreaCodeId());
        tbOrg.setUpdateDate(new Date());
        tbOrg.setStatusDate(new Date());
        tbOrg.setUpdateUser(HandleChannelConstant.HANDLE_USER);
        tbOrgMapper.updateById(tbOrg);

        // 修改组织类别
        String chnTypeCd = String.valueOf(channel.get("CHN_TYPE_CD"));
        TbOrgOrgtypeRel tbOrgOrgtypeRel = checkTbOrgOrgTypeRel(chnTypeCd, tbOrg.getOrgId());
        if (null == tbOrgOrgtypeRel) {
            return;
        }
        tbOrgOrgtypeRel.setStatusCd(HandleChannelConstant.VALID_STATE);
        tbOrgOrgtypeRel = tbOrgOrgtypeRelMapper.selectOne(tbOrgOrgtypeRel);
        TbOrgOrgtypeRel rel = checkTbOrgOrgTypeRel(chnTypeCd, tbOrg.getOrgId());
        if (null == tbOrgOrgtypeRel) {
            rel.setStatusCd(HandleChannelConstant.VALID_STATE);
            rel.setCreateDate(new Date());
            rel.setCreateUser(HandleChannelConstant.HANDLE_USER);
            tbOrgOrgtypeRelMapper.insert(rel);
        } else {
            tbOrgOrgtypeRel.setOrgTypeId(rel.getOrgTypeId());
            tbOrgOrgtypeRel.setUpdateDate(new Date());
            tbOrgOrgtypeRel.setUpdateUser(HandleChannelConstant.HANDLE_USER);
            tbOrgOrgtypeRelMapper.updateById(tbOrgOrgtypeRel);
        }


    }

    /**
     * 删除组织
     *
     * @param channel
     * @throws Exception
     */
    private void delChannel(Map<String, Object> channel) throws Exception {
        Expandovalue expandovalue = checkChannelNbr(channel);
        if (null != expandovalue) {

            // 删除扩展行
            Expandorow expandorow = new Expandorow();
            expandorow.setResourceId(HandleChannelConstant.RESOURCE_ID);
            expandorow.setTableId(expandovalue.getTableId());
            expandorow.setRecordId(expandovalue.getRecordId());
            expandorow.setStatusCd(HandleChannelConstant.VALID_STATE);
            expandorow = expandorowMapper.selectOneExpandRow(expandorow);
            if (null != expandorow) {
                expandorow.setStatusCd(HandleChannelConstant.INVALID_STATE);
                expandorow.setUpdateDate(new Date());
                expandorow.setStatusDate(new Date());
                expandorow.setUpdateUser(HandleChannelConstant.HANDLE_USER);
                expandorowMapper.updateById(expandorow);
            }

            // 删除扩展值
            List<Expandovalue> expandovalues = expandovalueMapper.selectExpandovalueList(expandovalue);
            if (null != expandovalues && expandovalues.size() > 0) {
                for (Expandovalue value : expandovalues) {
                    value.setStatusCd(HandleChannelConstant.INVALID_STATE);
                    value.setUpdateDate(new Date());
                    value.setStatusDate(new Date());
                    value.setUpdateUser(HandleChannelConstant.HANDLE_USER);
                    expandovalueMapper.updateById(value);
                }
            }

            /*
            // 删除组织
            TbOrg tbOrg = tbOrgMapper.selectById(Long.valueOf(expandovalue.getRecordId()));
            if (null != tbOrg) {
                tbOrg.setStatusCd(HandleChannelConstant.INVALID_STATE);
                tbOrg.setUpdateDate(new Date());
                tbOrg.setStatusDate(new Date());
                tbOrgMapper.updateById(tbOrg);
            }

            // 删除组织类别
            List<TbOrgOrgtypeRel> orgOrgtypeRels = tbOrgOrgtypeRelMapper.selectList(new EntityWrapper<TbOrgOrgtypeRel>().
                    eq("ORG_ID",tbOrg.getOrgId()).
                    eq("STATUS_CD",HandleChannelConstant.VALID_STATE));
            if (null != orgOrgtypeRels && orgOrgtypeRels.size() > 0) {
                for (TbOrgOrgtypeRel orgOrgtypeRel : orgOrgtypeRels) {
                    orgOrgtypeRel.setStatusCd(HandleChannelConstant.INVALID_STATE);
                    orgOrgtypeRel.setUpdateDate(new Date());
                    orgOrgtypeRel.setStatusDate(new Date());
                    tbOrgOrgtypeRelMapper.updateById(orgOrgtypeRel);
                }
            }
            */

        }

    }

    /**
     * 渠道操作前，检查渠道是否存在
     *
     * @param channel
     * @return expandovalue.get(recordId)
     */
    private Expandovalue checkChannelNbr(Map<String, Object> channel) {
        Systemtable systemtable = new Systemtable();
        systemtable.setStatusCd(HandleChannelConstant.VALID_STATE);
        systemtable.setTableName(HandleChannelConstant.ORG_TABLE_NAME);
        systemtable = systemtableMapper.selectOne(systemtable);
        Expandovalue expandovalue = new Expandovalue();
        expandovalue.setResourceId(HandleChannelConstant.RESOURCE_ID);
        expandovalue.setTableId(systemtable.getTableId());
        expandovalue.setData(String.valueOf(channel.get("CHANNEL_NBR")));
        expandovalue.setStatusCd(HandleChannelConstant.VALID_STATE);
        expandovalue = expandovalueMapper.selectValueByData(expandovalue);
        return expandovalue;
    }

    /**
     * 新增渠道人员关系
     *
     * @param staffChannelRel
     */
    private void addStaffChannelRelas(Map<String, Object> staffChannelRel) throws Exception {
        // 检查主账号组织关系
        AccountOrgRel accountOrgRel = checkAccoutOrgRel(staffChannelRel);
        AccountOrgRel rel = accountOrgRelMapper.selectOne(accountOrgRel);
        if (null == rel) {
            accountOrgRel.setCreateDate(new Date());
            accountOrgRel.setCreateUser(HandleChannelConstant.HANDLE_USER);
            accountOrgRelMapper.insert(accountOrgRel);
            // 更新从账号
            TbSlaveAcct tbSlaveAcct = new TbSlaveAcct();
            tbSlaveAcct.setResourceObjId(Long.valueOf(systemConstant.getCpcSystemId()));
            tbSlaveAcct.setAcctId(accountOrgRel.getAcctId());
            tbSlaveAcct.setStatusCd(HandleChannelConstant.VALID_STATE);
            tbSlaveAcct = tbSlaveAcctMapper.selectOne(tbSlaveAcct);
            if (null != tbSlaveAcct) {
                tbSlaveAcct.setAcctOrgRelId(accountOrgRel.getAcctOrgRelId());
                tbSlaveAcct.setUpdateDate(new Date());
                tbSlaveAcct.setStatusDate(new Date());
                tbSlaveAcct.setUpdateUser(HandleChannelConstant.HANDLE_USER);
                tbSlaveAcctMapper.updateById(tbSlaveAcct);
                String msg = "{\"type\":\"person\",\"handle\":\"insert\",\"context\":{\"column\":\"slaveAcctId\",\"value\":"+tbSlaveAcct.getSlaveAcctId()+"}}";
                send(msg);
            }
        }
    }

    /**
     * 修改渠道人员关系
     *
     * @param staffChannelRel
     */
    private void modStaffChannelRelas(Map<String, Object> staffChannelRel) throws Exception {

        delStaffChannelRelas(staffChannelRel);

        addStaffChannelRelas(staffChannelRel);
    }

    /**
     * 删除渠道人员关系
     *
     * @param staffChannelRel
     */
    private void delStaffChannelRelas(Map<String, Object> staffChannelRel) throws Exception {
        String salesCode = (String) staffChannelRel.get("SALES_CODE");
        AcctCrossRel acctCrossRel = new AcctCrossRel();
        acctCrossRel.setCrossTran(salesCode);
        acctCrossRel.setStatusCd(HandleChannelConstant.VALID_STATE);
        acctCrossRel = acctCrossRelMapper.selectOne(acctCrossRel);
        //CPC从账号 与销售员编码一一对应
        if (null != acctCrossRel) {
            TbSlaveAcct tbSlaveAcct = new TbSlaveAcct();
            tbSlaveAcct.setStatusCd(HandleChannelConstant.VALID_STATE);
            tbSlaveAcct.setAcctId(acctCrossRel.getAcctId());
            tbSlaveAcct = tbSlaveAcctMapper.selectOne(tbSlaveAcct);
            if (null != tbSlaveAcct) {
                Long acctOrgRelId = tbSlaveAcct.getAcctOrgRelId();
                if (null != acctOrgRelId) {
                    AccountOrgRel accountOrgRel = new AccountOrgRel();
                    accountOrgRel.setAcctOrgRelId(acctOrgRelId);
                    accountOrgRel.setStatusCd(HandleChannelConstant.INVALID_STATE);
                    accountOrgRelMapper.updateById(accountOrgRel);
                }

            }
        }

    }

    /**
     * 检查主账号组织关系
     *
     * @param staffChannelRel
     * @return
     */
    private AccountOrgRel checkAccoutOrgRel(Map<String, Object> staffChannelRel) throws Exception {
        Expandovalue expandovalue = checkChannelNbr(staffChannelRel);
        if (null == expandovalue || StringUtils.isEmpty(expandovalue.getRecordId())) {
            throw new Exception("组织不存在");
        }

        // 获取主账号
        List<AcctCrossRel> acctCrossRels = acctCrossRelMapper.selectList(new EntityWrapper<AcctCrossRel>().
                eq("CROSS_TRAN",staffChannelRel.get("SALES_CODE").toString())
                .eq("STATUS_CD",HandleChannelConstant.VALID_STATE));
        if (null == acctCrossRels || acctCrossRels.size() > 1) {
            throw new Exception("主账号信息不正确");
        }
        AccountOrgRel accountOrgRel = new AccountOrgRel();
        accountOrgRel.setAcctId(acctCrossRels.get(0).getAcctId());
        accountOrgRel.setOrgId(Long.valueOf(expandovalue.getRecordId()));
        accountOrgRel.setStatusCd(HandleChannelConstant.VALID_STATE);
        accountOrgRel.setOrgTreeId(HandleChannelConstant.ORG_TREE_ID);
        accountOrgRel.setRelType(staffChannelRel.get("RELA_TYPE").toString());
        return accountOrgRel;
    }

    private TbOrgOrgtypeRel checkTbOrgOrgTypeRel(String chnTypeCd, Long orgId) {
        if (StringUtils.isEmpty(chnTypeCd)) {
            return null;
        }
        String orgTypeCode = HandleChannelConstant.ORG_TYPE_REL.get(chnTypeCd).toString();
        OrgType orgType = new OrgType();
        orgType.setOrgTypeCode(orgTypeCode);
        orgType.setStatusCd(HandleChannelConstant.VALID_STATE);
        orgType = orgTypeMapper.selectOne(orgType);

        TbOrgOrgtypeRel tbOrgOrgtypeRel = new TbOrgOrgtypeRel();
        tbOrgOrgtypeRel.setOrgId(orgId);
        tbOrgOrgtypeRel.setOrgTypeId(orgType.getOrgTypeId());
        return tbOrgOrgtypeRel;
    }

    private void send(String msg){
        template.convertAndSend("message_sharing_center_queue",msg);
    }
}