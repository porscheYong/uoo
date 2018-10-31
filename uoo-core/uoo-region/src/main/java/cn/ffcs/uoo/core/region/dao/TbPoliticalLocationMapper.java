package cn.ffcs.uoo.core.region.dao;

import cn.ffcs.uoo.core.region.entity.TbPoliticalLocation;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 行政区域 Mapper 接口
 * </p>
 *
 * @author zxs
 * @since 2018-10-30
 */
public interface TbPoliticalLocationMapper extends BaseMapper<TbPoliticalLocation> {

    public Long getId();

}
