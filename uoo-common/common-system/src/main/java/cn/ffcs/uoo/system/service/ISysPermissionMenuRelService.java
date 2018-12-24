package cn.ffcs.uoo.system.service;

import cn.ffcs.uoo.system.entity.SysPermissionMenuRel;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 定义权限关联的菜单，一个权限可包含多个菜单。 服务类
 * </p>
 *
 * @author zengxsh
 * @since 2018-12-24
 */
public interface ISysPermissionMenuRelService extends IService<SysPermissionMenuRel> {
    Long getId();
}
