package cn.ffcs.uoo.message.service.service;

import cn.ffcs.uoo.message.service.vo.OrgVo;
import cn.ffcs.uoo.message.service.vo.PersonVo;

import java.util.List;
import java.util.Map;

public interface RuleService {
    /**
     * 根据组织的规则获取需要下发的系统和对应的队列
     * @param vo
     * @return
     * Map
     *  systemName,
     *  queueName
     */
    List<Map<String,String>> getSystemListByOrg(OrgVo vo);

    /**
     * 根据用户的规则获取需要下发的系统和对应的队列
     * @param vo
     * @return
     * Map
     *  systemName,
     *  queueName
     */
    List<Map<String,String>> getSystemListByPerson(PersonVo vo);

}
