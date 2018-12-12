package cn.ffcs.uoo.message.server.vo;

import cn.ffcs.uoo.message.server.pojo.*;

import java.util.Date;
import java.util.List;

public class TbAcctVo {
    private Long acctId;

    private String personnelId;

    private String acct;

    private String salt;

    private String password;

    private String symmetryPassword;

    private String source;

    private String statusCd;

    private Date createDate;

    private Long createUser;

    private Date updateDate;

    private Long updateUser;

    private Date statusDate;

    private String userHostType;

    private Date enableDate;

    private Date disableDate;

    private String acctType;

    //从账号标识
    private Long slaveAcctId;

    //跨域信息
    private List<TbAcctCrossRel> tbAcctCrossRel;

    //账号组织信息
    //private List<TbSalveAcctOrgRelVo> tbAcctOrgRel;
    private List<TbOrg> tbAcctOrgRel;

    //从账号信息
    private TbSlaveAcctVo tbSlaveAcct;

    //人员信息
    private TbPersonnel tbPersonnel;

    //联系方式信息
    private List<TbContact> tbContact;

    //教育信息
    private List<TbEdu> tbEdu;

    //家庭成员信息
    private List<TbFamily> tbFamily;

    //证件信息
    private List<TbCert> tbCert;

    //工作履历信息
    private List<TbPsnjobVo> tbPsnjob;

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

    public List<TbAcctCrossRel> getTbAcctCrossRel() {
        return tbAcctCrossRel;
    }

    public void setTbAcctCrossRel(List<TbAcctCrossRel> tbAcctCrossRel) {
        this.tbAcctCrossRel = tbAcctCrossRel;
    }

    public List<TbOrg> getTbAcctOrgRel() {
        return tbAcctOrgRel;
    }

    public void setTbAcctOrgRel(List<TbOrg> tbAcctOrgRel) {
        this.tbAcctOrgRel = tbAcctOrgRel;
    }

    public TbSlaveAcctVo getTbSlaveAcct() {
        return tbSlaveAcct;
    }

    public void setTbSlaveAcct(TbSlaveAcctVo tbSlaveAcct) {
        this.tbSlaveAcct = tbSlaveAcct;
    }

    public TbPersonnel getTbPersonnel() {
        return tbPersonnel;
    }

    public void setTbPersonnel(TbPersonnel tbPersonnel) {
        this.tbPersonnel = tbPersonnel;
    }

    public List<TbContact> getTbContact() {
        return tbContact;
    }

    public void setTbContact(List<TbContact> tbContact) {
        this.tbContact = tbContact;
    }

    public List<TbEdu> getTbEdu() {
        return tbEdu;
    }

    public void setTbEdu(List<TbEdu> tbEdu) {
        this.tbEdu = tbEdu;
    }

    public List<TbFamily> getTbFamily() {
        return tbFamily;
    }

    public void setTbFamily(List<TbFamily> tbFamily) {
        this.tbFamily = tbFamily;
    }

    public List<TbCert> getTbCert() {
        return tbCert;
    }

    public void setTbCert(List<TbCert> tbCert) {
        this.tbCert = tbCert;
    }

    public List<TbPsnjobVo> getTbPsnjob() {
        return tbPsnjob;
    }

    public void setTbPsnjob(List<TbPsnjobVo> tbPsnjob) {
        this.tbPsnjob = tbPsnjob;
    }

    public Long getSlaveAcctId() {
        return slaveAcctId;
    }

    public void setSlaveAcctId(Long slaveAcctId) {
        this.slaveAcctId = slaveAcctId;
    }
}
