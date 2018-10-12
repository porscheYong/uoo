package cn.ffcs.uoo.core.user.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 从账号主账号关系
 * </p>
 *
 * @author zhanglu
 * @since 2018-09-14
 */
@TableName("tb_slvacct_acct_ref")
public class TbSlvacctAcctRef extends Model<TbSlvacctAcctRef> {

    private static final long serialVersionUID = 1L;

    /**
     * 从账号主账号关系标识
     */
    @TableId("SLVACCT_ACCT_ID")
    private BigDecimal slvacctAcctId;
    /**
     * 主账号标识
     */
    @TableField("ACCT_ID")
    private BigDecimal acctId;
    /**
     * 从账号标识
     */
    @TableField("SLAVE_ACCT_ID")
    private BigDecimal slaveAcctId;
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
    private BigDecimal createUser;
    /**
     * 修改时间
     */
    @TableField("UPDATE_DATE")
    private Date updateDate;
    /**
     * 修改人
     */
    @TableField("UPDATE_USER")
    private BigDecimal updateUser;
    /**
     * 状态变更的时间
     */
    @TableField("STATUS_DATE")
    private Date statusDate;


    public BigDecimal getSlvacctAcctId() {
        return slvacctAcctId;
    }

    public void setSlvacctAcctId(BigDecimal slvacctAcctId) {
        this.slvacctAcctId = slvacctAcctId;
    }

    public BigDecimal getAcctId() {
        return acctId;
    }

    public void setAcctId(BigDecimal acctId) {
        this.acctId = acctId;
    }

    public BigDecimal getSlaveAcctId() {
        return slaveAcctId;
    }

    public void setSlaveAcctId(BigDecimal slaveAcctId) {
        this.slaveAcctId = slaveAcctId;
    }

    public String getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(String statusCd) {
        this.statusCd = statusCd;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public BigDecimal getCreateUser() {
        return createUser;
    }

    public void setCreateUser(BigDecimal createUser) {
        this.createUser = createUser;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public BigDecimal getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(BigDecimal updateUser) {
        this.updateUser = updateUser;
    }

    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    @Override
    protected Serializable pkVal() {
        return this.slvacctAcctId;
    }

    @Override
    public String toString() {
        return "TbSlvacctAcctRef{" +
        ", slvacctAcctId=" + slvacctAcctId +
        ", acctId=" + acctId +
        ", slaveAcctId=" + slaveAcctId +
        ", statusCd=" + statusCd +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        ", statusDate=" + statusDate +
        "}";
    }
}
