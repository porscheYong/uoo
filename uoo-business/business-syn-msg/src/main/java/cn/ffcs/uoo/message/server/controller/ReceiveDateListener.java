package cn.ffcs.uoo.message.server.controller;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = {"message_sharing_center_queue"})
public class ReceiveDateListener {

    /*@Autowired
    private */

    @RabbitHandler
    public void process(String json) {
        System.out.println(json);
    }
}
