package cn.ffcs.uoo.message.server.service;

import cn.ffcs.uoo.message.server.vo.OrgVo;
import cn.ffcs.uoo.message.server.vo.PersonVo;

public interface MoudleService {
    String getOrgMoudleDate(String systemName, OrgVo vo);
    String getPersonMoudleDate(String systemName, PersonVo vo);
}
