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
@TableName("TB_USER_PASSWD_HIS")
public class UserPasswdHis extends Model<UserPasswdHis> {

    private static final long serialVersionUID = 1L;

    /**
     * 记录系统用户的主键。
     */
    @TableId("PASSWD_HIS_ID")
    private Long passwdHisId;
    /**
     * 记录系统用户的主键。
     */
    @TableField("LOGIN_ACCOUNT_ID")
    private Long loginAccountId;
    /**
     * 密码
     */
    @TableField("PASSWORD")
    private String password;
    /**
     * 记录系统用户创建时间
     */
    @TableField("CREATE_DATE")
    private Date createDate;


    public Long getPasswdHisId() {
        return passwdHisId;
    }

    public void setPasswdHisId(Long passwdHisId) {
        this.passwdHisId = passwdHisId;
    }

    public Long getLoginAccountId() {
        return loginAccountId;
    }

    public void setLoginAccountId(Long loginAccountId) {
        this.loginAccountId = loginAccountId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    protected Serializable pkVal() {
        return this.passwdHisId;
    }

    @Override
    public String toString() {
        return "UserPasswdHis{" +
        ", passwdHisId=" + passwdHisId +
        ", loginAccountId=" + loginAccountId +
        ", password=" + password +
        ", createDate=" + createDate +
        "}";
    }
}
