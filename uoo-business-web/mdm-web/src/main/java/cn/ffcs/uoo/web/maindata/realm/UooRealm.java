package cn.ffcs.uoo.web.maindata.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import cn.ffcs.uoo.web.maindata.sysuser.client.SysUserClient;
import cn.ffcs.uoo.web.maindata.sysuser.dto.SysUser;
import cn.ffcs.uoo.web.maindata.sysuser.vo.ResponseResult;

public class UooRealm extends AuthorizingRealm {

    //@Resource
    //UserMapper userMapper;
    //@Resource
    //RoleMapper roleMapper;
    //@Resource
    //PermissionMapper permissionMapper;
    @Autowired
    SysUserClient client;
    /**
     * 验证当前登录的用户
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken usertoken=(UsernamePasswordToken) authenticationToken;
        String username = usertoken.getUsername();
        ResponseResult<SysUser> login = client.login(null);
        SysUser data = login.getData();
        if(data==null){
            return null;
        }
        AuthenticationInfo Info = new SimpleAuthenticationInfo(data, data.getPasswd(),this.getName());
        return Info;
    }

    /**
     * 为当前登录成功的用户授予角色和权限
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String name = (String) principals.getPrimaryPrincipal();//这里存储用户的acct
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addStringPermission("/index");
        
        return simpleAuthorizationInfo;
    }

}
