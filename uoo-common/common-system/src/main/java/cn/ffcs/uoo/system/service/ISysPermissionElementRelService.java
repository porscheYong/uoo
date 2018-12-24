package cn.ffcs.uoo.system.service;

import cn.ffcs.uoo.system.entity.SysPermissionElementRel;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 定义权限关联的元素，一个权限可包含多个元素。 服务类
 * </p>
 *
 * @author zengxsh
 * @since 2018-12-24
 */
public interface ISysPermissionElementRelService extends IService<SysPermissionElementRel> {
    Long getId();
    void updatePermissionCode(String oldCode, String newCode);
}
