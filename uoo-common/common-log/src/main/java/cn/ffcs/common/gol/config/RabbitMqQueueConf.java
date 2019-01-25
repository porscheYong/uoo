package cn.ffcs.common.gol.config;

import cn.ffcs.common.gol.entity.AccessLog;
import cn.ffcs.common.gol.service.AccessLogService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

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
 * @ClassName RabbitMqQueueConf
 * @Description 
 * @author WCNGS@QQ.COM
 * @date 2019/1/23 15:27
 * @Version 1.0.0
*/
@Configuration
public class RabbitMqQueueConf {

    @Autowired
    private AccessLogService accessLogService;

    private static final String QUEUE_NAME = "QUEUE_ACC_LOG";

    @RabbitListener(queues = {QUEUE_NAME})
    public void process(String str){
        try {
            JSONObject jObj = JSONObject.parseObject(str);
            AccessLog ca = JSON.toJavaObject(jObj,AccessLog.class);
            accessLogService.save(ca);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
