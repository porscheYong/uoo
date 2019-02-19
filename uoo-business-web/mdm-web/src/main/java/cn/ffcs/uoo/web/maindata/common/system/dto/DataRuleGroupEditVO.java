package cn.ffcs.uoo.web.maindata.common.system.dto;

import java.util.List;

import cn.ffcs.uoo.web.maindata.common.system.vo.SysDataRuleVo;

public class DataRuleGroupEditVO extends SysDataRuleGroup{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private List<SysDataRuleVo> dataRules;

    public List<SysDataRuleVo> getDataRules() {
        return dataRules;
    }

    public void setDataRules(List<SysDataRuleVo> dataRules) {
        this.dataRules = dataRules;
    }
    

}
