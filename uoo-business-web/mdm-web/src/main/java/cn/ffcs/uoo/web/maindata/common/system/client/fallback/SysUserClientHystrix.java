package cn.ffcs.uoo.web.maindata.common.system.client.fallback;

import org.springframework.stereotype.Component;

import cn.ffcs.uoo.web.maindata.common.system.client.SysUserClient;
import cn.ffcs.uoo.web.maindata.common.system.dto.AlterPwdDTO;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysUser;
import cn.ffcs.uoo.web.maindata.common.system.vo.ResponseResult;
@Component
public class SysUserClientHystrix implements SysUserClient {

    @Override
    public ResponseResult<SysUser> login(SysUser sysUser) {
        ResponseResult<SysUser> rr=new ResponseResult<>();
        rr.setState(ResponseResult.STATE_SERVICE_ERROR);
        rr.setMessage("服务不可用");
        return rr;
    }

    @Override
    public ResponseResult<SysUser> getSysUserByAccout(SysUser sysUser) {
        ResponseResult<SysUser> rr=new ResponseResult<>();
        rr.setState(ResponseResult.STATE_SERVICE_ERROR);
        rr.setMessage("服务不可用");
        return rr;
    }

    @Override
    public ResponseResult<String> alterPwd(AlterPwdDTO alterPwdDTO) {
        ResponseResult<String> rr=new ResponseResult<>();
        rr.setState(ResponseResult.STATE_SERVICE_ERROR);
        rr.setMessage("服务不可用");
        return rr;
    }

}
