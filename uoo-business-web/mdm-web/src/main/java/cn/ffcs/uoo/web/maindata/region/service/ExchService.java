package cn.ffcs.uoo.web.maindata.region.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import cn.ffcs.uoo.web.maindata.region.entity.TbExch;
import cn.ffcs.uoo.web.maindata.region.service.fallback.ExchServiceHystrix;
import cn.ffcs.uoo.web.maindata.region.vo.ResponseResult;
import common.config.PersonnelServiceConfiguration;

@FeignClient(value = "uoo-region-provider",configuration = {PersonnelServiceConfiguration.class},fallback = ExchServiceHystrix.class)
public interface ExchService {
    
    @GetMapping("/region/exch/getExch/id={id}")
    public ResponseResult getExch(@PathVariable(value = "id") Long id);
    
     
    @GetMapping("/region/exch/listExch/pageNo={pageNo}&pageSize={pageSize}")
    public ResponseResult listExch(@PathVariable(value = "pageNo") Integer pageNo, @PathVariable(value = "pageSize",required = false) Integer pageSize);
    
     
    @PostMapping("/region/exch/addExch")
    public ResponseResult addExch(@RequestBody TbExch exch);
     
    @PostMapping("/region/exch/updateExch")
    public ResponseResult updateExch(@RequestBody TbExch exch) ;
    
    
    @PostMapping("/region/exch/deleteExch")
    public ResponseResult deleteExch(@RequestBody TbExch exch) ;
    


}
