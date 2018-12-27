package cn.ffcs.uoo.message.server.pojo;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

@TableName("TB_SLAVE_ACCT")
public class TbSlaveAcct extends Model<TbSlaveAcct> {

    @TableId("SLAVE_ACCT_ID")
    private Long slaveAcctId;

    @TableField("SLAVE_ACCT")
    private String slaveAcct;

    @TableField("SALT")
    private String salt;

    @TableField("PASSWORD")
    private String password;

    @TableField("SYMMETRY_PASSWORD")
    private String symmetryPassword;

    @TableField("SLAVE_ACCT_TYPE")
    private String slaveAcctType;

    @TableField("RESOURCE_OBJ_ID")
    private Long resourceObjId;

    @TableField("STATUS_CD")
    private String statusCd;

    @TableField("CREATE_DATE")
    private Date createDate;

    @TableField("CREATE_USER")
    private Long createUser;

    @TableField("UPDATE_DATE")
    private Date updateDate;

    @TableField("UPDATE_USER")
    private Long updateUser;

    @TableField("STATUS_DATE")
    private Date statusDate;

    @TableField("ACCT_HOST_ID")
    private Long acctHostId;

    @TableField("ACCT_ID")
    private Long acctId;

    @TableField("ENABLE_DATE")
    private Date enableDate;

    @TableField("DISABLE_DATE")
    private Date disableDate;

/*    @TableField("MAPP_STAFF_ID")
    private String mappStaffId;*/

    public Long getSlaveAcctId() {
        return slaveAcctId;
    }

    public void setSlaveAcctId(Long slaveAcctId) {
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

    public Long getResourceObjId() {
        return resourceObjId;
    }

    public void setResourceObjId(Long resourceObjId) {
        this.resourceObjId = resourceObjId;
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

    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    public Long getAcctHostId() {
        return acctHostId;
    }

    public void setAcctHostId(Long acctHostId) {
        this.acctHostId = acctHostId;
    }

    public Long getAcctId() {
        return acctId;
    }

    public void setAcctId(Long acctId) {
        this.acctId = acctId;
    }

    public Date getEnableDate() {
        return enableDate;
    }

    public void setEnableDate(Date enableDate) {
        this.enableDate = enableDate;
    }

    public Date getDisableDate() {
        return disableDate;
    }

    public void setDisableDate(Date disableDate) {
        this.disableDate = disableDate;
    }

   /* public String getMappStaffId() {
        return mappStaffId;
    }

    public void setMappStaffId(String mappStaffId) {
        this.mappStaffId = mappStaffId;
    }*/

    @Override
    protected Serializable pkVal() {
        return this.slaveAcctId;
    }
}