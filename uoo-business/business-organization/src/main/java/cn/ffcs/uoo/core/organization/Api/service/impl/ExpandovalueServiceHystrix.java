package cn.ffcs.uoo.core.organization.Api.service.impl;

import cn.ffcs.uoo.core.organization.Api.service.ExpandovalueService;
import cn.ffcs.uoo.core.organization.util.ResponseResult;
import cn.ffcs.uoo.core.organization.vo.ExpandovalueVo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 */
@Component
public class ExpandovalueServiceHystrix implements ExpandovalueService {

    @Override
    public ResponseResult<List<ExpandovalueVo>> queryExpandovalueVoList(String tableName, String recordId){
        return null;
    }
}
