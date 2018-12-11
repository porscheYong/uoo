package cn.ffcs.uoo.core.permission.dao;

import cn.ffcs.uoo.core.permission.entity.FuncMenu;
import cn.ffcs.uoo.core.permission.entity.Privilege;
import cn.ffcs.uoo.core.permission.entity.Roles;
import cn.ffcs.uoo.core.permission.vo.RoleSystemPermissionVO;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 描述员工的系统权限集合，是用以定义系统使用人员操作权限的实体。 Mapper 接口
 * </p>
 *
 * @author zengxsh
 * @since 2018-11-13
 */
public interface RolesMapper extends BaseMapper<Roles> {
    Long getId();

    //@Param("acctType") Integer acctType,
    List<RoleSystemPermissionVO> getRoles(@Param("acctId")Long acctId, @Param("systemInfoId") Long systemInfoId);

    List<Privilege> getPermission(@Param("roleId")Long roleId);

    List<FuncMenu> getPermissionMenu(@Param("acctId")Long acctId, @Param("systemInfoId") Long systemInfoId);
}
