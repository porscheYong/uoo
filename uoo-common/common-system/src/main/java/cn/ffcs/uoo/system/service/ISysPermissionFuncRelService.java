package cn.ffcs.uoo.system.service;

import cn.ffcs.uoo.system.entity.SysPermissionFuncRel;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 定义权限关联的功能，一个权限可包含多个功能。 服务类
 * </p>
 *
 * @author zengxsh
 * @since 2018-12-24
 */
public interface ISysPermissionFuncRelService extends IService<SysPermissionFuncRel> {
    Long getId();

    void updatePermissionCode(String oldCode, String newCode);
}
