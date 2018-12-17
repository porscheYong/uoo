package cn.ffcs.uoo.web.maindata.mdm.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.ffcs.uoo.web.maindata.common.system.client.SysOperationLogClient;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysOperationLog;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysUser;
import cn.ffcs.uoo.web.maindata.mdm.consts.LoginConsts;

public class SystemLogHandlerInterceptor implements HandlerInterceptor{
    @Autowired
    SysOperationLogClient opLogClient;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        if(session!=null){
            Object object = session.getAttribute(LoginConsts.LOGIN_KEY);
            if(object!=null){
               /* SysUser user=(SysUser) object;
                SysOperationLog logs=new SysOperationLog();
                logs.setLogName(logName);
                logs.setLogType(logType);
                logs.setUserCode(user.getUcode());
                logs.setSucceed(succeed);
                logs.setFormData(formData);
                logs.setFuncCode(funcCode);
                logs.setMenuCode(menuCode);
                logs.setNotes(notes);
                //日志落库
                //请求参数request.getParameterMap();
                opLogClient.add(logs);*/
            }
        }
        
        
    }

}
