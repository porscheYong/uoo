package cn.ffcs.uoo.core.resource.dao;

import cn.ffcs.uoo.core.resource.entity.Reource;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
* 系统资源表 Mapper 接口
 * </p>
 *
 * @author zengxsh
 * @since 2019-01-15
 */
public interface ReourceMapper extends BaseMapper<Reource> {
    Long getId();
}
