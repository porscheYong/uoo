package cn.ffcs.uoo.system.service.impl;

import cn.ffcs.uoo.system.entity.SysPermissionFuncRel;
import cn.ffcs.uoo.system.dao.SysPermissionFuncRelMapper;
import cn.ffcs.uoo.system.service.ISysPermissionFuncRelService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 定义权限关联的功能，一个权限可包含多个功能。 服务实现类
 * </p>
 *
 * @author zengxsh
 * @since 2018-12-24
 */
@Service
public class SysPermissionFuncRelServiceImpl extends ServiceImpl<SysPermissionFuncRelMapper, SysPermissionFuncRel> implements ISysPermissionFuncRelService {
    @Override
    public Long getId() {
        return baseMapper.getId();
    }
}
