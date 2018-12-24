package cn.ffcs.uoo.system.vo;

import cn.ffcs.uoo.system.entity.SysPermission;

public class SysPermissionDTO extends SysPermission{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String regionName;
    public String getRegionName() {
        return regionName;
    }
    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }
}
