package cn.ffcs.uoo.core.resource.service.impl;

import cn.ffcs.uoo.core.resource.entity.BusinessFunc;
import cn.ffcs.uoo.core.resource.dao.BusinessFuncMapper;
import cn.ffcs.uoo.core.resource.service.IBusinessFuncService;
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
public class BusinessFuncServiceImpl extends ServiceImpl<BusinessFuncMapper, BusinessFunc> implements IBusinessFuncService {
    @Override
    public Integer getId(){
        return baseMapper.getId();
    }
}
