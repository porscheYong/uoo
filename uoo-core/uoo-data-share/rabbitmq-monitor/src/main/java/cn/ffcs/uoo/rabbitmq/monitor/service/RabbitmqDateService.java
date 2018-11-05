package cn.ffcs.uoo.rabbitmq.monitor.service;

import java.util.HashMap;
import java.util.Map;

public interface RabbitmqDateService {

    //获取监控数据
    Map<String,Object> getDate();

}
