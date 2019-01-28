package cn.ffcs.uoo.system.vo;

import java.util.List;

import cn.ffcs.uoo.system.entity.SysDataRule;
import cn.ffcs.uoo.system.entity.SysElement;
import cn.ffcs.uoo.system.entity.SysFile;
import cn.ffcs.uoo.system.entity.SysFunction;
import cn.ffcs.uoo.system.entity.SysMenu;
import cn.ffcs.uoo.system.entity.SysPermission;

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
