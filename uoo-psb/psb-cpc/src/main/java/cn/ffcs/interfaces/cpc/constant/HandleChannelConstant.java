package cn.ffcs.interfaces.cpc.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuxiaodong on 2019/1/14.
 */
public class HandleChannelConstant {

    /**
     * 系统资源标识
     */
    public static final String RESOURCE_ID = "1";

    /**
     *  CPC无组织
     */
    public static final Long ORG_ID = 10007029L;

    public static final String VALID_STATE = "1000";

    public static final String INVALID_STATE = "1100";

    public static final String ORG_TABLE_NAME = "TB_ORG";

    /**
     * 主账号与销售员编码跨域关系类型
     */
    public static final String RELA_TYPE = "100100102";

    /**
     * 组织人员关系类型
     */
    public static final String REL_TYPE = "30";

    /**
     * cpc系统主账号组织关系对应的组织树标识
     */
    public static final Long ORG_TREE_ID = 1L;

    public static final Long HANDLE_USER = 1004040L;

    public static final Map<String, Object> ORG_TYPE_REL = new HashMap<>();

    static {
        ORG_TYPE_REL.put("100101", "N0202030000");
        ORG_TYPE_REL.put("100102", "N0202030000");
        ORG_TYPE_REL.put("100201", "N0202050000");
        ORG_TYPE_REL.put("100202", "N0202050000");
        ORG_TYPE_REL.put("110101", "N0202010000");
        ORG_TYPE_REL.put("110102", "N0202010000");
        ORG_TYPE_REL.put("110201", "N0202020000");
        ORG_TYPE_REL.put("110202", "N0202020000");
        ORG_TYPE_REL.put("120101", "N0202040000");
        ORG_TYPE_REL.put("120102", "N0202040000");
        ORG_TYPE_REL.put("120201", "N0202060000");
        ORG_TYPE_REL.put("120202", "N0202060000");
        ORG_TYPE_REL.put("99", "N0202060000");

    }

}
