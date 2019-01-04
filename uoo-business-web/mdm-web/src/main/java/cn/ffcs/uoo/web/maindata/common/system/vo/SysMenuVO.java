package cn.ffcs.uoo.web.maindata.common.system.vo;

import com.baomidou.mybatisplus.annotations.TableName;

import cn.ffcs.uoo.web.maindata.common.system.dto.SysMenu;

/**
 * <p>
 * 权限登记时菜单所有和明细路径，如 user/*,user/add、user/del
 * </p>
 *
 * @author zengxsh
 * @since 2018-12-17
 */
public class SysMenuVO extends SysMenu {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String parentMenuName;
    public String getParentMenuName() {
        return parentMenuName;
    }
    public void setParentMenuName(String parentMenuName) {
        this.parentMenuName = parentMenuName;
    }
    
}
