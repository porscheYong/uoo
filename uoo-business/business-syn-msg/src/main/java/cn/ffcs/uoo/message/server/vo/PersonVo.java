package cn.ffcs.uoo.message.server.vo;

import java.util.List;

public class PersonVo {

    private String accountId;
    private String psnName;
    private String psnCode;
    private String ncCode;
    private String psnNbr;
    private String nationality;
    private String gender;
    private String nation;
    private String marriage;
    private String pliticalStatus;
    private String reason;
    private String toWorkTime;
    private int servingAge;
    private int lengthService;
    private String uuid;
    private String account;
    private String salt;
    private String password;
    private String symmetryPassword;
    private String resource;
    private String statusCd;

    private ExtendVo extend;

    private List<AccountOrgRefVo> accountOrgRefs;

    private List<SlaveAccountVo> slaveAccounts;

    private List<UserCrossTranVo> userCrossTrans;

    private List<ContactVo> contacts;

    private List<EduVo> edus;

    private List<FamilyVo> familys;

    private List<CertVo> certs;

    private List<PsnjobVo> psnjobs;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getPsnName() {
        return psnName;
    }

    public void setPsnName(String psnName) {
        this.psnName = psnName;
    }

    public String getPsnCode() {
        return psnCode;
    }

    public void setPsnCode(String psnCode) {
        this.psnCode = psnCode;
    }

    public String getNcCode() {
        return ncCode;
    }

    public void setNcCode(String ncCode) {
        this.ncCode = ncCode;
    }

    public String getPsnNbr() {
        return psnNbr;
    }

    public void setPsnNbr(String psnNbr) {
        this.psnNbr = psnNbr;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getMarriage() {
        return marriage;
    }

    public void setMarriage(String marriage) {
        this.marriage = marriage;
    }

    public String getPliticalStatus() {
        return pliticalStatus;
    }

    public void setPliticalStatus(String pliticalStatus) {
        this.pliticalStatus = pliticalStatus;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getToWorkTime() {
        return toWorkTime;
    }

    public void setToWorkTime(String toWorkTime) {
        this.toWorkTime = toWorkTime;
    }

    public int getServingAge() {
        return servingAge;
    }

    public void setServingAge(int servingAge) {
        this.servingAge = servingAge;
    }

    public int getLengthService() {
        return lengthService;
    }

    public void setLengthService(int lengthService) {
        this.lengthService = lengthService;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(String statusCd) {
        this.statusCd = statusCd;
    }

    public ExtendVo getExtend() {
        return extend;
    }

    public void setExtend(ExtendVo extend) {
        this.extend = extend;
    }

    public List<AccountOrgRefVo> getAccountOrgRefs() {
        return accountOrgRefs;
    }

    public void setAccountOrgRefs(List<AccountOrgRefVo> accountOrgRefs) {
        this.accountOrgRefs = accountOrgRefs;
    }

    public List<SlaveAccountVo> getSlaveAccounts() {
        return slaveAccounts;
    }

    public void setSlaveAccounts(List<SlaveAccountVo> slaveAccounts) {
        this.slaveAccounts = slaveAccounts;
    }

    public List<UserCrossTranVo> getUserCrossTrans() {
        return userCrossTrans;
    }

    public void setUserCrossTrans(List<UserCrossTranVo> userCrossTrans) {
        this.userCrossTrans = userCrossTrans;
    }

    public List<ContactVo> getContacts() {
        return contacts;
    }

    public void setContacts(List<ContactVo> contacts) {
        this.contacts = contacts;
    }

    public List<EduVo> getEdus() {
        return edus;
    }

    public void setEdus(List<EduVo> edus) {
        this.edus = edus;
    }

    public List<FamilyVo> getFamilys() {
        return familys;
    }

    public void setFamilys(List<FamilyVo> familys) {
        this.familys = familys;
    }

    public List<CertVo> getCerts() {
        return certs;
    }

    public void setCerts(List<CertVo> certs) {
        this.certs = certs;
    }

    public List<PsnjobVo> getPsnjobs() {
        return psnjobs;
    }

    public void setPsnjobs(List<PsnjobVo> psnjobs) {
        this.psnjobs = psnjobs;
    }
}