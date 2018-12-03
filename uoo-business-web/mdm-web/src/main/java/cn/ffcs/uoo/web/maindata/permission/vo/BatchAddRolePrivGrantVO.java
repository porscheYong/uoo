package cn.ffcs.uoo.web.maindata.permission.vo;

import java.util.Date;
import java.util.List;
/**
 * 给角色授权呢
 * @author zxs
 *
 */
public class BatchAddRolePrivGrantVO {
    private long roleId;//角色id
    private List<Long> privIds;//选中的功能权限 数据权限
    private long operateUser;
    /**
     * 生效时间
     */
    private Date effDate;
    /**
     * 失效时间
     */
    private Date expDate;
    public Date getEffDate() {
        return effDate;
    }
    public void setEffDate(Date effDate) {
        this.effDate = effDate;
    }
    public Date getExpDate() {
        return expDate;
    }
    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }
    public long getOperateUser() {
        return operateUser;
    }
    public void setOperateUser(long operateUser) {
        this.operateUser = operateUser;
    }
    public long getRoleId() {
        return roleId;
    }
    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }
    public List<Long> getPrivIds() {
        return privIds;
    }
    public void setPrivIds(List<Long> privIds) {
        this.privIds = privIds;
    }
    
}
