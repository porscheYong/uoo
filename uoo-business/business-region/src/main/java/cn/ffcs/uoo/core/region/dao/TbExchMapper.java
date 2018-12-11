package cn.ffcs.uoo.core.region.dao;

import cn.ffcs.uoo.core.region.entity.TbExch;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 描述信令所指的方向,如到某个局(每局对应一个DPC)的信令,可称到某局的局向。 Mapper 接口
 * </p>
 *
 * @author zxs
 * @since 2018-10-30
 */
public interface TbExchMapper extends BaseMapper<TbExch> {

    public Long getId();

}
