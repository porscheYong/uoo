package cn.ffcs.uoo.core.region.dao;

import cn.ffcs.uoo.base.common.annotion.MyBatisDao;
import cn.ffcs.uoo.core.region.entity.TbCommonRegion;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 指对于各种专业电信管理区域的共性管理区域信息的抽象表达，包括省公司、本地网、营业区。因为使用目的不同，可以定义不同使用类型的管理区域，如：CRM使用、计费使用、营销使用等。 Mapper 接口
 * </p>
 *
 * @author zxs
 * @since 2018-10-30
 */
@MyBatisDao
public interface TbCommonRegionMapper extends BaseMapper<TbCommonRegion> {
    @SuppressWarnings("rawtypes")
    public List<Map> selectUnionPolLoc(Map<String,Object> params);
    public Long getId();
    public List<TbCommonRegion> getTreeCommonRegion(Map<String, Object> params);
    public List<Map> getChildCommonRegionInfo(Map<String, Object> params);

}
