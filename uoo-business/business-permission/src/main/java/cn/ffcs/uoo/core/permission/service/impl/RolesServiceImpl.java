package cn.ffcs.uoo.core.permission.service.impl;

import cn.ffcs.uoo.core.permission.entity.FuncMenu;
import cn.ffcs.uoo.core.permission.entity.Privilege;
import cn.ffcs.uoo.core.permission.vo.RoleSystemPermissionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import cn.ffcs.uoo.core.permission.dao.RolesMapper;
import cn.ffcs.uoo.core.permission.entity.Roles;
import cn.ffcs.uoo.core.permission.service.IRolesService;

import java.util.List;

/**
 * <p>
 * 描述员工的系统权限集合，是用以定义系统使用人员操作权限的实体。 服务实现类
 * </p>
 *
 * @author zengxsh
 * @since 2018-11-13
 */
@Service
public class RolesServiceImpl extends ServiceImpl<RolesMapper, Roles> implements IRolesService {
    @Autowired
    private RolesMapper mapper;
    @Override
    public Long getId() {
        return mapper.getId();
    }

    @Override
    public List<RoleSystemPermissionVO> getRoles(Long acctId, Long systemInfoId) {
        return mapper.getRoles(acctId,systemInfoId);
    }

    @Override
    public List<Privilege> getPermission(Long roleId) {
        return mapper.getPermission(roleId);
    }

    @Override
    public List<FuncMenu> getPermissionMenu(Long acctId, Long systemInfoId) {
        return mapper.getPermissionMenu(acctId,systemInfoId);
    }

}
