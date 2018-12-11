package cn.ffcs.uoo.core.region.dao;

import cn.ffcs.uoo.base.common.annotion.MyBatisDao;
import cn.ffcs.uoo.core.region.entity.TbRegionLocationRel;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 行政区域和公用管理区域关系。 Mapper 接口
 * </p>
 *
 * @author zxs
 * @since 2018-10-30
 */
@MyBatisDao
public interface TbRegionLocationRelMapper extends BaseMapper<TbRegionLocationRel> {

    public Long getId();

}
