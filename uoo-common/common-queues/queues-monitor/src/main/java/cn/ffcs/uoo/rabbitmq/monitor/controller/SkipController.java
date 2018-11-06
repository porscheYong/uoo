package cn.ffcs.uoo.rabbitmq.monitor.controller;

import cn.ffcs.uoo.rabbitmq.monitor.config.RabbitMQConfig;
import cn.ffcs.uoo.rabbitmq.monitor.dao.RabbitmqIndexMapper;
import cn.ffcs.uoo.rabbitmq.monitor.pojo.RabbitmqIndex;
import cn.ffcs.uoo.rabbitmq.monitor.service.RabbitmqDateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

@Controller
public class SkipController {

    @Autowired
    private RabbitMQConfig rabbitMQConfig;

    @RequestMapping("/view")
    public String view(){
        StringBuffer sbf = new StringBuffer("redirect:http://");
        sbf.append(rabbitMQConfig.getIp());
        sbf.append(":");
        sbf.append(rabbitMQConfig.getPort());

        RabbitmqIndex d = new RabbitmqIndex();

        return sbf.toString();
    }
}
