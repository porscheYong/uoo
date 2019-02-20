package cn.ffcs.uoo.system.vo;

import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;

import cn.ffcs.uoo.system.entity.SysRole;

public class SysRoleDTO {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * 角色标识
     */
    private Long roleId;
    /**
     * 角色名称
     */
    @NotBlank(message="角色名称不能为空")
    private String roleName;
    /**
     * 角色编码
     */
    @NotBlank(message="角色编码不能为空")
    private String roleCode;
    /**
     * 上级角色角色编码
     */
    private String parentRoleCode;
    /**
     * 引用电信管理区域
     */
    private String regionNbr;
    /**
     * 角色优先级排序
     */
    private Integer sortNum;
    /**
     * 备注
     */
    private String notes;
    /**
     * 状态
     */
    private String statusCd;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 创建人
     */
    private Long createUser;
    /**
     * 修改时间
     */
    private Date updateDate;
    /**
     * 修改人
     */
    private Long updateUser;
    /**
     * 状态变更的时间
     */
    private Date statusDate;
    private String commonRegionName;
    private String parentRoleName;
    private String permissionNames;
    private String permissionCodes;
    private String permissionIds;
    private Long locId;
    private Long parentRoleId;
    
    public String getPermissionIds() {
        return permissionIds;
    }
    public void setPermissionIds(String permissionIds) {
        this.permissionIds = permissionIds;
    }
    public Long getParentRoleId() {
        return parentRoleId;
    }
    public void setParentRoleId(Long parentRoleId) {
        this.parentRoleId = parentRoleId;
    }
    public Long getLocId() {
        return locId;
    }
    public void setLocId(Long locId) {
        this.locId = locId;
    }
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
