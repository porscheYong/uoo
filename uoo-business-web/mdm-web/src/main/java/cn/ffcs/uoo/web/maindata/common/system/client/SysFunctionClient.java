package cn.ffcs.uoo.web.maindata.common.system.client;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.feign.FeignClientProperties.FeignClientConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.ffcs.uoo.web.maindata.common.system.client.fallback.SysFunctionClientHystrix;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysFunction;
import cn.ffcs.uoo.web.maindata.common.system.vo.ResponseResult;

@FeignClient(value = "common-system",configuration = {FeignClientConfiguration.class},fallback = SysFunctionClientHystrix.class)
public interface SysFunctionClient {
    
    @GetMapping("/system/sysFunction/list")
    public ResponseResult<List<SysFunction>> list(Integer pageNo,Integer pageSize,String keyWord);
    @RequestMapping(value = "/system/sysFunction/getFunctionByAccout", method = RequestMethod.GET)
    public ResponseResult<List<SysFunction>> getFunctionByAccout(String accout);
}
