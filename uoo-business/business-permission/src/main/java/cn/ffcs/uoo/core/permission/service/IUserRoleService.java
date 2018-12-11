package cn.ffcs.uoo.core.permission.service;

import cn.ffcs.uoo.core.permission.entity.UserRole;
import cn.ffcs.uoo.core.permission.vo.UserPersonnelVo;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 描述系统用户与角色之间的对应关系，是多对多关系。一个系统用户除了拥有系统岗位所带的角色和权限，也可以拥有多个私有的角色，一个角色可以分配给多个系统用户。 服务类
 * </p>
 *
 * @author zengxsh
 * @since 2018-11-13
 */
public interface IUserRoleService extends IService<UserRole> {
    Long getId();

    Page<UserPersonnelVo> selectUserPersonnelPage(Page<UserPersonnelVo> page, Long roleId);
}
