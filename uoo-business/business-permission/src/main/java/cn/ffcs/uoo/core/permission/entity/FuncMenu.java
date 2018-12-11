package cn.ffcs.uoo.core.permission.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 功能模块和功能模块之间存在上下级关系，一个功能模块可以有多个下级功能模块，一个功能模块只能属于一个上级功能模块。例如：一级菜单功能“订单管理”下挂二级菜单功能“订单受理”、“订单查询”、“订单历史”。
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-30
 */
@TableName("TB_FUNC_MENU")
public class FuncMenu extends Model<FuncMenu> {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单标识，主键
     */
    @TableId(value = "MENU_ID" )
    private Long menuId;
    /**
     * 菜单名称
     */
    @TableField("MENU_NAME")
    private String menuName;
    /**
     * 菜单类型。LOVB=STF-C-0013。
     */
    @TableField("MENU_TYPE")
    private String menuType;
    /**
     * 菜单项级别(从0开始)
     */
    @TableField("MENU_LEVEL")
    private Integer menuLevel;
    /**
     * 菜单排序号（从0开始）
     */
    @TableField("MENU_INDEX")
    private Integer menuIndex;
    /**
     * 上级菜单标识
     */
    @TableField("PAR_MENU_ID")
    private Long parMenuId;
    /**
     * 菜单描述
     */
    @TableField("MENU_DESC")
    private String menuDesc;
    /**
     * 菜单URL链接地址
     */
    @TableField("URL_ADDR")
    private String urlAddr;
    /**
     * 公用管理区域标识,记录区域唯一标识
     */
    @TableField("REGION_ID")
    private Long regionId;
    /**
     * 系统用户的归属系统
     */
    @TableField("SYSTEM_INFO_ID")
    private Long systemInfoId;
    /**
     * 菜单状态
     */
    @TableField("STATUS_CD")
    private String statusCd;
    /**
     * 状态时间
     */
    @TableField("STATUS_DATE")
    private Date statusDate;
    /**
     * 创建时间
     */
    @TableField("CREATE_DATE")
    private Date createDate;
    /**
     * 创建人
     */
    @TableField("CREATE_USER")
    private Long createUser;
    /**
     * 修改时间
     */
    @TableField("UPDATE_DATE")
    private Date updateDate;
    /**
     * 修改人
     */
    @TableField("UPDATE_USER")
    private Long updateUser;


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

    public Long getParMenuId() {
        return parMenuId;
    }

    public void setParMenuId(Long parMenuId) {
        this.parMenuId = parMenuId;
    }

    public String getMenuDesc() {
        return menuDesc;
    }

    public void setMenuDesc(String menuDesc) {
        this.menuDesc = menuDesc;
    }

    public String getUrlAddr() {
        return urlAddr;
    }

    public void setUrlAddr(String urlAddr) {
        this.urlAddr = urlAddr;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public Long getSystemInfoId() {
        return systemInfoId;
    }

    public void setSystemInfoId(Long systemInfoId) {
        this.systemInfoId = systemInfoId;
    }

    public String getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(String statusCd) {
        this.statusCd = statusCd;
    }

    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Long getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    @Override
    protected Serializable pkVal() {
        return this.menuId;
    }

    @Override
    public String toString() {
        return "FuncMenu{" +
        ", menuId=" + menuId +
        ", menuName=" + menuName +
        ", menuType=" + menuType +
        ", menuLevel=" + menuLevel +
        ", menuIndex=" + menuIndex +
        ", parMenuId=" + parMenuId +
        ", menuDesc=" + menuDesc +
        ", urlAddr=" + urlAddr +
        ", regionId=" + regionId +
        ", systemInfoId=" + systemInfoId +
        ", statusCd=" + statusCd +
        ", statusDate=" + statusDate +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        "}";
    }
}
