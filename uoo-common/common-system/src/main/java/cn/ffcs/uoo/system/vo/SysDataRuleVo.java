package cn.ffcs.uoo.system.vo;

import cn.ffcs.uoo.system.entity.SysDataRule;

public class SysDataRuleVo extends SysDataRule{

    private static final long serialVersionUID = 1L;
 
    

    private Long tabId;

    public Long getTabId() {
        return tabId;
    }

    public void setTabId(Long tabId) {
        this.tabId = tabId;
    }

 
    private Long colId;

    public Long getColId() {
        return colId;
    }

    public void setColId(Long colId) {
        this.colId = colId;
    }
 

    private String ruleOperatorName;


    private Long userId;

    private String accout;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAccout() {
        return accout;
    }

    public void setAccout(String accout) {
        this.accout = accout;
    }

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

      
}
