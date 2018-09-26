package cn.ffcs.uoo.message.service.controller;

import cn.ffcs.uoo.message.service.constant.StatusConstant;
import cn.ffcs.uoo.message.service.constant.ValidateConstant;
import cn.ffcs.uoo.message.service.dao.SystemQueueRelaMapper;
import cn.ffcs.uoo.message.service.service.MoudleService;
import cn.ffcs.uoo.message.service.service.RuleService;
import cn.ffcs.uoo.message.service.service.ValidateDateService;
import cn.ffcs.uoo.message.service.vo.OrgVo;
import cn.ffcs.uoo.message.service.vo.PersonVo;
import com.alibaba.fastjson.JSON;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ReceiveDataController {

    @Autowired
    private ValidateDateService validateDateService;

    @Autowired
    private RuleService ruleService;

    @Autowired
    private MoudleService moudleService;

    @Autowired
    private AmqpTemplate template;

    @PostMapping("receiveData")
    public Map<String,Object> receiveData(@RequestBody Map<String,String> map){
        String type = map.get("type");
        String context = map.get("context");
        OrgVo orgVo = null;
        PersonVo personVo = null;

        Map<String,Object> resultMap = new HashMap<>();

        switch (type){
            case "person":{
                //获取数据
                personVo = JSON.parseObject(context,PersonVo.class);
                //校验数据
                Map<String,Object> temp  = validateDateService.validatePerson(personVo);
                if(ValidateConstant.success.getValue().equals( temp.get("status"))){
                    //根据规则获取系统
                    List<Map<String,String>> systemList = ruleService.getSystemListByPerson(personVo);
                    resultMap = initMessage(systemList,personVo);
                }else{
                    //校验失败
                    resultMap = temp;
                }
            }break;
            case "org":{
                //获取数据
                orgVo = JSON.parseObject(context,OrgVo.class);
                //校验数据
                Map<String,Object> temp = validateDateService.validateOrg(orgVo);
                if(ValidateConstant.success.getValue().equals( map.get("status"))){
                    //根据规则获取系统
                    List<Map<String,String>> systemList = ruleService.getSystemListByOrg(orgVo);
                    resultMap = initMessage(systemList,orgVo);
                }else{
                    //校验失败
                    resultMap = temp;
                }
            } break;
            default:{
                resultMap.put("status", StatusConstant.validateFail.getValue());
                List<String> list = new ArrayList<>();
                list.add("type");
                resultMap.put("errors",list);
            }
        }

        return resultMap;
    }


    private Map<String,Object> initMessage(List<Map<String,String>> systemList,Object object){

        Map<String,Object> resultMap = new HashMap<>();

        if(systemList != null && systemList.size() != 0){
            if(object instanceof PersonVo){
                PersonVo vo = (PersonVo) object;
                systemList.forEach(str->{
                    String systemName = str.get("systemName");
                    //封装数据
                    String msg = moudleService.getPersonMoudleDate(systemName,vo);
                    //获取队列
                    String queueName = str.get("queueName");
                    //发送消息
                    send(resultMap,queueName,msg);
                });
            }else{
                OrgVo vo = (OrgVo) object;
                systemList.forEach(str->{
                    String systemName = str.get("systemName");
                    //获取模版封装数据
                    String msg = moudleService.getOrgMoudleDate(systemName,vo);
                    //获取队列名
                    String queueName = str.get("queueName");
                    //发送消息
                    send(resultMap,queueName,msg);
                });
            }
        }else{
            resultMap.put("status",StatusConstant.handleFail.getValue());
            List<String> errors = new ArrayList<>();
            errors.add("system not found.");
            resultMap.put("errors",errors);
        }

        return resultMap;
    }

    private void send(Map<String,Object> resultMap,String queueName,String msg){
        try{
            template.convertAndSend(queueName,msg);
            resultMap.put("status",StatusConstant.success.getValue());
        }catch (Exception e){
            resultMap.put("status",StatusConstant.handleFail.getValue());
            List<String> errors = new ArrayList<>();
            errors.add("message not send.");
            resultMap.put("errors",errors);

        }
    }
}
