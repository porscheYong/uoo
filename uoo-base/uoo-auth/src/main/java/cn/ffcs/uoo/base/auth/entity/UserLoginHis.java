package cn.ffcs.uoo.base.auth.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 系统用户登录历史
 * </p>
 *
 * @author zengxsh
 * @since 2018-11-01
 */
@TableName("TB_USER_LOGIN_HIS")
public class UserLoginHis extends Model<UserLoginHis> {

    private static final long serialVersionUID = 1L;

    /**
     * 记录系统用户的主键。
     */
    @TableId("LOGIN_HIS_ID")
    private Long loginHisId;
    /**
     * 记录系统用户的主键。
     */
    @TableField("LOGIN_ACCOUNT_ID")
    private Long loginAccountId;
    /**
     * 记录系统用户创建时间
     */
    @TableField("CREATE_DATE")
    private Date createDate;
    /**
     * 员工的系统用户账号。
     */
    @TableField("ACCOUNT")
    private String account;
    /**
     * 记录系统登录用户的短信通知手机号码，用于员工的系统安全验证、短信登录等功能
     */
    @TableField("PWD_SMS_TEL")
    private Long pwdSmsTel;
    /**
     * 记录系统登录用户的短信通知验证码
     */
    @TableField("PWD_SMS_CODE")
    private String pwdSmsCode;
    /**
     * 公用管理区域标识,记录区域唯一标识
     */
    @TableField("REGION_ID")
    private Long regionId;
    /**
     * 系统用户归属的系统信息标识
     */
    @TableField("SYSTEM_INFO_ID")
    private Long systemInfoId;
    /**
     * 登录设置IP值
     */
    @TableField("LOGIN_LIMIT_IP_VAL")
    private String loginLimitIpVal;
    /**
     * 登录设置MAC值
     */
    @TableField("LOGIN_LIMIT_MAC_VAL")
    private String loginLimitMacVal;
    /**
     * 接口、网页
     */
    @TableField("LOGIN_METHOD")
    private String loginMethod;
    /**
     * 记录登录成功失败状态
     */
    @TableField("STATUS_CD")
    private String statusCd;


    public Long getLoginHisId() {
        return loginHisId;
    }

    public void setLoginHisId(Long loginHisId) {
        this.loginHisId = loginHisId;
    }

    public Long getLoginAccountId() {
        return loginAccountId;
    }

    public void setLoginAccountId(Long loginAccountId) {
        this.loginAccountId = loginAccountId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Long getPwdSmsTel() {
        return pwdSmsTel;
    }

    public void setPwdSmsTel(Long pwdSmsTel) {
        this.pwdSmsTel = pwdSmsTel;
    }

    public String getPwdSmsCode() {
        return pwdSmsCode;
    }

    public void setPwdSmsCode(String pwdSmsCode) {
        this.pwdSmsCode = pwdSmsCode;
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

    public String getLoginLimitIpVal() {
        return loginLimitIpVal;
    }

    public void setLoginLimitIpVal(String loginLimitIpVal) {
        this.loginLimitIpVal = loginLimitIpVal;
    }

    public String getLoginLimitMacVal() {
        return loginLimitMacVal;
    }

    public void setLoginLimitMacVal(String loginLimitMacVal) {
        this.loginLimitMacVal = loginLimitMacVal;
    }

    public String getLoginMethod() {
        return loginMethod;
    }

    public void setLoginMethod(String loginMethod) {
        this.loginMethod = loginMethod;
    }

    public String getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(String statusCd) {
        this.statusCd = statusCd;
    }

    @Override
    protected Serializable pkVal() {
        return this.loginHisId;
    }

    @Override
    public String toString() {
        return "UserLoginHis{" +
        ", loginHisId=" + loginHisId +
        ", loginAccountId=" + loginAccountId +
        ", createDate=" + createDate +
        ", account=" + account +
        ", pwdSmsTel=" + pwdSmsTel +
        ", pwdSmsCode=" + pwdSmsCode +
        ", regionId=" + regionId +
        ", systemInfoId=" + systemInfoId +
        ", loginLimitIpVal=" + loginLimitIpVal +
        ", loginLimitMacVal=" + loginLimitMacVal +
        ", loginMethod=" + loginMethod +
        ", statusCd=" + statusCd +
        "}";
    }
}
