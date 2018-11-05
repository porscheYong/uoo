package cn.ffcs.uoo.message.server.service.impl;

import cn.ffcs.uoo.message.server.constant.ValidateConstant;
import cn.ffcs.uoo.message.server.service.ValidateDateService;
import cn.ffcs.uoo.message.server.vo.OrgVo;
import cn.ffcs.uoo.message.server.vo.PersonVo;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ValidateServiceImpl implements ValidateDateService {

    @Override
    public Map<String, Object> validateOrg(OrgVo vo) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("status",ValidateConstant.success.getValue());
        return resultMap;
    }

    @Override
    public Map<String, Object> validatePerson(PersonVo vo) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("status",ValidateConstant.success.getValue());
        return resultMap;
    }
}
