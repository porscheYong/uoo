package cn.ffcs.uoo.core.region.service;

import cn.ffcs.uoo.core.region.entity.TbRegionLocationRel;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 行政区域和公用管理区域关系。 服务类
 * </p>
 *
 * @author zxs
 * @since 2018-10-30
 */
public interface ITbRegionLocationRelService extends IService<TbRegionLocationRel> {
    public Long getId();
}
