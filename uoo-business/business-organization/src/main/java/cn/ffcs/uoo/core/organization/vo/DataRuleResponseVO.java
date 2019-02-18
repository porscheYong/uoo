package cn.ffcs.uoo.core.organization.vo;

import java.util.List;

public class DataRuleResponseVO {
    private List<DataRuleGroupVO> groups;
    private List<SysDataRule> dataRules;
    public List<SysDataRule> getDataRules() {
        return dataRules;
    }
    public void setDataRules(List<SysDataRule> dataRules) {
        this.dataRules = dataRules;
    }
    public List<DataRuleGroupVO> getGroups() {
        return groups;
    }
    public void setGroups(List<DataRuleGroupVO> groups) {
        this.groups = groups;
    }
}
