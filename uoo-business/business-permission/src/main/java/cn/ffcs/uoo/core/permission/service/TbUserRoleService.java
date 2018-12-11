package cn.ffcs.uoo.core.permission.service;

import cn.ffcs.uoo.core.permission.entity.TbUserRole;
import cn.ffcs.uoo.core.permission.vo.UserPersonnelVo;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  用户角色关系
 * 描述系统用户与角色之间的对应关系，是多对多关系。一个系统用户除了拥有系统岗位所带的角色和权限，也可以拥有多个私有的角色，一个角色可以分配给多个系统用户。 服务类
 * </p>
 *
 * @author zhanglu
 * @since 2018-10-24
 */
public interface TbUserRoleService extends IService<TbUserRole> {
    /**
     *  失效用户角色关系
     * @param userRoleId
     * @param updateStaff
     */
    void remove(Long userRoleId, Long updateStaff);

    /**
     * 新增用户角色关系
     * @param tbUserRole
     */
    void addTbUserRole(TbUserRole tbUserRole);

    /**
     * 分页查询人员用户信息
     * @param page
     * @param roleId
     * @return
     */
    Page<UserPersonnelVo> selectUserPersonnelPage(Page<UserPersonnelVo> page, Long roleId);
}
