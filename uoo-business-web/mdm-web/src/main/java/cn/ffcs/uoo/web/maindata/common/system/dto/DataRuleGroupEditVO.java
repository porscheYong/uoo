package cn.ffcs.uoo.web.maindata.common.system.dto;

import java.util.List;

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
