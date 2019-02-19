package cn.ffcs.uoo.core.organization.Api.service.impl;

import cn.ffcs.uoo.core.organization.Api.service.CertService;
import cn.ffcs.uoo.core.organization.Api.service.SystemService;
import cn.ffcs.uoo.core.organization.util.ResponseResult;
import cn.ffcs.uoo.core.organization.vo.DataRuleRequestVO;
import cn.ffcs.uoo.core.organization.vo.DataRuleResponseVO;
import cn.ffcs.uoo.core.organization.vo.ExpandovalueVo;
import cn.ffcs.uoo.core.organization.vo.SysDataRule;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 *
 */
@Component
public class SystemServiceHystrix implements SystemService {

    @Override
    public ResponseResult<List<SysDataRule>> getDataRuleByAccout(@RequestBody DataRuleRequestVO requestVo){
        ResponseResult<List<SysDataRule>> ret = new ResponseResult<List<SysDataRule>>();
        ret.setMessage("调用公共管理接口[getDataRuleByAccout]报错");
        ret.setState(ResponseResult.PARAMETER_ERROR);
        return ret;
    }

    @Override
    public ResponseResult<DataRuleResponseVO> getDataRuleByAccout2(@RequestBody DataRuleRequestVO requestVo){
        ResponseResult<DataRuleResponseVO> ret = new ResponseResult<DataRuleResponseVO>();
        ret.setMessage("调用公共管理接口[getDataRuleByAccout]报错");
        ret.setState(ResponseResult.PARAMETER_ERROR);
        return ret;
    }
}
