package cn.ffcs.uoo.rabbitmq.monitor.controller;

import cn.ffcs.uoo.rabbitmq.monitor.config.RabbitMQConfig;
import cn.ffcs.uoo.rabbitmq.monitor.service.RabbitmqDateService;
import cn.ffcs.uoo.rabbitmq.monitor.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

@RestController
public class TestController {

    @Autowired
    private RabbitmqDateService rabbitmqDateService;

    @Autowired
    private RabbitMQConfig rabbitMQConfig;

    @Autowired
    private TestService testService;

    @GetMapping("test")
    public Map<String,Object> test(){
        Object object = rabbitmqDateService.selectById("1");

        System.out.println("-----"+testService.test());
        Map<String,Object> map = rabbitmqDateService.test();
        map.put("o",object);
        map.put("z",rabbitMQConfig);
        return map;
    }

}
