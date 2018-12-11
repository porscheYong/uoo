package cn.ffcs.uoo.core.region.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import cn.ffcs.uoo.core.region.dao.TbRegionLocationRelMapper;
import cn.ffcs.uoo.core.region.entity.TbRegionLocationRel;
import cn.ffcs.uoo.core.region.service.ITbRegionLocationRelService;

/**
 * <p>
 * 行政区域和公用管理区域关系。 服务实现类
 * </p>
 *
 * @author zxs
 * @since 2018-10-30
 */
@Service
public class TbRegionLocationRelServiceImpl extends ServiceImpl<TbRegionLocationRelMapper, TbRegionLocationRel> implements ITbRegionLocationRelService {
    @Autowired
    private TbRegionLocationRelMapper mapper;
    @Override
    public Long getId() {
        return mapper.getId();
    }
}
