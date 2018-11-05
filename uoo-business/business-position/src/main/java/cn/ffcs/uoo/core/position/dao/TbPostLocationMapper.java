package cn.ffcs.uoo.core.position.dao;

import cn.ffcs.uoo.core.position.entity.TbPostLocation;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 职位行政区域 Mapper 接口
 * </p>
 *
 * @author zhanglu
 * @since 2018-10-30
 */
public interface TbPostLocationMapper extends BaseMapper<TbPostLocation> {
    /**
     * 新增职位行政区域
     * @param tbPostLocation
     * @return
     */
    int save(TbPostLocation tbPostLocation);

    /**
     * 删除职位行政区域
     * @param tbPostLocation
     */
    void remove(TbPostLocation tbPostLocation);
}
