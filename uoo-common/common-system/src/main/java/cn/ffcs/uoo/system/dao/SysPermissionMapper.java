package cn.ffcs.uoo.system.dao;

import java.util.HashMap;
import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;

import cn.ffcs.uoo.system.entity.SysPermission;
import cn.ffcs.uoo.system.vo.SysPermissionDTO;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zengxsh
 * @since 2018-12-24
 */
public interface SysPermissionMapper extends BaseMapper<SysPermission> {
    List<SysPermissionDTO> findList(Page<SysPermissionDTO> page,HashMap<String, Object> map);
    SysPermissionDTO selectOne(Long id);
    Long countList(HashMap<String, Object> map);
    Long getId();
    List<SysPermission> listByRoleCode(String roleCode);
}
