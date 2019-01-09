package cn.ffcs.uoo.web.maindata.common.system.client;

import cn.ffcs.uoo.web.maindata.common.system.vo.EditSysUserDeptRefVo;
import cn.ffcs.uoo.web.maindata.common.system.vo.SysUserDeptRefVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.feign.FeignClientProperties.FeignClientConfiguration;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/system/addsysUserDeptRef", method = RequestMethod.POST, headers={"Content-Type=application/json"})
    public Object addsysUserDeptRef(@RequestBody EditSysUserDeptRefVo sysUserDeptRefVo);

    @RequestMapping(value = "/system/updateSysUser", method = RequestMethod.POST, headers={"Content-Type=application/json"})
    public Object updateSysUser(@RequestBody SysUser sysUser);

    @RequestMapping(value = "/system/getSysUserDeptPosition", method = RequestMethod.GET, headers={"Content-Type=application/json"})
    public ResponseResult<SysUserDeptRefVo> getSysUserDeptPosition(@RequestParam("userId") Long userId, @RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize);

    @RequestMapping(value = "/system/deleteUser", method = RequestMethod.POST, headers={"Content-Type=application/json"})
    public Object deletePrivilege(@RequestBody SysUser sysUser);
}
