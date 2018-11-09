package cn.ffcs.uoo.core.permission.dao;

import cn.ffcs.uoo.core.permission.entity.PrivDataRel;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 记录权限与业务对象之间多对多的关系 Mapper 接口
 * </p>
 *
 * @author zengxsh
 * @since 2018-11-08
 */
public interface PrivDataRelMapper extends BaseMapper<PrivDataRel> {
    Long getId();
}
