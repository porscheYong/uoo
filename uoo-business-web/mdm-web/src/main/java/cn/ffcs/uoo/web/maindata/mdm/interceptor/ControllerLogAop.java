package cn.ffcs.uoo.web.maindata.mdm.interceptor;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.ffcs.uoo.web.maindata.common.system.client.SysOperationLogClient;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysOperationLog;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysUser;
import cn.ffcs.uoo.web.maindata.common.system.vo.ResponseResult;
import cn.ffcs.uoo.web.maindata.mdm.consts.LoginConsts;
import cn.ffcs.uoo.web.maindata.mdm.logs.OperateLog;
import cn.ffcs.uoo.web.maindata.mdm.logs.OperateType;
/*@Order(1)
@Aspect
@Component*/
public class ControllerLogAop {
    ThreadLocal<SysOperationLog> localLog = new ThreadLocal<>();
    @Autowired
    SysOperationLogClient opLogClient;
    @Autowired
    ShiroFilterFactoryBean shiroFilterFactoryBean;
    private static Logger log = LoggerFactory.getLogger(ControllerLogAop.class);
    @Pointcut("execution(public * cn.ffcs.uoo.web..controller.*.*(..))")
    public void pointCut() {
    }
    @Before("pointCut()")
    public void doBefore(JoinPoint joinPoint) throws Throwable{
        ServletRequestAttributes requestAttributes= (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        Subject subject = null;
        try {
            subject = SecurityUtils.getSubject();
        } catch (Exception e) {
        }
        if(subject==null){
            log.info("当前接口 {} 无法获取Subject对象",request.getRequestURI());
            return;
        }
        Session session = subject.getSession();
        if(session==null){
            log.info("当前接口 {} 无法获取Session对象",request.getRequestURI());
            return ;
        }
        SysUser user = (SysUser) session.getAttribute(LoginConsts.LOGIN_KEY);
        if(user==null){
            log.info("当前接口 {} 无法获取SysUser对象",request.getRequestURI());
            return;
        }
        Signature sig = joinPoint.getSignature();
        MethodSignature msig = null;
        if (!(sig instanceof MethodSignature)) {
            return;
        }
        msig = (MethodSignature) sig;
        Object target = joinPoint.getTarget();
        Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
        SysOperationLog logs=new SysOperationLog();
        if(currentMethod.isAnnotationPresent(OperateLog.class)){
            OperateLog annotation = currentMethod.getAnnotation(OperateLog.class);
            logs.setLogName(annotation.module()+"-"+annotation.methods());
            logs.setLogType(annotation.type().type);
            logs.setNotes(annotation.desc());
        }else{
            logs.setLogName(currentMethod.getName());
            logs.setLogType(getLogType(currentMethod.getName()));
        }
        
        logs.setUserCode(user.getUserCode());
        logs.setSucceed( 1L );
        List<Object> args = Arrays.asList(joinPoint.getArgs());
        List<Object> logArgs = args.stream().filter(arg -> (!(arg instanceof HttpServletRequest) && !(arg instanceof HttpServletResponse)))
                .collect(Collectors.toList());
        String requestBody=JSON.toJSONString(logArgs);
        logs.setFormData(requestBody.length()>4000?requestBody.substring(0, 4000):requestBody);
        
        logs.setMenuCode(request.getHeader("MENU_CODE"));
        String requestURI = request.getRequestURI();
        Map<String, String> filterChainDefinitionMap = shiroFilterFactoryBean.getFilterChainDefinitionMap();
        Set<Entry<String, String>> entrySet = filterChainDefinitionMap.entrySet();
        for (Entry<String, String> entry : entrySet) {
            String pm = entry.getValue();
            String url = entry.getKey();
            if(StringUtils.isNotBlank(url)){
                if(pm.startsWith("perms[")){
                    String tmp = url.replaceAll("\\*", ".*");
                    if(Pattern.matches(tmp, requestURI)){
                        logs.setFuncCode(pm.substring(6, pm.length()-1));
                        break;
                    }
                }
            }
             
        }
        
        localLog.set(logs);
    }
    
    @Around("pointCut()")
    public Object recordSysLog(ProceedingJoinPoint point) throws Throwable {
        //先执行业务
        Object result = null;
        Exception exception=null;
        SysOperationLog sysOperationLog = localLog.get();
        try {
            result = point.proceed();
        } catch (Exception e) {
            exception=e;
        }
        if(sysOperationLog!=null){
            System.err.println("新的AOP log :"+JSON.toJSONString(sysOperationLog));
            sysOperationLog.setSucceed(exception!=null?0L:1L);
            ResponseResult<Void> responseResult = opLogClient.add(sysOperationLog);
            if(responseResult.getState()!=ResponseResult.STATE_OK){
                log.info("记录操作日志异常：{}", responseResult.getMessage());
                log.info(JSONObject.toJSONString(sysOperationLog));
            }
        }
        return result;
    }
    
     
    private long getLogType(String methodName){
        methodName=methodName.toLowerCase();
        if(methodName.contains("select")||methodName.contains("page")||methodName.contains("get")||methodName.contains("list")){
            return OperateType.SELECT.type;
        }else if(methodName.contains("add")||methodName.contains("insert")||methodName.contains("save")){
            return OperateType.ADD.type;
        }else if(methodName.contains("update")){
            return OperateType.UPDATE.type;
        }else if(methodName.contains("delete")||methodName.contains("remove")||methodName.contains("clean")){
            return OperateType.DELETE.type;
        }else{
            return OperateType.OTHER.type;
        }
    }
}
