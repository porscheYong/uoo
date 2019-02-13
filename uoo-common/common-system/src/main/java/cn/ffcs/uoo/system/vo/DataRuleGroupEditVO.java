package cn.ffcs.uoo.system.vo;

import java.util.List;

import cn.ffcs.uoo.system.entity.SysDataRule;
import cn.ffcs.uoo.system.entity.SysDataRuleGroup;

public class DataRuleGroupEditVO extends SysDataRuleGroup{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private List<SysDataRule> dataRules;

    public List<SysDataRule> getDataRules() {
        return dataRules;
    }

    public void setDataRules(List<SysDataRule> dataRules) {
        this.dataRules = dataRules;
    }
    

}
