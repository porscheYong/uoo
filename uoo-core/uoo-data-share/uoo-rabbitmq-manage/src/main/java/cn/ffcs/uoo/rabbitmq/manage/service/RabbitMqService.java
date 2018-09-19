package cn.ffcs.uoo.rabbitmq.manage.service;

import java.util.Map;

public interface RabbitMqService {
	//创建队列
	public Map<String, Object> creatrQueue(String systemName,String doubleName,String chargePerson,String chargeContact) throws Exception;
	
	//删除队列
	public Map<String, Object> deleteQueue(String systemName,String doubleName,String queueName) throws Exception;
	
	//修改队列
	public Map<String, Object> updateQueue(String systemName,String doubleName,String queueName,String chargePerson,String chargeContact) throws Exception;
	
}