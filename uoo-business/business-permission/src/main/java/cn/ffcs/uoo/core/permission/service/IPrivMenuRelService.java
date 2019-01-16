package cn.ffcs.uoo.core.permission.service;

import cn.ffcs.uoo.core.permission.entity.PrivMenuRel;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 定义权限关联的菜单，一个权限可包含多个菜单。 服务类
 * </p>
 *
 * @author zengxsh
 * @since 2019-01-15
 */
public interface IPrivMenuRelService extends IService<PrivMenuRel> {
    public Long getId();
}
