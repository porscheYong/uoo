package cn.ffcs.uoo.core.region.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import cn.ffcs.uoo.core.region.dao.TbPoliticalLocationMapper;
import cn.ffcs.uoo.core.region.entity.TbPoliticalLocation;
import cn.ffcs.uoo.core.region.service.ITbPoliticalLocationService;

/**
 * <p>
 * 行政区域 服务实现类
 * </p>
 *
 * @author zxs
 * @since 2018-10-30
 */
@Service
public class TbPoliticalLocationServiceImpl extends ServiceImpl<TbPoliticalLocationMapper, TbPoliticalLocation> implements ITbPoliticalLocationService {
    @Autowired
    private TbPoliticalLocationMapper mapper;
    @Override
    public Long getId() {
        return mapper.getId();
    }
}
