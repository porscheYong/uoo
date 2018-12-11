package cn.ffcs.uoo.web.maindata.permission.dto;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * <p>
 * 定义权限关联的功能菜单、功能组件，一个权限可包含多个功能菜单或功能组件。
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-30
 */
@TableName("TB_PRIV_FUNC_REL")
public class PrivFuncRel extends Model<PrivFuncRel> {

    private static final long serialVersionUID = 1L;

    /**
     * 权限功能关联标识,主键
     */
    @TableId(value = "PRIV_FUNC_ID", type = IdType.AUTO)
    private Long privFuncId;
    /**
     * 权限标识，外键
     */
    @TableField("PRIV_ID")
    private Long privId;
    /**
     * 菜单标识，主键
     */
    @TableField("MENU_ID")
    private Long menuId;
    /**
     * 模块组件标识，主键
     */
    @TableField("COMP_ID")
    private Long compId;
    /**
     * 权限对象类型，LOVB=STF-C-0021；
     */
    @TableField("PRIV_REF_TYPE")
    private String privRefType;
    /**
     * 根据权限对象类型，如果为"业务对象",则填写业务对象ID;
     */
    @TableField("PRIV_REF_ID")
    private String privRefId;
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


    public Long getPrivFuncId() {
        return privFuncId;
    }

    public void setPrivFuncId(Long privFuncId) {
        this.privFuncId = privFuncId;
    }

    public Long getPrivId() {
        return privId;
    }

    public void setPrivId(Long privId) {
        this.privId = privId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Long getCompId() {
        return compId;
    }

    public void setCompId(Long compId) {
        this.compId = compId;
    }

    public String getPrivRefType() {
        return privRefType;
    }

    public void setPrivRefType(String privRefType) {
        this.privRefType = privRefType;
    }

    public String getPrivRefId() {
        return privRefId;
    }

    public void setPrivRefId(String privRefId) {
        this.privRefId = privRefId;
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
        return this.privFuncId;
    }

    @Override
    public String toString() {
        return "PrivFuncRel{" +
        ", privFuncId=" + privFuncId +
        ", privId=" + privId +
        ", menuId=" + menuId +
        ", compId=" + compId +
        ", privRefType=" + privRefType +
        ", privRefId=" + privRefId +
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
