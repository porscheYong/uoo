package cn.ffcs.uoo.core.permission.dao;

import cn.ffcs.uoo.core.permission.entity.TbUserRole;
import cn.ffcs.uoo.core.permission.vo.UserPersonnelVo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import java.util.List;

/**
 * <p>
 *  用户角色关系
 * 描述系统用户与角色之间的对应关系，是多对多关系。一个系统用户除了拥有系统岗位所带的角色和权限，也可以拥有多个私有的角色，一个角色可以分配给多个系统用户。 Mapper 接口
 * </p>
 *
 * @author zhanglu
 * @since 2018-10-24
 */
public interface TbUserRoleMapper extends BaseMapper<TbUserRole> {
    /**
     * 失效用户角色关系
     * @param tbUserRole
     */
    void remove(TbUserRole tbUserRole);

    /**
     * 新增用户角色关系
     * @param tbUserRole
     * @return
     */
    int insertSelective(TbUserRole tbUserRole);

    /**
     *  分页查询用户人员信息
     * @param page
     * @param roleId
     * @return
     */
    List<UserPersonnelVo> selectUserPersonnelList(Pagination page, Long roleId);
}
