package cn.ffcs.uoo.web.maindata.region.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.ffcs.uoo.web.maindata.region.entity.TbCommonRegion;
import cn.ffcs.uoo.web.maindata.region.service.fallback.CommonRegionServiceHystrix;
import cn.ffcs.uoo.web.maindata.region.vo.ResponseResult;
import common.config.PersonnelServiceConfiguration;

@RequestMapping("/region/commonRegion")
@FeignClient(value = "uoo-region-provider",configuration = {PersonnelServiceConfiguration.class},fallback = CommonRegionServiceHystrix.class)
public interface CommonRegionService {

    @GetMapping("getCommonRegion/id={id}")
    public ResponseResult getCommonRegion(@PathVariable(value = "id") Long id);
    
    @GetMapping("listAllCommonRegion")
    public ResponseResult listAllCommonRegion();

    @PostMapping("addCommonRegion")
    public ResponseResult addCommonRegion(TbCommonRegion commonRegion);
     
    @PostMapping("updateCommonRegion")
    public ResponseResult updateCommonRegion(TbCommonRegion commonRegion) ;
    
    @PostMapping("deleteCommonRegion")
    public ResponseResult deleteCommonRegion(TbCommonRegion commonRegion);
}
