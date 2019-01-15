package cn.ffcs.uoo.core.permission.service.impl;

import cn.ffcs.uoo.core.permission.entity.PrivFileRel;
import cn.ffcs.uoo.core.permission.dao.PrivFileRelMapper;
import cn.ffcs.uoo.core.permission.service.IPrivFileRelService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 定义权限关联的文件，一个权限可包含多个文件。 服务实现类
 * </p>
 *
 * @author zengxsh
 * @since 2019-01-15
 */
@Service
public class PrivFileRelServiceImpl extends ServiceImpl<PrivFileRelMapper, PrivFileRel> implements IPrivFileRelService {
    @Override
    public Long getId(){
        return baseMapper.getId();
    }
}
