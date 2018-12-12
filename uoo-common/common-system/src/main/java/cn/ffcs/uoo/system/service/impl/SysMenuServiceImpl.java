package cn.ffcs.uoo.system.service.impl;

import cn.ffcs.uoo.system.dao.SysDictMapper;
import cn.ffcs.uoo.system.dao.SysMenuMapper;
import cn.ffcs.uoo.system.entity.SysDict;
import cn.ffcs.uoo.system.entity.SysMenu;
import cn.ffcs.uoo.system.service.SysDictService;
import cn.ffcs.uoo.system.service.SysMenuService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/*
 * 系统域用户Service实现类
 * Created by liuxiaodong on 2018/11/12.
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    SysMenuMapper sysMenuMapper;

    @Override
    public Long getId() {
        return sysMenuMapper.getId();
    }

    @Override
    public List<SysMenu> getMenuByAccout(HashMap<String, Object> map) {
        return sysMenuMapper.getMenuByAccout(map);
    }
}
