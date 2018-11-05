package cn.ffcs.uoo.message.server.service.impl;

import cn.ffcs.uoo.message.server.service.MoudleService;
import cn.ffcs.uoo.message.server.vo.OrgVo;
import cn.ffcs.uoo.message.server.vo.PersonVo;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;

@Service
public class MoudleServiceImpl implements MoudleService {

    @Override
    public String getOrgMoudleDate(String systemName, OrgVo vo) {
        return JSON.toJSONString(vo);
    }

    @Override
    public String getPersonMoudleDate(String systemName, PersonVo vo) {
        return JSON.toJSONString(vo);
    }
}
