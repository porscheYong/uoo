package cn.ffcs.uoo.web.maindata.region.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.ffcs.uoo.web.maindata.region.dto.TbRegionLocationRel;
import cn.ffcs.uoo.web.maindata.region.service.fallback.RegionLocationRelServiceHystrix;
import cn.ffcs.uoo.web.maindata.region.vo.LocRegRelByLoc;
import cn.ffcs.uoo.web.maindata.region.vo.LocRegRelByReg;
import cn.ffcs.uoo.web.maindata.region.vo.ResponseResult;
import common.config.PersonnelServiceConfiguration;

@FeignClient(value = "uoo-region-provider",configuration = {PersonnelServiceConfiguration.class},fallback = RegionLocationRelServiceHystrix.class)
public interface RegionLocationRelService {
     
    @RequestMapping(value="/region/regionLocationRel/addLocRegRelByLoc",method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult addLocRegRelByLoc(@RequestBody LocRegRelByLoc locRegRelByLoc) ;
    
    @RequestMapping(value="/region/regionLocationRel/addLocRegRelByReg",method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult addLocRegRelByReg(@RequestBody LocRegRelByReg locRegRelByReg) ;
    
    @RequestMapping(value="/region/regionLocationRel/deleteRegionLocationRel",method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult deleteRegionLocationRel(@RequestBody TbRegionLocationRel regionLocationRel) ;
    
     
    @GetMapping("/region/regionLocationRel/listRegionLocationRel/pageNo={pageNo}&pageSize={pageSize}")
    public ResponseResult listRegionLocationRel(@PathVariable(value = "pageNo") Integer pageNo, @PathVariable(value = "pageSize",required = false) Integer pageSize) ;
     
    @GetMapping("/region/regionLocationRel/listRegionLocationRelByReg/regId={regId}&pageNo={pageNo}&pageSize={pageSize}")
    public ResponseResult listRegionLocationRelByReg(@PathVariable(value = "regId")Long regId,@PathVariable(value = "pageNo") Integer pageNo, @PathVariable(value = "pageSize",required = false) Integer pageSize) ;
     
    @GetMapping("/region/regionLocationRel/listRegionLocationRelByLoc/locId={locId}&pageNo={pageNo}&pageSize={pageSize}")
    public ResponseResult listRegionLocationRelByLoc(@PathVariable(value = "locId")Long locId,@PathVariable(value = "pageNo") Integer pageNo, @PathVariable(value = "pageSize",required = false) Integer pageSize) ;
    
    @RequestMapping(value="/region/regionLocationRel/addRegionLocationRel",method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult addRegionLocationRel(@RequestBody TbRegionLocationRel regLocRel) ;
     
    @RequestMapping(value="/region/regionLocationRel/updateRegionLocationRel",method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult updateRegionLocationRel(@RequestBody TbRegionLocationRel regLocRel) ;
    
    
    @GetMapping("/region/regionLocationRel/getRegionLocationRel/id={id}")
    public ResponseResult getRegionLocationRel(@PathVariable(value = "id") Long id);

}
