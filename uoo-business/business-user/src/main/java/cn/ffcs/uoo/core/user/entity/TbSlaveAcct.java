package cn.ffcs.uoo.core.user.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
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
     * 来源
     */
    @TableField("SOURCE")
    private String source;
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
     * 业务系统标识
     */
    @TableField("BUSINESS_SYSTEM_ID")
    private Long businessSystemId;
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
        ", businessSystemId=" + businessSystemId +
        ", statusCd=" + statusCd +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        ", statusDate=" + statusDate +
        "}";
    }
}
