package cn.ffcs.uoo.web.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

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
 * @Description 拦截Controller访问日志，可以使用消息队列将日志缓存到文件或者数据库中
 * @author WCNGS@QQ.COM
 * @date 2018/9/8 20:54
 * @Version 1.0.0
*/
@Component
@Aspect
@Order(7)
public class WebLogAspect {

    ThreadLocal<Long> startTime = new ThreadLocal<>();
    private Logger logger = LoggerFactory.getLogger(this.getClass());

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

        startTime.set(System.currentTimeMillis());

        ServletRequestAttributes requestAttributes= (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        // 记录下请求内容
        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("IP : " + request.getRemoteAddr());
        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
    }


    /**在处理结果返回之前执行，指定哪个Poingcut
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

        // 处理完请求，返回内容
        logger.info("RESPONSE : " + ret);
        logger.info("SPEND TIME : " + (System.currentTimeMillis() - startTime.get()));
    }
}
