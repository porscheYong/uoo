package cn.ffcs.uoo.web.maindata.common.system.client.fallback;

import java.util.List;

import org.springframework.stereotype.Component;

import cn.ffcs.uoo.web.maindata.common.system.client.SysDataRuleClient;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysDataRule;
import cn.ffcs.uoo.web.maindata.common.system.vo.ResponseResult;
@Component
public class SysDataRuleClientHystrix implements SysDataRuleClient {

    @Override
    public ResponseResult<List<SysDataRule>> getDataRuleByAccout(String accout, String tableName) {
        ResponseResult<List<SysDataRule>> rr=new ResponseResult<>();
        rr.setState(ResponseResult.STATE_SERVICE_ERROR);
        rr.setMessage("服务不可用");
        return rr;
    }

}
