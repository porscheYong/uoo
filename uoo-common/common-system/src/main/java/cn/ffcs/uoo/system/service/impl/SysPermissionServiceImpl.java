package cn.ffcs.uoo.system.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import cn.ffcs.uoo.system.dao.SysPermissionMapper;
import cn.ffcs.uoo.system.entity.SysPermission;
import cn.ffcs.uoo.system.service.ISysPermissionService;
import cn.ffcs.uoo.system.vo.SysPermissionDTO;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zengxsh
 * @since 2018-12-24
 */
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements ISysPermissionService {

    @Override
    public List<SysPermissionDTO> findList(HashMap<String, Object> map) {
        return baseMapper.findList(map);
    }

    @Override
    public Long countList(HashMap<String, Object> map) {
        return baseMapper.countList(map);
    }

    @Override
    public Long getId() {
        return baseMapper.getId();
    }

    @Override
    public SysPermissionDTO selectOne(Long id) {
        return baseMapper.selectOne(id);
    }

    @Override
    public List<SysPermission> listByRoleCode(String roleCode) {
        return baseMapper.listByRoleCode(roleCode);
    }

}
