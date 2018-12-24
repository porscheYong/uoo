package cn.ffcs.uoo.system.dao;

import cn.ffcs.uoo.system.entity.SysPermissionFuncRel;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 定义权限关联的功能，一个权限可包含多个功能。 Mapper 接口
 * </p>
 *
 * @author zengxsh
 * @since 2018-12-24
 */
public interface SysPermissionFuncRelMapper extends BaseMapper<SysPermissionFuncRel> {
    Long getId();
}
