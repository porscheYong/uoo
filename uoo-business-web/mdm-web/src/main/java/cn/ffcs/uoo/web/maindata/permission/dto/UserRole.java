package cn.ffcs.uoo.web.maindata.permission.dto;

import java.util.Date;

/**
 * <p>
 * 描述系统用户与角色之间的对应关系，是多对多关系。一个系统用户除了拥有系统岗位所带的角色和权限，也可以拥有多个私有的角色，一个角色可以分配给多个系统用户。
 * </p>
 *
 * @author zengxsh
 * @since 2018-11-13
 */
public class UserRole  {


    /**
     * 系统用户角色标识，主键
     */
    private Long userRoleId;
    /**
     * 主账号、从账号
     */
    private Long acctType;
    /**
     * 账号标识
     */
    private Long acctId;
    /**
     * 角色标识
     */
    private Long roleId;
    /**
     * 生效时间
     */
    private Date effDate;
    /**
     * 失效时间
     */
    private Date expDate;
    /**
     * 状态
     */
    private String statusCd;
    /**
     * 状态时间
     */
    private Date statusDate;
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


    public Long getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Long userRoleId) {
        this.userRoleId = userRoleId;
    }

    public Long getAcctType() {
        return acctType;
    }

    public void setAcctType(Long acctType) {
        this.acctType = acctType;
    }

    public Long getAcctId() {
        return acctId;
    }

    public void setAcctId(Long acctId) {
        this.acctId = acctId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
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
    public String toString() {
        return "UserRole{" +
        ", userRoleId=" + userRoleId +
        ", acctType=" + acctType +
        ", acctId=" + acctId +
        ", roleId=" + roleId +
        ", effDate=" + effDate +
        ", expDate=" + expDate +
        ", statusCd=" + statusCd +
        ", statusDate=" + statusDate +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        "}";
    }
}
