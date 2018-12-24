package cn.ffcs.uoo.system.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import cn.ffcs.uoo.system.entity.SysRolePermissionRef;
import cn.ffcs.uoo.system.dao.SysRolePermissionRefMapper;
import cn.ffcs.uoo.system.service.ISysRolePermissionRefService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zengxsh
 * @since 2018-12-20
 */
@Service
public class SysRolePermissionRefServiceImpl extends ServiceImpl<SysRolePermissionRefMapper, SysRolePermissionRef> implements ISysRolePermissionRefService {

    @Override
    public Long getId() {
        return baseMapper.getId();
    }
    @Override
    public void updatePermissionCode(String oldCode, String newCode) {
        baseMapper.updatePermissionCode(oldCode,newCode);
    }

}
