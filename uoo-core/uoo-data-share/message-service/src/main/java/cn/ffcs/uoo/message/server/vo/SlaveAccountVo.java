package cn.ffcs.uoo.message.server.vo;

import java.util.List;

public class SlaveAccountVo {

    private String slaveAccount;
    private String salt;
    private String password;
    private String symmetryPassword;
    private String slaveAcctType;
    private String resourceId;
    private List<AccountOrgRefVo> accountOrgRefs;

    public String getSlaveAccount() {
        return slaveAccount;
    }

    public void setSlaveAccount(String slaveAccount) {
        this.slaveAccount = slaveAccount;
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

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public List<AccountOrgRefVo> getAccountOrgRefs() {
        return accountOrgRefs;
    }

    public void setAccountOrgRefs(List<AccountOrgRefVo> accountOrgRefs) {
        this.accountOrgRefs = accountOrgRefs;
    }
}
