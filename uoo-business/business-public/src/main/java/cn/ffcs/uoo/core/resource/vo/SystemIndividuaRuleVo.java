package cn.ffcs.uoo.core.resource.vo;/**
 * @description:
 * @author: ffcs-gzb
 * @date: 2018-11-16
 */

/**
 * <p>
 *
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018/11/16
 */
public class SystemIndividuaRuleVo {

    private String individuationRuleId;
    private String systemOrgTreeId;
    private String busiObjId;
    private String busiObjAttrId;
    private String ruleOperator;
    private String attrValue;
    private String statusCd;


    public String getSystemOrgTreeId() {
        return systemOrgTreeId;
    }

    public void setSystemOrgTreeId(String systemOrgTreeId) {
        this.systemOrgTreeId = systemOrgTreeId;
    }

    public String getIndividuationRuleId() {
        return individuationRuleId;
    }

    public void setIndividuationRuleId(String individuationRuleId) {
        this.individuationRuleId = individuationRuleId;
    }

    public String getBusiObjId() {
        return busiObjId;
    }

    public void setBusiObjId(String busiObjId) {
        this.busiObjId = busiObjId;
    }

    public String getBusiObjAttrId() {
        return busiObjAttrId;
    }

    public void setBusiObjAttrId(String busiObjAttrId) {
        this.busiObjAttrId = busiObjAttrId;
    }

    public String getRuleOperator() {
        return ruleOperator;
    }

    public void setRuleOperator(String ruleOperator) {
        this.ruleOperator = ruleOperator;
    }

    public String getAttrValue() {
        return attrValue;
    }

    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue;
    }

    public String getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(String statusCd) {
        this.statusCd = statusCd;
    }
}
