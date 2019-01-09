package cn.ffcs.uoo.web.maindata.common.system.client.fallback;

import cn.ffcs.uoo.web.maindata.common.system.client.SysUserDeptRefClient;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysUser;
import cn.ffcs.uoo.web.maindata.common.system.vo.ResponseResult;
import cn.ffcs.uoo.web.maindata.common.system.vo.SysUserDeptPositionVo;
import org.springframework.stereotype.Component;

@Component
public class SysUserDeptRefClientHystrix implements SysUserDeptRefClient {
    @Override
    public Object addUserDeptPositionDef(SysUserDeptPositionVo userDeptPositionVo) {
        ResponseResult<SysUser> rr=new ResponseResult<>();
        rr.setState(ResponseResult.STATE_SERVICE_ERROR);
        rr.setMessage("服务不可用");
        return rr;
    }

    @Override
    public Object updateUserDeptPositionDef(SysUserDeptPositionVo userDeptPositionVo) {
        ResponseResult<SysUser> rr=new ResponseResult<>();
        rr.setState(ResponseResult.STATE_SERVICE_ERROR);
        rr.setMessage("服务不可用");
        return rr;
    }

    @Override
    public Object delUserDeptPositionDef(SysUserDeptPositionVo userDeptPositionVo) {
        ResponseResult<SysUser> rr=new ResponseResult<>();
        rr.setState(ResponseResult.STATE_SERVICE_ERROR);
        rr.setMessage("服务不可用");
        return rr;
    }
}
