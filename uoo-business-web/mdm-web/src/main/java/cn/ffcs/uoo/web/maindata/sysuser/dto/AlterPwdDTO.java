package cn.ffcs.uoo.web.maindata.sysuser.dto;


public class AlterPwdDTO {

    private String accout;

    private String passwd;

    private String newPwd;

    private String newPwd2;

    public String getAccout() {
        return accout;
    }

    public void setAccout(String accout) {
        this.accout = accout;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }

    public String getNewPwd2() {
        return newPwd2;
    }

    public void setNewPwd2(String newPwd2) {
        this.newPwd2 = newPwd2;
    }
}
