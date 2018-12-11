package cn.ffcs.uoo.message.server.service.impl;

import cn.ffcs.uoo.message.server.dao.TbBusinessSystemMapper;
import cn.ffcs.uoo.message.server.dao.TbSystemOrgTreeMapper;
import cn.ffcs.uoo.message.server.pojo.TbBusinessSystem;
import cn.ffcs.uoo.message.server.service.SystemRuleService;
import cn.ffcs.uoo.message.server.vo.OrgTreeRuleVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class SystemRuleServiceImpl implements SystemRuleService {

    @Resource
    private TbBusinessSystemMapper tbBusinessSystemMapper;

    @Resource
    private TbSystemOrgTreeMapper tbSystemOrgTreeMapper;

    @Override
    public Map<String, Object> getSystemRuleBySlaveAcct(Long slaveAcctId) {

        Map<String, Object> map = null ;
        TbBusinessSystem system = tbBusinessSystemMapper.getSystemBySlaveAcct(slaveAcctId);
        if (system != null){
            map = new HashMap<>();
            OrgTreeRuleVo vo = tbSystemOrgTreeMapper.getTbSystemOrgTreeAndTbSystemIndividuationRule(system.getBusinessSystemId());
            map.put("system",system);
            map.put("OrgTreeRuleVo",vo);
        }
        return map;
    }

    @Override
    public List<Map<String, Object>> getSystemRuleByPerson(Long PersonalId) {
        List<TbBusinessSystem> systems = tbBusinessSystemMapper.getSystemByPersonal(PersonalId);
        return getList(systems);
    }

    @Override
    public List<Map<String, Object>> getSystemRuleByOrg(Long orgId) {
        List<TbBusinessSystem> systems = tbBusinessSystemMapper.getSystemByOrg(orgId);
        return getList(systems);
    }

    private List<Map<String, Object>> getList(List<TbBusinessSystem> systems){
        List<Map<String, Object>> list = null;
        if(systems != null && systems.size() != 0){
            list = new ArrayList<>();
            List<Long> temp = new ArrayList<>();
            systems.forEach((system)->{
                temp.add(system.getBusinessSystemId());
            });
            List<OrgTreeRuleVo> orgTreeRuleVos = tbSystemOrgTreeMapper.getTbSystemOrgTreeAndTbSystemIndividuationRules(temp);
            for(TbBusinessSystem system: systems){
                Map<String, Object> map =new HashMap<>();
                map.put("system",system);
                OrgTreeRuleVo vo = null;
                if(orgTreeRuleVos != null){
                    for (OrgTreeRuleVo ruleVo :orgTreeRuleVos){
                        if(ruleVo.getBusinessSystemId().equals(system.getBusinessSystemId())){
                            vo = ruleVo;
                            break;
                        }
                    }
                }
                map.put("OrgTreeRuleVo",vo);
                list.add(map);
            }
        }
        return list;
    }
}