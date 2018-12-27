package cn.ffcs.uoo.system.vo;

import java.util.List;

import cn.ffcs.uoo.system.entity.SysDataRule;
import cn.ffcs.uoo.system.entity.SysElement;
import cn.ffcs.uoo.system.entity.SysFile;
import cn.ffcs.uoo.system.entity.SysFunction;
import cn.ffcs.uoo.system.entity.SysMenu;
import cn.ffcs.uoo.system.entity.SysPermission;
import cn.ffcs.uoo.system.entity.SysPermissionDataRulesRel;
import cn.ffcs.uoo.system.entity.SysPermissionElementRel;
import cn.ffcs.uoo.system.entity.SysPermissionFuncRel;
import cn.ffcs.uoo.system.entity.SysPermissionMenuRel;
import cn.ffcs.uoo.system.entity.SysPrivFileRel;

public class SysPermissionEditDTO extends SysPermission{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private List<SysPermissionFuncRel> funcRels;
    private List<SysPermissionMenuRel> menuRels;
    private List<SysPermissionElementRel> elementRels;
    private List<SysPrivFileRel> fileRels;
    private List<SysPermissionDataRulesRel> dataRuleRels;
    private String regionName;
     
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
    public List<SysPermissionDataRulesRel> getDataRuleRels() {
        return dataRuleRels;
    }
    public void setDataRuleRels(List<SysPermissionDataRulesRel> dataRuleRels) {
        this.dataRuleRels = dataRuleRels;
    }
     
     
    
}
