package cn.ffcs.uoo.message.server.pojo;

import java.util.Date;

public class TbSystemIndividuationRule {
    private Long individuationRuleId;

    private Long systemOrgTreeId;

    private Long busiObjId;

    private Long busiObjAttrId;

    private String ruleOperator;

    private String attrValue;

    private String statusCd;

    private Date statusDate;

    private Date createDate;

    private Long createUser;

    private Date updateDate;

    private Long updateUser;

    public Long getIndividuationRuleId() {
        return individuationRuleId;
    }

    public void setIndividuationRuleId(Long individuationRuleId) {
        this.individuationRuleId = individuationRuleId;
    }

    public Long getSystemOrgTreeId() {
        return systemOrgTreeId;
    }

    public void setSystemOrgTreeId(Long systemOrgTreeId) {
        this.systemOrgTreeId = systemOrgTreeId;
    }

    public Long getBusiObjId() {
        return busiObjId;
    }

    public void setBusiObjId(Long busiObjId) {
        this.busiObjId = busiObjId;
    }

    public Long getBusiObjAttrId() {
        return busiObjAttrId;
    }

    public void setBusiObjAttrId(Long busiObjAttrId) {
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

    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Long getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }
}