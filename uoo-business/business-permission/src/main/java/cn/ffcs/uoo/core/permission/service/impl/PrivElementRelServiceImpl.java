package cn.ffcs.uoo.core.permission.service.impl;

import cn.ffcs.uoo.core.permission.entity.PrivElementRel;
import cn.ffcs.uoo.core.permission.dao.PrivElementRelMapper;
import cn.ffcs.uoo.core.permission.service.IPrivElementRelService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 定义权限关联的元素，一个权限可包含多个元素。 服务实现类
 * </p>
 *
 * @author zengxsh
 * @since 2019-01-15
 */
@Service
public class PrivElementRelServiceImpl extends ServiceImpl<PrivElementRelMapper, PrivElementRel> implements IPrivElementRelService {
    @Override
    public Long getId(){
        return baseMapper.getId();
    }
}
