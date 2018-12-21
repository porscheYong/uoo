package cn.ffcs.uoo.system.service.impl;

import cn.ffcs.uoo.system.dao.SysMenuMapper;
import cn.ffcs.uoo.system.dao.SysRoleMapper;
import cn.ffcs.uoo.system.entity.SysMenu;
import cn.ffcs.uoo.system.entity.SysRole;
import cn.ffcs.uoo.system.service.SysMenuService;
import cn.ffcs.uoo.system.service.SysRoleService;
import cn.ffcs.uoo.system.vo.SysRoleDTO;

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
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {


    @Override
    public Long getId() {
        return baseMapper.getId();
    }

    @Override
    public List<SysRoleDTO> selectList(HashMap<String, Object> map) {
        return baseMapper.selectList(map);
    }

    @Override
    public Long countList(HashMap<String, Object> map) {
        return baseMapper.countList(map);
    }

    @Override
    public SysRoleDTO selectOne(Long ROLE_ID) {
        return baseMapper.selectOne(ROLE_ID);
    }
 
}
