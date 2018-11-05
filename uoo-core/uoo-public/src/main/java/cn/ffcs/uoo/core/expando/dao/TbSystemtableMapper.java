package cn.ffcs.uoo.core.expando.dao;

import cn.ffcs.uoo.core.expando.entity.TbSystemtable;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 *  系统表登记
 * 所有业务表都要登记，扩展表需要登记,非扩展表无须定义 Mapper 接口
 * </p>
 *
 * @author zhanglu
 * @since 2018-10-25
 */
public interface TbSystemtableMapper extends BaseMapper<TbSystemtable> {
    /**
     *  新增系统表登记
     * @param tbSystemtable
     * @return
     */
    int save(TbSystemtable tbSystemtable);

    /**
     * 删除系统表登记
     * @param tbSystemtable
     */
    void remove(TbSystemtable tbSystemtable);
}
