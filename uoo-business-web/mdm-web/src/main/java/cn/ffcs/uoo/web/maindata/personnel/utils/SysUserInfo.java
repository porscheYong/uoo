package cn.ffcs.uoo.web.maindata.personnel.utils;

import cn.ffcs.uoo.web.maindata.common.system.dto.SysUser;
import cn.ffcs.uoo.web.maindata.mdm.consts.LoginConsts;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * @ClassName SysUserInfo
 * @Description
 * @author wudj
 * @date 2018/12/27
 * @Version 1.0.0
 */
public class SysUserInfo {

    private static SysUser currentLoginUser = null;

    public static Long getUserId(){
        Subject subject = SecurityUtils.getSubject();
        currentLoginUser = (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
       if(currentLoginUser == null){
           return null;
       }
        return currentLoginUser.getUserId();
    }

    public static String getAccount(){
        Subject subject = SecurityUtils.getSubject();
        currentLoginUser = (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        if(currentLoginUser == null){
            return "";
        }
        return currentLoginUser.getAccout();
    }
}
