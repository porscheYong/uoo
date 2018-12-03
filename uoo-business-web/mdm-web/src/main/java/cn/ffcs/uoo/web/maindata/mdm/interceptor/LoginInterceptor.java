package cn.ffcs.uoo.web.maindata.mdm.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.ffcs.uoo.web.maindata.mdm.consts.LoginConsts;

public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HttpSession session = request.getSession();
        Object login = session.getAttribute(LoginConsts.LOGIN_KEY);
        if(login==null){
            response.sendRedirect("/login.html");
            return false;
        }
        return super.preHandle(request, response, handler);
    }
    
}
