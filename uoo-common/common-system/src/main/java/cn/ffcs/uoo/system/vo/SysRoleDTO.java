package cn.ffcs.uoo.system.vo;

import cn.ffcs.uoo.system.entity.SysRole;

public class SysRoleDTO extends SysRole{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String commonRegionName;
    private String parentRoleName;
    private String permissionNames;
    private String permissionCodes;
    public String getCommonRegionName() {
        return commonRegionName;
    }
    public void setCommonRegionName(String commonRegionName) {
        this.commonRegionName = commonRegionName;
    }
    public String getParentRoleName() {
        return parentRoleName;
    }
    public void setParentRoleName(String parentRoleName) {
        this.parentRoleName = parentRoleName;
    }
    public String getPermissionNames() {
        return permissionNames;
    }
    public void setPermissionNames(String permissionNames) {
        this.permissionNames = permissionNames;
    }
    public String getPermissionCodes() {
        return permissionCodes;
    }
    public void setPermissionCodes(String permissionCodes) {
        this.permissionCodes = permissionCodes;
    }
    
}
