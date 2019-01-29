package cn.ffcs.uoo.system.dao;


import java.util.HashMap;
import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import cn.ffcs.uoo.system.entity.SysMenu;
import cn.ffcs.uoo.system.vo.PermMenu;

public interface SysMenuMapper extends BaseMapper<SysMenu> {
    Long getId();
    List<SysMenu> getMenuByAccout(HashMap<String, Object> map);
    public List<PermMenu> listByPermissionId(Long permId);
}