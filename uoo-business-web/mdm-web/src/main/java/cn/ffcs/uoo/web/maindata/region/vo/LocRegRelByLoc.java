package cn.ffcs.uoo.web.maindata.region.vo;

import java.util.List;

public class LocRegRelByLoc {
    private Long locId;
    private List<Long> regIds;
    private Long createUser;
    public Long getLocId() {
        return locId;
    }
    public void setLocId(Long locId) {
        this.locId = locId;
    }
    public List<Long> getRegIds() {
        return regIds;
    }
    public void setRegIds(List<Long> regIds) {
        this.regIds = regIds;
    }
    public Long getCreateUser() {
        return createUser;
    }
    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }
    
}
