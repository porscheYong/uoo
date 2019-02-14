package cn.ffcs.uoo.system.service;

import java.util.HashMap;
import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import cn.ffcs.uoo.system.entity.SysRole;
import cn.ffcs.uoo.system.vo.SysRoleDTO;
import cn.ffcs.uoo.system.vo.TreeNodeVo;

/**
 * 系统域用户Service接口
 * Created by liuxiaodong on 2018/11/12.
 */
public interface SysRoleService extends IService<SysRole> {
    Long getId();
    List<SysRoleDTO> findList(Page<SysRoleDTO> page,HashMap<String, Object> map);
    Long countList(HashMap<String, Object> map);
    List<TreeNodeVo> treeRole();
    SysRoleDTO selectOne(Long ROLE_ID);
}
