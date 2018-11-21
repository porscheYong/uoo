package cn.ffcs.uoo.core.personnel.aop;

import cn.ffcs.uoo.core.personnel.annotion.SendMqMsg;
import cn.ffcs.uoo.core.personnel.constant.BaseUnitConstants;
import cn.ffcs.uoo.core.personnel.util.ResponseResult;
import cn.ffcs.uoo.core.personnel.util.StrUtil;
import cn.ffcs.uoo.core.personnel.vo.MqContextVo;
import cn.ffcs.uoo.core.personnel.vo.MqMessageVo;
import org.springframework.amqp.core.AmqpTemplate;
import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @ClassName RabbitMqAop
 * @Description MQ调用
 * @author WCNGS@QQ.COM
 * @date 2018/8/29 22:25
 * @Version 1.0.0
 */
@Aspect
@Component
public class RabbitMqAop {

    @Resource
    private AmqpTemplate template;

    @Pointcut(value = "@annotation(cn.ffcs.uoo.core.personnel.annotion.SendMqMsg)")
    private void pointcut() {
    }

    @AfterReturning(value = "pointcut() && @annotation(sendMqMsg)", returning = "result")
    public Object afterReturning(JoinPoint joinPoint, SendMqMsg sendMqMsg, Object result) {


        ResponseResult responseResult = (ResponseResult)result;
        System.out.println("执行结果：" + responseResult.getData());
        MqMessageVo mqMessageVo = new MqMessageVo();
        MqContextVo mqContextVo = new MqContextVo();

        mqMessageVo.setType(sendMqMsg.type());
        mqMessageVo.setHandle(sendMqMsg.handle());
        mqContextVo.setColumn(sendMqMsg.column());
        mqContextVo.setValue(Long.valueOf(String.valueOf(responseResult.getData())));
        mqMessageVo.setContext(mqContextVo);

        if(!StrUtil.isNullOrEmpty(mqMessageVo)){
            System.out.println("aa:" + JSON.toJSONString(mqMessageVo));
            template.convertAndSend(BaseUnitConstants.MSG_SHARING_QUEUE, JSON.toJSONString(mqMessageVo));
        }
        return result;
    }
}
