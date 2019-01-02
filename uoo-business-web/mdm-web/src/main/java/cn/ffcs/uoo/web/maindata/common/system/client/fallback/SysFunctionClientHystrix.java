package cn.ffcs.uoo.web.maindata.common.system.client.fallback;

import java.util.List;

import org.springframework.stereotype.Component;

import cn.ffcs.uoo.web.maindata.common.system.client.SysFunctionClient;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysFunction;
import cn.ffcs.uoo.web.maindata.common.system.vo.ResponseResult;
@Component
public class SysFunctionClientHystrix implements SysFunctionClient {

    @Override
    public ResponseResult<List<SysFunction>> list(Integer pageNo, Integer pageSize, String keyWord) {
        ResponseResult<List<SysFunction>> rr=new ResponseResult<>();
        rr.setState(ResponseResult.STATE_SERVICE_ERROR);
        rr.setMessage("服务不可用");
        return rr;
    }

    @Override
    public ResponseResult<List<SysFunction>> getFunctionByAccout(String accout) {
        ResponseResult<List<SysFunction>> rr=new ResponseResult<>();
        rr.setState(ResponseResult.STATE_SERVICE_ERROR);
        rr.setMessage("服务不可用");
        return rr;
    }

    @Override
    public ResponseResult<SysFunction> get(Long id) {
        return ResponseResult.createErrorResult("服务不可用");
    }

    @Override
    public ResponseResult<Void> add(SysFunction fun) {
        return ResponseResult.createErrorResult("服务不可用");
    }

    @Override
    public ResponseResult<Void> update(SysFunction fun) {
        return ResponseResult.createErrorResult("服务不可用");
    }

    @Override
    public ResponseResult<Void> delete(SysFunction fun) {
        return ResponseResult.createErrorResult("服务不可用");
    }

}
