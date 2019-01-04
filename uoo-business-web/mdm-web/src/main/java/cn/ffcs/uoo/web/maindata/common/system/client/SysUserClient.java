package cn.ffcs.uoo.web.maindata.common.system.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.feign.FeignClientProperties.FeignClientConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.ffcs.uoo.web.maindata.common.system.client.fallback.SysUserClientHystrix;
import cn.ffcs.uoo.web.maindata.common.system.dto.AlterPwdDTO;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysUser;
import cn.ffcs.uoo.web.maindata.common.system.vo.ResponseResult;
@FeignClient(value = "common-system",configuration = {FeignClientConfiguration.class},fallback = SysUserClientHystrix.class)
public interface SysUserClient {
    @RequestMapping(value = "/system/sysUserLogin", method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult<SysUser> login(SysUser sysUser) ;
    /**
     * 方法只可用来做登陆  不可用作他途
     * @param sysUser
     * @return
     */
    @RequestMapping(value = "/system/getSysUserByAccout", method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult<SysUser> getSysUserByAccout(@RequestBody SysUser sysUser);
    @RequestMapping(value = "/system/alterPwd", method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult<String> alterPwd(@RequestBody AlterPwdDTO alterPwdDTO);
    @RequestMapping(value = "/system/updateLoginInfo", method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult<Void> updateLoginInfo(@RequestBody SysUser sysUser) ;
}
