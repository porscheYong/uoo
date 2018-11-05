package cn.ffcs.uoo.core.expando.service;

import cn.ffcs.uoo.core.expando.entity.TbSystemtable;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 系统表登记
 * 所有业务表都要登记，扩展表需要登记,非扩展表无须定义 服务类
 * </p>
 *
 * @author zhanglu
 * @since 2018-10-25
 */
public interface TbSystemtableService extends IService<TbSystemtable> {
    /**
     * 新增系统表登记
     * @param tbSystemtable
     * @return
     */
    int save(TbSystemtable tbSystemtable);

    /**
     * 删除系统表登记
     * @param tableId
     * @param updateUser
     */
    void remove(Long tableId, Long updateUser);
}
