package cn.ffcs.uoo.web.maindata.sysuser.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.feign.FeignClientProperties.FeignClientConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.ffcs.uoo.web.maindata.sysuser.client.fallback.SysUserClientHystrix;
import cn.ffcs.uoo.web.maindata.sysuser.dto.AlterPwdDTO;
import cn.ffcs.uoo.web.maindata.sysuser.dto.SysUser;
import cn.ffcs.uoo.web.maindata.sysuser.vo.ResponseResult;
@FeignClient(value = "common-system",configuration = {FeignClientConfiguration.class},fallback = SysUserClientHystrix.class)
public interface SysUserClient {
    @RequestMapping(value = "/system/sysUserLogin", method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult<SysUser> login(SysUser sysUser) ;
    @RequestMapping(value = "/system/getSysUserByAccout", method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult<SysUser> getSysUserByAccout(@RequestBody SysUser sysUser);
    @RequestMapping(value = "/system/alterPwd", method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult<String> alterPwd(@RequestBody AlterPwdDTO alterPwdDTO);
}
