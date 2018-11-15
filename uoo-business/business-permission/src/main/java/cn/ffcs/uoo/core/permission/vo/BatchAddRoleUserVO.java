package cn.ffcs.uoo.core.permission.vo;

import java.util.List;

public class BatchAddRoleUserVO {
    private long roleId;
    private List<UserTypeVO> users;
    private long operateUser;
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
    public List<UserTypeVO> getUsers() {
        return users;
    }
    public void setUsers(List<UserTypeVO> users) {
        this.users = users;
    }
    
    
}
