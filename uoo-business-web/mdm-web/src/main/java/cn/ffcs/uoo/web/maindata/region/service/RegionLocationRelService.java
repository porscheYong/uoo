package cn.ffcs.uoo.web.maindata.region.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.ffcs.uoo.web.maindata.region.entity.TbRegionLocationRel;
import cn.ffcs.uoo.web.maindata.region.service.fallback.RegionLocationRelServiceHystrix;
import cn.ffcs.uoo.web.maindata.region.vo.LocRegRelByLoc;
import cn.ffcs.uoo.web.maindata.region.vo.LocRegRelByReg;
import cn.ffcs.uoo.web.maindata.region.vo.ResponseResult;
import common.config.PersonnelServiceConfiguration;

@RequestMapping("/region/regionLocationRel")
@FeignClient(value = "uoo-region-provider",configuration = {PersonnelServiceConfiguration.class},fallback = RegionLocationRelServiceHystrix.class)
public interface RegionLocationRelService {
     
    @PostMapping("addLocRegRelByLoc")
    public ResponseResult addLocRegRelByLoc(LocRegRelByLoc locRegRelByLoc) ;
    
    @PostMapping("addLocRegRelByReg")
    public ResponseResult addLocRegRelByReg(LocRegRelByReg locRegRelByReg) ;
    
    @PostMapping("deleteRegionLocationRel")
    public ResponseResult deleteRegionLocationRel(TbRegionLocationRel regionLocationRel) ;
    
     
    @GetMapping("listRegionLocationRel/pageNo={pageNo}&pageSize={pageSize}")
    public ResponseResult listRegionLocationRel(@PathVariable(value = "pageNo") Integer pageNo, @PathVariable(value = "pageSize",required = false) Integer pageSize) ;
     
    @GetMapping("listRegionLocationRelByReg/regId={regId}&pageNo={pageNo}&pageSize={pageSize}")
    public ResponseResult listRegionLocationRelByReg(@PathVariable(value = "regId")Long regId,@PathVariable(value = "pageNo") Integer pageNo, @PathVariable(value = "pageSize",required = false) Integer pageSize) ;
     
    @GetMapping("listRegionLocationRelByLoc/locId={locId}&pageNo={pageNo}&pageSize={pageSize}")
    public ResponseResult listRegionLocationRelByLoc(@PathVariable(value = "locId")Long locId,@PathVariable(value = "pageNo") Integer pageNo, @PathVariable(value = "pageSize",required = false) Integer pageSize) ;
    
    @PostMapping("addRegionLocationRel")
    public ResponseResult addRegionLocationRel(TbRegionLocationRel regLocRel) ;
     
    @PostMapping("updateRegionLocationRel")
    public ResponseResult updateRegionLocationRel(TbRegionLocationRel regLocRel) ;
    
    
    @GetMapping("getRegionLocationRel/id={id}")
    public ResponseResult getRegionLocationRel(@PathVariable(value = "id") Long id);

}
