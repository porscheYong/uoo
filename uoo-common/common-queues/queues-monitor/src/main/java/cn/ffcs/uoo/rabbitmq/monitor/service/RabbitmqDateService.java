package cn.ffcs.uoo.rabbitmq.monitor.service;

import cn.ffcs.uoo.rabbitmq.monitor.pojo.RabbitmqIndex;
import com.baomidou.mybatisplus.service.IService;

import java.util.HashMap;
import java.util.Map;

public interface RabbitmqDateService extends IService<RabbitmqIndex> {

    //获取监控数据
    Map<String,Object> getDate();

    //test
    Map<String,Object> test();

}
