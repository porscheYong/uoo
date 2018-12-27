package cn.ffcs.uoo.web.maindata.common.system.client.fallback;

import java.util.List;

import org.springframework.stereotype.Component;

import cn.ffcs.uoo.web.maindata.common.system.client.SysRoleClient;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysRole;
import cn.ffcs.uoo.web.maindata.common.system.vo.ResponseResult;
import cn.ffcs.uoo.web.maindata.common.system.vo.TreeNodeVo;
@Component
public class SysRoleClientHystrix implements SysRoleClient{

    @Override
    public ResponseResult<List<SysRole>> listPage(Integer pageNo, Integer pageSize) {
        ResponseResult<List<SysRole>> rr=new ResponseResult<>();
        rr.setState(ResponseResult.STATE_SERVICE_ERROR);
        rr.setMessage("服务不可用");
        return rr;
    }

    @Override
    public ResponseResult<List<TreeNodeVo>> treeRole(String parentRoleCode) {
        ResponseResult<List<TreeNodeVo>> rr=new ResponseResult<>();
        rr.setState(ResponseResult.STATE_SERVICE_ERROR);
        rr.setMessage("服务不可用");
        return rr;
    }

}
