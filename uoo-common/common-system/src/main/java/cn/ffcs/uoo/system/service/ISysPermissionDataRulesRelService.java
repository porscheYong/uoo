package cn.ffcs.uoo.system.service;

import cn.ffcs.uoo.system.entity.SysPermissionDataRulesRel;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 记录权限与业务对象之间多对多的关系 服务类
 * </p>
 *
 * @author zengxsh
 * @since 2018-12-24
 */
public interface ISysPermissionDataRulesRelService extends IService<SysPermissionDataRulesRel> {
    Long getId();
}
