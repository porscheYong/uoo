package cn.ffcs.uoo.message.server.controller;
import java.util.Date;

import cn.ffcs.uoo.message.server.constant.QueueConstant;
import cn.ffcs.uoo.message.server.constant.ValidateConstant;
import cn.ffcs.uoo.message.server.dao.*;
import cn.ffcs.uoo.message.server.pojo.*;
import cn.ffcs.uoo.message.server.service.RabbitMqSendService;
import cn.ffcs.uoo.message.server.service.SystemRuleService;
import cn.ffcs.uoo.message.server.util.OrgShowUtil;
import cn.ffcs.uoo.message.server.util.PersonShowUtil;
import cn.ffcs.uoo.message.server.vo.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
@RabbitListener(queues = {"message_sharing_center_queue"})
public class ReceiveDateListenerRec {


    @Autowired
    private SystemRuleService systemRuleService;//获取系统和对应的规则
    @Autowired
    private RabbitMqSendService rabbitMqSendService;//发送消息
    @Resource
    private RabbitmqIndexMapper rabbitmqIndexMapper;//消息报文日志表
    @Resource
    private TbOrgMapper tbOrgMapper;//组织查询
    @Resource
    private SystemQueueRelaMapper systemQueueRelaMapper;//系统队列查询
    @Resource
    private TbOrgCrossRelMapper tbOrgCrossRelMapper;//跨域查询
    @Resource
    private TbSlaveAcctMapper tbSlaveAcctMapper;//从账号
    @Resource
    private TbAcctMapper tbAcctMapper;
    @Resource
    private TbBusinessSystemMapper tbBusinessSystemMapper;
    @Resource
    private TbAcctCrossRelMapper tbAcctCrossRelMapper;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");//日期

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RabbitHandler
    public void process(String json, Channel channel, Message message) throws IOException {
        try {
            logger.info("json:{}", json);
            //json 转换成Map
            Map<String, Object> jsonMap = JSON.parseObject(json, Map.class);
            //处理模式
            String handle = (String) jsonMap.get("handle");
            //类型
            String type = (String) jsonMap.get("type");
            //内容
            Map<String, Object> context = (Map<String, Object>) jsonMap.get("context");
            //操作对象
            String column = (String) context.get("column");
            //操作对象值
            Long value = null;
            if (context.get("value") != null) {
                value = Long.parseLong(String.valueOf(context.get("value")));
            }
            //下发报文时间格式定义
            JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

            //下发报文集合
            List<RabbitmqIndex> rs = null;
            //组织
            if ("org".equals(type) && "orgId".equals(column) && value != null) {
                rs = handleOrg(json, handle, type, value);
            }
            //人员
            else if ("person".equals(type) && "personnelId".equals(column) && value != null) {
                rs = handlePersonnel(json, handle, type, value);
            }
            //从账号
            else if ("person".equals(type) && "slaveAcctId".equals(column) && value != null) {
                rs = handleSlaveAcct(json, handle, type, value);
            }

            //下发报文
            if (rs != null && rs.size() >0) {
                for (RabbitmqIndex index : rs) {
                    rabbitmqIndexMapper.insert(index);
                    rabbitMqSendService.sendMsg(index.getQueueName(), index.getRabbitmqDate());
                }
            }else{
                logger.warn("json:{},该json不进行下发的数据。",json);
            }
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            logger.error("msg:{},Exception:{}", json, e);
            //重回队列
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
    }

    //操作组织
    private List<RabbitmqIndex> handleOrg(String json, String handle, String type, Long orgId) throws IOException {
        List<RabbitmqIndex> rs = new ArrayList<>();
        //获取需要下发的数据的所有的系统和对应的规则
        List<Map<String, Object>> list = systemRuleService.getSystemRuleByOrg(orgId);
        if (list == null || list.size() == 0) {
            logger.warn("json:{},没有需要下发的系统", json);
            rs = null;
        } else {
            switch (handle) {
                case "insert":
                case "update": {
                    for (Map<String, Object> map : list) {
                        //规则
                        OrgTreeRuleVo vo = (OrgTreeRuleVo) map.get("OrgTreeRuleVo");
                        //系统
                        TbBusinessSystem system = (TbBusinessSystem) map.get("system");
                        //获取组织Vo
                        TbOrgVo tbOrgVo = tbOrgMapper.getOrgVo(orgId, vo.getOrgTreeId(), vo.getBusinessSystemId());

                        if (tbOrgVo == null) {
                            logger.warn("json:{},treeId:{},systemId:{} 没有查询到数据", json, vo.getOrgTreeId(), vo.getBusinessSystemId());
                            continue;
                        } else {
                            //不展示部分数据
                            OrgShowUtil.noShow(tbOrgVo, system);

                            if (tbOrgVo == null) {
                                logger.warn("json:{},treeId:{},systemId:{} 数据校验不合规。", json, vo.getOrgTreeId(), vo.getBusinessSystemId());
                                continue;
                            }

                            //规则校验
                            if (vo.getTbSystemIndividuationRules() != null && vo.getTbSystemIndividuationRules().size() > 0) {

                            }
                            RabbitmqIndex index = montage(handle, type, json, tbOrgVo, system);
                            rs.add(index);
                        }
                    }
                }
                ;
                break;
                case "delete": {
                    //校验
                    TbOrg tbOrg = tbOrgMapper.selectById(orgId);

                    //判断组织是否失效
                    if (tbOrg != null && ValidateConstant.fail.getValue().equals(tbOrg.getStatusCd())) {
                        for (Map<String, Object> map : list) {
                            //规则
                            OrgTreeRuleVo vo = (OrgTreeRuleVo) map.get("OrgTreeRuleVo");
                            //系统
                            TbBusinessSystem system = (TbBusinessSystem) map.get("system");
                            //规则校验
                            if (vo.getTbSystemIndividuationRules() != null && vo.getTbSystemIndividuationRules().size() > 0) {

                            }
                            TbOrgVo tbOrgVo = new TbOrgVo();
                            tbOrgVo.setOrgId(orgId);
                            tbOrgVo.setOrgCode(tbOrg.getOrgCode());
                            tbOrgVo.setUuid(tbOrg.getUuid());
                            //跨域
                            List<TbOrgCrossRel> crossRelList = tbOrgCrossRelMapper.getListByOrgIdAndSystemCode(orgId);
                            tbOrgVo.setOrgCrossRelations(crossRelList);
                            RabbitmqIndex index = montage(handle, type, json, tbOrgVo, system);
                            rs.add(index);
                        }
                    } else {
                        logger.warn("json:{},非法组织Id,或者组织已经不是失效状态了。");
                    }
                }
                ;
                break;
                default:
                    break;
            }
        }
        return rs;
    }

    //操作人员
    private List<RabbitmqIndex> handlePersonnel(String json, String handle, String type, Long personnelId) throws IOException {
        List<RabbitmqIndex> rs = new ArrayList<>();
        int UnUseFlag = tbSlaveAcctMapper.checkPersonnelAndAcctByUnUse(personnelId);//失效的人员或主账号条数
        int UseFlag = tbSlaveAcctMapper.checkPersonnelAndAcctByUser(personnelId);//1:是有效的人员和主账号

        List<Map<String, Object>> list = null;
        if ("insert".equals(handle) || "update".equals(handle)) {

            if (UseFlag != 1) {
                logger.warn("json:{},人员，主账号信息不是有效数据不存在", json);
                return null;
            }
            list = systemRuleService.getSystemRuleByPerson(personnelId);
        } else if ("delete".equals(handle)) {
            if (UseFlag == 1 || UnUseFlag == 0) {
                logger.warn("json:{},人员，主账号信息是有效数据或者不存在", json);
                return null;
            }
            list = systemRuleService.getSystemRuleByPersonLimitDelete(personnelId);
        }
        if (list == null || list.size() == 0) {
            logger.warn("json:{},没有需要下发的系统", json);
            rs = null;
        } else {
            for (Map<String, Object> map : list) {
                OrgTreeRuleVo vo = (OrgTreeRuleVo) map.get("OrgTreeRuleVo");
                TbBusinessSystem system = (TbBusinessSystem) map.get("system");

                switch (handle) {
                    case "insert":
                    case "update": {
                        //获取所有的从账号
                        List<TbSlaveAcct> slaveList = tbSlaveAcctMapper.insertOrUpdateSalveAcctByPersonnelIdAndSystemId(personnelId, system.getBusinessSystemId());
                        //是否有从账号
                        boolean isHaveSlave = slaveList != null && slaveList.size() > 0;
                        //下发人员和从账号
                        if (vo.getIncludePsn() == 1 && vo.getIncludeSlaveAcct() == 1 && isHaveSlave) {
                            for (TbSlaveAcct temp : slaveList) {
                                //校验
                                if(tbBusinessSystemMapper.validateSlave(system.getBusinessSystemId(),temp.getSlaveAcctId()) == 2){
                                    logger.warn("json:{},SlaveAcctId:{}校验失败，错误数据,请检查据库数据据。", json,temp.getSlaveAcctId());
                                    continue;
                                }
                                TbAcctVo tbAcctVo = tbSlaveAcctMapper.insertOrUpdateSalveAcct(temp.getSlaveAcctId());
                                tbAcctVo.getTbSlaveAcct().setSystemName(system.getSystemName());
                                tbAcctVo.getTbSlaveAcct().setBusinessSystemId(system.getBusinessSystemId());
                                tbAcctVo.getTbSlaveAcct().setSystemCode(system.getSystemCode());

                                PersonShowUtil.noShow(tbAcctVo);
                                if (tbAcctVo == null) {
                                    logger.warn("json:{},treeId:{},systemId:{} 数据校验不合规。", json, vo.getOrgTreeId(), vo.getBusinessSystemId());
                                    continue;
                                }

                                if (vo.getTbSystemIndividuationRules() != null && vo.getTbSystemIndividuationRules().size() > 0) {

                                }
                                RabbitmqIndex index = montage(handle, type, json, tbAcctVo, system);
                                rs.add(index);
                                break;
                            }
                        }
                        //只下发人员
                        else if (vo.getIncludePsn() == 1 && vo.getIncludeSlaveAcct() != 1) {
                            TbAcctVo tbAcctVo = tbSlaveAcctMapper.insertOrUpdateAcct(personnelId, vo.getOrgTreeId());
                            PersonShowUtil.noShow(tbAcctVo);
                            if (tbAcctVo == null) {
                                logger.warn("json:{},treeId:{},systemId:{} 数据校验不合规。", json, vo.getOrgTreeId(), vo.getBusinessSystemId());
                                continue;
                            }
                            if (vo.getTbSystemIndividuationRules() != null && vo.getTbSystemIndividuationRules().size() > 0) {

                            }
                            RabbitmqIndex index = montage(handle, type, json, tbAcctVo, system);
                            rs.add(index);
                        }
                    }
                    ;
                    break;
                    case "delete": {
                        TbAcct tbAcct = tbAcctMapper.selectByPersonIdLimitDelete(personnelId);
                        List<TbSlaveAcct> slaveList = tbSlaveAcctMapper.deleteSalveAcctByAcct(tbAcct.getAcctId(), system.getBusinessSystemId());
                        //是否有从账号
                        boolean isHaveSlave = slaveList != null && slaveList.size() > 0;
                        //下发人员和从账号
                        if (vo.getIncludePsn() == 1 && vo.getIncludeSlaveAcct() == 1 && isHaveSlave) {
                            for (TbSlaveAcct temp : slaveList) {
                                if (temp != null && ValidateConstant.fail.getValue().equals(temp.getStatusCd())) {
                                    //校验
                                    if(tbBusinessSystemMapper.validateSlave(system.getBusinessSystemId(),temp.getSlaveAcctId()) == 2){
                                        logger.warn("json:{},SlaveAcctId:{}校验失败，错误数据,请检查数据库数据据。", json,temp.getSlaveAcctId());
                                        continue;
                                    }
                                    TbSlaveAcctVo tbSlaveAcct = tbSlaveAcctMapper.selectVoById(temp.getSlaveAcctId());
                                    //规则判断
                                    if (vo.getTbSystemIndividuationRules() != null && vo.getTbSystemIndividuationRules().size() > 0) {
                                    }
                                    TbAcctVo tbAcctVo = new TbAcctVo();
                                    tbAcctVo.setAcctId(tbAcct.getAcctId());
                                    tbAcctVo.setAcct(tbAcct.getAcct());
                                    tbAcctVo.setStatusCd(tbAcct.getStatusCd());

                                    TbSlaveAcctVo tbSlaveAcctVo = new TbSlaveAcctVo();
                                    tbSlaveAcctVo.setMappStaffId(tbSlaveAcct.getMappStaffId());
                                    tbSlaveAcctVo.setStatusCd(tbSlaveAcct.getStatusCd());
                                    tbSlaveAcctVo.setSlaveAcct(tbSlaveAcct.getSlaveAcct());
                                    tbSlaveAcctVo.setSlaveAcctId(tbSlaveAcct.getSlaveAcctId());
                                    tbSlaveAcctVo.setSystemName(system.getSystemName());//添加系统的名称
                                    tbSlaveAcctVo.setBusinessSystemId(system.getBusinessSystemId());//添加系统的id
                                    tbSlaveAcctVo.setSystemCode(system.getSystemCode());//添加系统的编码

                                    tbAcctVo.setTbSlaveAcct(tbSlaveAcctVo);
                                    RabbitmqIndex index = montage(handle, type, json, tbAcctVo, system);
                                    rs.add(index);
                                } else {
                                    logger.warn("json:{},从账号是有效的", json);
                                }

                            }
                        }
                        //只下发人员
                        else if (vo.getIncludePsn() == 1 && vo.getIncludeSlaveAcct() != 1) {
                            TbAcctVo tbAcctVo = new TbAcctVo();
                            tbAcctVo.setAcctId(tbAcct.getAcctId());
                            tbAcctVo.setAcct(tbAcct.getAcct());
                            tbAcctVo.setStatusCd(tbAcct.getStatusCd());

                            TbPersonnel tbPersonnel = new TbPersonnel();
                            tbPersonnel.setPersonnelId(personnelId);
                            tbPersonnel.setStatusCd("1100");

                            tbAcctVo.setTbPersonnel(tbPersonnel);
                            if (vo.getTbSystemIndividuationRules() != null && vo.getTbSystemIndividuationRules().size() > 0) {

                            }
                            RabbitmqIndex index = montage(handle, type, json, tbAcctVo, system);
                            rs.add(index);
                        }
                    }
                    ;
                    break;
                    default:
                        break;
                }
            }
        }
        return rs;
    }

    //操作从账号
    private List<RabbitmqIndex> handleSlaveAcct(String json, String handle, String type, Long slaveAcctId) throws IOException {
        List<RabbitmqIndex> rs = new ArrayList<>();
        Map<String, Object> map = systemRuleService.getSystemRuleBySlaveAcct(slaveAcctId);
        if (map == null) {
            logger.warn("json:{},systemId:{} is error", json, null);
            return null;
        }
        OrgTreeRuleVo vo = (OrgTreeRuleVo) map.get("OrgTreeRuleVo");
        TbBusinessSystem system = (TbBusinessSystem) map.get("system");
        //没有系统。
        if (vo == null) {
            logger.warn("json:{},systemId:{} is error", json, null);
            return null;
        }
        //校验
        if(tbBusinessSystemMapper.validateSlave(system.getBusinessSystemId(),slaveAcctId) == 2){
            logger.warn("json:{} 校验失败，错误数据,请检查数据库数据。", json);
            return null;
        }
        switch (handle) {
            case "insert":
            case "update": {
                TbAcctVo tbAcctVo = tbSlaveAcctMapper.insertOrUpdateSalveAcct(slaveAcctId);//获取下发的报文

                if (tbAcctVo == null) {
                    logger.warn("json:{},systemId:{} 账号信息不是有效数据或不存在", json, vo.getBusinessSystemId());
                    return null;
                }

                tbAcctVo.getTbSlaveAcct().setSystemName(system.getSystemName());//添加系统的名称
                tbAcctVo.getTbSlaveAcct().setBusinessSystemId(system.getBusinessSystemId());//添加系统的id
                tbAcctVo.getTbSlaveAcct().setSystemCode(system.getSystemCode());//添加系统的编码
                PersonShowUtil.noShow(tbAcctVo);//不下发数据
                if (tbAcctVo == null) {
                    logger.warn("json:{},treeId:{},systemId:{} 数据校验不合规。", json, vo.getOrgTreeId(), vo.getBusinessSystemId());
                    return null;
                }
                //规则判断
                if (vo.getTbSystemIndividuationRules() != null && vo.getTbSystemIndividuationRules().size() > 0) {

                }
                //下发人员与从账号
                if (vo.getIncludePsn() == 1 && vo.getIncludeSlaveAcct() == 1) {
                    RabbitmqIndex index = montage(handle, type, json, tbAcctVo, system);
                    rs.add(index);
                }
                //下发人员不下发从账号[因为，从账号层面修改，人员信息未发生改变。可以不下发]
                /*else if (vo.getIncludePsn() == 1 && vo.getIncludeSlaveAcct() != 1) {
                    tbAcctVo.setTbSlaveAcct(null);
                    RabbitmqIndex index = montage(handle,type,json,tbAcctVo,system);
                    rs.add(index);
                }*/
            }
            ;
            break;
            case "delete": {
                TbSlaveAcctVo tbSlaveAcct = tbSlaveAcctMapper.selectVoById(slaveAcctId);
                TbAcct tbAcct = tbAcctMapper.selectBySlaveAcctId(slaveAcctId);
                if (tbSlaveAcct != null && ValidateConstant.fail.getValue().equals(tbSlaveAcct.getStatusCd())) {
                    TbAcctVo tbAcctVo = new TbAcctVo();

                    tbAcctVo.setAcct(tbAcct.getAcct());
                    tbAcctVo.setAcctId(tbAcct.getAcctId());
                    tbAcctVo.setStatusCd(tbAcct.getStatusCd());

                    TbSlaveAcctVo tbSlaveAcctVo = new TbSlaveAcctVo();
                    tbSlaveAcctVo.setSlaveAcctId(slaveAcctId);
                    tbSlaveAcctVo.setSlaveAcct(tbSlaveAcct.getSlaveAcct());
                    tbSlaveAcctVo.setStatusCd(tbSlaveAcct.getStatusCd());
                    tbSlaveAcctVo.setMappStaffId(tbSlaveAcct.getMappStaffId());
                    tbSlaveAcctVo.setSystemName(system.getSystemName());//添加系统的名称
                    tbSlaveAcctVo.setBusinessSystemId(system.getBusinessSystemId());//添加系统的id
                    tbSlaveAcctVo.setSystemCode(system.getSystemCode());//添加系统的编码

                    tbAcctVo.setTbAcctCrossRel(getAcctCrossRel(tbAcct.getAcctId()));
                    tbAcctVo.setTbSlaveAcct(tbSlaveAcctVo);
                    //规则判断
                    if (vo.getTbSystemIndividuationRules() != null && vo.getTbSystemIndividuationRules().size() > 0) {

                    }
                    //下发从账号
                    if (vo.getIncludePsn() == 1 && vo.getIncludeSlaveAcct() == 1) {
                        RabbitmqIndex index = montage(handle, type, json, tbAcctVo, system);
                        rs.add(index);
                    }
                } else {
                    logger.warn("json:{},非法从账号Id,或者从账号Id已经不是失效状态了。",json);
                }
            }
            ;
            break;
            default:
                break;
        }
        return rs;
    }

    //拼接
    private RabbitmqIndex montage(String handle, String type, String json, Object obj, TbBusinessSystem system) {
        RabbitmqIndex index = new RabbitmqIndex();
        String id = sdf.format(new Date()) + UUID.randomUUID().toString().replaceAll("-", "").trim();
        List<String> queueNames = systemQueueRelaMapper.getQueueName(system.getSystemName(), "" + system.getSystemCode(), QueueConstant.valid.getValue());
        //下发内容拼接
        RestfulVo restfulVo = new RestfulVo();
        restfulVo.setHandle(handle);
        restfulVo.setSerial(id);
        restfulVo.setType(type);


        String queueName = "";
        if(queueNames != null){
            for(int i =0 ;i < queueNames.size();i++){
               if(i==0){
                   queueName = queueNames.get(i);
               }else{
                   queueName = queueName+","+queueNames.get(i);
               }
            }
        }

        if (obj instanceof TbOrgVo) {
            restfulVo.setContext((TbOrgVo) obj);
        } else if (obj instanceof TbAcctVo) {
            restfulVo.setContext((TbAcctVo) obj);
        } else {
            return null;
        }

        String msg = JSON.toJSONString(restfulVo, SerializerFeature.WriteDateUseDateFormat);
        logger.info("json:{}下发内容为---->msg:{}", json, msg);
        //下发报文存取到数据库
        index.setId(id);
        index.setState(QueueConstant.valid.getValue());
        index.setCollectionData(new Date());
        index.setRabbitmqDate(msg);
        index.setQueueName(queueName);
        return index;
    }

    /*获取账号跨域*/
    private List<TbAcctCrossRel> getAcctCrossRel(Long acctId){
        List<TbAcctCrossRel> tbAcctCrossRel = tbAcctCrossRelMapper.selectList(
                new EntityWrapper<TbAcctCrossRel>().eq("STATUS_CD","1000")
                        .eq("ACCT_ID",acctId));
        if(tbAcctCrossRel != null){
            tbAcctCrossRel.forEach((rel)->{
                rel.setAcctCrossId(null);
                rel.setAcctId(null);
                rel.setStatusCd(null);
                rel.setCreateDate(null);
                rel.setCreateUser(null);
                rel.setUpdateDate(null);
                rel.setUpdateUser(null);
                rel.setStatusDate(null);
            });
        }
        return tbAcctCrossRel;
    }
}