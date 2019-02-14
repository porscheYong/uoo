package cn.ffcs.uoo.system.service;

import cn.ffcs.uoo.system.entity.SysPermission;
import cn.ffcs.uoo.system.vo.SysPermissionDTO;

import java.util.HashMap;
import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zengxsh
 * @since 2018-12-24
 */
public interface ISysPermissionService extends IService<SysPermission> {
    List<SysPermissionDTO> findList(Page<SysPermissionDTO> page,HashMap<String, Object> map);
    Long countList(HashMap<String, Object> map);
    Long getId();
    SysPermissionDTO selectOne(Long id);
    List<SysPermission> listByRoleCode(String roleCode);
}
