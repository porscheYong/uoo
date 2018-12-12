package cn.ffcs.uoo.system.dao;


import java.util.HashMap;
import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import cn.ffcs.uoo.system.entity.SysMenu;

public interface SysMenuMapper extends BaseMapper<SysMenu> {
    Long getId();
    List<SysMenu> getMenuByAccout(HashMap<String, Object> map);
}