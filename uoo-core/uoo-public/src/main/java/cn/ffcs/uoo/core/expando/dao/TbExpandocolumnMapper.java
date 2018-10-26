package cn.ffcs.uoo.core.expando.dao;

import cn.ffcs.uoo.core.expando.entity.TbExpandocolumn;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 扩展列 Mapper 接口
 * </p>
 *
 * @author zhanglu
 * @since 2018-10-25
 */
public interface TbExpandocolumnMapper extends BaseMapper<TbExpandocolumn> {
    /**
     * 新增扩展列
     * @param tbExpandocolumn
     * @return
     */
    int save(TbExpandocolumn tbExpandocolumn);

    /**
     * 删除扩展列
     * @param tbExpandocolumn
     */
    void remove(TbExpandocolumn tbExpandocolumn);
}
