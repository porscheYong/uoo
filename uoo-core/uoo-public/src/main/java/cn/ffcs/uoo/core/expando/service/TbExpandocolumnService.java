package cn.ffcs.uoo.core.expando.service;

import cn.ffcs.uoo.core.expando.entity.TbExpandocolumn;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 扩展列 服务类
 * </p>
 *
 * @author zhanglu
 * @since 2018-10-25
 */
public interface TbExpandocolumnService extends IService<TbExpandocolumn> {
    /**
     *  新增扩展列
     * @param tbExpandocolumn
     * @return
     */
    int save(TbExpandocolumn tbExpandocolumn);

    /**
     * 删除扩展列
     * @param columnId
     * @param updateUser
     */
    void remove(Long columnId, Long updateUser);
}
