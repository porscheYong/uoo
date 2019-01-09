package cn.ffcs.uoo.web.maindata.common.system.vo;

import java.util.Date;
public class SysDataRuleVo {

    private static final long serialVersionUID = 1L;

    /**
     * 权限规则标识,主键
     */

    private Long dataRuleId;
    /**
     * 表的名称
     */

    private String tabName;

    private Long tabId;

    public Long getTabId() {
        return tabId;
    }

    public void setTabId(Long tabId) {
        this.tabId = tabId;
    }

    /**
     * 记录字段名称
     */

    private String colName;

    private Long colId;

    public Long getColId() {
        return colId;
    }

    public void setColId(Long colId) {
        this.colId = colId;
    }

    /**
     * 规则操作符,包含、等于、大于、大于等于、小于、小于等于
     */

    private String ruleOperator;

    private String ruleOperatorName;

    public String getRuleOperatorName() {
        return ruleOperatorName;
    }

    public void setRuleOperatorName(String ruleOperatorName) {
        this.ruleOperatorName = ruleOperatorName;
    }

    private String ruleOperatorId;

    public String getRuleOperatorId() {
        return ruleOperatorId;
    }

    public void setRuleOperatorId(String ruleOperatorId) {
        this.ruleOperatorId = ruleOperatorId;
    }

    /**
     * 字段值,同一权限的同一业务对象属性标识的多个取值,可用逗号间隔
     */

    private String colValue;
    /**
     * 状态
     */

    private String statusCd;
    /**
     * 状态时间
     */

    private Date statusDate;
    /**
     * 创建时间
     */

    private Date createDate;
    /**
     * 创建人
     */

    private Long createUser;
    /**
     * 修改时间
     */

    private Date updateDate;
    /**
     * 修改人
     */

    private Long updateUser;


    public Long getDataRuleId() {
        return dataRuleId;
    }

    public void setDataRuleId(Long dataRuleId) {
        this.dataRuleId = dataRuleId;
    }

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public String getRuleOperator() {
        return ruleOperator;
    }

    public void setRuleOperator(String ruleOperator) {
        this.ruleOperator = ruleOperator;
    }

    public String getColValue() {
        return colValue;
    }

    public void setColValue(String colValue) {
        this.colValue = colValue;
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
