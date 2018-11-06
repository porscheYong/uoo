package cn.ffcs.uoo.rabbitmq.monitor.job;

import cn.ffcs.uoo.rabbitmq.monitor.service.RabbitmqDateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;

@EnableScheduling
@Component
public class AlarmJob {

    @Autowired
    private RabbitmqDateService rabbitmqDateService;

    @Scheduled(cron = "0 0/15 * * * ? ")
    public void alarm(){
        //获取数据
        Map<String,Object> map = rabbitmqDateService.getDate();
    }
}
