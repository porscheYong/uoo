package cn.ffcs.uoo.core.expando.dao;

import cn.ffcs.uoo.core.expando.entity.TbExpandorow;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 扩展行
 * 保留，如通讯号码就对应多个 Mapper 接口
 * </p>
 *
 * @author zhanglu
 * @since 2018-10-25
 */
public interface TbExpandorowMapper extends BaseMapper<TbExpandorow> {
    /**
     * 新增扩展行
     * @param tbExpandorow
     * @return
     */
    int save(TbExpandorow tbExpandorow);

    /**
     * 删除扩展行
     * @param tbExpandorow
     */
    void remove(TbExpandorow tbExpandorow);
}
