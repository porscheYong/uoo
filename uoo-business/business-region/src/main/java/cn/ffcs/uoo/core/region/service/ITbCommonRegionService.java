package cn.ffcs.uoo.core.region.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

import cn.ffcs.uoo.core.region.entity.TbCommonRegion;

/**
 * <p>
 * 指对于各种专业电信管理区域的共性管理区域信息的抽象表达，包括省公司、本地网、营业区。因为使用目的不同，可以定义不同使用类型的管理区域，如：CRM使用、计费使用、营销使用等。 服务类
 * </p>
 *
 * @author zxs
 * @since 2018-10-30
 */
public interface ITbCommonRegionService extends IService<TbCommonRegion> {
    public Long getId();
    @SuppressWarnings("rawtypes")
    public List<Map> selectUnionPolLoc(Map<String,Object> params);
}
