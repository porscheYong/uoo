package cn.ffcs.uoo.message.service.service;

import cn.ffcs.uoo.message.service.vo.OrgVo;
import cn.ffcs.uoo.message.service.vo.PersonVo;

import java.util.Map;

public interface ValidateDateService {

    /**
     * 校验组织信息
     * @param vo
     * @return
     *  status
     *  list<String> 那些字段校验不成功
     */
     Map<String,Object> validateOrg(OrgVo vo);

    /**
     * 校验用户信息
     * @param vo
     * @return
     * status
     *  list<String> 那些字段校验不成功
     */
     Map<String,Object> validatePerson(PersonVo vo);

}