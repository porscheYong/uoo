package cn.ffcs.uoo.message.server.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.ffcs.uoo.message.server.constant.QueueConstant;
import cn.ffcs.uoo.message.server.dao.SystemQueueRelaMapper;
import cn.ffcs.uoo.message.server.service.RuleService;
import cn.ffcs.uoo.message.server.vo.OrgVo;
import cn.ffcs.uoo.message.server.vo.PersonVo;

@Service
public class RuleServiceImpl implements RuleService {

    @Resource
    private SystemQueueRelaMapper systemQueueRelaMapper;

    @Override
    public List<Map<String,String>> getSystemListByOrg(OrgVo vo) {
        return systemQueueRelaMapper.getAllSystemAndQueueName(QueueConstant.valid.getValue());
    }

    @Override
    public List<Map<String,String>> getSystemListByPerson(PersonVo vo) {
        return systemQueueRelaMapper.getAllSystemAndQueueName(QueueConstant.valid.getValue());
    }
}
