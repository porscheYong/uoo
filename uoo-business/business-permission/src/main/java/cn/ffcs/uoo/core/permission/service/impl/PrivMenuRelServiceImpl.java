package cn.ffcs.uoo.core.permission.service.impl;

import cn.ffcs.uoo.core.permission.entity.PrivMenuRel;
import cn.ffcs.uoo.core.permission.dao.PrivMenuRelMapper;
import cn.ffcs.uoo.core.permission.service.IPrivMenuRelService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 定义权限关联的菜单，一个权限可包含多个菜单。 服务实现类
 * </p>
 *
 * @author zengxsh
 * @since 2019-01-15
 */
@Service
public class PrivMenuRelServiceImpl extends ServiceImpl<PrivMenuRelMapper, PrivMenuRel> implements IPrivMenuRelService {
    @Override
    public Long getId(){
        return baseMapper.getId();
    }
}
