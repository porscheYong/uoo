package cn.ffcs.uoo.core.organization.util;

/**
 * @description:
 * @author: ffcs-gzb
 * @date: 2018-12-26
 */
public enum EnumRuleOperator {

    EQ("=","eq"),
    GT(">","gt"),
    LT("<","lt"),
    LTE("<=","lte"),
    GTE(">=","gte"),
    NE("!=","ne"),
    IN("IN","in"),
    NOIN("NOT IN","notin"),
    ISNULL("IS NULL","isnull"),
    ISNOTNULL("IS NOT NULL","isnotnull");

    private String sqlOper;

    private String htmlOper;

    private EnumRuleOperator(String sqlOper, String htmlOper) {
        this.sqlOper = sqlOper;
        this.htmlOper = htmlOper;
    }

    public String getSqlOper() {
        return sqlOper;
    }

    public String getHtmlOper() {
        return htmlOper;
    }

    public static String getSqlRuleOper(String str) {
        for (EnumRuleOperator e : EnumRuleOperator.values()) {
            if (str.equals(e.getHtmlOper())) {
                return e.getSqlOper();
            }
        }
        return "";
    }

    public static String getHtmlRuleOper(String str) {
        for (EnumRuleOperator e : EnumRuleOperator.values()) {
            if (str.equals(e.getSqlOper())) {
                return e.getHtmlOper();
            }
        }
        return "";
    }

    public static void main(String[] args) {

        System.out.println(EnumRuleOperator.getSqlRuleOper("="));
    }
}
