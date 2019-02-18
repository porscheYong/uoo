package cn.ffcs.uoo.system.vo;

import java.util.List;

import cn.ffcs.uoo.system.entity.SysPermission;

public class SysPermissionPrivDTO extends SysPermission{
 
    private static final long serialVersionUID = 1L;
    private List<PermFunction> funcs;
    private List<PermMenu> menus;
    private List<PermElement> elements;
    private List<PermFile> files;
    private List<DataRuleGroupEditVO> dataRuleGroups;
    private String regionName;
    private Long locId;
    public Long getLocId() {
        return locId;
    }
    public void setLocId(Long locId) {
        this.locId = locId;
    }
    public String getRegionName() {
        return regionName;
    }
    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }
    public List<PermFunction> getFuncs() {
        return funcs;
    }
    public void setFuncs(List<PermFunction> funcs) {
        this.funcs = funcs;
    }
    public List<PermMenu> getMenus() {
        return menus;
    }
    public void setMenus(List<PermMenu> menus) {
        this.menus = menus;
    }
    public List<PermElement> getElements() {
        return elements;
    }
    public void setElements(List<PermElement> elements) {
        this.elements = elements;
    }
    public List<PermFile> getFiles() {
        return files;
    }
    public void setFiles(List<PermFile> files) {
        this.files = files;
    }
    public List<DataRuleGroupEditVO> getDataRuleGroups() {
        return dataRuleGroups;
    }
    public void setDataRules(List<DataRuleGroupEditVO> dataRuleGroups) {
        this.dataRuleGroups = dataRuleGroups;
    }
     
     
}
