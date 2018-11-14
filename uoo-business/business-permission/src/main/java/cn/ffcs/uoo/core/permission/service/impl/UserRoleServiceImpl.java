package cn.ffcs.uoo.core.permission.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import cn.ffcs.uoo.core.permission.dao.UserRoleMapper;
import cn.ffcs.uoo.core.permission.entity.UserRole;
import cn.ffcs.uoo.core.permission.service.IUserRoleService;
import cn.ffcs.uoo.core.permission.vo.UserPersonnelVo;

/**
 * <p>
 * 描述系统用户与角色之间的对应关系，是多对多关系。一个系统用户除了拥有系统岗位所带的角色和权限，也可以拥有多个私有的角色，一个角色可以分配给多个系统用户。 服务实现类
 * </p>
 *
 * @author zengxsh
 * @since 2018-11-13
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {
    @Autowired
    UserRoleMapper mapper;
    @Override
    public Long getId() {
        return mapper.getId();
    }
    @Override
    public Page<UserPersonnelVo> selectUserPersonnelPage(Page<UserPersonnelVo> page, Long roleId) {
        return page.setRecords(mapper.selectUserPersonnelList(page, roleId));
    }

}
