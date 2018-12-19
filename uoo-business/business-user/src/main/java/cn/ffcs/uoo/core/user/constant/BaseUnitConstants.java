package cn.ffcs.uoo.core.user.constant;

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

	public static final String TABLE_PERSONNEL_ID = "PERSONNEL_ID";

	public static final String TABLE_ACCT_ID = "ACCT_ID";

	public static final String TABLE_ACCT = "ACCT";

	public static final String TABLE_SLAVE_ACCT = "SLAVE_ACCT";

	public static final String TB_ACCT_ORG_REL_ID = "ACCT_ORG_REL_ID";

	public static final String TB_RESOURCE_OBJ_ID = "RESOURCE_OBJ_ID";

	public static final String TABLE_ORG_ID = "ORG_ID";

	public static final String TABLE_SLAVE_ACCT_ID = "SLAVE_ACCT_ID";

	public static  final String TBALE_USER_ID = "USER_ID";

	public static  final String TBALE_ACCT_TYPE = "ACCT_TYPE";

	public static  final String TBALE_ROLE_ID = "ROLE_ID";

	public static final String  MSG_SHARING_QUEUE= "message_sharing_center_queue";

	public static final Integer PAGE_NO = 1;

	public static final Integer PAGE_SIZE = 5;



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
