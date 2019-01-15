package cn.ffcs.uoo.core.resource.dao;

import cn.ffcs.uoo.core.resource.entity.Func;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
* 功能 Mapper 接口
 * </p>
 *
 * @author zengxsh
 * @since 2019-01-15
 */
public interface FuncMapper extends BaseMapper<Func> {
    Integer getId();
}
