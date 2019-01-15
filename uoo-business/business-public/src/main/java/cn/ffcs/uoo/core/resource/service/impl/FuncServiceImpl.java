package cn.ffcs.uoo.core.resource.service.impl;

import cn.ffcs.uoo.core.resource.entity.Func;
import cn.ffcs.uoo.core.resource.dao.FuncMapper;
import cn.ffcs.uoo.core.resource.service.IFuncService;
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
public class FuncServiceImpl extends ServiceImpl<FuncMapper, Func> implements IFuncService {
    @Override
    public Integer getId(){
        return baseMapper.getId();
    }
}
