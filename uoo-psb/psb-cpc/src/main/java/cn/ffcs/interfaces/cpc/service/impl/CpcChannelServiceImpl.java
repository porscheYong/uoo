package cn.ffcs.interfaces.cpc.service.impl;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class CpcChannelServiceImpl implements CpcChannelService {

    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";

    /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    @Resource
    private TbOrgService tbOrgService;
    @Resource
    private CommonRegionService commonRegionService;
    @Resource
    private SystemtableService systemtableService;
    @Resource
    private ExpandocolumnService expandocolumnService;
    @Resource
    private ExpandorowService expandorowService;
    @Resource
    private ExpandovalueService expandovalueService;
    @Resource
    private AcctCrossRelService acctCrossRelService;

    @Resource
    private TbPersonnelService tbPersonnelService;
    @Resource
    private TbCertService tbCertService;
    @Resource
    private TbAcctService tbAcctService;
    @Resource
    private TbContactService tbContactService;
    @Resource
    private TbSlaveAcctService tbSlaveAcctService;

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
        if (map == null) {
            result_msg.append("json is null.");
        } else {
            TransactionID = (String) map.get("TransactionID") == null ? "" : (String) map.get("TransactionID");
            result_code = "0";
            /*渠道*/
            Map<String, Object> CHANNEL = (Map<String, Object>) map.get("CHANNEL");
            hand_CHANNEL(CHANNEL, rsMap);
            /*员工*/
            Map<String, Object> STAFF = (Map<String, Object>) map.get("STAFF");
            /*员工渠道关系*/
            Map<String, Object> STAFF_CHANNEL_RELAS = (Map<String, Object>) map.get("STAFF_CHANNEL_RELAS");

            hand_STAFF(STAFF, rsMap);
            hand_STAFF_CHANNEL_RELAS(STAFF_CHANNEL_RELAS, rsMap);

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
        if (StringUtils.isNotEmpty(commoinRegionId) && StringUtils.isNotEmpty(orgName)) {
            try {
                // 插入组织表
                TbOrg tbOrg = new TbOrg();
                CommonRegion region = commonRegionService.selectOne(new EntityWrapper<CommonRegion>().
                        eq("COMMON_REGION_ID", commoinRegionId.substring(0, 5) + "00")
                        .eq("STATUS_CD", "1000"));
                tbOrg.setAreaCodeId(region.getAreaCodeId());
                tbOrg.setOrgName(orgName);
                tbOrgService.insertChannel(tbOrg);

                // 插入扩展行
                Systemtable systemtable = systemtableService.selectOne(new EntityWrapper<Systemtable>().
                        eq("TABLE_NAME", "TB_ORG")
                        .eq("STATUS_CD", "1000"));
                Expandorow expandorow = new Expandorow(systemtable.getTableId(), "1",
                        tbOrg.getOrgId().toString(), "1000", DateUtils.parseDate(DateUtils.getDateTime()));
                expandorowService.insert(expandorow);

                Expandocolumn columnOfIsChannel = expandocolumnService.selectOne(new EntityWrapper<Expandocolumn>().
                        eq("COLUMN_NAME", "IsChannel").eq("STATUS_CD", "1000"));
                Expandocolumn columnOfChannelNBR = expandocolumnService.selectOne(new EntityWrapper<Expandocolumn>().
                        eq("COLUMN_NAME", "channelNBR").eq("STATUS_CD", "1000"));

                // 插入扩展值
                Expandovalue valueOfIsChannel = new Expandovalue("1", systemtable.getTableId(),
                        columnOfIsChannel.getColumnId(), expandorow.getRowId(), tbOrg.getOrgId().toString(),
                        "Y", "1000", DateUtils.parseDate(DateUtils.getDateTime()));
                Expandovalue valueOfChannelNBR = new Expandovalue("1", systemtable.getTableId(),
                        columnOfChannelNBR.getColumnId(), expandorow.getRowId(), tbOrg.getOrgId().toString(),
                        String.valueOf(channel.get("CHANNEL_NBR")),
                        "1000", DateUtils.parseDate(DateUtils.getDateTime()));
                expandovalueService.insert(valueOfIsChannel);
                expandovalueService.insert(valueOfChannelNBR);
            } catch (Exception e) {
                rsMap.put("result_code", "1000");
                e.printStackTrace();
                rsMap.put("message",e.getStackTrace());
//                throw new Exception("执行失败，请查看日志");
            }
        } else {
            rsMap.put("result_code", "1000");
            rsMap.put("message","必要信息为空");
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
                // 插入TB_PERSONNEL表
                String uuid = UUID.randomUUID().toString();
                TbPersonnel tbPersonnel = new TbPersonnel(staffName, psnCode, psnCode, idCard);
                tbPersonnelService.insertValueOfPersonnel(tbPersonnel);

                // 插入TB_CERT
                TbCert tbCert = new TbCert(tbPersonnel.getPersonnelId(), staffName,
                        String.valueOf(staff.get("CERT_TYPE")), String.valueOf(staff.get("CERT_NUMBER")),
                        UUID.randomUUID().toString(), "1", "1000",
                        DateUtils.parseDate(DateUtils.getDateTime()));
                tbCertService.insert(tbCert);

                // 插入TB_ACCT
                TbAcct tbAcct = new TbAcct(String.valueOf(tbPersonnel.getPersonnelId()), psnCode, "1314",
                        "0DB7DBB1F7EAF44CF5C077C9BC699A35", "1000",
                        DateUtils.parseDate(DateUtils.getDateTime()), "2",
                        DateUtils.parseDate("20190101"), DateUtils.parseDate("20990101"), "2");
                tbAcctService.insert(tbAcct);

                // 插入TB_CONTACT
                if (StringUtils.isNotEmpty(String.valueOf(staff.get("MOBILE_PHONE")))) {
                    TbContact tbContact = new TbContact(tbPersonnel.getPersonnelId(), "1",
                            String.valueOf(staff.get("MOBILE_PHONE")), UUID.randomUUID().toString(),
                            "1000", DateUtils.parseDate(DateUtils.getDateTime()), Short.valueOf("1"));
                    tbContactService.insert(tbContact);
                }
                if (StringUtils.isNotEmpty(String.valueOf(staff.get("E_MAIL")))) {
                    TbContact tbContact = new TbContact(tbPersonnel.getPersonnelId(), "2",
                            String.valueOf(staff.get("E_MAIL")), UUID.randomUUID().toString(),
                            "1000", DateUtils.parseDate(DateUtils.getDateTime()), Short.valueOf("0"));
                    tbContactService.insert(tbContact);
                }

                //插入TB_ACCT_CROSS_REL
                AcctCrossRel acctCrossRel = new AcctCrossRel(tbAcct.getAcctId(),
                        String.valueOf(staff.get("SALES_CODE")), "100100102", "1000",
                        DateUtils.parseDate(DateUtils.getDateTime()));
                acctCrossRelService.insert(acctCrossRel);

                //插入TB_SLAVE_ACCT

                TbSlaveAcct tbSlaveAcct = new TbSlaveAcct(String.valueOf(staff.get("ACCOUNT")), "1314",
                        "0DB7DBB1F7EAF44CF5C077C9BC699A35", "1", 5L,
                        "1000", DateUtils.parseDate(DateUtils.getDateTime()), 0L,
                        tbAcct.getAcctId(), DateUtils.parseDate("20190101"), DateUtils.parseDate("20990101"));
                tbSlaveAcctService.insert(tbSlaveAcct);
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
        if (!rsMap.get("result_code").toString().equals("1000")) {
            String salesCode = String.valueOf(staffChannelRel.get("SALES_CODE"));
            String channelNBR = String.valueOf(staffChannelRel.get("CHANNEL_NBR"));
            if (StringUtils.isNotEmpty(salesCode) && StringUtils.isNotEmpty(channelNBR)) {
                try {
                    // 获取orgId
                    List<Expandovalue> expandovalues = expandovalueService.selectList(new EntityWrapper<Expandovalue>().
                            eq("DATA_", staffChannelRel.get("CHANNEL_NBR")).eq("STATUS_CD", "1000"));
                    String recordId = expandovalues.get(0).getRecordId();

                    // 获取主账号
                    AcctCrossRel acctCrossRel = acctCrossRelService.selectOne(new EntityWrapper<AcctCrossRel>().
                            eq("CROSS_TRAN", staffChannelRel.get("SALES_CODE")).
                            eq("STATUS_CD", "1000"));
                    if (null == acctCrossRel) {
                        rsMap.put("result_code", "1000");
                        return;
                    }

                    AccountOrgRel accountOrgRel = new AccountOrgRel(Long.valueOf(recordId), acctCrossRel.getAcctId(),
                            "1000", DateUtils.parseDate(DateUtils.getDateTime()), 1L, "30");
                    acctCrossRelService.insert(acctCrossRel);

                    // 更新从账号
                    TbSlaveAcct tbSlaveAcct = tbSlaveAcctService.selectOne(new EntityWrapper<TbSlaveAcct>().
                            eq("ACCT_ORG_REL_ID",accountOrgRel.getAcctOrgRelId())
                            .eq("ACCT_ID",acctCrossRel.getAcctId())
                            .eq("STATUS_CD","1000"));
                    tbSlaveAcct.setAcctOrgRelId(accountOrgRel.getAcctOrgRelId());
                    tbSlaveAcctService.updateById(tbSlaveAcct);

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    rsMap.put("result_code", "1000");
                    rsMap.put("message",e.getStackTrace());
//                    throw new Exception("执行失败，请查看日志");
                }
            } else {
                rsMap.put("result_code", "1000");
            }

        }
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
