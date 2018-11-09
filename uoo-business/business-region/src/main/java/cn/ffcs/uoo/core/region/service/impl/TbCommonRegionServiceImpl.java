package cn.ffcs.uoo.core.region.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import cn.ffcs.uoo.core.region.dao.TbCommonRegionMapper;
import cn.ffcs.uoo.core.region.entity.TbCommonRegion;
import cn.ffcs.uoo.core.region.service.ITbCommonRegionService;

/**
 * <p>
 * 指对于各种专业电信管理区域的共性管理区域信息的抽象表达，包括省公司、本地网、营业区。因为使用目的不同，可以定义不同使用类型的管理区域，如：CRM使用、计费使用、营销使用等。 服务实现类
 * </p>
 *
 * @author zxs
 * @since 2018-10-30
 */
@Service
public class TbCommonRegionServiceImpl extends ServiceImpl<TbCommonRegionMapper, TbCommonRegion> implements ITbCommonRegionService {
    @Autowired
    private TbCommonRegionMapper mapper;
    @Override
    public Long getId() {
        return mapper.getId();
    }
    @SuppressWarnings("rawtypes")
    @Override
    public List<Map> selectUnionPolLoc(Map<String,Object> params) {
        return mapper.selectUnionPolLoc(params);
    }
}
