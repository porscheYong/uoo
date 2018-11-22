package cn.ffcs.uoo.core.permission.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UcServerRealm extends AuthorizingRealm {

    //@Resource
    //UserMapper userMapper;
    //@Resource
    //RoleMapper roleMapper;
    //@Resource
    //PermissionMapper permissionMapper;

    /**
     * 验证当前登录的用户
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String name = (String) authenticationToken.getPrincipal();
        //User user = userMapper.getNameAndPasswordByName(name);
        //if(user != null) {
        //    return new SimpleAuthenticationInfo(user.getName(), user.getPassword(), "UcServerRealm");
        //} else {
            return null;
        //}
    }

    /**
     * 为当前登录成功的用户授予角色和权限
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String name = (String) principals.getPrimaryPrincipal();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        HttpSession session = request!=null?request.getSession():null;
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        if(session != null && session.getAttribute("simpleAuthorizationInfo")==null) {
            //simpleAuthorizationInfo.setRoles(roleMapper.listRoleNameByUserName(name));
            //simpleAuthorizationInfo.setStringPermissions(permissionMapper.listPermissionNameByUserName(name));
            session.setAttribute("simpleAuthorizationInfo",simpleAuthorizationInfo);
        }else {
            simpleAuthorizationInfo = (SimpleAuthorizationInfo)session.getAttribute("simpleAuthorizationInfo");
        }
        return simpleAuthorizationInfo;
    }

}
