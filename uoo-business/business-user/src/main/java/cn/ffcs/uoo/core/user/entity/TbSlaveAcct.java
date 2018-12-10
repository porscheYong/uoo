package cn.ffcs.uoo.core.user.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 从账号
 * </p>
 *
 * @author wudj
 * @since 2018-10-30
 */
@TableName("tb_slave_acct")
@Data
public class TbSlaveAcct extends Model<TbSlaveAcct> {

    private static final long serialVersionUID = 1L;

    /**
     * 从账号标识
     */
    @TableId("SLAVE_ACCT_ID")
    private Long slaveAcctId;
    /**
     * 主账号组织关系标识
     */
    @TableField("ACCT_HOST_ID")
    private Long acctHostId;
    /**
     * 主账号标识
     */
    @TableField("ACCT_ID")
    private Long acctId;
    /**
     * 从账号
     */
    @TableField("SLAVE_ACCT")
    private String slaveAcct;
    /**
     * SALT
     */
    @TableField("SALT")
    private String salt;
    /**
     * 密码
     */
    @TableField("PASSWORD")
    private String password;
    /**
     * 资源标识
     */
    @TableField("RESOURCE_OBJ_ID")
    private Long resourceObjId;
    /**
     * 密码可被解密的
     */
    @TableField("SYMMETRY_PASSWORD")
    private String symmetryPassword;
    /**
     * 主机、中间件、数据库、应用
     */
    @TableField("SLAVE_ACCT_TYPE")
    private String slaveAcctType;
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
    @JsonIgnore
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
    @JsonIgnore
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
        return this.slaveAcctId;
    }

    @Override
    public String toString() {
        return "TbSlaveAcct{" +
        ", slaveAcctId=" + slaveAcctId +
        ", slaveAcct=" + slaveAcct +
        ", salt=" + salt +
        ", password=" + password +
        ", symmetryPassword=" + symmetryPassword +
        ", slaveAcctType=" + slaveAcctType +
        ", statusCd=" + statusCd +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        ", statusDate=" + statusDate +
                ", enableDate=" + enableDate +
                ", disableDate=" + disableDate +
        "}";
    }
}
