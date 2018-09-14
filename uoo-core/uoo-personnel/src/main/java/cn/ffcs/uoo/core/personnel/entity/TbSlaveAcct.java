package cn.ffcs.uoo.core.personnel.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 从账号
 * </p>
 *
 * @author zhanglu
 * @since 2018-09-14
 */
@TableName("tb_slave_acct")
public class TbSlaveAcct extends Model<TbSlaveAcct> {

    private static final long serialVersionUID = 1L;

    /**
     * 从账号标识
     */
    @TableId("SLAVE_ACCT_ID")
    private BigDecimal slaveAcctId;
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
     * 资源标识
     */
    @TableField("RESOURCE_ID")
    private BigDecimal resourceId;
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


    public BigDecimal getSlaveAcctId() {
        return slaveAcctId;
    }

    public void setSlaveAcctId(BigDecimal slaveAcctId) {
        this.slaveAcctId = slaveAcctId;
    }

    public String getSlaveAcct() {
        return slaveAcct;
    }

    public void setSlaveAcct(String slaveAcct) {
        this.slaveAcct = slaveAcct;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSymmetryPassword() {
        return symmetryPassword;
    }

    public void setSymmetryPassword(String symmetryPassword) {
        this.symmetryPassword = symmetryPassword;
    }

    public String getSlaveAcctType() {
        return slaveAcctType;
    }

    public void setSlaveAcctType(String slaveAcctType) {
        this.slaveAcctType = slaveAcctType;
    }

    public BigDecimal getResourceId() {
        return resourceId;
    }

    public void setResourceId(BigDecimal resourceId) {
        this.resourceId = resourceId;
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
        ", resourceId=" + resourceId +
        ", statusCd=" + statusCd +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        ", statusDate=" + statusDate +
        "}";
    }
}
