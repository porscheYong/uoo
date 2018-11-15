package cn.ffcs.uoo.core.permission.service;

import cn.ffcs.uoo.core.permission.entity.Roles;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 描述员工的系统权限集合，是用以定义系统使用人员操作权限的实体。 服务类
 * </p>
 *
 * @author zengxsh
 * @since 2018-11-13
 */
public interface IRolesService extends IService<Roles> {
    Long getId();
}
