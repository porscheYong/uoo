//package cn.ffcs.uoo.base.common.aop;
//
//import cn.ffcs.uoo.base.common.annotion.SendMqMsg;
//
//import cn.ffcs.uoo.base.common.vo.MqContextVo;
//import cn.ffcs.uoo.base.common.vo.MqMessageVo;
//import cn.ffcs.uoo.base.common.vo.ResponseResultVo;
//import com.alibaba.fastjson.JSON;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.AfterReturning;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.amqp.core.AmqpTemplate;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//
///**
// * @ClassName RabbitMqAop
// * @Description MQ调用
// * @author WCNGS@QQ.COM
// * @date 2018/8/29 22:25
// * @Version 1.0.0
// */
//@Aspect
//@Component
//public class RabbitMqAop {
//
//    @Resource
//    private AmqpTemplate template;
//
//    @Pointcut(value = "@annotation(cn.ffcs.uoo.base.common.annotion.SendMqMsg)")
//    private void pointcut() {
//    }
//
//    @AfterReturning(value = "pointcut() && @annotation(sendMqMsg)", returning = "result")
//    public Object afterReturning(JoinPoint joinPoint, SendMqMsg sendMqMsg, Object result) {
//
//
//        ResponseResultVo responseResult = (ResponseResultVo)result;
//        if("1000".equals(String.valueOf(responseResult.getState())) &&
//                responseResult.getData() != null){
//            MqMessageVo mqMessageVo = new MqMessageVo();
//            MqContextVo mqContextVo = new MqContextVo();
//
//            mqMessageVo.setType(sendMqMsg.type());
//            mqMessageVo.setHandle(sendMqMsg.handle());
//
//            mqContextVo.setColumn(sendMqMsg.column());
//            mqContextVo.setValue(Long.valueOf(String.valueOf(responseResult.getData())));
//
//            mqMessageVo.setContext(mqContextVo);
//
//            System.out.println("aa:" + JSON.toJSONString(mqMessageVo));
//            template.convertAndSend("message_sharing_center_queue", JSON.toJSONString(mqMessageVo));
//
//        }
//        return result;
//    }
//}
