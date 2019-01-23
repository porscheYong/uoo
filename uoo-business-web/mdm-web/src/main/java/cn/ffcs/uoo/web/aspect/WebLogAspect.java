package cn.ffcs.uoo.web.aspect;

import cn.ffcs.uoo.web.accesslog.ControllerAccessLog;
import cn.ffcs.uoo.web.maindata.tool.MdmTool;
import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    ThreadLocal<ControllerAccessLog> startLog = new ThreadLocal<ControllerAccessLog>();
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AmqpTemplate template;

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
        log.setIpAddress(request.getRemoteAddr());
        List<Object> args = Arrays.asList(joinPoint.getArgs());
        List<Object> logArgs = args.stream().filter(arg -> (!(arg instanceof HttpServletRequest) && !(arg instanceof HttpServletResponse)))
                .collect(Collectors.toList());
//        String argStr = JSON.toJSONString(logArgs);
        log.setArgs(logArgs);
        String operating = joinPoint.getSignature().getName();
        log.setMethod(operating);
        log.setOperate(MdmTool.Operate(operating));
        log.setClazz(joinPoint.getSignature().getDeclaringTypeName());
        log.setCostMillis(System.currentTimeMillis());
        log.setUserId(MdmTool.getUser());
        startLog.set(log);
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

        ControllerAccessLog log = startLog.get();
        //Non-empty judgment to prevent handling of empty objects
        if(null != log){
            // Responsed Content
            log.setResponse(ret);
            log.setCostMillis(System.currentTimeMillis() - log.getCostMillis());
//            template.convertAndSend("", JSON.toJSONString(accesslog));
        }
        logger.error(JSON.toJSONString(log));
    }


}
