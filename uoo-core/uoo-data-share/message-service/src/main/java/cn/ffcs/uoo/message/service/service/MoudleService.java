package cn.ffcs.uoo.message.service.service;

import cn.ffcs.uoo.message.service.vo.OrgVo;
import cn.ffcs.uoo.message.service.vo.PersonVo;

public interface MoudleService {
    String getOrgMoudleDate(String systemName, OrgVo vo);
    String getPersonMoudleDate(String systemName, PersonVo vo);
}
