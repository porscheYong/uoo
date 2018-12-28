package cn.ffcs.uoo.core.user.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
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
     * 人员标识
     */
    @TableField("PERSONNEL_ID")
    private Long personnelId;
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
    @JsonInclude(JsonInclude.Include.ALWAYS)
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
     *用户主体类型
     */
    @TableField("USER_HOST_TYPE")
    private String userHostType;
    /**
     * 来源
     */
    @TableField("SOURCE")
    private String source;
    /**
     * 状态
     */
    @TableField(value = "STATUS_CD", fill = FieldFill.INSERT)
    private String statusCd;
    /**
     * 创建时间
     */
    @JsonIgnore
    @TableField(value = "CREATE_DATE", fill = FieldFill.INSERT)
    private Date createDate;
    /**
     * 创建人
     */
    //@JsonIgnore
    @TableField(value = "CREATE_USER", fill = FieldFill.INSERT)
    private Long createUser;
    /**
     * 修改时间
     */
    @JsonIgnore
    @TableField(value = "UPDATE_DATE", fill = FieldFill.INSERT_UPDATE)
    private Date updateDate;
    /**
     * 修改人
     */
    //@JsonIgnore
    @TableField(value = "UPDATE_USER", fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;
    /**
     * 状态变更的时间
     */
    @JsonIgnore
    @TableField(value = "STATUS_DATE", fill = FieldFill.INSERT)
    private Date statusDate;
    /**
     * 生效时间
     */
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @TableField("ENABLE_DATE")
    private Date enableDate;
    /**
     * 失效时间
     */
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @TableField("DISABLE_DATE")
    private Date disableDate;


    @Override
    protected Serializable pkVal() {
        return this.acctId;
    }

    @Override
    public String toString() {
        return "TbAcct{" +
        ", acctId=" + acctId +
        ", personnelId=" + personnelId +
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
