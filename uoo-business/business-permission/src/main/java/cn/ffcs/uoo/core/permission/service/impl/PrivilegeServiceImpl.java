package cn.ffcs.uoo.core.permission.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import cn.ffcs.uoo.core.permission.dao.PrivilegeMapper;
import cn.ffcs.uoo.core.permission.entity.Privilege;
import cn.ffcs.uoo.core.permission.service.IPrivilegeService;

/**
 * <p>
 * 权限规格表，记录权限的配置 服务实现类
 * </p>
 *
 * @author zengxsh
 * @since 2018-11-08
 */
@Service
public class PrivilegeServiceImpl extends ServiceImpl<PrivilegeMapper, Privilege> implements IPrivilegeService {
    @Autowired
    private PrivilegeMapper mapper;
    @Override
    public Long getId() {
        return mapper.getId();
    }
}
