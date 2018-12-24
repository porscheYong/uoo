package cn.ffcs.uoo.system.service.impl;

import cn.ffcs.uoo.system.entity.SysPermissionElementRel;
import cn.ffcs.uoo.system.dao.SysPermissionElementRelMapper;
import cn.ffcs.uoo.system.service.ISysPermissionElementRelService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 定义权限关联的元素，一个权限可包含多个元素。 服务实现类
 * </p>
 *
 * @author zengxsh
 * @since 2018-12-24
 */
@Service
public class SysPermissionElementRelServiceImpl extends ServiceImpl<SysPermissionElementRelMapper, SysPermissionElementRel> implements ISysPermissionElementRelService {
    @Override
    public Long getId() {
        return baseMapper.getId();
    }
}
