package cn.ffcs.uoo.system.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import cn.ffcs.uoo.system.dao.SysMenuMapper;
import cn.ffcs.uoo.system.entity.SysMenu;
import cn.ffcs.uoo.system.service.SysMenuService;
import cn.ffcs.uoo.system.vo.PermMenu;


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

    @Override
    public List<PermMenu> listByPermissionId(Long permId) {
        return baseMapper.listByPermissionId(permId);
    }
}
