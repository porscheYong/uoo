package cn.ffcs.uoo.rabbitmq.monitor.service.impl;

import cn.ffcs.uoo.rabbitmq.monitor.dao.RabbitmqIndexMapper;
import cn.ffcs.uoo.rabbitmq.monitor.service.TestService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TestServiceImpl implements TestService {

    @Resource
    private RabbitmqIndexMapper rabbitmqIndexMapper;

    @Override
    public int test() {
        return rabbitmqIndexMapper.test2();
    }
}
