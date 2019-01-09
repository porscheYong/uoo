package cn.ffcs.uoo.web.maindata.common.system.client.fallback;

import java.util.List;

import cn.ffcs.uoo.web.maindata.common.system.dto.SysTable;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysTableColumn;
import cn.ffcs.uoo.web.maindata.common.system.vo.SysDataRuleVo;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.stereotype.Component;

import cn.ffcs.uoo.web.maindata.common.system.client.SysDataRuleClient;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysDataRule;
import cn.ffcs.uoo.web.maindata.common.system.vo.ResponseResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

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
    public ResponseResult<String> updateDataRule(SysDataRuleVo sysDataRuleVo){
        ResponseResult<String> rr=new ResponseResult<>();
        rr.setState(ResponseResult.STATE_SERVICE_ERROR);
        rr.setMessage("服务不可用");
        return rr;
    }
    @Override
    public ResponseResult<List<SysTable>> getTab(){
        ResponseResult<List<SysTable>> rr=new ResponseResult<>();
        rr.setState(ResponseResult.STATE_SERVICE_ERROR);
        rr.setMessage("服务不可用");
        return rr;
    }
    @Override
    public ResponseResult<List<SysTableColumn>> getTabColumn(String tabId){
        ResponseResult<List<SysTableColumn>> rr=new ResponseResult<>();
        rr.setState(ResponseResult.STATE_SERVICE_ERROR);
        rr.setMessage("服务不可用");
        return rr;
    }
    @Override
    public ResponseResult<String> deleteDataRule(@RequestBody SysDataRuleVo sysDataRuleVo){
        ResponseResult<String> rr=new ResponseResult<>();
        rr.setState(ResponseResult.STATE_SERVICE_ERROR);
        rr.setMessage("服务不可用");
        return rr;
    }

}
