package cn.ffcs.uoo.message.server.controller;

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

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");//日期

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RabbitHandler
    public void process(String json, Channel channel, Message message) throws IOException {
        try{
            logger.info("json:{}",json);
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
            if(context.get("value") != null){
                value = Long.parseLong(String.valueOf(context.get("value")));
            }
            //下发报文时间格式定义
            JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

            //下发报文集合
            List<RabbitmqIndex> rs = null;
            //组织
            if("org".equals(type) && "orgId".equals(column) && value != null){
                rs = handleOrg(json,handle,type,value);
            }
            //人员
            else if("person".equals(type) && "personnelId".equals(column) && value != null){
                rs = handlePersonnel(json,handle,type,value);
            }
            //从账号
            else if("person".equals(type) && "slaveAcctId".equals(column) && value != null){
                rs = handleSlaveAcct(json,handle,type,value);
            }

            //下发报文
            if(rs != null) {
                for(RabbitmqIndex index:rs){
                    rabbitmqIndexMapper.insert(index);
                    rabbitMqSendService.sendMsg(index.getQueueName(), index.getRabbitmqDate());
                }
            }
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }catch (Exception e){
            logger.error("msg:{},Exception:{}",json,e);
            //重回队列
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
    }

    //操作组织
    private List<RabbitmqIndex> handleOrg(String json,String handle,String type,Long orgId) throws IOException{
        List<RabbitmqIndex> rs = new ArrayList<>();
        //获取需要下发的数据的所有的系统和对应的规则
        List<Map<String, Object>> list = systemRuleService.getSystemRuleByOrg(orgId);
        if(list == null || list.size() ==0 ) {
            logger.warn("json:{},没有需要下发的系统",json);
            rs = null;
        }else{
            switch (handle){
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
                            logger.warn("json:{},treeId:{},systemId:{} 没有查询到数据",json,vo.getOrgTreeId(),vo.getBusinessSystemId());
                            continue;
                        }else{
                            //不展示部分数据
                            OrgShowUtil.noShow(tbOrgVo, system);
                            //规则校验
                            if (vo.getTbSystemIndividuationRules() != null && vo.getTbSystemIndividuationRules().size() > 0) {

                            }
                            RabbitmqIndex index = montage(handle,type,json,tbOrgVo,system);
                            rs.add(index);
                        }
                    }
                };break;
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
                            RabbitmqIndex index = montage(handle,type,json,tbOrgVo,system);
                            rs.add(index);
                        }
                    }else{
                        logger.warn("json:{},非法组织Id,或者组织已经不是失效状态了。");
                    }
                };break;
                default:break;
            }
        }
        return rs;
    }

    //操作人员
    private List<RabbitmqIndex> handlePersonnel(String json,String handle,String type,Long personnelId)throws IOException{
        List<RabbitmqIndex> rs = new ArrayList<>();
        int UnUseFlag = tbSlaveAcctMapper.checkPersonnelAndAcctByUnUse(personnelId);//1:是失效的人员或主账号
        int UseFlag = tbSlaveAcctMapper.checkPersonnelAndAcctByUser(personnelId);//1:是有效的人员和主账号

        if("insert".equals(handle) || "update".equals(handle) ){

            if(UseFlag != 1){
                logger.warn("json:{},人员，主账号信息不是有效数据不存在",json);
                return null;
            }

        }else if("delete".equals(handle)){
            if(UnUseFlag != 1){
                logger.warn("json:{},人员，主账号信息是有效数据或者不存在",json);
                return null;
            }
        }


        List<Map<String, Object>> list = systemRuleService.getSystemRuleByPerson(personnelId);
        if (list== null || list.size() ==0 ) {
            logger.warn("json:{},没有需要下发的系统",json);
            rs = null;
        }else{
            for (Map<String, Object> map : list) {
                OrgTreeRuleVo vo = (OrgTreeRuleVo) map.get("OrgTreeRuleVo");
                TbBusinessSystem system = (TbBusinessSystem) map.get("system");

                //获取所有的从账号
                List<TbSlaveAcct> slaveList = tbSlaveAcctMapper.insertOrUpdateSalveAcctByPersonnelIdAndSystemId(personnelId, system.getBusinessSystemId());

                //是否有从账号
                boolean isHaveSlave = slaveList !=null && slaveList.size()>0;

                switch (handle){
                    case "insert":
                    case "update":{
                        //下发人员和从账号
                        if(vo.getIncludePsn() == 1 && vo.getIncludeSlaveAcct() ==1 && isHaveSlave){
                            TbSlaveAcct temp = slaveList.get(0);
                            TbAcctVo tbAcctVo = tbSlaveAcctMapper.insertOrUpdateSalveAcct(temp.getSlaveAcctId());
                            tbAcctVo.getTbSlaveAcct().setSystemName(system.getSystemName());
                            tbAcctVo.getTbSlaveAcct().setBusinessSystemId(system.getBusinessSystemId());
                            PersonShowUtil.noShow(tbAcctVo);
                            if (vo.getTbSystemIndividuationRules() != null && vo.getTbSystemIndividuationRules().size() > 0) {

                            }
                            RabbitmqIndex index =montage(handle,type,json,tbAcctVo,system);
                            rs.add(index);
                        }
                        //只下发人员
                        else if (vo.getIncludePsn() == 1 && vo.getIncludeSlaveAcct() !=1){
                            TbAcctVo tbAcctVo = tbSlaveAcctMapper.insertOrUpdateAcct(personnelId, vo.getOrgTreeId());
                            PersonShowUtil.noShow(tbAcctVo);
                            if (vo.getTbSystemIndividuationRules() != null && vo.getTbSystemIndividuationRules().size() > 0) {

                            }
                            RabbitmqIndex index =montage(handle,type,json,tbAcctVo,system);
                            rs.add(index);
                        }
                    };break;
                    case "delete":{

                        TbAcct tbAcct = tbAcctMapper.selectByPersonId(personnelId);
                        //下发人员和从账号
                        if(vo.getIncludePsn() == 1 && vo.getIncludeSlaveAcct() ==1 && isHaveSlave){
                            for (TbSlaveAcct temp:slaveList){
                                if (temp != null && ValidateConstant.fail.getValue().equals(temp.getStatusCd())) {
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

                                    tbAcctVo.setTbSlaveAcct(tbSlaveAcctVo);
                                    RabbitmqIndex index =montage(handle,type,json,tbAcctVo,system);
                                    rs.add(index);
                                }else{
                                    logger.warn("json:{},从账号是有效的");
                                }

                            }
                        }
                        //只下发人员
                        else if (vo.getIncludePsn() == 1 && vo.getIncludeSlaveAcct() !=1) {
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
                    };break;
                    default:break;
                }
            }
        }
        return rs;
    }

    //操作从账号
    private List<RabbitmqIndex> handleSlaveAcct(String json,String handle,String type,Long slaveAcctId)throws IOException{
        List<RabbitmqIndex> rs = new ArrayList<>();
        Map<String, Object> map = systemRuleService.getSystemRuleBySlaveAcct(slaveAcctId);
        if (map == null) {
            logger.warn("json:{},systemId:{} is error",json,null);
            return null;
        }
        OrgTreeRuleVo vo = (OrgTreeRuleVo) map.get("OrgTreeRuleVo");
        TbBusinessSystem system = (TbBusinessSystem) map.get("system");
        //没有系统。
        if (vo == null) {
            logger.warn("json:{},systemId:{} is error",json,null);
            return null;
        }
        switch (handle){
            case "insert":
            case "update": {
                TbAcctVo tbAcctVo = tbSlaveAcctMapper.insertOrUpdateSalveAcct(slaveAcctId);//获取下发的报文
                tbAcctVo.getTbSlaveAcct().setSystemName(system.getSystemName());//添加系统的名称
                tbAcctVo.getTbSlaveAcct().setBusinessSystemId(system.getBusinessSystemId());//添加系统的id
                PersonShowUtil.noShow(tbAcctVo);//不下发数据
                //规则判断
                if (vo.getTbSystemIndividuationRules() != null && vo.getTbSystemIndividuationRules().size() > 0) {

                }
                //下发人员与从账号
                if (vo.getIncludePsn() == 1 && vo.getIncludeSlaveAcct() == 1) {
                    RabbitmqIndex index =montage(handle,type,json,tbAcctVo,system);
                    rs.add(index);
                }
                //下发人员不下发从账号[因为，从账号层面修改，人员信息未发生改变。可以不下发]
                /*else if (vo.getIncludePsn() == 1 && vo.getIncludeSlaveAcct() != 1) {
                    tbAcctVo.setTbSlaveAcct(null);
                    RabbitmqIndex index = montage(handle,type,json,tbAcctVo,system);
                    rs.add(index);
                }*/
            };break;
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

                    tbAcctVo.setTbSlaveAcct(tbSlaveAcctVo);
                    //规则判断
                    if (vo.getTbSystemIndividuationRules() != null && vo.getTbSystemIndividuationRules().size() > 0) {

                    }
                    //下发从账号
                    if (vo.getIncludePsn() == 1 && vo.getIncludeSlaveAcct() == 1) {
                        RabbitmqIndex index =montage(handle,type,json,tbAcctVo,system);
                        rs.add(index);
                    }
                }else{
                    logger.warn("json:{},非法从账号Id,或者从账号Id已经不是失效状态了。");
                }
            };break;
            default:break;
        }
        return rs;
    }

    //拼接
    private RabbitmqIndex montage(String handle,String type,String json,Object obj,TbBusinessSystem system ){
        RabbitmqIndex index = new RabbitmqIndex();
        String id = sdf.format(new Date()) + UUID.randomUUID().toString().replaceAll("-", "").trim();
        String queueName = systemQueueRelaMapper.getQueueName(system.getSystemName(), "" + system.getBusinessSystemId(), QueueConstant.valid.getValue());
        //下发内容拼接
        RestfulVo restfulVo = new RestfulVo();
        restfulVo.setHandle(handle);
        restfulVo.setSerial(id);
        restfulVo.setType(type);

        if(obj instanceof TbOrgVo){
            restfulVo.setContext((TbOrgVo)obj);
        }else if(obj instanceof TbAcctVo){
            restfulVo.setContext((TbAcctVo)obj);
        }else{
            return null;
        }

        String msg = JSON.toJSONString(restfulVo, SerializerFeature.WriteDateUseDateFormat);
        logger.info("json:{}下发内容为---->msg:{}",json,msg);
        //下发报文存取到数据库
        index.setId(id);
        index.setState(QueueConstant.valid.getValue());
        index.setCollectionData(new Date());
        index.setRabbitmqDate(msg);
        index.setQueueName(queueName);
        return index;
    }
}