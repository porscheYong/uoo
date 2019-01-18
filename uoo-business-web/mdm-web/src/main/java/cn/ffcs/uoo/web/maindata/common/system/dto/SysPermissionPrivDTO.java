package cn.ffcs.uoo.web.maindata.common.system.dto;

import java.util.List;

public class SysPermissionPrivDTO extends SysPermission{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private List<SysFunction> funcs;
    private List<SysMenu> menus;
    private List<SysElement> elements;
    private List<SysFile> files;
    private List<SysDataRule> dataRules;
    private String regionName;
     
    public String getRegionName() {
        return regionName;
    }
    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }
    public List<SysFunction> getFuncs() {
        return funcs;
    }
    public void setFuncs(List<SysFunction> funcs) {
        this.funcs = funcs;
    }
    public List<SysMenu> getMenus() {
        return menus;
    }
    public void setMenus(List<SysMenu> menus) {
        this.menus = menus;
    }
    public List<SysElement> getElements() {
        return elements;
    }
    public void setElements(List<SysElement> elements) {
        this.elements = elements;
    }
    public List<SysFile> getFiles() {
        return files;
    }
    public void setFiles(List<SysFile> files) {
        this.files = files;
    }
    public List<SysDataRule> getDataRules() {
        return dataRules;
    }
    public void setDataRules(List<SysDataRule> dataRules) {
        this.dataRules = dataRules;
    }
    
}