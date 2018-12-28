package cn.ffcs.uoo.system.dao;


import cn.ffcs.uoo.system.entity.SysRole;
import cn.ffcs.uoo.system.vo.SysRoleDTO;
import cn.ffcs.uoo.system.vo.TreeNodeVo;

import java.util.HashMap;
import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;

public interface SysRoleMapper extends BaseMapper<SysRole> {
    Long getId();
    
    List<SysRoleDTO> findList(HashMap<String, Object> map);
    Long countList(HashMap<String, Object> map);
    List<TreeNodeVo> treeRole();
    SysRoleDTO selectOne(Long ROLE_ID);
}