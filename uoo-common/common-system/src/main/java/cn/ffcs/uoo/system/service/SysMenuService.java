package cn.ffcs.uoo.system.service;

import java.util.HashMap;
import java.util.List;

import com.baomidou.mybatisplus.service.IService;

import cn.ffcs.uoo.system.entity.SysMenu;
import cn.ffcs.uoo.system.vo.PermMenu;

/**
 * 系统域用户Service接口
 * Created by liuxiaodong on 2018/11/12.
 */
public interface SysMenuService extends IService<SysMenu> {
    List<SysMenu> getMenuByAccout(HashMap<String, Object> map);
    Long getId();
    public List<PermMenu> listByPermissionId(Long permId);
}
