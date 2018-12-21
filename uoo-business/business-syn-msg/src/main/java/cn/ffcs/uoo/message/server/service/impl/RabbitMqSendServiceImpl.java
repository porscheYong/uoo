package cn.ffcs.uoo.message.server.service.impl;

import cn.ffcs.uoo.message.server.constant.QueueConstant;
import cn.ffcs.uoo.message.server.dao.RabbitmqIndexMapper;
import cn.ffcs.uoo.message.server.pojo.RabbitmqIndex;
import cn.ffcs.uoo.message.server.service.RabbitMqSendService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class RabbitMqSendServiceImpl implements RabbitMqSendService {

    @Autowired
    private AmqpTemplate template;

    @Resource
    private RabbitmqIndexMapper rabbitmqIndexMapper;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void sendMsg(String queueName, String msg) {

        if(queueName != null && !queueName.equals("")){
            template.convertAndSend(queueName,msg);
            //所有的数据给uom一份。
            template.convertAndSend("queue_3",msg);

            JSONObject jo = JSON.parseObject(msg);
            String serial = (String) jo.get("serial");
            RabbitmqIndex index = new RabbitmqIndex();
            index.setId(serial);
            index.setState(QueueConstant.unvalid.getValue());
            index.setUseData(new Date());
            rabbitmqIndexMapper.updateById(index);
        }else{
            logger.error("queueName is null,Please contact the administrator.msg is {}",msg);
        }
    }
}
