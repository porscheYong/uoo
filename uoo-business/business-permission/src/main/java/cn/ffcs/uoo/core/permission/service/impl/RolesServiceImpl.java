package cn.ffcs.uoo.core.permission.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import cn.ffcs.uoo.core.permission.dao.RolesMapper;
import cn.ffcs.uoo.core.permission.entity.Roles;
import cn.ffcs.uoo.core.permission.service.IRolesService;

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

}
