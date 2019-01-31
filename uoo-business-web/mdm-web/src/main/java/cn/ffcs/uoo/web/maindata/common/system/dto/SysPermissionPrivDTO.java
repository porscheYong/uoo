package cn.ffcs.uoo.web.maindata.common.system.dto;

import java.util.List;

import cn.ffcs.uoo.web.maindata.common.system.vo.PermDataRule;
import cn.ffcs.uoo.web.maindata.common.system.vo.PermElement;
import cn.ffcs.uoo.web.maindata.common.system.vo.PermFile;
import cn.ffcs.uoo.web.maindata.common.system.vo.PermFunction;
import cn.ffcs.uoo.web.maindata.common.system.vo.PermMenu;

public class SysPermissionPrivDTO extends SysPermission{ 
 
    private static final long serialVersionUID = 1L;
    private List<PermFunction> funcs;
    private List<PermMenu> menus;
    private List<PermElement> elements;
    private List<PermFile> files;
    private List<PermDataRule> dataRules;
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
    public List<PermDataRule> getDataRules() {
        return dataRules;
    }
    public void setDataRules(List<PermDataRule> dataRules) {
        this.dataRules = dataRules;
    }
     
}
