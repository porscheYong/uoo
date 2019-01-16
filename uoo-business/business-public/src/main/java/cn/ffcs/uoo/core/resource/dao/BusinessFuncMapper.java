package cn.ffcs.uoo.core.resource.dao;

import cn.ffcs.uoo.core.resource.entity.BusinessFunc;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
* 设计目的：为了方便授权使用 Mapper 接口
 * </p>
 *
 * @author zengxsh
 * @since 2019-01-15
 */
public interface BusinessFuncMapper extends BaseMapper<BusinessFunc> {
    Integer getId();
}
