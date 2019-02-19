package cn.ffcs.uoo.system.vo;

import cn.ffcs.uoo.system.entity.SysPermission;

public class SysPermissionDTO extends SysPermission{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
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
}
