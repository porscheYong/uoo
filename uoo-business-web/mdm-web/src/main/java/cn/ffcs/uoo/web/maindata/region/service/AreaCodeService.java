package cn.ffcs.uoo.web.maindata.region.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.ffcs.uoo.web.maindata.region.entity.TbAreaCode;
import cn.ffcs.uoo.web.maindata.region.service.fallback.AreaCodeServiceHystrix;
import cn.ffcs.uoo.web.maindata.region.vo.ResponseResult;
import common.config.PersonnelServiceConfiguration;
@RequestMapping("/region/areaCode")
@FeignClient(value = "uoo-region-provider",configuration = {PersonnelServiceConfiguration.class},fallback = AreaCodeServiceHystrix.class)
public interface AreaCodeService {
    
    @GetMapping("getAreaCode/id={id}")
    public ResponseResult getAreaCode(@PathVariable(value = "id") Long id);
    
    @GetMapping("listAreaCode/pageNo={pageNo}&pageSize={pageSize}")
    public ResponseResult listAreaCode(@PathVariable(value = "pageNo") Integer pageNo, @PathVariable(value = "pageSize",required = false) Integer pageSize);
    @PostMapping("addAreaCode")
    public ResponseResult addAreaCode(TbAreaCode areaCode);
     
    @PostMapping("updateAreaCode")
    public ResponseResult updateAreaCode(TbAreaCode areaCode) ;
    
    @PostMapping("deleteAreaCode")
    public ResponseResult deleteAreaCode(TbAreaCode areaCode);
}
