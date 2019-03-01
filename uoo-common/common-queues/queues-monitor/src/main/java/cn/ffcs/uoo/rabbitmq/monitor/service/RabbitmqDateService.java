package cn.ffcs.uoo.rabbitmq.monitor.service;

import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

public interface RabbitmqDateService{
    //获取监控数据
    Map<String,Object> getDate();
}
