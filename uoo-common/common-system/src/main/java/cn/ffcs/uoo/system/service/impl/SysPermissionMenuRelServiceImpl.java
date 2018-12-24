package cn.ffcs.uoo.system.service.impl;

import cn.ffcs.uoo.system.entity.SysPermissionMenuRel;
import cn.ffcs.uoo.system.dao.SysPermissionMenuRelMapper;
import cn.ffcs.uoo.system.service.ISysPermissionMenuRelService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 定义权限关联的菜单，一个权限可包含多个菜单。 服务实现类
 * </p>
 *
 * @author zengxsh
 * @since 2018-12-24
 */
@Service
public class SysPermissionMenuRelServiceImpl extends ServiceImpl<SysPermissionMenuRelMapper, SysPermissionMenuRel> implements ISysPermissionMenuRelService {
    @Override
    public Long getId() {
        return baseMapper.getId();
    }
}
