package cn.ffcs.uoo.web.maindata.common.system.client.fallback;

import cn.ffcs.uoo.web.maindata.common.system.client.SysOrganizationClient;
import cn.ffcs.uoo.web.maindata.common.system.client.SysPositionClient;
import cn.ffcs.uoo.web.maindata.common.system.vo.ResponseResult;
import cn.ffcs.uoo.web.maindata.common.system.vo.SysOrganizationVo;
import cn.ffcs.uoo.web.maindata.common.system.vo.SysPositionVo;
import cn.ffcs.uoo.web.maindata.common.system.vo.TreeNodeVo;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;

@Component
public class SysPositionClientHystrix implements SysPositionClient {
    @Override
    public ResponseResult<List<TreeNodeVo>> getPositionTree(String id, boolean isSync,
                                                            Long userId, String accout){
        ResponseResult<List<TreeNodeVo>> rr=new ResponseResult<>();
        rr.setState(ResponseResult.STATE_SERVICE_ERROR);
        rr.setMessage("服务不可用");
        return rr;
    }

    @Override
    public ResponseResult<Page<SysPositionVo>> getPositionRelPage(String positionCode,
                                                                  String search,
                                                                  Integer pageSize,
                                                                  Integer pageNo,
                                                                  String isSearchlower,
                                                                  Long userId,
                                                                  String accout){
        ResponseResult<Page<SysPositionVo>> rr=new ResponseResult<>();
        rr.setState(ResponseResult.STATE_SERVICE_ERROR);
        rr.setMessage("服务不可用");
        return rr;
    }

    @Override
    public ResponseResult<HashMap<String,String>> getRolesByPositionId(String positionId){
        ResponseResult<HashMap<String,String>> rr=new ResponseResult<>();
        rr.setState(ResponseResult.STATE_SERVICE_ERROR);
        rr.setMessage("服务不可用");
        return rr;
    }

    @Override
    public ResponseResult<SysPositionVo> getPosition(String id){
        ResponseResult<SysPositionVo> rr=new ResponseResult<>();
        rr.setState(ResponseResult.STATE_SERVICE_ERROR);
        rr.setMessage("服务不可用");
        return rr;
    }

    @Override
    public ResponseResult<String> updatePosition(SysPositionVo sysPositionVo){
        ResponseResult<String> rr=new ResponseResult<>();
        rr.setState(ResponseResult.STATE_SERVICE_ERROR);
        rr.setMessage("服务不可用");
        return rr;
    }

    @Override
    public ResponseResult<TreeNodeVo> addPosition(SysPositionVo pos){
        ResponseResult<TreeNodeVo> rr=new ResponseResult<>();
        rr.setState(ResponseResult.STATE_SERVICE_ERROR);
        rr.setMessage("服务不可用");
        return rr;
    }

    @Override
    public ResponseResult<String> deletePosition(@RequestBody SysPositionVo pos){
        ResponseResult<String> rr=new ResponseResult<>();
        rr.setState(ResponseResult.STATE_SERVICE_ERROR);
        rr.setMessage("服务不可用");
        return rr;
    }
}
