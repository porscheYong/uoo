package cn.ffcs.uoo.core.resource.service.impl;

import cn.ffcs.uoo.core.resource.entity.BusinessFuncMap;
import cn.ffcs.uoo.core.resource.dao.BusinessFuncMapMapper;
import cn.ffcs.uoo.core.resource.service.IBusinessFuncMapService;
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
public class BusinessFuncMapServiceImpl extends ServiceImpl<BusinessFuncMapMapper, BusinessFuncMap> implements IBusinessFuncMapService {
    @Override
    public Integer getId(){
        return baseMapper.getId();
    }
}
