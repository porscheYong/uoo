package cn.ffcs.uoo.web.maindata.region.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import cn.ffcs.uoo.web.maindata.region.entity.TbCommonRegion;
import cn.ffcs.uoo.web.maindata.region.service.fallback.CommonRegionServiceHystrix;
import cn.ffcs.uoo.web.maindata.region.vo.ResponseResult;
import common.config.PersonnelServiceConfiguration;

@FeignClient(value = "uoo-region-provider",configuration = {PersonnelServiceConfiguration.class},fallback = CommonRegionServiceHystrix.class)
public interface CommonRegionService {

    @GetMapping("/region/commonRegion/getCommonRegion/id={id}")
    public ResponseResult getCommonRegion(@PathVariable(value = "id") Long id);
    
    @GetMapping("/region/commonRegion/listAllCommonRegion")
    public ResponseResult listAllCommonRegion();

    @PostMapping("/region/commonRegion/addCommonRegion")
    public ResponseResult addCommonRegion(@RequestBody TbCommonRegion commonRegion);
     
    @PostMapping("/region/commonRegion/updateCommonRegion")
    public ResponseResult updateCommonRegion(@RequestBody TbCommonRegion commonRegion) ;
    
    @PostMapping("/region/commonRegion/deleteCommonRegion")
    public ResponseResult deleteCommonRegion(@RequestBody TbCommonRegion commonRegion);
}
