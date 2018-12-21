package cn.ffcs.uoo.system.dao;


import cn.ffcs.uoo.system.entity.SysRole;
import cn.ffcs.uoo.system.vo.SysRoleDTO;

import java.util.HashMap;
import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;

public interface SysRoleMapper extends BaseMapper<SysRole> {
    Long getId();
    List<SysRoleDTO> selectToPage(HashMap<String, Object> map);
    Long countToPage(HashMap<String, Object> map);
}