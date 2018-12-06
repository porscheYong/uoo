/**
 * Copyright (C), 2017-2018, 中电福富信息科技有限公司
 * FileName: RoleSystemPermissionVO
 * Author:   linmingxu
 * Date:     2018/12/3 11:06
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.ffcs.uoo.web.maindata.permission.vo;

import java.util.List;

import cn.ffcs.uoo.web.maindata.permission.dto.Privilege;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author linmingxu
 * @create 2018/12/3
 * @since 1.0.0
 */
public class RoleSystemPermissionVO {
    private Long roleId;
    private String roleName;
    private String roleCode;
    private List<Privilege> Privileges;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public List<Privilege> getPrivileges() {
        return Privileges;
    }

    public void setPrivileges(List<Privilege> privileges) {
        Privileges = privileges;
    }
}
