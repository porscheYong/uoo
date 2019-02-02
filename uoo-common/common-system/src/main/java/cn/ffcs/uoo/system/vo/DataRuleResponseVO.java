package cn.ffcs.uoo.system.vo;

import java.util.List;

import cn.ffcs.uoo.system.entity.SysDataRule;

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
