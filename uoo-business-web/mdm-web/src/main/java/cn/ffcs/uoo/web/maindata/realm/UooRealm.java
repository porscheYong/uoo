package cn.ffcs.uoo.web.maindata.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import cn.ffcs.uoo.web.maindata.sysuser.client.SysUserClient;
import cn.ffcs.uoo.web.maindata.sysuser.dto.SysUser;
import cn.ffcs.uoo.web.maindata.sysuser.vo.ResponseResult;

public class UooRealm extends AuthorizingRealm {

    // @Resource
    // UserMapper userMapper;
    // @Resource
    // RoleMapper roleMapper;
    // @Resource
    // PermissionMapper permissionMapper;
    @Autowired
    SysUserClient client;

    /**
     * 验证当前登录的用户
     * 
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        UsernamePasswordToken usertoken = (UsernamePasswordToken) authenticationToken;
        String username = usertoken.getUsername();
        SysUser t = new SysUser();
        t.setAccout(username);
        ResponseResult<SysUser> r = client.getSysUserByAccout(t);
        if(r.getState()!=ResponseResult.STATE_OK){
            return null;
        }
        String md5Encoding = MD5Util.md5Encoding(new String(usertoken.getPassword()), r.getData().getSalt());
        usertoken.setPassword(md5Encoding.toCharArray());
        AuthenticationInfo Info = new SimpleAuthenticationInfo(usertoken.getUsername(), r.getData().getPasswd(),
                this.getName());
        return Info;
    }

    /**
     * 为当前登录成功的用户授予角色和权限
     * 
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //String name = (String) principals.getPrimaryPrincipal();// 这里存储用户的acct
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
    //sesision loginkey acid


        simpleAuthorizationInfo.addStringPermission("index");
       // System.err.println("获取权限");
        return simpleAuthorizationInfo;
    }
    /**
     * 更新用户授权信息缓存.
     */
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }
    /**
     * 更新用户信息缓存.
     */
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    /**
     * 清除用户授权信息缓存.
     */
    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    /**
     * 清除用户信息缓存.
     */
    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }
    
    /**
     * 清空所有缓存
     */
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }


    /**
     * 清空所有认证缓存
     */
    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }
}
