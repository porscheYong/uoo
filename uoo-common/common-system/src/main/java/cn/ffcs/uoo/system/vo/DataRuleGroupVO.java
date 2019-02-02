package cn.ffcs.uoo.system.vo;

import java.util.List;

import cn.ffcs.uoo.system.entity.SysDataRule;
import cn.ffcs.uoo.system.entity.SysDataRuleGroup;

public class DataRuleGroupVO extends SysDataRuleGroup {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private List<SysDataRule> dataRules;
    private List<DataRuleGroupVO> childs;
    public List<DataRuleGroupVO> getChilds() {
        return childs;
    }
    public void setChilds(List<DataRuleGroupVO> childs) {
        this.childs = childs;
    }
    public List<SysDataRule> getDataRules() {
        return dataRules;
    }
    public void setDataRules(List<SysDataRule> dataRules) {
        this.dataRules = dataRules;
    }
}
