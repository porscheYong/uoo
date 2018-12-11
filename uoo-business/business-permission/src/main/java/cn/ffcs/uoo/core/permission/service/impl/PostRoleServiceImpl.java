package cn.ffcs.uoo.core.permission.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import cn.ffcs.uoo.core.permission.dao.PostRoleMapper;
import cn.ffcs.uoo.core.permission.entity.PostRole;
import cn.ffcs.uoo.core.permission.service.IPostRoleService;

import java.util.List;

/**
 * <p>
 * 记录系统职位授予的角色关系，一个系统职位可以包含多个角色，一个角色可以分配给多个系统职位。 服务实现类
 * </p>
 *
 * @author zengxsh
 * @since 2018-11-13
 */
@Service
public class PostRoleServiceImpl extends ServiceImpl<PostRoleMapper, PostRole> implements IPostRoleService {
    @Autowired
    private PostRoleMapper mapper;
    @Override
    public Long getId() {
        return mapper.getId();
    }

    @Override
    public List<PostRole> getPostRole() {
        return mapper.getPostRole();
    }

}
