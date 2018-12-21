package cn.ffcs.uoo.system.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import cn.ffcs.uoo.system.dao.SysPermissionDataRulesRelMapper;
import cn.ffcs.uoo.system.entity.SysPermissionDataRulesRel;
import cn.ffcs.uoo.system.service.ISysPermissionDataRulesRelService;

/**
 * <p>
 * 记录权限与业务对象之间多对多的关系 服务实现类
 * </p>
 *
 * @author zengxsh
 * @since 2018-12-21
 */
@Service
public class SysPermissionDataRulesRelServiceImpl extends ServiceImpl<SysPermissionDataRulesRelMapper, SysPermissionDataRulesRel> implements ISysPermissionDataRulesRelService {

}
