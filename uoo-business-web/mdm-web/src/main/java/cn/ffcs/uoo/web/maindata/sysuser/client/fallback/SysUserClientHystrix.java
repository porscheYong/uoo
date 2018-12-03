package cn.ffcs.uoo.web.maindata.sysuser.client.fallback;

import org.springframework.stereotype.Component;

import cn.ffcs.uoo.web.maindata.sysuser.client.SysUserClient;
import cn.ffcs.uoo.web.maindata.sysuser.dto.SysUser;
import cn.ffcs.uoo.web.maindata.sysuser.vo.ResponseResult;
@Component
public class SysUserClientHystrix implements SysUserClient {

    @Override
    public ResponseResult<Void> login(SysUser sysUser) {
        ResponseResult<Void> rr=new ResponseResult<>();
        rr.setState(ResponseResult.STATE_ERROR);
        rr.setMessage("服务不可用");
        return rr;
    }

}
