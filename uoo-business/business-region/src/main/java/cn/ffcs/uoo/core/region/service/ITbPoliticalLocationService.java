package cn.ffcs.uoo.core.region.service;

import cn.ffcs.uoo.core.region.entity.TbPoliticalLocation;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 行政区域 服务类
 * </p>
 *
 * @author zxs
 * @since 2018-10-30
 */
public interface ITbPoliticalLocationService extends IService<TbPoliticalLocation> {
    public Long getId();
}
