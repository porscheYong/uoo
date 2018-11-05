package cn.ffcs.uoo.rabbitmq.manage.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.ffcs.uoo.rabbitmq.manage.constant.QueueConstant;
import cn.ffcs.uoo.rabbitmq.manage.constant.StatusConstant;
import cn.ffcs.uoo.rabbitmq.manage.dao.NodeInfoMapper;
import cn.ffcs.uoo.rabbitmq.manage.dao.RabbitmqDao;
import cn.ffcs.uoo.rabbitmq.manage.dao.SystemQueueRelaMapper;
import cn.ffcs.uoo.rabbitmq.manage.pojo.SystemQueueRela;
import cn.ffcs.uoo.rabbitmq.manage.service.RabbitMqService;
import cn.ffcs.uoo.rabbitmq.manage.util.BuildQueueNameUtil;
import cn.ffcs.uoo.rabbitmq.manage.util.StringUtil;
import cn.ffcs.uoo.rabbitmq.manage.vo.NodeVo;

@Service
@Transactional(readOnly=false,rollbackFor=Exception.class)
public class RabbitMqServiceImpl implements RabbitMqService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SystemQueueRelaMapper systemQueueRelaMapper;
	
	@Autowired
	private RabbitmqDao rabbitmqDao;
	
	@Autowired
	private NodeInfoMapper nodeInfoMapper;
	
	@Override
	public Map<String, Object> creatrQueue(String systemName, String doubleName, String chargePerson, String chargeContact) throws AmqpException, IOException {
		
		Map<String, Object> resultMap = new HashMap<>();
		
		if(StringUtil.isEmpty(systemName) || StringUtil.isEmpty(doubleName) || StringUtil.isEmpty(chargePerson) 
				|| StringUtil.isEmpty(chargeContact)) {
			logger.warn("systemName:{},doubleName:{},chargePerson:{},chargeContact:{}.---参数不全---.",systemName,doubleName,chargePerson,chargeContact);
			
			resultMap.put("status",StatusConstant.validateFail.getValue());
			
			return resultMap;
		}
		
		if(systemQueueRelaMapper.checkSystemQueueRela(systemName, doubleName,null,QueueConstant.valid.getValue()) > 0) {
			logger.warn("systemName:{},doubleName:{}.---系统中有该条数据---.",systemName,doubleName);
			resultMap.put("status",StatusConstant.validateFail.getValue());
			return resultMap;
		}
		
		NodeVo rand = nodeInfoMapper.getRandNodeInfo();
		
		String queueName = BuildQueueNameUtil.build();
		
		SystemQueueRela record = new SystemQueueRela();
		record.setSystemName(systemName);
		record.setChargeContact(chargeContact);
		record.setChargePerson(chargePerson);
		record.setDoubleName(doubleName);
		record.setQueueName(queueName);
		record.setStatus(QueueConstant.valid.getValue());
		record.setIp(rand.getIp());
		record.setPort(rand.getPort());
		record.setUsername(rand.getUsername());
		record.setPassword(rand.getPassword());
		systemQueueRelaMapper.insert(record);
		
		rabbitmqDao.createRabbitmqQueue(queueName);
		logger.info("queueName:{}创建成功",queueName);
		
		resultMap.put("status","1000");
		Map<String, Object> dataMap = new HashMap<>();
		dataMap.put("queueName", queueName);
		dataMap.put("queueHost", rand.getIp());
		dataMap.put("queuePost", rand.getPort());
		dataMap.put("username", rand.getUsername());
		dataMap.put("password", rand.getPassword());
		resultMap.put("data",dataMap);
		
		return resultMap;
	}

	@Override
	public Map<String, Object> deleteQueue(String systemName, String doubleName, String queueName) throws AmqpException, IOException {
		
		Map<String, Object> resultMap = new HashMap<>();
		
		if(StringUtil.isEmpty(systemName) || StringUtil.isEmpty(doubleName) || StringUtil.isEmpty(queueName)) {
			logger.warn("systemName:{},doubleName:{},queueName:{}.---参数不全---.",systemName,doubleName,queueName);
			
			resultMap.put("status",StatusConstant.validateFail.getValue());
			
			return resultMap;
		}
		
		if(systemQueueRelaMapper.checkSystemQueueRela(systemName, doubleName,queueName,QueueConstant.valid.getValue()) <= 0) {
			logger.warn("systemName:{},doubleName:{},queueName:{}.---系统中没有该条数据---.",systemName,doubleName,queueName);
			resultMap.put("status",StatusConstant.validateFail.getValue());
			return resultMap;
		}
		
		SystemQueueRela record = new SystemQueueRela();
		record.setQueueName(queueName);
		record.setStatus(QueueConstant.unvalid.getValue());
		systemQueueRelaMapper.updateByPrimaryKeySelective(record);
		
		rabbitmqDao.deleteRabbitmqQueue(queueName);
		
		logger.info("queueName:{}删除成功",queueName);
		
		resultMap.put("status","1000");
		return resultMap;
	}

	@Override
	public Map<String, Object> updateQueue(String systemName, String doubleName, String queueName, String chargePerson,
			String chargeContact) throws AmqpException, IOException {
		
		Map<String, Object> resultMap = new HashMap<>();
		
		if(StringUtil.isEmpty(systemName) || StringUtil.isEmpty(doubleName) || StringUtil.isEmpty(chargePerson) 
				|| StringUtil.isEmpty(chargeContact) || StringUtil.isEmpty(queueName)) {
			logger.warn("systemName:{},doubleName:{},chargePerson:{},chargeContact:{},queueName:{}.---参数不全---.",systemName,doubleName,chargePerson,chargeContact,queueName);
			
			resultMap.put("status",StatusConstant.validateFail.getValue());
			
			return resultMap;
		}
		
		if(systemQueueRelaMapper.checkSystemQueueRela(systemName, doubleName,queueName,QueueConstant.valid.getValue()) <= 0) {
			logger.warn("systemName:{},doubleName:{},queueName:{}.---系统中没有该条数据---.",systemName,doubleName,queueName);
			resultMap.put("status",StatusConstant.validateFail.getValue());
			return resultMap;
		}
		
		//删除部分
		SystemQueueRela record = new SystemQueueRela();
		record.setQueueName(queueName);
		record.setStatus(QueueConstant.unvalid.getValue());
		systemQueueRelaMapper.updateByPrimaryKeySelective(record);
		
		rabbitmqDao.deleteRabbitmqQueue(queueName);
		logger.info("queueName:{}删除成功",queueName);
		
		//重新绑定
		queueName = BuildQueueNameUtil.build();
		
		NodeVo rand = nodeInfoMapper.getRandNodeInfo();
		
		record = new SystemQueueRela();
		record.setSystemName(systemName);
		record.setChargeContact(chargeContact);
		record.setChargePerson(chargePerson);
		record.setDoubleName(doubleName);
		record.setQueueName(queueName);
		record.setStatus(QueueConstant.valid.getValue());
		record.setIp(rand.getIp());
		record.setPort(rand.getPort());
		record.setUsername(rand.getUsername());
		record.setPassword(rand.getPassword());
		systemQueueRelaMapper.insert(record);
		
		rabbitmqDao.createRabbitmqQueue(queueName);
		logger.info("queueName:{}创建成功",queueName);
		
		resultMap.put("status","1000");
		
		Map<String, Object> dataMap = new HashMap<>();
		
		dataMap.put("queueName", queueName);
		dataMap.put("queueHost", rand.getIp());
		dataMap.put("queuePost", rand.getPort());
		dataMap.put("username", rand.getUsername());
		dataMap.put("password", rand.getPassword());
		
		resultMap.put("data",dataMap);
		
		return resultMap;
	}
}