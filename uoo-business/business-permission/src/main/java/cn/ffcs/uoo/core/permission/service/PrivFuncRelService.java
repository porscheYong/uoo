package cn.ffcs.uoo.core.permission.service;

import com.baomidou.mybatisplus.service.IService;

import cn.ffcs.uoo.core.permission.entity.PrivFuncRel;

/**
 * <p>
 * 定义权限关联的功能菜单、功能组件，一个权限可包含多个功能菜单或功能组件。 服务类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-30
 */
public interface PrivFuncRelService extends IService<PrivFuncRel> {
    Long getId();
}
