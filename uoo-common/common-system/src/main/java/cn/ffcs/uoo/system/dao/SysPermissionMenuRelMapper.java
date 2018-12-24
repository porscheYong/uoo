package cn.ffcs.uoo.system.dao;

import cn.ffcs.uoo.system.entity.SysPermissionMenuRel;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 定义权限关联的菜单，一个权限可包含多个菜单。 Mapper 接口
 * </p>
 *
 * @author zengxsh
 * @since 2018-12-24
 */
public interface SysPermissionMenuRelMapper extends BaseMapper<SysPermissionMenuRel> {
    Long getId();
}
