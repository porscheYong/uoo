package cn.ffcs.uoo.core.resource.service.impl;

import cn.ffcs.uoo.core.resource.entity.Element;
import cn.ffcs.uoo.core.resource.dao.ElementMapper;
import cn.ffcs.uoo.core.resource.service.IElementService;
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
public class ElementServiceImpl extends ServiceImpl<ElementMapper, Element> implements IElementService {
    @Override
    public Integer getId(){
        return baseMapper.getId();
    }
}
