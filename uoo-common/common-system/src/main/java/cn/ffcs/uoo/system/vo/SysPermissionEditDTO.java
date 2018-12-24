package cn.ffcs.uoo.system.vo;

import java.util.List;

import cn.ffcs.uoo.system.entity.SysPermission;

public class SysPermissionEditDTO extends SysPermission{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private List<String> funcCodes;
    private List<String> menuCodes;
    private List<String> elementCodes;
    private List<Long> fileIds;
    private List<Long> dataRuleIds;
    
    private String regionName;
    public String getRegionName() {
        return regionName;
    }
    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }
    public List<String> getFuncCodes() {
        return funcCodes;
    }
    public void setFuncCodes(List<String> funcCodes) {
        this.funcCodes = funcCodes;
    }
    public List<String> getMenuCodes() {
        return menuCodes;
    }
    public void setMenuCodes(List<String> menuCodes) {
        this.menuCodes = menuCodes;
    }
    public List<String> getElementCodes() {
        return elementCodes;
    }
    public void setElementCodes(List<String> elementCodes) {
        this.elementCodes = elementCodes;
    }
    public List<Long> getFileIds() {
        return fileIds;
    }
    public void setFileIds(List<Long> fileIds) {
        this.fileIds = fileIds;
    }
    public List<Long> getDataRuleIds() {
        return dataRuleIds;
    }
    public void setDataRuleIds(List<Long> dataRuleIds) {
        this.dataRuleIds = dataRuleIds;
    }
    
}
