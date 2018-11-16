package cn.ffcs.uoo.core.region.service;

import cn.ffcs.uoo.core.region.entity.TbPoliticalLocation;

import java.util.List;
import java.util.Map;

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

    public List<Map> getTreePoliticalLocation(Map<String, Object> params);

    public List<Map> getChildPoliticalLocationInfo(Map<String, Object> params);
}
