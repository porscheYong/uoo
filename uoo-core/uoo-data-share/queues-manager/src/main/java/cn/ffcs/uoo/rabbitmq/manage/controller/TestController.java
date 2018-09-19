package cn.ffcs.uoo.rabbitmq.manage.controller;


import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cn.ffcs.uoo.rabbitmq.manage.constant.StatusConstant;
import cn.ffcs.uoo.rabbitmq.manage.service.RabbitMqService;
import cn.ffcs.uoo.rabbitmq.manage.vo.SystemQueueVo;

@RestController
public class TestController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
    @Autowired
    private RabbitMqService rabbitMqService;    
    
    @PostMapping(value="queue/add")
    public Map<String, Object> add(@RequestBody SystemQueueVo vo){
    	try {
    		return rabbitMqService.creatrQueue(vo.getSystemName(), vo.getDoubleName(), vo.getChargePerson(), vo.getChargeContact());
    	}catch(Exception e) {
    		Map<String, Object> resultMap = new HashMap<>();
    		logger.warn(".---创建队列失败，RabbitMq连接不上---.");
			resultMap.put("status",StatusConstant.handleFail.getValue());
    		return resultMap;
    	}
    }
    
    
    @PostMapping(value="queue/del")
    public Map<String, Object> del(@RequestBody SystemQueueVo vo){
    	try {
    		return rabbitMqService.deleteQueue(vo.getSystemName(), vo.getDoubleName(),vo.getQueueName());
    	}catch(Exception e) {
    		Map<String, Object> resultMap = new HashMap<>();
    		logger.warn(".---删除队列失败，RabbitMq连接不上---.");
			resultMap.put("status",StatusConstant.handleFail.getValue());
    		return resultMap;
    	}
    }
    
    @PostMapping(value="queue/update")
    public Map<String, Object> update(@RequestBody SystemQueueVo vo){
    	try {
    		return rabbitMqService.updateQueue(vo.getSystemName(), vo.getDoubleName(),vo.getQueueName(), vo.getChargePerson(), vo.getChargeContact());
    	}catch(Exception e) {
    		Map<String, Object> resultMap = new HashMap<>();
    		logger.warn(".---更新队列失败，RabbitMq连接不上---.");
			resultMap.put("status",StatusConstant.handleFail.getValue());
    		return resultMap;
    	}
    }
    
}