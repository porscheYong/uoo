package cn.ffcs.uoo.system.service.impl;

import cn.ffcs.uoo.system.entity.SysPermissionDataRulesRel;
import cn.ffcs.uoo.system.dao.SysPermissionDataRulesRelMapper;
import cn.ffcs.uoo.system.service.ISysPermissionDataRulesRelService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 记录权限与业务对象之间多对多的关系 服务实现类
 * </p>
 *
 * @author zengxsh
 * @since 2018-12-24
 */
@Service
public class SysPermissionDataRulesRelServiceImpl extends ServiceImpl<SysPermissionDataRulesRelMapper, SysPermissionDataRulesRel> implements ISysPermissionDataRulesRelService {

    @Override
    public Long getId() {
        return baseMapper.getId();
    }

}
