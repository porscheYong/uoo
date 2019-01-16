package cn.ffcs.uoo.core.permission.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 定义权限关联的菜单，一个权限可包含多个菜单。
 * </p>
 *
 * @author zengxsh
 * @since 2019-01-15
 */
@TableName("TB_PRIV_MENU_REL")
public class PrivMenuRel extends Model<PrivMenuRel> {

    private static final long serialVersionUID = 1L;

    /**
     * 权限菜单关联标识,主键
     */
    @TableId("PRIV_MENU_ID")
    private Long privMenuId;

    /**
     * 菜单编码
     */
    @TableField("MENU_CODE")
    private String menuCode;

    /**
     * 权限标识，主键
     */
    @TableField("PRIV_ID")
    private Long privId;

    /**
     * 生效时间
     */
    @TableField("EFF_DATE")
    private Date effDate;

    /**
     * 失效时间
     */
    @TableField("EXP_DATE")
    private Date expDate;

    /**
     * 权限的归属系统
     */
    @TableField("SYSTEM_INFO_ID")
    private Long systemInfoId;

    /**
     * 状态
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

    public Long getPrivMenuId() {
        return privMenuId;
    }

    public void setPrivMenuId(Long privMenuId) {
        this.privMenuId = privMenuId;
    }
    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }
    public Long getPrivId() {
        return privId;
    }

    public void setPrivId(Long privId) {
        this.privId = privId;
    }
    public Date getEffDate() {
        return effDate;
    }

    public void setEffDate(Date effDate) {
        this.effDate = effDate;
    }
    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
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
        return this.privMenuId;
    }

    @Override
    public String toString() {
        return "PrivMenuRel{" +
        "privMenuId=" + privMenuId +
        ", menuCode=" + menuCode +
        ", privId=" + privId +
        ", effDate=" + effDate +
        ", expDate=" + expDate +
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
