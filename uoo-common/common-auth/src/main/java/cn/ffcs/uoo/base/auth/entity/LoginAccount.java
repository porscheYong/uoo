package cn.ffcs.uoo.base.auth.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 记录员工登录系统使用的系统帐户，不同的系统可有不同的系统用户。
 * </p>
 *
 * @author zengxsh
 * @since 2018-11-01
 */
@TableName("TB_LOGIN_ACCOUNT")
public class LoginAccount extends Model<LoginAccount> {

    private static final long serialVersionUID = 1L;

    /**
     * 记录系统用户的主键。
     */
    @TableId("LOGIN_ACCOUNT_ID")
    private Long loginAccountId;
    /**
     * 员工的系统用户账号。
     */
    @TableField("ACCOUNT")
    private String account;
    /**
     * 账号类型，主、从
     */
    @TableField("ACCOUNT_TYPE")
    private String accountType;
    /**
     * 密码
     */
    @TableField("PASSWORD")
    private String password;
    /**
     * 员工标识
     */
    @TableField("STAFF_ID")
    private Long staffId;
    /**
     * 记录系统登录用户的短信通知手机号码，用于员工的系统安全验证、短信登录等功能
     */
    @TableField("PWD_SMS_TEL")
    private Long pwdSmsTel;
    /**
     * 记录密码生效时间
     */
    @TableField("PWD_EFF_TIME")
    private Date pwdEffTime;
    /**
     * 记录密码失效时间
     */
    @TableField("PWD_EXP_TIME")
    private Date pwdExpTime;
    /**
     * 系统用户生效时间
     */
    @TableField("EFF_DATE")
    private Date effDate;
    /**
     * 系统用户归属的系统信息标识
     */
    @TableField("SYSTEM_INFO_ID")
    private Long systemInfoId;
    /**
     * 系统用户失效时间
     */
    @TableField("EXP_DATE")
    private Date expDate;
    /**
     * 记录系统用户状态。LOVB=STF-0001。
     */
    @TableField("STATUS_CD")
    private String statusCd;
    /**
     * 状态变更的时间
     */
    @TableField("STATUS_DATE")
    private Date statusDate;
    /**
     * 记录系统用户创建时间
     */
    @TableField("CREATE_DATE")
    private Date createDate;
    /**
     * 记录系统用户创建人
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
     * 账号归属组织
     */
    @TableField("ACCOUNT_ORGID")
    private Long accountOrgid;


    public Long getLoginAccountId() {
        return loginAccountId;
    }

    public void setLoginAccountId(Long loginAccountId) {
        this.loginAccountId = loginAccountId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public Long getPwdSmsTel() {
        return pwdSmsTel;
    }

    public void setPwdSmsTel(Long pwdSmsTel) {
        this.pwdSmsTel = pwdSmsTel;
    }

    public Date getPwdEffTime() {
        return pwdEffTime;
    }

    public void setPwdEffTime(Date pwdEffTime) {
        this.pwdEffTime = pwdEffTime;
    }

    public Date getPwdExpTime() {
        return pwdExpTime;
    }

    public void setPwdExpTime(Date pwdExpTime) {
        this.pwdExpTime = pwdExpTime;
    }

    public Date getEffDate() {
        return effDate;
    }

    public void setEffDate(Date effDate) {
        this.effDate = effDate;
    }

    public Long getSystemInfoId() {
        return systemInfoId;
    }

    public void setSystemInfoId(Long systemInfoId) {
        this.systemInfoId = systemInfoId;
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

    public Long getAccountOrgid() {
        return accountOrgid;
    }

    public void setAccountOrgid(Long accountOrgid) {
        this.accountOrgid = accountOrgid;
    }

    @Override
    protected Serializable pkVal() {
        return this.loginAccountId;
    }

    @Override
    public String toString() {
        return "LoginAccount{" +
        ", loginAccountId=" + loginAccountId +
        ", account=" + account +
        ", accountType=" + accountType +
        ", password=" + password +
        ", staffId=" + staffId +
        ", pwdSmsTel=" + pwdSmsTel +
        ", pwdEffTime=" + pwdEffTime +
        ", pwdExpTime=" + pwdExpTime +
        ", effDate=" + effDate +
        ", systemInfoId=" + systemInfoId +
        ", expDate=" + expDate +
        ", statusCd=" + statusCd +
        ", statusDate=" + statusDate +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        ", accountOrgid=" + accountOrgid +
        "}";
    }
}
