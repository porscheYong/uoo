package cn.ffcs.uoo.web.aspect;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
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
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.ffcs.uoo.web.accesslog.ControllerAccessLog;
import cn.ffcs.uoo.web.maindata.common.system.client.SysOperationLogClient;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysOperationLog;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysUser;
import cn.ffcs.uoo.web.maindata.common.system.vo.ResponseResult;
import cn.ffcs.uoo.web.maindata.mdm.consts.LoginConsts;
import cn.ffcs.uoo.web.maindata.mdm.logs.OperateLog;
import cn.ffcs.uoo.web.maindata.mdm.logs.OperateType;
import cn.ffcs.uoo.web.maindata.tool.IpAddressTool;
import cn.ffcs.uoo.web.maindata.tool.MdmTool;

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
 * @ClassName WebLogAspect
 * @Description Intercept the <b>Controller</b> access accesslog, you can use the message queue to cache the accesslog to a file or database
 * @author WCNGS@QQ.COM
 * @date 2018/9/8 20:54
 * @Version 1.0.0
*/
@Component
@Aspect
@Order(7)
public class WebLogAspect {

    ThreadLocal<LogCollections> startLog = new ThreadLocal<>();
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    SysOperationLogClient opLogClient;
    @Autowired
    ShiroFilterFactoryBean shiroFilterFactoryBean;
    @Autowired
    private AmqpTemplate template;

    @Autowired
    private Environment env;

    @Pointcut("execution(public * cn.ffcs.uoo.web..controller.*.*(..))")
    public void pointCut(){

    }

    /**
     * @author WCNGS@QQ.COM
     * @See
     * @date 2018/9/8 21:33
     * @param joinPoint
     * @return void
     * @throws
     * @since
     */
    @Before("pointCut()")
    public void doBefore(JoinPoint joinPoint) throws Throwable{
        ServletRequestAttributes requestAttributes= (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        ControllerAccessLog log = new ControllerAccessLog();

        // 记录下请求内容
        log.setUrl(request.getRequestURL().toString());
        log.setIpAddress(IpAddressTool.getIPAddress(request));
        List<Object> args = Arrays.asList(joinPoint.getArgs());
        List<Object> logArgs = args.stream().filter(arg -> (!(arg instanceof HttpServletRequest) && !(arg instanceof HttpServletResponse)))
                .collect(Collectors.toList());
        log.setArgs(JSON.toJSONString(logArgs));
        String operating = joinPoint.getSignature().getName();
        log.setMethod(operating);
        log.setOperate(MdmTool.Operate(operating));
        log.setClazz(joinPoint.getSignature().getDeclaringTypeName());
        log.setCostMillis(System.currentTimeMillis());
        log.setUserId(MdmTool.getUser());
        LogCollections c=new LogCollections();
        c.setControllerAccessLog(log);
        SysOperationLog logOperaLog = logOperaLog(joinPoint);
        c.setSysOperationLog(logOperaLog);
        startLog.set(c);
    }
    @Around("pointCut()")
    public Object recordSysLog(ProceedingJoinPoint point) throws Throwable {
        //先执行业务
        Object result = null;
        Exception exception=null;
        try {
            result = point.proceed();
        } catch (Exception e) {
            exception=e;
        }
        LogCollections logc = startLog.get();
        if(logc!=null && logc.getSysOperationLog()!=null){
            logc.getSysOperationLog().setSucceed(exception!=null?0L:1L);
            ResponseResult<Void> responseResult = opLogClient.add(logc.getSysOperationLog());
            if(responseResult.getState()!=ResponseResult.STATE_OK){
                logger.info("记录操作日志异常：{}", responseResult.getMessage());
                logger.info(JSONObject.toJSONString(logc.getSysOperationLog()));
            }
        }
        return result;
    }
    public SysOperationLog logOperaLog(JoinPoint joinPoint) throws Throwable{
        ServletRequestAttributes requestAttributes= (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        Subject subject = null;
        try {
            subject = SecurityUtils.getSubject();
        } catch (Exception e) {
        }
        if(subject==null){
            logger.info("当前接口 {} 无法获取Subject对象",request.getRequestURI());
            return null;
        }
        Session session = subject.getSession();
        if(session==null){
            logger.info("当前接口 {} 无法获取Session对象",request.getRequestURI());
            return null;
        }
        SysUser user = (SysUser) session.getAttribute(LoginConsts.LOGIN_KEY);
        if(user==null){
            logger.info("当前接口 {} 无法获取SysUser对象",request.getRequestURI());
            return null;
        }
        Signature sig = joinPoint.getSignature();
        MethodSignature msig = null;
        if (!(sig instanceof MethodSignature)) {
            return null;
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
        return logs;
    }

    /**Execute before the processing result returns, specify which <b>Poingcut</b>
     * @author WCNGS@QQ.COM
     * @See
     * @date 2018/9/8 20:56
     * @param ret
     * @return void
     * @throws
     * @since
     */
    @AfterReturning(returning="ret",pointcut="pointCut()")
    public void doAfterReturning(Object ret){

        LogCollections logc = startLog.get();
        //Non-empty judgment to prevent handling of empty objects
        if(null != logc&&null != logc.getControllerAccessLog()){
            // Responsed Content
//            log.setResponse(JSON.toJSONString(ret));
            logc.getControllerAccessLog().setCostMillis(System.currentTimeMillis() - logc.getControllerAccessLog().getCostMillis());
            String active = env.getProperty("spring.profiles");
            if("dev".equalsIgnoreCase(active)){
                logger.error(" 使用开发环境"+" ; ActiveProfile value is "+active);
                template.convertAndSend("QUEUE_ACC_LOG", JSON.toJSONString(logc.getControllerAccessLog()));
            }
        }
//        logger.error(JSON.toJSONString(log));
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
