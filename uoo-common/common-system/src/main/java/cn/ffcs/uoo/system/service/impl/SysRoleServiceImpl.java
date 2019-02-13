package cn.ffcs.uoo.system.service.impl;

import cn.ffcs.uoo.system.dao.SysMenuMapper;
import cn.ffcs.uoo.system.dao.SysPermissionMapper;
import cn.ffcs.uoo.system.dao.SysRoleMapper;
import cn.ffcs.uoo.system.entity.SysMenu;
import cn.ffcs.uoo.system.entity.SysPermission;
import cn.ffcs.uoo.system.entity.SysRole;
import cn.ffcs.uoo.system.service.SysMenuService;
import cn.ffcs.uoo.system.service.SysRoleService;
import cn.ffcs.uoo.system.vo.SysRoleDTO;
import cn.ffcs.uoo.system.vo.TreeNodeVo;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/*
 * 系统域用户Service实现类
 * Created by liuxiaodong on 2018/11/12.
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    @Autowired
    SysPermissionMapper permMapper;

    @Override
    public Long getId() {
        return baseMapper.getId();
    }

    @Override
    public List<SysRoleDTO> findList(HashMap<String, Object> map) {
        List<SysRoleDTO> findList = baseMapper.findList(map);
        for (SysRoleDTO sysRoleDTO : findList) {
            List<SysPermission> listByRoleCode = permMapper.listByRoleCode(sysRoleDTO.getRoleCode());
            StringBuilder permNames=new StringBuilder();
            StringBuilder permCodes=new StringBuilder();
            StringBuilder permIds=new StringBuilder();
            int i=0;
            for (SysPermission sysPermission : listByRoleCode) {
                if(i!=0){
                    permNames.append(",");
                    permCodes.append(",");
                    permIds.append(",");
                }
                i++;
                permNames.append(sysPermission.getPermissionName());
                permCodes.append(sysPermission.getPermissionCode());
                permIds.append(sysPermission.getPermissionId());
            }
            sysRoleDTO.setPermissionCodes(permCodes.toString());
            sysRoleDTO.setPermissionNames(permNames.toString());
            sysRoleDTO.setPermissionIds(permIds.toString());
        }
        return findList;
    }

    @Override
    public Long countList(HashMap<String, Object> map) {
        return baseMapper.countList(map);
    }

    @Override
    public SysRoleDTO selectOne(Long ROLE_ID) {
        List<SysRoleDTO> selectOne = baseMapper.selectOne(ROLE_ID);
        if(selectOne!=null&&!selectOne.isEmpty()){
            SysRoleDTO sysRoleDTO = selectOne.get(0);
            List<SysPermission> listByRoleCode = permMapper.listByRoleCode(sysRoleDTO.getRoleCode());
            StringBuilder permNames=new StringBuilder();
            StringBuilder permCodes=new StringBuilder();
            StringBuilder permIds=new StringBuilder();
            int i=0;
            for (SysPermission sysPermission : listByRoleCode) {
                if(i!=0){
                    permNames.append(",");
                    permCodes.append(",");
                    permIds.append(",");
                }
                i++;
                permNames.append(sysPermission.getPermissionName());
                permCodes.append(sysPermission.getPermissionCode());
                permIds.append(sysPermission.getPermissionId());
            }
            sysRoleDTO.setPermissionCodes(permCodes.toString());
            sysRoleDTO.setPermissionNames(permNames.toString());
            sysRoleDTO.setPermissionIds(permIds.toString());
            return sysRoleDTO;
        }
        return null;
    }

    @Override
    public List<TreeNodeVo> treeRole() {
        return baseMapper.treeRole();
    }
 
}
