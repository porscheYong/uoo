package cn.ffcs.uoo.web.maindata.region.vo;

import java.util.List;

public class LocRegRelByReg {
    private Long regId;
    private List<Long> locIds;
    private Long createUser;
    public Long getRegId() {
        return regId;
    }
    public void setRegId(Long regId) {
        this.regId = regId;
    }
    public List<Long> getLocIds() {
        return locIds;
    }
    public void setLocIds(List<Long> locIds) {
        this.locIds = locIds;
    }
    public Long getCreateUser() {
        return createUser;
    }
    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }
     
    
}
