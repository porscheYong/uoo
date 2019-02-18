package cn.ffcs.uoo.system.dao;


import java.util.HashMap;
import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;

import cn.ffcs.uoo.system.entity.SysRole;
import cn.ffcs.uoo.system.vo.SysRoleDTO;
import cn.ffcs.uoo.system.vo.TreeNodeVo;

public interface SysRoleMapper extends BaseMapper<SysRole> {
    Long getId();
    
    List<SysRoleDTO> findList(Page<SysRoleDTO> page,HashMap<String, Object> map);
    Long countList(HashMap<String, Object> map);
    List<TreeNodeVo> treeRole();
    List<SysRoleDTO> selectOne(Long ROLE_ID);
}