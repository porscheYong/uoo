package cn.ffcs.uoo.web.maindata.common.system.client.fallback;

import cn.ffcs.uoo.web.maindata.common.system.client.SysFileClient;
import cn.ffcs.uoo.web.maindata.common.system.client.SysPositionClient;
import cn.ffcs.uoo.web.maindata.common.system.vo.ResponseResult;
import cn.ffcs.uoo.web.maindata.common.system.vo.SysFileVo;
import cn.ffcs.uoo.web.maindata.common.system.vo.SysPositionVo;
import cn.ffcs.uoo.web.maindata.common.system.vo.TreeNodeVo;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;

@Component
public class SysFileClientHystrix implements SysFileClient {

    @Override
    public ResponseResult<Page<SysFileVo>> getSysFilePage(String search,Integer pageSize,Integer pageNo,
                                                         Long userId,String accout){
        ResponseResult<Page<SysFileVo>> rr=new ResponseResult<>();
        rr.setState(ResponseResult.STATE_SERVICE_ERROR);
        rr.setMessage("服务不可用");
        return rr;
    }

    @Override
    public ResponseResult<SysFileVo> getSysFile(String id,Long userId,String accout){
        ResponseResult<SysFileVo> rr=new ResponseResult<>();
        rr.setState(ResponseResult.STATE_SERVICE_ERROR);
        rr.setMessage("服务不可用");
        return rr;
    }

    @Override
    public ResponseResult<String> addSysFile(SysFileVo sysFileVo){
        ResponseResult<String> rr=new ResponseResult<>();
        rr.setState(ResponseResult.STATE_SERVICE_ERROR);
        rr.setMessage("服务不可用");
        return rr;
    }

    @Override
    public ResponseResult<String> updateSysFile(SysFileVo sysFileVo){
        ResponseResult<String> rr=new ResponseResult<>();
        rr.setState(ResponseResult.STATE_SERVICE_ERROR);
        rr.setMessage("服务不可用");
        return rr;
    }
    @Override
    public ResponseResult<String> deleteSysFile(SysFileVo sysFileVo){
        ResponseResult<String> rr=new ResponseResult<>();
        rr.setState(ResponseResult.STATE_SERVICE_ERROR);
        rr.setMessage("服务不可用");
        return rr;
    }
}
