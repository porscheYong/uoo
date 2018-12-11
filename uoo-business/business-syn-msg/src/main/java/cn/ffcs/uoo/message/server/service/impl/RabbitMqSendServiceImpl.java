package cn.ffcs.uoo.message.server.service.impl;

import cn.ffcs.uoo.message.server.service.RabbitMqSendService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqSendServiceImpl implements RabbitMqSendService {

    @Autowired
    private AmqpTemplate template;

    @Override
    public void sendMsg(String queueName, String msg) {
        template.convertAndSend(queueName,msg);
    }
}
