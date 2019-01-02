package cn.ffcs.uoo.system.vo;

import cn.ffcs.uoo.system.entity.SysElement;

public class SysElementVO extends SysElement{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String menuName;
    public String getMenuName() {
        return menuName;
    }
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }
}
