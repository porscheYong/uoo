package cn.ffcs.uoo.web.maindata.realm;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONObject;

import cn.ffcs.uoo.web.maindata.mdm.consts.LoginConsts;
import cn.ffcs.uoo.web.maindata.permission.dto.FuncComp;
import cn.ffcs.uoo.web.maindata.permission.dto.FuncMenu;
import cn.ffcs.uoo.web.maindata.permission.service.PrivilegeService;
import cn.ffcs.uoo.web.maindata.permission.service.RolesService;
import cn.ffcs.uoo.web.maindata.permission.vo.AccoutPermissionVO;
import cn.ffcs.uoo.web.maindata.realm.exception.ServiceException;
import cn.ffcs.uoo.web.maindata.sysuser.client.SysUserClient;
import cn.ffcs.uoo.web.maindata.sysuser.dto.SysUser;
import cn.ffcs.uoo.web.maindata.sysuser.vo.ResponseResult;

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
        String md5Encoding = MD5Util.md5Encoding(new String(usertoken.getPassword()), r.getData().getSalt());
        usertoken.setPassword(md5Encoding.toCharArray());
        AuthenticationInfo info = new SimpleAuthenticationInfo(usertoken.getUsername(), r.getData().getPasswd(),
                this.getName());
        if(info!=null){
            clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
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
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        HttpSession session = request==null?null: request.getSession();
        if(session!=null){
            Object attribute = session.getAttribute(LoginConsts.LOGIN_KEY);
            if(attribute!=null){
                JSONObject json=JSONObject.parseObject(JSONObject.toJSONString(attribute));
                JSONObject data = json.getJSONObject("data");
                if(data!=null){
                    Long acctId = data.getLong("acctId");
                    cn.ffcs.uoo.web.maindata.permission.vo.ResponseResult<AccoutPermissionVO> accoutMenuPermission = privSvc.getAccoutMenuPermission(acctId);
                    if(accoutMenuPermission.getState()==cn.ffcs.uoo.web.maindata.permission.vo.ResponseResult.STATE_OK){
                        AccoutPermissionVO vo = accoutMenuPermission.getData();
                        List<FuncComp> funcComps = vo.getFuncComps();
                        List<FuncMenu> funcMemus = vo.getFuncMemus();
                        for (FuncMenu funcMenu : funcMemus) {
                            simpleAuthorizationInfo.addStringPermission("M"+funcMenu.getMenuId());
                        }
                        for (FuncComp funcComp : funcComps) {
                            simpleAuthorizationInfo.addStringPermission("C"+funcComp.getCompId());
                        }
                    }else{
                        log.info("没有权限的账户："+json);
                    }
                }
            }
        }
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