package cn.ffcs.uoo.web.maindata.common.system.client.fallback;

import java.util.List;

import cn.ffcs.uoo.web.maindata.common.system.vo.SysDataRuleVo;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.stereotype.Component;

import cn.ffcs.uoo.web.maindata.common.system.client.SysDataRuleClient;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysDataRule;
import cn.ffcs.uoo.web.maindata.common.system.vo.ResponseResult;
import org.springframework.web.bind.annotation.RequestBody;

@Component
public class SysDataRuleClientHystrix implements SysDataRuleClient {

    @Override
    public ResponseResult<List<SysDataRule>> getDataRuleByAccout(String accout, String tableName) {
        ResponseResult<List<SysDataRule>> rr=new ResponseResult<>();
        rr.setState(ResponseResult.STATE_SERVICE_ERROR);
        rr.setMessage("服务不可用");
        return rr;
    }

    @Override
    public ResponseResult<Page<SysDataRuleVo>> getDataRulePage(String search, Integer pageSize, Integer pageNo,
                                                               Long userId, String accout){
        ResponseResult<Page<SysDataRuleVo>> rr=new ResponseResult<>();
        rr.setState(ResponseResult.STATE_SERVICE_ERROR);
        rr.setMessage("服务不可用");
        return rr;
    }
    @Override
    public ResponseResult<SysDataRuleVo> getDataRule(String id,
                                                     Long userId, String accout){
        ResponseResult<SysDataRuleVo> rr=new ResponseResult<>();
        rr.setState(ResponseResult.STATE_SERVICE_ERROR);
        rr.setMessage("服务不可用");
        return rr;
    }

    @Override
    public ResponseResult<String> addDataRule(SysDataRuleVo sysDataRuleVo){
        ResponseResult<String> rr=new ResponseResult<>();
        rr.setState(ResponseResult.STATE_SERVICE_ERROR);
        rr.setMessage("服务不可用");
        return rr;
    }
    @Override
    public ResponseResult<String> updateDataRule(@RequestBody SysDataRuleVo sysDataRuleVo){
        ResponseResult<String> rr=new ResponseResult<>();
        rr.setState(ResponseResult.STATE_SERVICE_ERROR);
        rr.setMessage("服务不可用");
        return rr;
    }

}
