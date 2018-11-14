package cn.ffcs.uoo.core.permission.vo;

import java.util.Date;

import cn.ffcs.uoo.base.common.vo.BaseVo;

public class MenuVO extends BaseVo{
    private Long menuId;
    private String menuName;
    private String menuType;
    private Integer menuLevel;
    private Integer menuIndex;
    private Integer parMenuId;
    private Date createDate;
    private String statusCd;
    public Long getMenuId() {
        return menuId;
    }
    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }
    public String getMenuName() {
        return menuName;
    }
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }
    public String getMenuType() {
        return menuType;
    }
    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }
    public Integer getMenuLevel() {
        return menuLevel;
    }
    public void setMenuLevel(Integer menuLevel) {
        this.menuLevel = menuLevel;
    }
    public Integer getMenuIndex() {
        return menuIndex;
    }
    public void setMenuIndex(Integer menuIndex) {
        this.menuIndex = menuIndex;
    }
    public Integer getParMenuId() {
        return parMenuId;
    }
    public void setParMenuId(Integer parMenuId) {
        this.parMenuId = parMenuId;
    }
    public Date getCreateDate() {
        return createDate;
    }
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    public String getStatusCd() {
        return statusCd;
    }
    public void setStatusCd(String statusCd) {
        this.statusCd = statusCd;
    }
}
