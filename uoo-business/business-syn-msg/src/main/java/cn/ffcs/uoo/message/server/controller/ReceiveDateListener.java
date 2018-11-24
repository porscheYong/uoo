package cn.ffcs.uoo.message.server.controller;

import cn.ffcs.uoo.message.server.dao.TbOrgCrossRelMapper;
import cn.ffcs.uoo.message.server.dao.TbOrgMapper;
import cn.ffcs.uoo.message.server.pojo.*;
import cn.ffcs.uoo.message.server.util.OrgShowUtil;
import cn.ffcs.uoo.message.server.vo.*;
import com.google.common.collect.Lists;

import java.util.Date;

import cn.ffcs.uoo.message.server.dao.TbSlaveAcctMapper;
import cn.ffcs.uoo.message.server.service.SystemRuleService;
import cn.ffcs.uoo.message.server.util.PersonShowUtil;
import com.alibaba.fastjson.JSON;
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
    @Resource
    private TbSlaveAcctMapper tbSlaveAcctMapper;
    @Resource
    private TbOrgMapper tbOrgMapper;
    @Resource
    private TbOrgCrossRelMapper tbOrgCrossRelMapper;

    //获取数据
    @RabbitHandler
    public void process(String json) {
        //解析json
        Map<String, Object> jsonMap = JSON.parseObject(json, Map.class);
        String handle = (String) jsonMap.get("handle");
        String type = (String) jsonMap.get("type");
        Map<String, Object> context = (Map<String, Object>) jsonMap.get("context");
        String column = (String) context.get("column");
        Long value = (Long) context.get("value");

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
                                OrgShowUtil.noShow(tbOrgVo, system);
                                if (vo.getTbSystemIndividuationRules() != null && vo.getTbSystemIndividuationRules().size() > 0) {

                                }
                                RestfulVo restfulVo = new RestfulVo();
                                restfulVo.setHandle(handle);
                                restfulVo.setSerial(UUID.randomUUID().toString().replaceAll("-", "").trim());
                                restfulVo.setType(type);
                                restfulVo.setContext(tbOrgVo);
                                System.out.println(JSON.toJSONString(restfulVo));
                            }
                            ;
                            break;
                            case "delete": {
                                TbOrgVo tbOrgVo = new TbOrgVo();
                                tbOrgVo.setOrgId(value);
                                List<TbOrgCrossRel> crossRelList = tbOrgCrossRelMapper.getListByOrgIdAndSystemCode(value, system.getSystemCode() + "_%");
                                tbOrgVo.setOrgCrossRelations(crossRelList);

                                if (vo.getTbSystemIndividuationRules() != null && vo.getTbSystemIndividuationRules().size() > 0) {

                                }
                                RestfulVo restfulVo = new RestfulVo();
                                restfulVo.setHandle(handle);
                                restfulVo.setSerial(UUID.randomUUID().toString().replaceAll("-", "").trim());
                                restfulVo.setType(type);
                                restfulVo.setContext(tbOrgVo);
                                System.out.println(JSON.toJSONString(restfulVo));
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
                                if (slaveList != null) {
                                    //下发人员和从账号
                                    if (vo.getIncludePsn() == 1 && vo.getIncludeSlaveAcct() == 1) {
                                        TbSlaveAcct temp = slaveList.get(0);
                                        TbAcctVo tbAcctVo = tbSlaveAcctMapper.insertOrUpdateSalveAcct(temp.getSlaveAcctId());
                                        tbAcctVo.getTbSlaveAcctVo().setSystemName(system.getSystemName());
                                        tbAcctVo.getTbSlaveAcctVo().setBusinessSystemId(system.getBusinessSystemId());
                                        PersonShowUtil.noShow(tbAcctVo);
                                        if (vo.getTbSystemIndividuationRules() != null && vo.getTbSystemIndividuationRules().size() > 0) {

                                        }
                                        RestfulVo restfulVo = new RestfulVo();
                                        restfulVo.setHandle(handle);
                                        restfulVo.setSerial(UUID.randomUUID().toString().replaceAll("-", "").trim());
                                        restfulVo.setType(type);
                                        restfulVo.setContext(tbAcctVo);
                                        System.out.println(JSON.toJSONString(restfulVo));
                                    } else if (vo.getIncludePsn() == 1 && vo.getIncludeSlaveAcct() != 1) {
                                        TbSlaveAcct temp = slaveList.get(0);
                                        TbAcctVo tbAcctVo = tbSlaveAcctMapper.insertOrUpdateSalveAcct(temp.getSlaveAcctId());
                                        tbAcctVo.setTbSlaveAcctVo(null);
                                        PersonShowUtil.noShow(tbAcctVo);
                                        if (vo.getTbSystemIndividuationRules() != null && vo.getTbSystemIndividuationRules().size() > 0) {

                                        }
                                        RestfulVo restfulVo = new RestfulVo();
                                        tbAcctVo.setTbSlaveAcctVo(null);
                                        restfulVo.setHandle(handle);
                                        restfulVo.setSerial(UUID.randomUUID().toString().replaceAll("-", "").trim());
                                        restfulVo.setType(type);
                                        restfulVo.setContext(tbAcctVo);
                                        System.out.println(JSON.toJSONString(restfulVo));
                                    }
                                } else {
                                    //没有从账号
                                    if (vo.getIncludePsn() == 1 && vo.getIncludeSlaveAcct() != 1) {
                                        TbAcctVo tbAcctVo = tbSlaveAcctMapper.insertOrUpdateAcct(value, vo.getOrgTreeId());
                                        PersonShowUtil.noShow(tbAcctVo);
                                        if (vo.getTbSystemIndividuationRules() != null && vo.getTbSystemIndividuationRules().size() > 0) {

                                        }
                                        RestfulVo restfulVo = new RestfulVo();
                                        tbAcctVo.setTbSlaveAcctVo(null);
                                        restfulVo.setHandle(handle);
                                        restfulVo.setSerial(UUID.randomUUID().toString().replaceAll("-", "").trim());
                                        restfulVo.setType(type);
                                        restfulVo.setContext(tbAcctVo);
                                        System.out.println(JSON.toJSONString(restfulVo));
                                    }
                                }
                            }
                            ;
                            break;
                            case "delete": {
                                List<TbSlaveAcct> slaveList = tbSlaveAcctMapper.insertOrUpdateSalveAcctByPersonnelIdAndSystemId(value, system.getBusinessSystemId());
                                if (slaveList != null) {

                                    if (vo.getIncludePsn() == 1 && vo.getIncludeSlaveAcct() == 1) {

                                        slaveList.forEach((temp) -> {
                                            TbAcctVo tbAcctVo = new TbAcctVo();
                                            TbSlaveAcctVo tbSlaveAcctVo = new TbSlaveAcctVo();
                                            tbSlaveAcctVo.setSlaveAcctId(temp.getSlaveAcctId());
                                            tbAcctVo.setTbSlaveAcctVo(tbSlaveAcctVo);
                                            if (vo.getTbSystemIndividuationRules() != null && vo.getTbSystemIndividuationRules().size() > 0) {
                                            }
                                            RestfulVo restfulVo = new RestfulVo();
                                            restfulVo.setHandle(handle);
                                            restfulVo.setSerial(UUID.randomUUID().toString().replaceAll("-", "").trim());
                                            restfulVo.setType(type);
                                            restfulVo.setContext(tbAcctVo);
                                            System.out.println(JSON.toJSONString(restfulVo));
                                        });
                                    }else if (vo.getIncludePsn() == 1 && vo.getIncludeSlaveAcct() != 1){
                                        TbSlaveAcct temp = slaveList.get(0);
                                        TbAcctVo tbAcctVo = new TbAcctVo();
                                        TbPersonnel tbPersonnel = new TbPersonnel();
                                        tbPersonnel.setPersonnelId(value);
                                        tbAcctVo.setTbPersonnel(tbPersonnel);
                                        if (vo.getTbSystemIndividuationRules() != null && vo.getTbSystemIndividuationRules().size() > 0) {

                                        }
                                        RestfulVo restfulVo = new RestfulVo();
                                        restfulVo.setHandle(handle);
                                        restfulVo.setSerial(UUID.randomUUID().toString().replaceAll("-", "").trim());
                                        restfulVo.setType(type);
                                        restfulVo.setContext(tbAcctVo);
                                        System.out.println(JSON.toJSONString(restfulVo));
                                    }
                                } else {
                                    if (vo.getIncludePsn() == 1) {
                                        TbAcctVo tbAcctVo = new TbAcctVo();
                                        TbPersonnel tbPersonnel = new TbPersonnel();
                                        tbPersonnel.setPersonnelId(value);
                                        tbAcctVo.setTbPersonnel(tbPersonnel);
                                        if (vo.getTbSystemIndividuationRules() != null && vo.getTbSystemIndividuationRules().size() > 0) {

                                        }
                                        RestfulVo restfulVo = new RestfulVo();
                                        restfulVo.setHandle(handle);
                                        restfulVo.setSerial(UUID.randomUUID().toString().replaceAll("-", "").trim());
                                        restfulVo.setType(type);
                                        restfulVo.setContext(tbAcctVo);
                                        System.out.println(JSON.toJSONString(restfulVo));
                                    }
                                }
                            }
                            ;
                            break;
                            default:
                                break;
                        }
                    }
                } else if (value != null && !"".equals(value) && "slaveAcctId".equals(column)) {
                    //获取需要下发的系统和对应的规则
                    Map<String, Object> map = SystemRuleService.getSystemRuleBySlaveAcct(value);
                    OrgTreeRuleVo vo = (OrgTreeRuleVo) map.get("OrgTreeRuleVo");
                    TbBusinessSystem system = (TbBusinessSystem) map.get("system");
                    //没有系统。
                    if (vo == null) {
                        return;
                    }

                    switch (handle) {
                        case "insert":
                        case "update": {
                            TbAcctVo tbAcctVo = tbSlaveAcctMapper.insertOrUpdateSalveAcct(value);

                            tbAcctVo.getTbSlaveAcctVo().setSystemName(system.getSystemName());
                            tbAcctVo.getTbSlaveAcctVo().setBusinessSystemId(system.getBusinessSystemId());
                            PersonShowUtil.noShow(tbAcctVo);

                            if (vo.getTbSystemIndividuationRules() != null && vo.getTbSystemIndividuationRules().size() > 0) {

                            }

                            if (vo.getIncludePsn() == 1 && vo.getIncludeSlaveAcct() == 1) {
                                //下发人员和从账号
                                RestfulVo restfulVo = new RestfulVo();
                                restfulVo.setHandle(handle);
                                restfulVo.setSerial(UUID.randomUUID().toString().replaceAll("-", "").trim());
                                restfulVo.setType(type);
                                restfulVo.setContext(tbAcctVo);
                                System.out.println(JSON.toJSONString(restfulVo));
                            } else if (vo.getIncludePsn() == 1 && vo.getIncludeSlaveAcct() != 1) {
                                //只下发人员
                                RestfulVo restfulVo = new RestfulVo();
                                tbAcctVo.setTbSlaveAcctVo(null);
                                restfulVo.setHandle(handle);
                                restfulVo.setSerial(UUID.randomUUID().toString().replaceAll("-", "").trim());
                                restfulVo.setType(type);
                                restfulVo.setContext(tbAcctVo);
                                System.out.println(JSON.toJSONString(restfulVo));
                            }
                        }
                        ;
                        break;
                        case "delete": {
                            TbAcctVo tbAcctVo = new TbAcctVo();
                            TbSlaveAcctVo tbSlaveAcctVo = new TbSlaveAcctVo();
                            tbSlaveAcctVo.setSlaveAcctId(value);
                            tbAcctVo.setTbSlaveAcctVo(tbSlaveAcctVo);
                            if (vo.getTbSystemIndividuationRules() != null && vo.getTbSystemIndividuationRules().size() > 0) {
                            }
                            if (vo.getIncludePsn() == 1 && vo.getIncludeSlaveAcct() == 1) {
                                //下发人员和从账号
                                RestfulVo restfulVo = new RestfulVo();
                                restfulVo.setHandle(handle);
                                restfulVo.setSerial(UUID.randomUUID().toString().replaceAll("-", "").trim());
                                restfulVo.setType(type);
                                restfulVo.setContext(tbAcctVo);
                                System.out.println(JSON.toJSONString(restfulVo));
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
    }
}