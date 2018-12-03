package cn.ffcs.uoo.web.maindata.sysuser.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.ffcs.uoo.web.maindata.sysuser.client.fallback.SysUserClientHystrix;
import cn.ffcs.uoo.web.maindata.sysuser.dto.SysUser;
import cn.ffcs.uoo.web.maindata.sysuser.vo.ResponseResult;
@FeignClient(value = "common-system",fallback = SysUserClientHystrix.class)
public interface SysUserClient {
    @RequestMapping(value = "/system/sysUserLogin", method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult<Void> login(SysUser sysUser) ;
}
