package cn.ffcs.uoo.system.vo;

import cn.ffcs.uoo.system.entity.SysOperationLog;

public class SysOperationLogVO extends SysOperationLog {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String accout;//账号
    private String menuName;// 菜单名称
    private String operateName;// 操作名称  增删改查等。
    public String getAccout() {
        return accout;
    }
    public void setAccout(String accout) {
        this.accout = accout;
    }
    public String getMenuName() {
        return menuName;
    }
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }
    public String getOperateName() {
        return operateName;
    }
    public void setOperateName(String operateName) {
        this.operateName = operateName;
    }
}
