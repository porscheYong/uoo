package cn.ffcs.uoo.core.region.dao;

import cn.ffcs.uoo.base.common.annotion.MyBatisDao;
import cn.ffcs.uoo.core.region.entity.TbPoliticalLocation;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 行政区域 Mapper 接口
 * </p>
 *
 * @author zxs
 * @since 2018-10-30
 */
@MyBatisDao
public interface TbPoliticalLocationMapper extends BaseMapper<TbPoliticalLocation> {

    public Long getId();

    public List<Map> getTreePoliticalLocation(Map<String, Object> params);

    public List<Map> getChildPoliticalLocationInfo(Map<String, Object> params);

}
