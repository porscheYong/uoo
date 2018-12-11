package cn.ffcs.uoo.core.permission.service;

import cn.ffcs.uoo.core.permission.entity.FuncMenu;
import cn.ffcs.uoo.core.permission.entity.Privilege;
import cn.ffcs.uoo.core.permission.entity.Roles;
import cn.ffcs.uoo.core.permission.vo.RoleSystemPermissionVO;
import com.baomidou.mybatisplus.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 描述员工的系统权限集合，是用以定义系统使用人员操作权限的实体。 服务类
 * </p>
 *
 * @author zengxsh
 * @since 2018-11-13
 */
public interface IRolesService extends IService<Roles> {
    Long getId();
    //Integer acctType,
    List<RoleSystemPermissionVO> getRoles(Long acctId,  Long systemInfoId);

    List<Privilege> getPermission(Long roleId);

    List<FuncMenu> getPermissionMenu(Long acctId, Long systemInfoId);
}
