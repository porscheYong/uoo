package cn.ffcs.uoo.web.maindata.tool;


import cn.ffcs.uoo.web.maindata.common.system.dto.SysUser;
import cn.ffcs.uoo.web.maindata.mdm.consts.LoginConsts;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 *  ┏┓　　　┏┓
 *┏┛┻━━━┛┻┓
 *┃　　　　　　　┃ 　
 *┃　　　━　　　┃
 *┃　┳┛　┗┳　┃
 *┃　　　　　　　┃
 *┃　　　┻　　　┃
 *┃　　　　　　　┃
 *┗━┓　　　┏━┛
 *　　┃　　　┃神兽保佑
 *　　┃　　　┃代码无BUG！
 *　　┃　　　┗━━━┓
 *　　┃　　　　　　　┣┓
 *　　┃　　　　　　　┏┛
 *　　┗┓┓┏━┳┓┏┛
 *　　　┃┫┫　┃┫┫
 *　　　┗┻┛　┗┻┛
 * @ClassName MdmTool
 * @Description 
 * @author WCNGS@QQ.COM
 * @date 2019/1/23 11:34
 * @Version 1.0.0
*/
public class MdmTool {

    /** Get the current user ID
     * @author WCNGS@QQ.COM
     * @See
     * @date 2019/1/23 11:35
     * @param
     * @return java.lang.Long
     * @throws
     * @since
     */
    public static Long getUser(){
        Long userId = null;
        Subject subject=SecurityUtils.getSubject();
        if(null!=subject){
            SysUser currentLoginUser = (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
            //First login. currentLoginUser is Empty
            if(null!=currentLoginUser){
                userId = currentLoginUser.getUserId();
            }
        }
        return userId;
    }

    public static String Operate(String operate){

        String result = Operat.getDesc(LoginConsts.QUERY_STR);

        if(operate.startsWith(LoginConsts.UPDATE_STR)){
            result = Operat.getDesc(LoginConsts.UPDATE_STR);
        }

        if(operate.startsWith(LoginConsts.DELETE_STR)){
            result = Operat.getDesc(LoginConsts.DELETE_STR);
        }
        return result;
    }
}


