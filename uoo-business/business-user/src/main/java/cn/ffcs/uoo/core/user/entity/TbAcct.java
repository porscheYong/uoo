package cn.ffcs.uoo.core.user.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 主账号
 * </p>
 *
 * @author zhanglu
 * @since 2018-09-14
 */
@TableName("tb_acct")
@Data
public class TbAcct extends Model<TbAcct> {

    private static final long serialVersionUID = 1L;

    /**
     * 主账号标识
     */
    @TableId("ACCT_ID")
    private Long acctId;
    /**
     * 用户标识
     */
    @TableField("USER_ID")
    private String userId;
    /**
     * 工号
     */
    @TableField("ACCT_NBR")
    private String acctNbr;
    /**
     * 手机号
     */
    @TableField("ACCT_PHONE")
    private String acctPhone;
    /**
     * 邮箱
     */
    @TableField("ACCT_EMAIL")
    private String acctEmail;
    /**
     * 身份证
     */
    @TableField("ACCT_CERT")
    private String acctCert;
    /**
     * 主账号
     */
    @TableField("ACCT")
    private String acct;
    /**
     * SALT
     */
    @TableField("SALT")
    private String salt;
    /**
     * 非对称密码
     */
    @TableField("PASSWORD")
    private String password;
    /**
     * 对称密码
     */
    @TableField("SYMMETRY_PASSWORD")
    private String symmetryPassword;
    /**
     *账号类别/性质
     */
    @TableField("ACCT_TYPE")
    private String acctType;
    /**
     * 来源
     */
    @TableField("SOURCE")
    private String source;
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


    @Override
    protected Serializable pkVal() {
        return this.acctId;
    }

    @Override
    public String toString() {
        return "TbAcct{" +
        ", acctId=" + acctId +
        ", userId=" + userId +
        ", acct=" + acct +
        ", salt=" + salt +
        ", password=" + password +
        ", symmetryPassword=" + symmetryPassword +
        ", source=" + source +
        ", statusCd=" + statusCd +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        ", statusDate=" + statusDate +
        "}";
    }
}
