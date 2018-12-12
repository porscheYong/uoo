package cn.ffcs.uoo.web.maindata.sysuser.client.fallback;

import java.util.List;

import cn.ffcs.uoo.web.maindata.sysuser.client.SysRoleClient;
import cn.ffcs.uoo.web.maindata.sysuser.dto.SysRole;
import cn.ffcs.uoo.web.maindata.sysuser.vo.ResponseResult;

public class SysRoleClientHystrix implements SysRoleClient{

    @Override
    public ResponseResult<List<SysRole>> listPage(Integer pageNo, Integer pageSize) {
        ResponseResult<List<SysRole>> rr=new ResponseResult<>();
        rr.setState(ResponseResult.STATE_SERVICE_ERROR);
        rr.setMessage("服务不可用");
        return rr;
    }

}
