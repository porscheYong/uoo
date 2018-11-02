package cn.ffcs.uoo.core.position.service;

import cn.ffcs.uoo.core.position.entity.TbPostLocation;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 职位行政区域 服务类
 * </p>
 *
 * @author zhanglu
 * @since 2018-10-30
 */
public interface TbPostLocationService extends IService<TbPostLocation> {
    /**
     * 新增职位行政区域
     * @param tbPostLocation
     * @return
     */
    int save(TbPostLocation tbPostLocation);

    /**
     *  删除职位行政区域
     * @param postLocationId
     * @param updateUser
     */
    void remove(Long postLocationId, Long updateUser);
}
