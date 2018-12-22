package cn.ffcs.uoo.system.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zengxsh
 * @since 2018-12-17
 */
@TableName("SYS_ROLE")
public class SysRole extends Model<SysRole> {

    private static final long serialVersionUID = 1L;

    /**
     * 角色标识
     */
    @TableId("ROLE_ID")
    private Long roleId;
    /**
     * 角色名称
     */
    @TableField("ROLE_NAME")
    private String roleName;
    /**
     * 角色编码
     */
    @TableField("ROLE_CODE")
    private String roleCode;
    /**
     * 上级角色角色编码
     */
    @TableField("PARENT_ROLE_CODE")
    private String parentRoleCode;
    /**
     * 引用电信管理区域
     */
    @TableField("REGION_NBR")
    private String regionNbr;
    /**
     * 角色优先级排序
     */
    @TableField("SORT_NUM")
    private Integer sortNum;
    /**
     * 备注
     */
    @TableField("NOTES")
    private String notes;
    /**
     * 状态
     */
    @TableField("STATUS_CD")
    private String statusCd;
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
    /**
     * 状态变更的时间
     */
    @TableField("STATUS_DATE")
    private Date statusDate;


    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getParentRoleCode() {
        return parentRoleCode;
    }

    public void setParentRoleCode(String parentRoleCode) {
        this.parentRoleCode = parentRoleCode;
    }

    public String getRegionNbr() {
        return regionNbr;
    }

    public void setRegionNbr(String regionNbr) {
        this.regionNbr = regionNbr;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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

    @Override
    protected Serializable pkVal() {
        return this.roleId;
    }

    @Override
    public String toString() {
        return "SysRole{" +
        ", roleId=" + roleId +
        ", roleName=" + roleName +
        ", roleCode=" + roleCode +
        ", parentRoleCode=" + parentRoleCode +
        ", regionNbr=" + regionNbr +
        ", sortNum=" + sortNum +
        ", notes=" + notes +
        ", statusCd=" + statusCd +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        ", statusDate=" + statusDate +
        "}";
    }
}
