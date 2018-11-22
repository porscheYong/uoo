package cn.ffcs.uoo.core.personnel.constant;

public class BaseUnitConstants {
    /**
     * 生效状态
     */
    public static final String ENTT_STATE_ACTIVE = "1000";
    /**
     * 失效状态
     */
    public static final String ENTT_STATE_INACTIVE = "1100";

    /**
     * 人员组织变更状态
     */
    public static final String CHANGE_ORG_STATE = "1200";
    /**
     * 状态
     */
    public static final String TABLE_CLOUMN_STATUS_CD = "STATUS_CD";
    /**
     * 失效时间
     */
    public static final String TABLE_CLOUMN_EXP_DATE = "EXP_DATE";
    /**
     * 状态时间
     */
    public static final String TABLE_CLOUMN_STATUS_DATE = "STATUS_DATE";
    /**
     * 修改时间
     */
    public static final String TABLE_CLOUMN_UPDATE_DATE = "UPDATE_DATE";
    /**
     * 身份证
     */
    public static final String TBCERT_CERT_NO = "CERT_NO";

    public static final String TBCERT_CERT_NAME = "CERT_NAME";

    public static final String TBCONTACT_CONTACT_TYPE = "CONTACT_TYPE";

    public static final String TBPERSONNEL_PERSONNEL_ID = "PERSONNEL_ID";

    public static final String TBEDU_EDU_ID = "EDU_ID";

    public static final String TBFAMILY_FAMILY_ID = "FAMILY_ID";

    public static final String TBPSNJOB_PSNJOB_ID = "PSNJOB_ID";

    public static final String  MSG_SHARING_QUEUE= "message_sharing_center_queue";

    /**
     * 是推导
     */
    public static final String IS_CALC_ACTIVE = "1";
    /**
     * 不是推导
     */
    public static final String IS_CALC_INACTIVE = "2";
    /**
     * 新增
     */
    public static final String OPE_TYPE_ADD = "1";
    /**
     * 修改
     */
    public static final String OPE_TYPE_UPDATE = "2";
    /**
     * 删除
     */
    public static final String OPE_TYPE_DEL = "3";

    /**
     * 归属主部门
     */
    public static final String RALA_CD_1 = "1";

    /**
     * 兼职  rala_cd=2
     */
    public static final String RALA_CD_2 = "2";
    /**
     * 兼职  rala_cd 由3修改为2
     */
    public static final String RALA_CD_3 = "2";

    /**
     * 开关开启
     */
    public static final String SWITCH_OPEN ="1";

    /**
     * 开关关闭
     */
    public static final String SWITCH_CLOSE ="0";
}
