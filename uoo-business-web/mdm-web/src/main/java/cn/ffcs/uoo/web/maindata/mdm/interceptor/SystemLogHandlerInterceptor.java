package cn.ffcs.uoo.web.maindata.mdm.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

import cn.ffcs.uoo.web.maindata.common.system.client.SysOperationLogClient;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysOperationLog;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysUser;
import cn.ffcs.uoo.web.maindata.common.system.vo.ResponseResult;
import cn.ffcs.uoo.web.maindata.mdm.consts.LoginConsts;
import cn.ffcs.uoo.web.maindata.mdm.logs.OperateLog;

public class SystemLogHandlerInterceptor implements HandlerInterceptor{
    private static final Logger log=LoggerFactory.getLogger(SystemLogHandlerInterceptor.class);
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
        if(handler instanceof HandlerMethod){
            Subject subject = null;
            try {
                subject = SecurityUtils.getSubject();
            } catch (Exception e) {
            }
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            if(subject==null){
                log.info("当前接口 {} 无法获取Subject对象",request.getRequestURI());
                return;
            }
            Session session = subject.getSession();
            if(session!=null){
                Object object = session.getAttribute(LoginConsts.LOGIN_KEY);
                if(object!=null ){
                    if(handlerMethod.hasMethodAnnotation(OperateLog.class)){
                        OperateLog l = handlerMethod.getMethodAnnotation(OperateLog.class);
                        SysUser user=(SysUser) object;
                        SysOperationLog logs=new SysOperationLog();
                        logs.setLogName(l.module()+"-"+l.methods());
                        logs.setLogType(l.type().type);
                        logs.setUserCode(user.getUserCode());
                        logs.setSucceed(ex==null?1L:0L);
                        Map<String, String[]> parameterMap = request.getParameterMap();
                        String formData=parameterMap==null||parameterMap.isEmpty()?"":JSONObject.toJSONString(parameterMap);
                        if(formData.length()>4000){
                            formData=formData.substring(0, 4000);
                        }
                        logs.setFormData(formData);
                        logs.setFuncCode(null);
                        logs.setMenuCode(null);
                        logs.setNotes(l.desc());
                        //日志落库
                        //请求参数;
                        ResponseResult<Void> responseResult = opLogClient.add(logs);
                        if(responseResult.getState()!=ResponseResult.STATE_OK){
                            log.info("记录操作日志异常：{}", responseResult.getMessage());
                            log.info(JSONObject.toJSONString(logs));
                        }
                    }
                }
            }
        }


    }

}
