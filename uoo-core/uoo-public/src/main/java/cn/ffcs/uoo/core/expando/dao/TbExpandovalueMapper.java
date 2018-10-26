package cn.ffcs.uoo.core.expando.dao;

import cn.ffcs.uoo.core.expando.entity.TbExpandovalue;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 扩展值 Mapper 接口
 * </p>
 *
 * @author zhanglu
 * @since 2018-10-25
 */
public interface TbExpandovalueMapper extends BaseMapper<TbExpandovalue> {
    /**
     * 新增扩展值
     * @param tbExpandovalue
     * @return
     */
    int save(TbExpandovalue tbExpandovalue);

    /**
     * 删除扩展值
     * @param tbExpandovalue
     */
    void remove(TbExpandovalue tbExpandovalue);
}
