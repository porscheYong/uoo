package cn.ffcs.uoo.web.maindata.common.system.client.fallback;

import java.util.List;

import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.plugins.Page;

import cn.ffcs.uoo.web.maindata.busipublic.resource.dto.ModifyHistory;
import cn.ffcs.uoo.web.maindata.common.system.client.SysOperationLogClient;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysOperationLog;
import cn.ffcs.uoo.web.maindata.common.system.vo.LogDTO;
import cn.ffcs.uoo.web.maindata.common.system.vo.ResponseResult;
@Component
public class SysOperationLogClientHystrix implements SysOperationLogClient {

    @Override
    public ResponseResult<Void> add(SysOperationLog sysOperationLog) {
        ResponseResult<Void> rr=new ResponseResult<>();
        rr.setState(ResponseResult.STATE_SERVICE_ERROR);
        rr.setMessage("服务不可用");
        return rr;
    }

    @Override
    public ResponseResult<Object> get(Long id, String logEnum) {
        ResponseResult<Object> rr=new ResponseResult<>();
        rr.setState(ResponseResult.STATE_SERVICE_ERROR);
        rr.setMessage("服务不可用");
        return rr;
    }

    @Override
    public ResponseResult<Page<LogDTO>> listPage(Integer pageNo, Integer pageSize, String keyWord) {

        ResponseResult<Page<LogDTO>> rr=new ResponseResult<>();
        rr.setState(ResponseResult.STATE_SERVICE_ERROR);
        rr.setMessage("服务不可用");
        return rr;
    
    }

    @Override
    public ResponseResult<List<ModifyHistory>> getOperatModifyHistory(Long id, String userCode, Long userId) {
        ResponseResult<List<ModifyHistory>> rr=new ResponseResult<>();
        rr.setState(ResponseResult.STATE_SERVICE_ERROR);
        rr.setMessage("服务不可用");
        return rr;
    }

}
