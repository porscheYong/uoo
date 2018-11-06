package cn.ffcs.uoo.web.maindata.region.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import cn.ffcs.uoo.web.maindata.region.entity.TbRegionLocationRel;
import cn.ffcs.uoo.web.maindata.region.service.fallback.RegionLocationRelServiceHystrix;
import cn.ffcs.uoo.web.maindata.region.vo.LocRegRelByLoc;
import cn.ffcs.uoo.web.maindata.region.vo.LocRegRelByReg;
import cn.ffcs.uoo.web.maindata.region.vo.ResponseResult;
import common.config.PersonnelServiceConfiguration;

@FeignClient(value = "uoo-region-provider",configuration = {PersonnelServiceConfiguration.class},fallback = RegionLocationRelServiceHystrix.class)
public interface RegionLocationRelService {
     
    @PostMapping("/region/regionLocationRel/addLocRegRelByLoc")
    public ResponseResult addLocRegRelByLoc(@RequestBody LocRegRelByLoc locRegRelByLoc) ;
    
    @PostMapping("/region/regionLocationRel/addLocRegRelByReg")
    public ResponseResult addLocRegRelByReg(@RequestBody LocRegRelByReg locRegRelByReg) ;
    
    @PostMapping("/region/regionLocationRel/deleteRegionLocationRel")
    public ResponseResult deleteRegionLocationRel(@RequestBody TbRegionLocationRel regionLocationRel) ;
    
     
    @GetMapping("/region/regionLocationRel/listRegionLocationRel/pageNo={pageNo}&pageSize={pageSize}")
    public ResponseResult listRegionLocationRel(@PathVariable(value = "pageNo") Integer pageNo, @PathVariable(value = "pageSize",required = false) Integer pageSize) ;
     
    @GetMapping("/region/regionLocationRel/listRegionLocationRelByReg/regId={regId}&pageNo={pageNo}&pageSize={pageSize}")
    public ResponseResult listRegionLocationRelByReg(@PathVariable(value = "regId")Long regId,@PathVariable(value = "pageNo") Integer pageNo, @PathVariable(value = "pageSize",required = false) Integer pageSize) ;
     
    @GetMapping("/region/regionLocationRel/listRegionLocationRelByLoc/locId={locId}&pageNo={pageNo}&pageSize={pageSize}")
    public ResponseResult listRegionLocationRelByLoc(@PathVariable(value = "locId")Long locId,@PathVariable(value = "pageNo") Integer pageNo, @PathVariable(value = "pageSize",required = false) Integer pageSize) ;
    
    @PostMapping("/region/regionLocationRel/addRegionLocationRel")
    public ResponseResult addRegionLocationRel(@RequestBody TbRegionLocationRel regLocRel) ;
     
    @PostMapping("/region/regionLocationRel/updateRegionLocationRel")
    public ResponseResult updateRegionLocationRel(@RequestBody TbRegionLocationRel regLocRel) ;
    
    
    @GetMapping("/region/regionLocationRel/getRegionLocationRel/id={id}")
    public ResponseResult getRegionLocationRel(@PathVariable(value = "id") Long id);

}
