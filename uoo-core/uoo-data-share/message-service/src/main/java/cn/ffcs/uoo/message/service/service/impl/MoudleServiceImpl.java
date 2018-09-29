package cn.ffcs.uoo.message.service.service.impl;

import cn.ffcs.uoo.message.service.service.MoudleService;
import cn.ffcs.uoo.message.service.vo.OrgVo;
import cn.ffcs.uoo.message.service.vo.PersonVo;
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
