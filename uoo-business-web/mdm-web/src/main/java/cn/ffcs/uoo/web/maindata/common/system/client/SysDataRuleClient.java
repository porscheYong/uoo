package cn.ffcs.uoo.web.maindata.common.system.client;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.feign.FeignClientProperties.FeignClientConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cn.ffcs.uoo.web.maindata.common.system.client.fallback.SysDataRuleClientHystrix;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysDataRule;
import cn.ffcs.uoo.web.maindata.common.system.vo.ResponseResult;

@FeignClient(value = "common-system",configuration = {FeignClientConfiguration.class},fallback = SysDataRuleClientHystrix.class)
public interface SysDataRuleClient {
    
    @GetMapping("/system/sysDataRule/getDataRuleByAccout/{accout}/{tableName}")
    public ResponseResult<List<SysDataRule>> getDataRuleByAccout(@PathVariable(value = "accout") String accout,@PathVariable(value = "tableName") String tableName);
}
