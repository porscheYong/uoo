package cn.ffcs.uoo.web.maindata.common.system.client.fallback;

import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.plugins.Page;

import cn.ffcs.uoo.web.maindata.common.system.client.SysLoginLogClient;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysLoginLog;
import cn.ffcs.uoo.web.maindata.common.system.vo.ResponseResult;
@Component
public class SysLoginLogClientHystrix implements SysLoginLogClient {


    @Override
    public ResponseResult<Void> add(SysLoginLog sysLoginLog) {
        ResponseResult<Void> rr=new ResponseResult<>();
        rr.setState(ResponseResult.STATE_SERVICE_ERROR);
        rr.setMessage("服务不可用");
        return rr;
    }

    @Override
    public ResponseResult<Page<SysLoginLog>> listPage(Integer pageNo, Integer pageSize, String accout) {
        return ResponseResult.createErrorResult("服务不可用");
    }

}
