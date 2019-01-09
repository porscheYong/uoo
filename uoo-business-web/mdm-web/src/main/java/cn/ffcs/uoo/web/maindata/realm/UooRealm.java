package cn.ffcs.uoo.web.maindata.realm;

import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import cn.ffcs.uoo.web.maindata.common.system.client.SysFunctionClient;
import cn.ffcs.uoo.web.maindata.common.system.client.SysMenuClient;
import cn.ffcs.uoo.web.maindata.common.system.client.SysUserClient;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysFunction;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysMenu;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysUser;
import cn.ffcs.uoo.web.maindata.common.system.vo.ResponseResult;
import cn.ffcs.uoo.web.maindata.mdm.consts.LoginConsts;
import cn.ffcs.uoo.web.maindata.permission.service.PrivilegeService;
import cn.ffcs.uoo.web.maindata.permission.service.RolesService;
import cn.ffcs.uoo.web.maindata.realm.exception.AccoutLockedException;
import cn.ffcs.uoo.web.maindata.realm.exception.ServiceException;

public class UooRealm extends AuthorizingRealm {
    private static Logger log=LoggerFactory.getLogger(UooRealm.class);
    // @Resource
    // UserMapper userMapper;
    // @Resource
    // RoleMapper roleMapper;
    // @Resource
    // PermissionMapper permissionMapper;
    @Autowired
    SysUserClient client;
    @Autowired
    PrivilegeService privSvc;
    @Autowired
    RolesService rolesSVC;
    @Autowired 
    SysMenuClient sysMenuClient;
    @Autowired
    SysFunctionClient sysFuncClient;
    Integer lockedCount=6;
    Long lockedTime=600L;//秒
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
        if(ResponseResult.STATE_SERVICE_ERROR==r.getState()){
            throw new ServiceException();
        }
        if(r.getState()!=ResponseResult.STATE_OK){
            return null;
        }
        SysUser user = r.getData();
        Subject subject = SecurityUtils.getSubject();
        if(user.getLocked()!=null&&user.getLocked()==1){
            //查看是否可用解锁
            Date lastLogin = user.getLastLogin();
            if(lastLogin!=null){
                Integer i=(Integer) subject.getSession().getAttribute(LoginConsts.LOCKED_COUNT_KEY);
                if(System.currentTimeMillis() - lastLogin.getTime() < lockedTime*1000 && i!=null && i>=6){
                    throw new AccoutLockedException();
                }else{
                    subject.getSession().setAttribute(LoginConsts.LOCKED_COUNT_KEY, 0);
                    user.setLocked(0);
                }
            }
        } 
        String md5Encoding = MD5Util.md5Encoding(new String(usertoken.getPassword()), user.getSalt());
        usertoken.setPassword(md5Encoding.toCharArray());
        AuthenticationInfo info = new SimpleAuthenticationInfo(user.getAccout(), user.getPasswd(),
                this.getName());
        
        if(!md5Encoding.equals(user.getPasswd())){
            Integer i=(Integer) subject.getSession().getAttribute(LoginConsts.LOCKED_COUNT_KEY);
            i=i==null?1:i+1;
            subject.getSession().setAttribute(LoginConsts.LOCKED_COUNT_KEY, i);
            //log.info("出错次数：{}",i);
            if(i==lockedCount){
                user.setLocked(1);
            }
        }
        user.setLastIp(subject.getSession().getHost());
        user.setLastLogin(new Date());
        client.updateLoginInfo(user);
        if(info!=null && md5Encoding.equals(user.getPasswd())){
            clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
            SysUser sysuser=new SysUser();
            BeanUtils.copyProperties(user, sysuser);
            sysuser.setPasswd(null);
            sysuser.setSalt(null);
            subject.getSession().setAttribute(LoginConsts.LOGIN_KEY, sysuser);
            subject.getSession().setAttribute(LoginConsts.LOCKED_COUNT_KEY, 0);
        }
        return info;
    }

    /**
     * 为当前登录成功的用户授予角色和权限
     * 
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        Subject sub=SecurityUtils.getSubject();
        Object primaryPrincipal = sub.getPrincipals().getPrimaryPrincipal();
        //alterPwdDTO.setAccout(primaryPrincipal.toString());
        ResponseResult<List<SysMenu>> ms = sysMenuClient.getMenuByAccout(primaryPrincipal.toString());
        if(ResponseResult.STATE_OK==ms.getState()){
            if(ms.getData()!=null){
                for (SysMenu m : ms.getData()) {
                    simpleAuthorizationInfo.addStringPermission(m.getMenuCode());
                }
            }
        }
        ResponseResult<List<SysFunction>> fs = sysFuncClient.getFunctionByAccout(primaryPrincipal.toString());
        if(ResponseResult.STATE_OK==fs.getState()){
            if(fs.getData()!=null){
                for (SysFunction f : fs.getData()) {
                    simpleAuthorizationInfo.addStringPermission(f.getFuncCode());
                }
            }
        }
       // simpleAuthorizationInfo.addStringPermission("sad");
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
