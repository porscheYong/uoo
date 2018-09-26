package cn.ffcs.uoo.message.service.service.impl;

import cn.ffcs.uoo.message.service.constant.QueueConstant;
import cn.ffcs.uoo.message.service.dao.SystemQueueRelaMapper;
import cn.ffcs.uoo.message.service.service.RuleService;
import cn.ffcs.uoo.message.service.vo.OrgVo;
import cn.ffcs.uoo.message.service.vo.PersonVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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
