package cn.ffcs.uoo.core.permission.dao;

import cn.ffcs.uoo.core.permission.entity.Privilege;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 权限规格表，记录权限的配置 Mapper 接口
 * </p>
 *
 * @author zengxsh
 * @since 2018-11-08
 */
public interface PrivilegeMapper extends BaseMapper<Privilege> {
    Long getId();
}
