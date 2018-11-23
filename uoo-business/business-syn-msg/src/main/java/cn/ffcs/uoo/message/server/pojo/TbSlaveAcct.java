package cn.ffcs.uoo.message.server.pojo;

import com.baomidou.mybatisplus.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

public class TbSlaveAcct extends Model<TbSlaveAcct> {
    private Long slaveAcctId;

    private String slaveAcct;

    private String salt;

    private String password;

    private String symmetryPassword;

    private String slaveAcctType;

    private Long resourceObjId;

    private String statusCd;

    private Date createDate;

    private Long createUser;

    private Date updateDate;

    private Long updateUser;

    private Date statusDate;

    private Long acctHostId;

    private Long acctId;

    private Date enableDate;

    private Date disableDate;

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

    @Override
    protected Serializable pkVal() {
        return this.slaveAcctId;
    }
}