package cn.ffcs.uoo.message.server.controller;

import cn.ffcs.uoo.message.server.constant.QueueConstant;
import cn.ffcs.uoo.message.server.constant.StatusConstant;
import cn.ffcs.uoo.message.server.constant.ValidateConstant;
import cn.ffcs.uoo.message.server.dao.*;
import cn.ffcs.uoo.message.server.pojo.*;
import cn.ffcs.uoo.message.server.service.RabbitMqSendService;
import cn.ffcs.uoo.message.server.util.OrgShowUtil;
import cn.ffcs.uoo.message.server.vo.*;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.ffcs.uoo.message.server.service.SystemRuleService;
import cn.ffcs.uoo.message.server.util.PersonShowUtil;
import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
@RabbitListener(queues = {"message_sharing_center_queue"})
public class ReceiveDateListener {

    @Autowired
    private SystemRuleService SystemRuleService;

    @Autowired
    private RabbitMqSendService rabbitMqSendService;

    @Resource
    private TbSlaveAcctMapper tbSlaveAcctMapper;
    @Resource
    private TbOrgMapper tbOrgMapper;
    @Resource
    private TbOrgCrossRelMapper tbOrgCrossRelMapper;
    @Resource
    private SystemQueueRelaMapper systemQueueRelaMapper;
    @Resource
    private RabbitmqIndexMapper rabbitmqIndexMapper;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    //获取数据
    @RabbitHandler
    public void process(String json, Channel channel, Message message) throws IOException {
        try {
            //解析json
            Map<String, Object> jsonMap = JSON.parseObject(json, Map.class);
            String handle = (String) jsonMap.get("handle");
            String type = (String) jsonMap.get("type");
            Map<String, Object> context = (Map<String, Object>) jsonMap.get("context");
            String column = (String) context.get("column");
            Long value = null;
            if(context.get("value") != null){
                value = Long.parseLong(String.valueOf(context.get("value")));
            }
            JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

            if ("org".equals(type)) {
                if (value != null && "orgId".equals(column)) {
                    //获取需要下发的系统和对应的规则
                    List<Map<String, Object>> list = SystemRuleService.getSystemRuleByOrg(value);
                    if (list != null) {
                        for (Map<String, Object> map : list) {
                            OrgTreeRuleVo vo = (OrgTreeRuleVo) map.get("OrgTreeRuleVo");
                            TbBusinessSystem system = (TbBusinessSystem) map.get("system");

                            switch (handle) {
                                case "insert":
                                case "update": {
                                    TbOrgVo tbOrgVo = tbOrgMapper.getOrgVo(value, vo.getOrgTreeId(), vo.getBusinessSystemId());

                                    if (tbOrgVo == null) {
                                        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                                        return;
                                    }

                                    OrgShowUtil.noShow(tbOrgVo, system);
                                    if (vo.getTbSystemIndividuationRules() != null && vo.getTbSystemIndividuationRules().size() > 0) {

                                    }
                                    String id = sdf.format(new Date()) + UUID.randomUUID().toString().replaceAll("-", "").trim();
                                    RestfulVo restfulVo = new RestfulVo();
                                    restfulVo.setHandle(handle);
                                    restfulVo.setSerial(id);
                                    restfulVo.setType(type);
                                    restfulVo.setContext(tbOrgVo);
                                    String msg = JSON.toJSONString(restfulVo, SerializerFeature.WriteDateUseDateFormat);
                                    System.out.println(msg);
                                    String queueName = systemQueueRelaMapper.getQueueName(system.getSystemName(), "" + system.getBusinessSystemId(), QueueConstant.valid.getValue());
                                    RabbitmqIndex index = new RabbitmqIndex();
                                    index.setId(id);
                                    index.setState(QueueConstant.valid.getValue());
                                    index.setCollectionData(new Date());
                                    index.setRabbitmqDate(msg);
                                    index.setQueueName(queueName);
                                    rabbitmqIndexMapper.insert(index);
                                    rabbitMqSendService.sendMsg(queueName, msg);
                                }
                                ;
                                break;
                                case "delete": {
                                    //校验
                                    TbOrg tbOrg = tbOrgMapper.selectById(value);

                                    if (tbOrg != null && ValidateConstant.fail.getValue().equals(tbOrg.getStatusCd())) {
                                        TbOrgVo tbOrgVo = new TbOrgVo();
                                        tbOrgVo.setOrgId(value);
                                        List<TbOrgCrossRel> crossRelList = tbOrgCrossRelMapper.getListByOrgIdAndSystemCode(value);
                                        tbOrgVo.setOrgCrossRelations(crossRelList);

                                        if (vo.getTbSystemIndividuationRules() != null && vo.getTbSystemIndividuationRules().size() > 0) {

                                        }
                                        String id = sdf.format(new Date()) + UUID.randomUUID().toString().replaceAll("-", "").trim();
                                        RestfulVo restfulVo = new RestfulVo();
                                        restfulVo.setHandle(handle);
                                        restfulVo.setSerial(id);
                                        restfulVo.setType(type);
                                        restfulVo.setContext(tbOrgVo);
                                        String msg = JSON.toJSONString(restfulVo, SerializerFeature.WriteDateUseDateFormat);
                                        System.out.println(msg);
                                        String queueName = systemQueueRelaMapper.getQueueName(system.getSystemName(), "" + system.getBusinessSystemId(), QueueConstant.valid.getValue());
                                        RabbitmqIndex index = new RabbitmqIndex();
                                        index.setId(id);
                                        index.setState(QueueConstant.valid.getValue());
                                        index.setCollectionData(new Date());
                                        index.setRabbitmqDate(msg);
                                        index.setQueueName(queueName);
                                        rabbitmqIndexMapper.insert(index);
                                        rabbitMqSendService.sendMsg(queueName, msg);
                                    }
                                }
                                ;
                                break;
                                default:
                                    break;
                            }
                        }
                    }
                }
            } else if ("person".equals(type)) {
                if (value != null && "personnelId".equals(column)) {
                    List<Map<String, Object>> list = SystemRuleService.getSystemRuleByPerson(value);

                    if (list != null) {
                        for (Map<String, Object> map : list) {
                            OrgTreeRuleVo vo = (OrgTreeRuleVo) map.get("OrgTreeRuleVo");
                            TbBusinessSystem system = (TbBusinessSystem) map.get("system");

                            switch (handle) {
                                case "insert":
                                case "update": {
                                    List<TbSlaveAcct> slaveList = tbSlaveAcctMapper.insertOrUpdateSalveAcctByPersonnelIdAndSystemId(value, system.getBusinessSystemId());
                                    if (slaveList != null && slaveList.size() !=0) {
                                        //下发人员和从账号
                                        if (vo.getIncludePsn() == 1 && vo.getIncludeSlaveAcct() == 1) {
                                            TbSlaveAcct temp = slaveList.get(0);
                                            TbAcctVo tbAcctVo = tbSlaveAcctMapper.insertOrUpdateSalveAcct(temp.getSlaveAcctId());
                                            tbAcctVo.getTbSlaveAcct().setSystemName(system.getSystemName());
                                            tbAcctVo.getTbSlaveAcct().setBusinessSystemId(system.getBusinessSystemId());
                                            PersonShowUtil.noShow(tbAcctVo);
                                            if (vo.getTbSystemIndividuationRules() != null && vo.getTbSystemIndividuationRules().size() > 0) {

                                            }
                                            String id = sdf.format(new Date()) + UUID.randomUUID().toString().replaceAll("-", "").trim();
                                            RestfulVo restfulVo = new RestfulVo();
                                            restfulVo.setHandle(handle);
                                            restfulVo.setSerial(id);
                                            restfulVo.setType(type);
                                            restfulVo.setContext(tbAcctVo);
                                            String msg = JSON.toJSONString(restfulVo, SerializerFeature.WriteDateUseDateFormat);
                                            System.out.println(msg);
                                            String queueName = systemQueueRelaMapper.getQueueName(system.getSystemName(), "" + system.getBusinessSystemId(), QueueConstant.valid.getValue());
                                            RabbitmqIndex index = new RabbitmqIndex();
                                            index.setId(id);
                                            index.setState(QueueConstant.valid.getValue());
                                            index.setCollectionData(new Date());
                                            index.setRabbitmqDate(msg);
                                            index.setQueueName(queueName);
                                            rabbitmqIndexMapper.insert(index);
                                            rabbitMqSendService.sendMsg(queueName, msg);
                                        } else if (vo.getIncludePsn() == 1 && vo.getIncludeSlaveAcct() != 1) {
                                            TbSlaveAcct temp = slaveList.get(0);
                                            TbAcctVo tbAcctVo = tbSlaveAcctMapper.insertOrUpdateSalveAcct(temp.getSlaveAcctId());
                                            tbAcctVo.setTbSlaveAcct(null);
                                            PersonShowUtil.noShow(tbAcctVo);
                                            if (vo.getTbSystemIndividuationRules() != null && vo.getTbSystemIndividuationRules().size() > 0) {

                                            }
                                            String id = sdf.format(new Date()) + UUID.randomUUID().toString().replaceAll("-", "").trim();
                                            RestfulVo restfulVo = new RestfulVo();
                                            tbAcctVo.setTbSlaveAcct(null);
                                            restfulVo.setHandle(handle);
                                            restfulVo.setSerial(id);
                                            restfulVo.setType(type);
                                            restfulVo.setContext(tbAcctVo);
                                            String msg = JSON.toJSONString(restfulVo, SerializerFeature.WriteDateUseDateFormat);
                                            System.out.println(msg);
                                            String queueName = systemQueueRelaMapper.getQueueName(system.getSystemName(), "" + system.getBusinessSystemId(), QueueConstant.valid.getValue());
                                            RabbitmqIndex index = new RabbitmqIndex();
                                            index.setId(id);
                                            index.setState(QueueConstant.valid.getValue());
                                            index.setCollectionData(new Date());
                                            index.setRabbitmqDate(msg);
                                            index.setQueueName(queueName);
                                            rabbitmqIndexMapper.insert(index);
                                            rabbitMqSendService.sendMsg(queueName, msg);
                                        }
                                    } else {
                                        //没有从账号
                                        if (vo.getIncludePsn() == 1 && vo.getIncludeSlaveAcct() != 1) {
                                            TbAcctVo tbAcctVo = tbSlaveAcctMapper.insertOrUpdateAcct(value, vo.getOrgTreeId());
                                            PersonShowUtil.noShow(tbAcctVo);
                                            if (vo.getTbSystemIndividuationRules() != null && vo.getTbSystemIndividuationRules().size() > 0) {

                                            }
                                            String id = sdf.format(new Date()) + UUID.randomUUID().toString().replaceAll("-", "").trim();
                                            RestfulVo restfulVo = new RestfulVo();
                                            tbAcctVo.setTbSlaveAcct(null);
                                            restfulVo.setHandle(handle);
                                            restfulVo.setSerial(id);
                                            restfulVo.setType(type);
                                            restfulVo.setContext(tbAcctVo);
                                            String msg = JSON.toJSONString(restfulVo, SerializerFeature.WriteDateUseDateFormat);
                                            System.out.println(msg);
                                            String queueName = systemQueueRelaMapper.getQueueName(system.getSystemName(), "" + system.getBusinessSystemId(), QueueConstant.valid.getValue());
                                            RabbitmqIndex index = new RabbitmqIndex();
                                            index.setId(id);
                                            index.setState(QueueConstant.valid.getValue());
                                            index.setCollectionData(new Date());
                                            index.setRabbitmqDate(msg);
                                            index.setQueueName(queueName);
                                            rabbitmqIndexMapper.insert(index);
                                            rabbitMqSendService.sendMsg(queueName, msg);
                                        }
                                    }
                                }
                                ;
                                break;
                                case "delete": {

                                    //人员删除或者主账号删除
                                    int flag = tbSlaveAcctMapper.checkPersonnelAndAcct(value);

                                    if (flag == 1) {
                                        List<TbSlaveAcct> slaveList = tbSlaveAcctMapper.insertOrUpdateSalveAcctByPersonnelIdAndSystemId(value, system.getBusinessSystemId());
                                        if (slaveList != null) {
                                            if (vo.getIncludePsn() == 1 && vo.getIncludeSlaveAcct() == 1) {
                                                slaveList.forEach((temp) -> {
                                                    TbAcctVo tbAcctVo = new TbAcctVo();
                                                    TbSlaveAcctVo tbSlaveAcctVo = new TbSlaveAcctVo();
                                                    tbSlaveAcctVo.setSlaveAcctId(temp.getSlaveAcctId());
                                                    tbAcctVo.setTbSlaveAcct(tbSlaveAcctVo);
                                                    if (vo.getTbSystemIndividuationRules() != null && vo.getTbSystemIndividuationRules().size() > 0) {

                                                    }
                                                    String id = sdf.format(new Date()) + UUID.randomUUID().toString().replaceAll("-", "").trim();
                                                    RestfulVo restfulVo = new RestfulVo();
                                                    restfulVo.setHandle(handle);
                                                    restfulVo.setSerial(id);
                                                    restfulVo.setType(type);
                                                    restfulVo.setContext(tbAcctVo);
                                                    String msg = JSON.toJSONString(restfulVo, SerializerFeature.WriteDateUseDateFormat);
                                                    System.out.println(msg);
                                                    String queueName = systemQueueRelaMapper.getQueueName(system.getSystemName(), "" + system.getBusinessSystemId(), QueueConstant.valid.getValue());
                                                    RabbitmqIndex index = new RabbitmqIndex();
                                                    index.setId(id);
                                                    index.setState(QueueConstant.valid.getValue());
                                                    index.setCollectionData(new Date());
                                                    index.setRabbitmqDate(msg);
                                                    index.setQueueName(queueName);
                                                    rabbitmqIndexMapper.insert(index);
                                                    rabbitMqSendService.sendMsg(queueName, msg);
                                                });
                                            } else if (vo.getIncludePsn() == 1 && vo.getIncludeSlaveAcct() != 1) {
                                                TbSlaveAcct temp = slaveList.get(0);
                                                TbAcctVo tbAcctVo = new TbAcctVo();
                                                TbPersonnel tbPersonnel = new TbPersonnel();
                                                tbPersonnel.setPersonnelId(value);
                                                tbAcctVo.setTbPersonnel(tbPersonnel);
                                                if (vo.getTbSystemIndividuationRules() != null && vo.getTbSystemIndividuationRules().size() > 0) {

                                                }
                                                String id = sdf.format(new Date()) + UUID.randomUUID().toString().replaceAll("-", "").trim();
                                                RestfulVo restfulVo = new RestfulVo();
                                                restfulVo.setHandle(handle);
                                                restfulVo.setSerial(id);
                                                restfulVo.setType(type);
                                                restfulVo.setContext(tbAcctVo);
                                                String msg = JSON.toJSONString(restfulVo, SerializerFeature.WriteDateUseDateFormat);
                                                System.out.println(msg);
                                                String queueName = systemQueueRelaMapper.getQueueName(system.getSystemName(), "" + system.getBusinessSystemId(), QueueConstant.valid.getValue());
                                                RabbitmqIndex index = new RabbitmqIndex();
                                                index.setId(id);
                                                index.setState(QueueConstant.valid.getValue());
                                                index.setCollectionData(new Date());
                                                index.setRabbitmqDate(msg);
                                                index.setQueueName(queueName);
                                                rabbitmqIndexMapper.insert(index);
                                                rabbitMqSendService.sendMsg(queueName, msg);
                                            }
                                        } else {
                                            if (vo.getIncludePsn() == 1 && vo.getIncludeSlaveAcct() != 1) {
                                                TbAcctVo tbAcctVo = new TbAcctVo();
                                                TbPersonnel tbPersonnel = new TbPersonnel();
                                                tbPersonnel.setPersonnelId(value);
                                                tbAcctVo.setTbPersonnel(tbPersonnel);
                                                if (vo.getTbSystemIndividuationRules() != null && vo.getTbSystemIndividuationRules().size() > 0) {

                                                }
                                                String id = sdf.format(new Date()) + UUID.randomUUID().toString().replaceAll("-", "").trim();
                                                RestfulVo restfulVo = new RestfulVo();
                                                restfulVo.setHandle(handle);
                                                restfulVo.setSerial(id);
                                                restfulVo.setType(type);
                                                restfulVo.setContext(tbAcctVo);
                                                String msg = JSON.toJSONString(restfulVo, SerializerFeature.WriteDateUseDateFormat);
                                                System.out.println(msg);
                                                String queueName = systemQueueRelaMapper.getQueueName(system.getSystemName(), "" + system.getBusinessSystemId(), QueueConstant.valid.getValue());
                                                RabbitmqIndex index = new RabbitmqIndex();
                                                index.setId(id);
                                                index.setState(QueueConstant.valid.getValue());
                                                index.setCollectionData(new Date());
                                                index.setRabbitmqDate(msg);
                                                index.setQueueName(queueName);
                                                rabbitmqIndexMapper.insert(index);
                                                rabbitMqSendService.sendMsg(queueName, msg);
                                            }
                                        }
                                    }
                                }
                                ;
                                break;
                                default:
                                    break;
                            }
                        }
                    }
                } else if (value != null && "slaveAcctId".equals(column)) {
                    //获取需要下发的系统和对应的规则
                    Map<String, Object> map = SystemRuleService.getSystemRuleBySlaveAcct(value);

                    if (map == null) {
                        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                        return;
                    }

                    OrgTreeRuleVo vo = (OrgTreeRuleVo) map.get("OrgTreeRuleVo");
                    TbBusinessSystem system = (TbBusinessSystem) map.get("system");
                    //没有系统。
                    if (vo == null) {
                        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                        return;
                    }

                    switch (handle) {
                        case "insert":
                        case "update": {
                            TbAcctVo tbAcctVo = tbSlaveAcctMapper.insertOrUpdateSalveAcct(value);

                            tbAcctVo.getTbSlaveAcct().setSystemName(system.getSystemName());
                            tbAcctVo.getTbSlaveAcct().setBusinessSystemId(system.getBusinessSystemId());
                            PersonShowUtil.noShow(tbAcctVo);

                            if (vo.getTbSystemIndividuationRules() != null && vo.getTbSystemIndividuationRules().size() > 0) {

                            }

                            if (vo.getIncludePsn() == 1 && vo.getIncludeSlaveAcct() == 1) {
                                //下发人员和从账号
                                String id = sdf.format(new Date()) + UUID.randomUUID().toString().replaceAll("-", "").trim();
                                RestfulVo restfulVo = new RestfulVo();
                                restfulVo.setHandle(handle);
                                restfulVo.setSerial(id);
                                restfulVo.setType(type);
                                restfulVo.setContext(tbAcctVo);
                                String msg = JSON.toJSONString(restfulVo, SerializerFeature.WriteDateUseDateFormat);
                                System.out.println(msg);
                                String queueName = systemQueueRelaMapper.getQueueName(system.getSystemName(), "" + system.getBusinessSystemId(), QueueConstant.valid.getValue());
                                RabbitmqIndex index = new RabbitmqIndex();
                                index.setId(id);
                                index.setState(QueueConstant.valid.getValue());
                                index.setCollectionData(new Date());
                                index.setRabbitmqDate(msg);
                                index.setQueueName(queueName);
                                rabbitmqIndexMapper.insert(index);
                                rabbitMqSendService.sendMsg(queueName, msg);
                            } else if (vo.getIncludePsn() == 1 && vo.getIncludeSlaveAcct() != 1) {
                                //只下发人员
                                String id = sdf.format(new Date()) + UUID.randomUUID().toString().replaceAll("-", "").trim();
                                RestfulVo restfulVo = new RestfulVo();
                                tbAcctVo.setTbSlaveAcct(null);
                                restfulVo.setHandle(handle);
                                restfulVo.setSerial(id);
                                restfulVo.setType(type);
                                restfulVo.setContext(tbAcctVo);
                                String msg = JSON.toJSONString(restfulVo, SerializerFeature.WriteDateUseDateFormat);
                                System.out.println(msg);
                                String queueName = systemQueueRelaMapper.getQueueName(system.getSystemName(), "" + system.getBusinessSystemId(), QueueConstant.valid.getValue());
                                RabbitmqIndex index = new RabbitmqIndex();
                                index.setId(id);
                                index.setState(QueueConstant.valid.getValue());
                                index.setCollectionData(new Date());
                                index.setRabbitmqDate(msg);
                                index.setQueueName(queueName);
                                rabbitmqIndexMapper.insert(index);
                                rabbitMqSendService.sendMsg(queueName, msg);
                            }
                        }
                        ;
                        break;
                        case "delete": {
                            TbSlaveAcct tbSlaveAcct = tbSlaveAcctMapper.selectById(value);
                            if (tbSlaveAcct != null && ValidateConstant.fail.getValue().equals(tbSlaveAcct.getStatusCd())) {

                                TbAcctVo tbAcctVo = new TbAcctVo();
                                TbSlaveAcctVo tbSlaveAcctVo = new TbSlaveAcctVo();
                                tbSlaveAcctVo.setSlaveAcctId(value);
                                tbAcctVo.setTbSlaveAcct(tbSlaveAcctVo);
                                if (vo.getTbSystemIndividuationRules() != null && vo.getTbSystemIndividuationRules().size() > 0) {

                                }
                                if (vo.getIncludePsn() == 1 && vo.getIncludeSlaveAcct() == 1) {
                                    //下发人员和从账号
                                    String id = sdf.format(new Date()) + UUID.randomUUID().toString().replaceAll("-", "").trim();
                                    RestfulVo restfulVo = new RestfulVo();
                                    restfulVo.setHandle(handle);
                                    restfulVo.setSerial(id);
                                    restfulVo.setType(type);
                                    restfulVo.setContext(tbAcctVo);
                                    String msg = JSON.toJSONString(restfulVo, SerializerFeature.WriteDateUseDateFormat);
                                    System.out.println(msg);
                                    String queueName = systemQueueRelaMapper.getQueueName(system.getSystemName(), "" + system.getBusinessSystemId(), QueueConstant.valid.getValue());
                                    RabbitmqIndex index = new RabbitmqIndex();
                                    index.setId(id);
                                    index.setState(QueueConstant.valid.getValue());
                                    index.setCollectionData(new Date());
                                    index.setRabbitmqDate(msg);
                                    index.setQueueName(queueName);
                                    rabbitmqIndexMapper.insert(index);
                                    rabbitMqSendService.sendMsg(queueName, msg);
                                }
                            }
                        }
                        ;
                        break;
                        default:
                            break;
                    }
                }
            }
            //Thread.sleep(1000);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            e.printStackTrace();
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
    }
}