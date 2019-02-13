package cn.ffcs.uoo.web.maindata.common.system.dto;

import java.util.List;

public class SysPermissionEditDTO extends SysPermission{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private List<SysPermissionFuncRel> funcRels;
    private List<SysPermissionMenuRel> menuRels;
    private List<SysPermissionElementRel> elementRels;
    private List<SysPrivFileRel> fileRels;
    private List<DataRuleGroupEditVO> dataRuleGroups;
    private String regionName;
     
    public List<DataRuleGroupEditVO> getDataRuleGroups() {
        return dataRuleGroups;
    }
    public void setDataRuleGroups(List<DataRuleGroupEditVO> dataRuleGroups) {
        this.dataRuleGroups = dataRuleGroups;
    }
    public String getRegionName() {
        return regionName;
    }
    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }
    public List<SysPermissionFuncRel> getFuncRels() {
        return funcRels;
    }
    public void setFuncRels(List<SysPermissionFuncRel> funcRels) {
        this.funcRels = funcRels;
    }
    public List<SysPermissionMenuRel> getMenuRels() {
        return menuRels;
    }
    public void setMenuRels(List<SysPermissionMenuRel> menuRels) {
        this.menuRels = menuRels;
    }
    public List<SysPermissionElementRel> getElementRels() {
        return elementRels;
    }
    public void setElementRels(List<SysPermissionElementRel> elementRels) {
        this.elementRels = elementRels;
    }
    public List<SysPrivFileRel> getFileRels() {
        return fileRels;
    }
    public void setFileRels(List<SysPrivFileRel> fileRels) {
        this.fileRels = fileRels;
    }
    
     
     
    
}
