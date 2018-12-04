package cn.ffcs.uoo.system.service.impl;

import cn.ffcs.uoo.system.dao.SysMenuMapper;
import cn.ffcs.uoo.system.dao.SysRoleMapper;
import cn.ffcs.uoo.system.entity.SysMenu;
import cn.ffcs.uoo.system.entity.SysRole;
import cn.ffcs.uoo.system.service.SysMenuService;
import cn.ffcs.uoo.system.service.SysRoleService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/*
 * 系统域用户Service实现类
 * Created by liuxiaodong on 2018/11/12.
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    SysRoleMapper sysRoleMapper;

    @Override
    public Long getId() {
        return sysRoleMapper.getId();
    }
}
