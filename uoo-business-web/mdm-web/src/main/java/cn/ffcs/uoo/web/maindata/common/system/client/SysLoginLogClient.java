package cn.ffcs.uoo.web.maindata.common.system.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.feign.FeignClientProperties.FeignClientConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.baomidou.mybatisplus.plugins.Page;

import cn.ffcs.uoo.web.maindata.common.system.client.fallback.SysLoginLogClientHystrix;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysLoginLog;
import cn.ffcs.uoo.web.maindata.common.system.vo.ResponseResult;

@FeignClient(value = "common-system",configuration = {FeignClientConfiguration.class},fallback = SysLoginLogClientHystrix.class)
public interface SysLoginLogClient {
    @GetMapping("/system/sysLoginLog/listAccoutLog")
    public ResponseResult<Page<SysLoginLog>> listAccoutLog(@RequestParam(value = "pageNo") Integer pageNo, @RequestParam(value = "pageSize") Integer pageSize,
            @RequestParam(value="accout")String accout);
    @RequestMapping(value = "/system/sysLoginLog/add", method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult<Void> add(@RequestBody SysLoginLog sysLoginLog);
}
