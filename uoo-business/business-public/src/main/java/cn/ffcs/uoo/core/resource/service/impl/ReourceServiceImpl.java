package cn.ffcs.uoo.core.resource.service.impl;

import cn.ffcs.uoo.core.resource.entity.Reource;
import cn.ffcs.uoo.core.resource.dao.ReourceMapper;
import cn.ffcs.uoo.core.resource.service.IReourceService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 
 * </p>
 *
 * @author zengxsh
 * @since 2019-01-15
 */
@Service
public class ReourceServiceImpl extends ServiceImpl<ReourceMapper, Reource> implements IReourceService {
    @Override
    public Long getId(){
        return baseMapper.getId();
    }
}
