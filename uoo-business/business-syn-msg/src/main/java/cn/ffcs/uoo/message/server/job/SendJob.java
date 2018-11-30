package cn.ffcs.uoo.message.server.job;

import cn.ffcs.uoo.message.server.dao.RabbitmqIndexMapper;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@EnableScheduling
public class SendJob {

    @Resource
    private RabbitmqIndexMapper rabbitmqIndexMapper;

}
