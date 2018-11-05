package cn.ffcs.uoo.base.auth.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 系统用户登录设置。同一系统用户可以有多种登录设置信息。
 * </p>
 *
 * @author zengxsh
 * @since 2018-11-01
 */
@TableName("TB_SYSUSER_LOGIN_LIMIT")
public class SysuserLoginLimit extends Model<SysuserLoginLimit> {

    private static final long serialVersionUID = 1L;

    /**
     * 登录设置标识,主键
     */
    @TableId("LOGIN_LIMIT_ID")
    private Integer loginLimitId;
    /**
     * 记录系统用户的主键。
     */
    @TableField("LOGIN_ACCOUNT_ID")
    private Long loginAccountId;
    /**
     * 业务字典，如现在登录是否需要短信验证
     */
    @TableField("LOGIN_SAFE_LEVEL")
    private String loginSafeLevel;
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
     * 限制登录时间Cron表达式
     */
    @TableField("LOGIN_LIMIT_DATETIME_CRON")
    private String loginLimitDatetimeCron;
    /**
     * 公用管理区域标识,记录区域唯一标识
     */
    @TableField("REGION_ID")
    private Long regionId;
    /**
     * 系统用户生效时间
     */
    @TableField("EFF_DATE")
    private Date effDate;
    /**
     * 系统用户失效时间
     */
    @TableField("EXP_DATE")
    private Date expDate;
    /**
     * 记录系统用户登录设置的状态，如有效、无效等。
     */
    @TableField("STATUS_CD")
    private String statusCd;
    /**
     * 状态变更的时间
     */
    @TableField("STATUS_DATE")
    private Date statusDate;
    /**
     * 记录系统用户登录设置创建时间
     */
    @TableField("CREATE_DATE")
    private Date createDate;
    /**
     * 记录系统用户登录设置的创建人
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


    public Integer getLoginLimitId() {
        return loginLimitId;
    }

    public void setLoginLimitId(Integer loginLimitId) {
        this.loginLimitId = loginLimitId;
    }

    public Long getLoginAccountId() {
        return loginAccountId;
    }

    public void setLoginAccountId(Long loginAccountId) {
        this.loginAccountId = loginAccountId;
    }

    public String getLoginSafeLevel() {
        return loginSafeLevel;
    }

    public void setLoginSafeLevel(String loginSafeLevel) {
        this.loginSafeLevel = loginSafeLevel;
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

    public String getLoginLimitDatetimeCron() {
        return loginLimitDatetimeCron;
    }

    public void setLoginLimitDatetimeCron(String loginLimitDatetimeCron) {
        this.loginLimitDatetimeCron = loginLimitDatetimeCron;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
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
    protected Serializable pkVal() {
        return this.loginLimitId;
    }

    @Override
    public String toString() {
        return "SysuserLoginLimit{" +
        ", loginLimitId=" + loginLimitId +
        ", loginAccountId=" + loginAccountId +
        ", loginSafeLevel=" + loginSafeLevel +
        ", loginLimitIpVal=" + loginLimitIpVal +
        ", loginLimitMacVal=" + loginLimitMacVal +
        ", loginLimitDatetimeCron=" + loginLimitDatetimeCron +
        ", regionId=" + regionId +
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
