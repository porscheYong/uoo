package cn.ffcs.uoo.web.maindata.common.system.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.feign.FeignClientProperties.FeignClientConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.ffcs.uoo.web.maindata.common.system.client.fallback.SysLoginLogClientHystrix;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysLoginLog;
import cn.ffcs.uoo.web.maindata.common.system.vo.ResponseResult;

@FeignClient(value = "common-system",configuration = {FeignClientConfiguration.class},fallback = SysLoginLogClientHystrix.class)
public interface SysLoginLogClient {
    
    @RequestMapping(value = "/system/sysLoginLog/add", method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult<Void> add(@RequestBody SysLoginLog sysLoginLog);
}