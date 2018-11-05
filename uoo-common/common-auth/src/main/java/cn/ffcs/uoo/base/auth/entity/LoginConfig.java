package cn.ffcs.uoo.base.auth.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 系统用户登录配置表
 * </p>
 *
 * @author zengxsh
 * @since 2018-11-01
 */
@TableName("TB_LOGIN_CONFIG")
public class LoginConfig extends Model<LoginConfig> {

    private static final long serialVersionUID = 1L;

    /**
     * 记录系统用户的主键。
     */
    @TableId("LOGIN_CONF_ID")
    private Long loginConfId;
    /**
     * 记录系统用户的主键。
     */
    @TableField("LOGIN_ACCOUNT_ID")
    private Long loginAccountId;
    /**
     * 记录用户登录系统时输入秘密错误的次数
     */
    @TableField("PWD_ERR_CNT")
    private Integer pwdErrCnt;
    /**
     * 当前登录次数
     */
    @TableField("LOGINED_NUM")
    private Integer loginedNum;
    /**
     * 最后一次登录时间
     */
    @TableField("LAST_LOGIN_TIME")
    private Date lastLoginTime;


    public Long getLoginConfId() {
        return loginConfId;
    }

    public void setLoginConfId(Long loginConfId) {
        this.loginConfId = loginConfId;
    }

    public Long getLoginAccountId() {
        return loginAccountId;
    }

    public void setLoginAccountId(Long loginAccountId) {
        this.loginAccountId = loginAccountId;
    }

    public Integer getPwdErrCnt() {
        return pwdErrCnt;
    }

    public void setPwdErrCnt(Integer pwdErrCnt) {
        this.pwdErrCnt = pwdErrCnt;
    }

    public Integer getLoginedNum() {
        return loginedNum;
    }

    public void setLoginedNum(Integer loginedNum) {
        this.loginedNum = loginedNum;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.loginConfId;
    }

    @Override
    public String toString() {
        return "LoginConfig{" +
        ", loginConfId=" + loginConfId +
        ", loginAccountId=" + loginAccountId +
        ", pwdErrCnt=" + pwdErrCnt +
        ", loginedNum=" + loginedNum +
        ", lastLoginTime=" + lastLoginTime +
        "}";
    }
}
