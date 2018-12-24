package cn.ffcs.uoo.system.dao;

import cn.ffcs.uoo.system.entity.SysPermissionDataRulesRel;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 记录权限与业务对象之间多对多的关系 Mapper 接口
 * </p>
 *
 * @author zengxsh
 * @since 2018-12-24
 */
public interface SysPermissionDataRulesRelMapper extends BaseMapper<SysPermissionDataRulesRel> {
    Long getId();

    void updatePermissionCode(String oldCode, String newCode);
}
