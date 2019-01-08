package cn.ffcs.uoo.web.maindata.common.system.client;

import cn.ffcs.uoo.web.maindata.common.system.client.fallback.SysUserClientHystrix;
import cn.ffcs.uoo.web.maindata.common.system.client.fallback.SysUserDeptRefClientHystrix;
import cn.ffcs.uoo.web.maindata.common.system.vo.SysUserDeptPositionVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.feign.FeignClientProperties;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "common-system",configuration = {FeignClientProperties.FeignClientConfiguration.class},fallback = SysUserDeptRefClientHystrix.class)
public interface SysUserDeptRefClient {

    @RequestMapping(value = "/sysUserDeptRef/addUserDeptPositionDef", method = RequestMethod.POST, headers={"Content-Type=application/json"})
    public Object addUserDeptPositionDef(@RequestBody SysUserDeptPositionVo userDeptPositionVo);

    @RequestMapping(value = "/sysUserDeptRef/updateUserDeptPositionDef", method = RequestMethod.POST, headers={"Content-Type=application/json"})
    public Object updateUserDeptPositionDef(@RequestBody SysUserDeptPositionVo userDeptPositionVo);

    @RequestMapping(value = "/sysUserDeptRef/delUserDeptPositionDef", method = RequestMethod.DELETE, headers={"Content-Type=application/json"})
    public Object delUserDeptPositionDef(@RequestBody SysUserDeptPositionVo userDeptPositionVo);


}
