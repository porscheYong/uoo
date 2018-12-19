package cn.ffcs.uoo.message.server.service;

import java.util.List;
import java.util.Map;

public interface SystemRuleService {
    //从账号获取规则
    Map<String,Object> getSystemRuleBySlaveAcct(Long slaveAcctId);
    //人员获取规则
    List<Map<String, Object>> getSystemRuleByPerson(Long PersonalId);
    //组织获取规则
    List<Map<String, Object>> getSystemRuleByOrg(Long orgId);
}
