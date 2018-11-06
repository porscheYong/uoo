package cn.ffcs.uoo.web.maindata.region.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.ffcs.uoo.web.maindata.region.entity.TbExch;
import cn.ffcs.uoo.web.maindata.region.service.fallback.ExchServiceHystrix;
import cn.ffcs.uoo.web.maindata.region.vo.ResponseResult;
import common.config.PersonnelServiceConfiguration;

@RequestMapping("/region/exch")
@FeignClient(value = "uoo-region-provider",configuration = {PersonnelServiceConfiguration.class},fallback = ExchServiceHystrix.class)
public interface ExchService {
    
    @GetMapping("getExch/id={id}")
    public ResponseResult getExch(@PathVariable(value = "id") Long id);
    
     
    @GetMapping("listExch/pageNo={pageNo}&pageSize={pageSize}")
    public ResponseResult listExch(@PathVariable(value = "pageNo") Integer pageNo, @PathVariable(value = "pageSize",required = false) Integer pageSize);
    
     
    @PostMapping("addExch")
    public ResponseResult addExch(TbExch exch);
     
    @PostMapping("updateExch")
    public ResponseResult updateExch(TbExch exch) ;
    
    
    @PostMapping("deleteExch")
    public ResponseResult deleteExch(TbExch exch) ;
    


}
