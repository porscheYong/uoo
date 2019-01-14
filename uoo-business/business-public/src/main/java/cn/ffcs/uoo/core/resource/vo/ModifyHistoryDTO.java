package cn.ffcs.uoo.core.resource.vo;

import cn.ffcs.uoo.core.resource.entity.ModifyHistory;

public class ModifyHistoryDTO extends ModifyHistory{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String userAccout;
    private String userName;
    private String userOrgName;
    public String getUserAccout() {
        return userAccout;
    }
    public void setUserAccout(String userAccout) {
        this.userAccout = userAccout;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserOrgName() {
        return userOrgName;
    }
    public void setUserOrgName(String userOrgName) {
        this.userOrgName = userOrgName;
    }
}
