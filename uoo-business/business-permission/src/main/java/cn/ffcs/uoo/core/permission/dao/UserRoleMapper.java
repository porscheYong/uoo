package cn.ffcs.uoo.core.permission.dao;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import cn.ffcs.uoo.core.permission.entity.UserRole;
import cn.ffcs.uoo.core.permission.vo.UserPersonnelVo;

/**
 * <p>
 * 描述系统用户与角色之间的对应关系，是多对多关系。一个系统用户除了拥有系统岗位所带的角色和权限，也可以拥有多个私有的角色，一个角色可以分配给多个系统用户。 Mapper 接口
 * </p>
 *
 * @author zengxsh
 * @since 2018-11-13
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {
    Long getId();
    /**
     *  分页查询用户人员信息
     * @param page
     * @param roleId
     * @return
     */
    List<UserPersonnelVo> selectUserPersonnelList(Pagination page, Long roleId);

}
