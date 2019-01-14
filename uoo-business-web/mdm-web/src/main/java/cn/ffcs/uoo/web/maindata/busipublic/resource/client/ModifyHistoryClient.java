package cn.ffcs.uoo.web.maindata.busipublic.resource.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.feign.FeignClientProperties.FeignClientConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.baomidou.mybatisplus.plugins.Page;

import cn.ffcs.uoo.web.maindata.busipublic.resource.client.fallback.ModifyHistoryClientHystrix;
import cn.ffcs.uoo.web.maindata.busipublic.resource.dto.ModifyHistoryDTO;
import cn.ffcs.uoo.web.maindata.busipublic.vo.ResponseResult;

@FeignClient(value = "business-public",configuration = {FeignClientConfiguration.class},fallback = ModifyHistoryClientHystrix.class)
public interface ModifyHistoryClient {
    
    @GetMapping("/public/modifyHistory/listByRecord")
    public ResponseResult<Page<ModifyHistoryDTO>> listByRecord(@RequestParam(value = "pageNo") Integer pageNo, @RequestParam(value = "pageSize") Integer pageSize,
            @RequestParam(value="tableName")String tableName,@RequestParam(value="recordId")String recordId);
}
