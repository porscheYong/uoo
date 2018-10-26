package cn.ffcs.uoo.core.expando.service;

import cn.ffcs.uoo.core.expando.entity.TbExpandorow;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 保留，如通讯号码就对应多个 服务类
 * </p>
 *
 * @author zhanglu
 * @since 2018-10-25
 */
public interface TbExpandorowService extends IService<TbExpandorow> {
    /**
     * 新增扩展行
     * @param tbExpandorow
     * @return
     */
    int save(TbExpandorow tbExpandorow);

    /**
     * 删除扩展行
     * @param rowId
     * @param updateUser
     */
    void remove(Long rowId, Long updateUser);
}
