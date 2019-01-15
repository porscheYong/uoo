package cn.ffcs.interfaces.cpc.pojo;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

@TableName("Tb_Acct")
public class TbAcct extends Model<TbAcct> {
    @TableId(value="acct_Id",type = IdType.INPUT)
    private Long acctId;

    @TableField("personnel_Id")
    private String personnelId;

    @TableField("acct")
    private String acct;

    @TableField("salt")
    private String salt;

    @TableField("password")
    private String password;

    @TableField("symmetry_Password")
    private String symmetryPassword;

    @TableField("source")
    private String source;

    @TableField("status_Cd")
    private String statusCd;

    @TableField("create_Date")
    private Date createDate;

    @TableField("create_User")
    private Long createUser;

    @TableField("update_Date")
    private Date updateDate;

    @TableField("update_User")
    private Long updateUser;

    @TableField("status_Date")
    private Date statusDate;

    @TableField("user_Host_Type")
    private String userHostType;

    @TableField("enable_Date")
    private Date enableDate;

    @TableField("disable_Date")
    private Date disableDate;

    @TableField("acct_Type")
    private String acctType;

    public TbAcct() {
    }

    public TbAcct(String personnelId, String acct, String salt, String password, String statusCd, Date createDate, String userHostType, Date enableDate, Date disableDate, String acctType) {
        this.personnelId = personnelId;
        this.acct = acct;
        this.salt = salt;
        this.password = password;
        this.statusCd = statusCd;
        this.createDate = createDate;
        this.userHostType = userHostType;
        this.enableDate = enableDate;
        this.disableDate = disableDate;
        this.acctType = acctType;
    }

    public Long getAcctId() {
        return acctId;
    }

    public void setAcctId(Long acctId) {
        this.acctId = acctId;
    }

    public String getPersonnelId() {
        return personnelId;
    }

    public void setPersonnelId(String personnelId) {
        this.personnelId = personnelId;
    }

    public String getAcct() {
        return acct;
    }

    public void setAcct(String acct) {
        this.acct = acct;
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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

    public String getUserHostType() {
        return userHostType;
    }

    public void setUserHostType(String userHostType) {
        this.userHostType = userHostType;
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

    public String getAcctType() {
        return acctType;
    }

    public void setAcctType(String acctType) {
        this.acctType = acctType;
    }

    @Override
    protected Serializable pkVal() {
        return this.acctId;
    }
}