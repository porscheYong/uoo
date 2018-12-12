package cn.ffcs.uoo.web.maindata.common.system.dto;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统域菜单
 */
@TableName("SYS_MENU")
public class SysMenu extends Model<SysMenu> {

    /** 系统菜单标识  */
    @TableId(value = "MENU_ID")
    private Long menuId;

    /** 菜单名称    */
    @TableField("MENU_NAME")
    private String menuName;

    /** 编码  */
    @TableField("MENU_CODE")
    private String menuCode;

    /** 上级菜单    */
    @TableField("P_MENU_ID")
    private Long pMenuId;

    /** 所有上级菜单  */
    @TableField("P_MENU_IDS")
    private String pMenuIds;

    /** 图标  */
    @TableField("ICON")
    private String icon;

    /** url地址   */
    @TableField("URL")
    private String url;

    /** 排序号 */
    @TableField("NUM")
    private Integer num;

    /** 层级  */
    @TableField("MENU_LEVEL")
    private Integer menuLevel;

    /** 是否菜单    */
    @TableField("MENU_FLAG")
    private Integer menuFlag;

    /** 是否打开    */
    @TableField("OPEN_FLAG")
    private Integer openFlag;

    /** 状态  */
    @TableField("STATUS_CD")
    private String statusCd;

    /** 创建时间  */
    @TableField("CREATE_DATE")
    private Date createDate;

    /** 创建人    */
    @TableField("CREATE_USER")
    private Long createUser;

    /** 修改时间 */
    @TableField("UPDATE_DATE")
    private Date updateDate;

    /** 修改人 */
    @TableField("UPDATE_USER")
    private Long updateUser;

    /** 状态时间    */
    @TableField("STATUS_DATE")
    private Date statusDate;

    /** 备注  */
    @TableField("NOTES")
    private String notes;

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

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public Long getpMenuId() {
        return pMenuId;
    }

    public void setpMenuId(Long pMenuId) {
        this.pMenuId = pMenuId;
    }

    public String getpMenuIds() {
        return pMenuIds;
    }

    public void setpMenuIds(String pMenuIds) {
        this.pMenuIds = pMenuIds;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

     

    public Integer getMenuLevel() {
        return menuLevel;
    }

    public void setMenuLevel(Integer menuLevel) {
        this.menuLevel = menuLevel;
    }

    public Integer getMenuFlag() {
        return menuFlag;
    }

    public void setMenuFlag(Integer menuFlag) {
        this.menuFlag = menuFlag;
    }

    public Integer getOpenFlag() {
        return openFlag;
    }

    public void setOpenFlag(Integer openFlag) {
        this.openFlag = openFlag;
    }

    public String getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(String statusCd) {
        this.statusCd = statusCd;
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

    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    protected Serializable pkVal() {
        return this.menuId;
    }
}