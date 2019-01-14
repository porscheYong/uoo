package cn.ffcs.interfaces.cpc.service.impl;

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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.*;

@Service
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class CpcChannelServiceImpl implements CpcChannelService {

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

    @Override
    public String handle(String json) throws Exception {

        Map<String, Object> map = Json2MapUtil.handle(json);
        //1000是失败，0成功
        String result_code = "0";
        //结果描述
        StringBuffer result_msg = new StringBuffer();
        //流水号
        String TransactionID = "";
        //结果集
        Map<String, Object> rsMap = new HashMap<>();
        rsMap.put("result_code",result_code);
        if (map == null) {
            result_msg.append("json is null.");
        } else {
            TransactionID = (String) map.get("TransactionID") == null ? "" : (String) map.get("TransactionID");
            rsMap.put("TransactionID",TransactionID);

            /*渠道*/
            Map<String, Object> CHANNEL = (Map<String, Object>) map.get("CHANNEL");
            /*员工*/
            Map<String, Object> STAFF = (Map<String, Object>) map.get("STAFF");
            /*员工渠道关系*/
            Map<String, Object> STAFF_CHANNEL_RELAS = (Map<String, Object>) map.get("STAFF_CHANNEL_RELAS");

            hand_CHANNEL(CHANNEL, rsMap);
            hand_STAFF(STAFF, rsMap);
            //hand_STAFF_CHANNEL_RELAS(STAFF_CHANNEL_RELAS, rsMap);

            //事务回滚
            if ("1000".equals(rsMap.get("result_code"))) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
        }
        return JSON.toJSONString(rsMap);
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
        String orgName = String.valueOf(channel.get("CHANNEL_NAME"));
        String action = String.valueOf(channel.get("ACTION"));
        String channelNbr = String.valueOf(channel.get("CHANNEL_NBR"));
        if (StringUtils.isEmpty(commoinRegionId) || StringUtils.isEmpty(orgName) || StringUtils.isEmpty(action) || StringUtils.isEmpty(channelNbr)) {
            rsMap.put("result_code", "1000");
            rsMap.put("message", "必要信息为空");
            return;
        }
        try {
            switch (action) {
                case "ADD":
                    addChannel(channel);
                case "MOD":
                    modChannel(channel);
                case "DEL":
                    delChannel(channel);
            }
        } catch (Exception e) {
            rsMap.put("result_code", "1000");
            e.printStackTrace();
            rsMap.put("message", e.getStackTrace());
//                throw new Exception("执行失败，请查看日志");
        }

    }

    /**
     * 渠道人员信息落入人员相关表
     *
     * @param staff
     * @param rsMap
     */
    private void hand_STAFF(Map<String, Object> staff, Map<String, Object> rsMap) {

        if (null == staff || "1000".equals(String.valueOf(rsMap.get("result_code")))) {
            return;
        }
        String staffName = String.valueOf(staff.get("STAFF_NAME"));
        String psnCode = String.valueOf(staff.get("STAFF_CODE"));
        String idCard = String.valueOf(staff.get("CERT_NUMBER"));
        if (StringUtils.isNotEmpty(staffName) && StringUtils.isNotEmpty(psnCode) && StringUtils.isNotEmpty(idCard)) {
            try {
                //ADD|MOD|DEL
                switch((String)rsMap.get("ACTION")){
                    case "ADD" :
                    case "MOD" :{
                        //根据CERT_TYPE 和 CERT_NUMBER 判断 该人是否已经存在
                        Long personnelId = tbCertMapper.checkExistCertTypeAndCertNumber(String.valueOf(staff.get("CERT_TYPE")), String.valueOf(staff.get("CERT_NUMBER")));
                        if(personnelId == null){
                            //新增
                            // 插入TB_PERSONNEL表
                            String uuid = UUID.randomUUID().toString();
                            TbPersonnel tbPersonnel = new TbPersonnel(staffName, psnCode, psnCode, idCard);
                            tbPersonnelMapper.insertValueOfPersonnel(tbPersonnel);
                            // 插入TB_CERT
                            TbCert tbCert = new TbCert(tbPersonnel.getPersonnelId(), staffName,
                                    String.valueOf(staff.get("CERT_TYPE")), String.valueOf(staff.get("CERT_NUMBER")),
                                    UUID.randomUUID().toString(), "1", "1000",
                                    DateUtils.parseDate(DateUtils.getDateTime()));
                            tbCertMapper.insert(tbCert);
                            // 插入TB_ACCT
                            TbAcct tbAcct = new TbAcct(String.valueOf(tbPersonnel.getPersonnelId()), psnCode, "1314",
                                    "0DB7DBB1F7EAF44CF5C077C9BC699A35", "1000",
                                    DateUtils.parseDate(DateUtils.getDateTime()), "2",
                                    DateUtils.parseDate("20190101"), DateUtils.parseDate("20990101"), "2");
                            tbAcctMapper.insert(tbAcct);
                            // 插入TB_CONTACT
                            if (StringUtils.isNotEmpty(String.valueOf(staff.get("MOBILE_PHONE")))) {
                                TbContact tbContact = new TbContact(tbPersonnel.getPersonnelId(), "1",
                                        String.valueOf(staff.get("MOBILE_PHONE")), UUID.randomUUID().toString(),
                                        "1000", DateUtils.parseDate(DateUtils.getDateTime()), Short.valueOf("1"));
                                tbContactMapper.insert(tbContact);
                            }
                            if (StringUtils.isNotEmpty(String.valueOf(staff.get("E_MAIL")))) {
                                TbContact tbContact = new TbContact(tbPersonnel.getPersonnelId(), "2",
                                        String.valueOf(staff.get("E_MAIL")), UUID.randomUUID().toString(),
                                        "1000", DateUtils.parseDate(DateUtils.getDateTime()), Short.valueOf("0"));
                                tbContactMapper.insert(tbContact);
                            }
                            //插入TB_ACCT_CROSS_REL
                            AcctCrossRel acctCrossRel = new AcctCrossRel(tbAcct.getAcctId(),
                                    String.valueOf(staff.get("SALES_CODE")), "100100102", "1000",
                                    DateUtils.parseDate(DateUtils.getDateTime()));
                            acctCrossRelMapper.insert(acctCrossRel);
                            //插入TB_SLAVE_ACCT
                            TbSlaveAcct tbSlaveAcct = new TbSlaveAcct(String.valueOf(staff.get("ACCOUNT")), "1314",
                                    "0DB7DBB1F7EAF44CF5C077C9BC699A35", "1", SystemConstant.CPC_SYSTEM_ID,
                                    "1000", DateUtils.parseDate(DateUtils.getDateTime()), null,
                                    tbAcct.getAcctId(), DateUtils.parseDate("20190101"), DateUtils.parseDate("20990101"));
                            tbSlaveAcctMapper.insert(tbSlaveAcct);
                        }else {
                            //修改人
                            TbPersonnel tbPersonnel = new TbPersonnel();
                            tbPersonnel.setPersonnelId(personnelId);
                            tbPersonnel.setStatusCd("1000");
                            tbPersonnel.setUpdateDate(new Date());
                            tbPersonnel.setStatusDate(new Date());
                            tbPersonnel.setPsnName(staffName);
                            tbPersonnelMapper.updateById(tbPersonnel);
                            //修改 TB_CERT（不用修改）
                            //修改 TB_ACCT（有则不用修改，没有则需要生成）
                            //获取主账号
                            TbAcct tbAcct = tbAcctMapper.selectByPersonnelId(personnelId);
                            if(tbAcct == null){
                                // 插入TB_ACCT
                                 tbAcct = new TbAcct(String.valueOf(tbPersonnel.getPersonnelId()), psnCode, "1314",
                                        "0DB7DBB1F7EAF44CF5C077C9BC699A35", "1000",
                                        DateUtils.parseDate(DateUtils.getDateTime()), "2",
                                        DateUtils.parseDate("20190101"), DateUtils.parseDate("20990101"), "2");
                                tbAcctMapper.insert(tbAcct);
                            }
                            //修改 TB_CONTACT 1.删除该人的所有的联系方式 2.插入新的联系方式
                            tbContactMapper.deleteByPersonnelId(personnelId);
                            if (StringUtils.isNotEmpty(String.valueOf(staff.get("MOBILE_PHONE")))) {
                                TbContact tbContact = new TbContact(tbPersonnel.getPersonnelId(), "1",
                                        String.valueOf(staff.get("MOBILE_PHONE")), UUID.randomUUID().toString(),
                                        "1000", DateUtils.parseDate(DateUtils.getDateTime()), Short.valueOf("1"));
                                tbContactMapper.insert(tbContact);
                            }
                            if (StringUtils.isNotEmpty(String.valueOf(staff.get("E_MAIL")))) {
                                TbContact tbContact = new TbContact(tbPersonnel.getPersonnelId(), "2",
                                        String.valueOf(staff.get("E_MAIL")), UUID.randomUUID().toString(),
                                        "1000", DateUtils.parseDate(DateUtils.getDateTime()), Short.valueOf("0"));
                                tbContactMapper.insert(tbContact);
                            }
                            //修改TB_ACCT_CROSS_REL1.删除该人的关系类型为'100100102'跨域账号 2.插入新的TB_ACCT_CROSS_REL1
                            acctCrossRelMapper.deleteByAcctIdAndRelaType(tbAcct.getAcctId(),"100100102");
                            AcctCrossRel acctCrossRel = new AcctCrossRel(tbAcct.getAcctId(),
                                    String.valueOf(staff.get("SALES_CODE")), "100100102", "1000",
                                    DateUtils.parseDate(DateUtils.getDateTime()));
                            acctCrossRelMapper.insert(acctCrossRel);
                            //插入TB_SLAVE_ACCT。判断该账号是否存在。
                            if(tbSlaveAcctMapper.selectBySlaveAcctAndAcctId(String.valueOf(staff.get("ACCOUNT")),tbAcct.getAcctId()) < 1){
                                TbSlaveAcct tbSlaveAcct = new TbSlaveAcct(String.valueOf(staff.get("ACCOUNT")), "1314",
                                        "0DB7DBB1F7EAF44CF5C077C9BC699A35", "1", SystemConstant.CPC_SYSTEM_ID,
                                        "1000", DateUtils.parseDate(DateUtils.getDateTime()), null,
                                        tbAcct.getAcctId(), DateUtils.parseDate("20190101"), DateUtils.parseDate("20990101"));
                                tbSlaveAcctMapper.insert(tbSlaveAcct);
                            }
                        }
                    };break;
                    case "DEL" :{
                        Long personnelId = acctCrossRelMapper.checkExistCrossRelTypeAndSalesCode("100100102", String.valueOf(staff.get("SALES_CODE")));
                        if(personnelId == null){
                            rsMap.put("result_code","1000");
                            rsMap.put("message","人员标识不存在。");
                            return;
                        }else{
                            //修改人
                            TbPersonnel tbPersonnel = new TbPersonnel();
                            tbPersonnel.setPersonnelId(personnelId);
                            tbPersonnel.setStatusCd("1100");
                            tbPersonnel.setUpdateDate(new Date());
                            tbPersonnel.setStatusDate(new Date());
                            tbPersonnel.setPsnName(staffName);
                            tbPersonnelMapper.updateById(tbPersonnel);
                            //修改 TB_CERT
                            tbCertMapper.deleteByPersonnelId(personnelId);
                            //修改TB_ACCT
                            TbAcct tbAcct = tbAcctMapper.selectByPersonnelId(personnelId);
                            if(tbAcct == null){
                                rsMap.put("result_code","1000");
                                rsMap.put("message","主账号标识不存在。");
                                return ;
                            }
                            tbAcct.setStatusCd("1100");
                            tbAcctMapper.updateById(tbAcct);
                            //删除TB_ACCT_CROSS_REL
                            acctCrossRelMapper.deleteByAcctIdAndRelaType(tbAcct.getAcctId(),"100100102");
                            //删除联系方式
                            tbContactMapper.deleteByPersonnelId(personnelId);
                            //删除从账号
                            tbSlaveAcctMapper.deleteByAcctId(tbAcct.getAcctId());
                        }
                    };break;
                }


            } catch (NumberFormatException e) {
                e.printStackTrace();
                rsMap.put("result_code","1000");
                rsMap.put("message",e.getStackTrace());
            }
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
        String salesCode = String.valueOf(staffChannelRel.get("SALES_CODE"));
        String channelNBR = String.valueOf(staffChannelRel.get("CHANNEL_NBR"));
        String action = String.valueOf(staffChannelRel.get("ACTION"));
        if (StringUtils.isEmpty(salesCode) || StringUtils.isEmpty(channelNBR) || StringUtils.isEmpty(action)) {
            rsMap.put("result_code", "1000");
            rsMap.put("message", "必要信息为空");
            return;
        }
        try {
            switch (action) {
                case "ADD":
                    addStaffChannelRelas(staffChannelRel);
                case "MOD":
                    modStaffChannelRelas(staffChannelRel);
                case "DEL":
                    delStaffChannelRelas(staffChannelRel);
            }
        } catch (Exception e) {
            e.printStackTrace();
            rsMap.put("result_code", "1000");
            rsMap.put("message", e.getStackTrace());
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
        tbOrg.setAreaCodeId(commonRegions.get(0).getAreaCodeId());
        tbOrg.setOrgName(channel.get("CHANNEL_NAME").toString());
        tbOrgMapper.insertChannel(tbOrg);

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
        valueOfIsChannel.setTableId(systemtable.getTableId());
        valueOfIsChannel.setColumnId(columnOfIsChannel.getColumnId());
        valueOfIsChannel.setRowId(expandorow.getRowId());
        valueOfIsChannel.setRecordId(String.valueOf(tbOrg.getOrgId()));
        valueOfIsChannel.setData("Y");
        valueOfIsChannel.setStatusCd(HandleChannelConstant.VALID_STATE);
        valueOfIsChannel.setCreateDate(new Date());

        Expandovalue valueOfChannelNBR = new Expandovalue();
        valueOfChannelNBR.setResourceId(HandleChannelConstant.RESOURCE_ID);
        valueOfChannelNBR.setTableId(systemtable.getTableId());
        valueOfChannelNBR.setColumnId(columnOfChannelNBR.getColumnId());
        valueOfChannelNBR.setRowId(expandorow.getRowId());
        valueOfChannelNBR.setRecordId(String.valueOf(tbOrg.getOrgId()));
        valueOfChannelNBR.setData(channel.get("CHANNEL_NBR").toString());
        valueOfChannelNBR.setStatusCd(HandleChannelConstant.VALID_STATE);
        valueOfChannelNBR.setCreateDate(new Date());
        expandovalueMapper.insert(valueOfIsChannel);
        expandovalueMapper.insert(valueOfChannelNBR);

        // 插入组织类别
        String chnTypeCd = String.valueOf(channel.get("CHN_TYPE_CD"));
        TbOrgOrgtypeRel tbOrgOrgtypeRel = checkTbOrgOrgTypeRel(chnTypeCd, tbOrg.getOrgId());
        if (null == tbOrgOrgtypeRel) {
            return;
        }
        tbOrgOrgtypeRel.setStatusCd(HandleChannelConstant.VALID_STATE);
        tbOrgOrgtypeRel.setCreateDate(new Date());
        tbOrgOrgtypeRelMapper.insert(tbOrgOrgtypeRel);

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
        tbOrg.setAreaCodeId(commonRegions.get(0).getAreaCodeId());
        tbOrg.setUpdateDate(new Date());
        tbOrg.setStatusDate(new Date());
        tbOrgMapper.updateById(tbOrg);

        // 修改组织类别
        String chnTypeCd = String.valueOf(channel.get("CHN_TYPE_CD"));
        TbOrgOrgtypeRel tbOrgOrgtypeRel = checkTbOrgOrgTypeRel(chnTypeCd, tbOrg.getOrgId());
        if (null == tbOrgOrgtypeRel) {
            return;
        }
        tbOrgOrgtypeRel.setStatusCd(HandleChannelConstant.VALID_STATE);
        tbOrgOrgtypeRel = tbOrgOrgtypeRelMapper.selectOne(tbOrgOrgtypeRel);
        if (null == tbOrgOrgtypeRel) {
            TbOrgOrgtypeRel rel = checkTbOrgOrgTypeRel(chnTypeCd, tbOrg.getOrgId());
            rel.setStatusCd(HandleChannelConstant.VALID_STATE);
            rel.setCreateDate(new Date());
            tbOrgOrgtypeRelMapper.insert(rel);
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
            expandorow = expandorowMapper.selectOne(expandorow);
            if (null != expandorow) {
                expandorow.setStatusCd(HandleChannelConstant.INVALID_STATE);
                expandorowMapper.updateById(expandorow);
            }

            // 删除扩展值
            expandovalue.setStatusCd(HandleChannelConstant.INVALID_STATE);
            expandovalueMapper.updateById(expandovalue);

            // 删除组织
            TbOrg tbOrg = tbOrgMapper.selectById(Long.valueOf(expandovalue.getRecordId()));
            tbOrg.setStatusCd(HandleChannelConstant.INVALID_STATE);
            tbOrgMapper.updateById(tbOrg);

            // 删除组织类别
            String chnTypeCd = String.valueOf(channel.get("CHN_TYPE_CD"));
            TbOrgOrgtypeRel tbOrgOrgtypeRel = checkTbOrgOrgTypeRel(chnTypeCd, tbOrg.getOrgId());
            if (null == tbOrgOrgtypeRel) {
                return;
            }
            tbOrgOrgtypeRel.setStatusCd(HandleChannelConstant.VALID_STATE);
            tbOrgOrgtypeRel = tbOrgOrgtypeRelMapper.selectOne(tbOrgOrgtypeRel);
            if (null != tbOrgOrgtypeRel) {
                tbOrgOrgtypeRel.setStatusCd(HandleChannelConstant.INVALID_STATE);
                tbOrgOrgtypeRelMapper.updateById(tbOrgOrgtypeRel);
            }

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
        expandovalue = expandovalueMapper.selectOne(expandovalue);
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
        if (null != rel) {
            throw new RuntimeException("主账号组织关系已存在");
        }
        accountOrgRel.setCreateDate(new Date());
        accountOrgRelMapper.insert(accountOrgRel);

        // 更新从账号
        TbSlaveAcct tbSlaveAcct = new TbSlaveAcct();
        tbSlaveAcct.setResourceObjId(Long.valueOf(HandleChannelConstant.RESOURCE_ID));
        tbSlaveAcct.setAcctId(accountOrgRel.getAcctId());
        tbSlaveAcct.setStatusCd(HandleChannelConstant.VALID_STATE);
        tbSlaveAcct = tbSlaveAcctMapper.selectOne(tbSlaveAcct);
        tbSlaveAcct.setAcctOrgRelId(accountOrgRel.getAcctOrgRelId());
        tbSlaveAcctMapper.updateById(tbSlaveAcct);
    }

    /**
     * 修改渠道人员关系
     *
     * @param staffChannelRel
     */
    private void modStaffChannelRelas(Map<String, Object> staffChannelRel) throws Exception {
        //
        AccountOrgRel accountOrgRel = checkAccoutOrgRel(staffChannelRel);

        // 删除原来的关系，新增现有关系
        if (null != accountOrgRel) {
            accountOrgRel.setStatusCd(HandleChannelConstant.INVALID_STATE);
            accountOrgRel.setUpdateDate(new Date());
            accountOrgRel.setStatusDate(new Date());
            accountOrgRelMapper.updateById(accountOrgRel);
        }

        addStaffChannelRelas(staffChannelRel);
    }

    /**
     * 删除渠道人员关系
     *
     * @param staffChannelRel
     */
    private void delStaffChannelRelas(Map<String, Object> staffChannelRel) {
        AccountOrgRel accountOrgRel = checkAccoutOrgRel(staffChannelRel);
        if (null == accountOrgRel) {
            throw new RuntimeException("主账号组织关系不存在");
        }
        accountOrgRel.setStatusCd(HandleChannelConstant.INVALID_STATE);
        accountOrgRel.setUpdateDate(new Date());
        accountOrgRel.setStatusDate(new Date());
        accountOrgRelMapper.updateById(accountOrgRel);

    }

    /**
     * 检查主账号组织关系
     *
     * @param staffChannelRel
     * @return
     */
    private AccountOrgRel checkAccoutOrgRel(Map<String, Object> staffChannelRel) {
        Expandovalue expandovalue = checkChannelNbr(staffChannelRel);
        if (null == expandovalue || StringUtils.isEmpty(expandovalue.getRecordId())) {
            throw new RuntimeException("组织不存在");
        }

        // 获取主账号
        AcctCrossRel acctCrossRel = new AcctCrossRel();
        acctCrossRel.setCrossTran(staffChannelRel.get("SALES_CODE").toString());
        acctCrossRel.setRelaType(HandleChannelConstant.RELA_TYPE);
        acctCrossRel.setStatusCd(HandleChannelConstant.VALID_STATE);
        acctCrossRel = acctCrossRelMapper.selectOne(acctCrossRel);
        if (null == acctCrossRel || StringUtils.isEmpty(String.valueOf(acctCrossRel.getAcctId()))) {
            throw new RuntimeException("主账号不存在");
        }

        AccountOrgRel accountOrgRel = new AccountOrgRel();
        accountOrgRel.setAcctId(acctCrossRel.getAcctId());
        accountOrgRel.setOrgId(Long.valueOf(expandovalue.getRecordId()));
        accountOrgRel.setStatusCd(HandleChannelConstant.VALID_STATE);
        accountOrgRel.setOrgTreeId(HandleChannelConstant.ORG_TREE_ID);
        accountOrgRel.setRelType(HandleChannelConstant.REL_TYPE);
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

    public static void main(String[] args) {
        String json = "{\"TransactionID\":\"1000000045201901078888457736\",\"CHANNEL\":{\"CHANNEL_NBR\":" +
                "\"3301063199917\",\"CHANNEL_NAME\":\"西湖梦途丰谭路专柜\",\"CHANNEL_CLASS\":\"10\"," +
                "\"CHN_TYPE_CD\":\"100202\",\"CHANNEL_TYPE_CD\":\"100000\",\"COMMON_REGION_ID\":\"8330106\"," +
                "\"STATUS_CD\":\"1000\",\"STATUS_DATE\":\"20190109151622\",\"DESCRIPTION\":null,\"ACTION\":" +
                "\"MOD\"},\"OPERATORS\":{\"OPERATORS_NBR\":\"J33010757714\",\"OPERATORS_NAME\":\"余婷\"," +
                "\"OPERATORS_TYPE_CD\":\"20\",\"OPERATORS_AREA_GRADE\":\"1000\",\"PARENT_OPER_NBR\":null," +
                "\"COMMON_REGION_ID\":\"8330106\",\"STATUS_CD\":\"1000\",\"STATUS_DATE\":null,\"DESCRIPTION\":null," +
                "\"ACTION\":\"MOD\"},\"STAFF\":{\"SALES_CODE\":\"Y99999999\",\"STAFF_CODE\":\"Y99999999\",\"ACCOUNT\":" +
                "\"999999\",\"STAFF_NAME\":\"测试yxg123\",\"CERT_TYPE\":\"1\",\"CERT_NUMBER\":\"330702197911041225\"," +
                "\"MOBILE_PHONE\":\"15372128883\",\"E_MAIL\":null,\"COMMON_REGION_ID\":\"8330106\",\"STAFF_DESC\":null," +
                "\"STATUS_CD\":\"1000\",\"STATUS_DATE\":null,\"CREATE_DATE\":null,\"ACTION\":\"MOD\"}," +
                "\"CHANNEL_OPERATORS_RELAS\":{\"CHANNEL_NBR\":\"3301063199917\",\"OPERATORS_NBR\":\"J33010757714\"," +
                "\"RELA_TYPE\":\"10\",\"DESCRIPTION\":null,\"ACTION\":\"MOD\"},\"STAFF_CHANNEL_RELAS\":{\"SALES_CODE\":" +
                "\"Y99999999\",\"CHANNEL_NBR\":\"3301063199917\",\"RELA_TYPE\":\"10\",\"DESCRIPTION\":null,\"ACTION\":\"MOD\"}}";

        System.out.println(JSON.parseObject(json, Map.class));
        System.out.println(Json2MapUtil.handle(json));


        //System.out.println(new CpcChannelServiceImpl().handle(json));
    }
}
