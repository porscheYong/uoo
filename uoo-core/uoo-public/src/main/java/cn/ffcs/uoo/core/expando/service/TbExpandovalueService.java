package cn.ffcs.uoo.core.expando.service;

import cn.ffcs.uoo.core.expando.entity.TbExpandovalue;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 扩展值 服务类
 * </p>
 *
 * @author zhanglu
 * @since 2018-10-25
 */
public interface TbExpandovalueService extends IService<TbExpandovalue> {
    /**
     * 新增扩展值
     * @param tbExpandovalue
     * @return
     */
    int save(TbExpandovalue tbExpandovalue);

    /**
     * 删除扩展值
     * @param valueId
     * @param updateUser
     */
    void remove(Long valueId, Long updateUser);
}
