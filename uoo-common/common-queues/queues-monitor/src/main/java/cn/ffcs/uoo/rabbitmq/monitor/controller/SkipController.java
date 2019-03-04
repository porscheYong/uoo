package cn.ffcs.uoo.rabbitmq.monitor.controller;

import cn.ffcs.uoo.rabbitmq.monitor.config.RabbitMQConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
        return sbf.toString();
    }
}
