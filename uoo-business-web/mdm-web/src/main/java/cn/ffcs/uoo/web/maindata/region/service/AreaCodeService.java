package cn.ffcs.uoo.web.maindata.region.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.ffcs.uoo.web.maindata.region.entity.TbAreaCode;
import cn.ffcs.uoo.web.maindata.region.service.fallback.AreaCodeServiceHystrix;
import cn.ffcs.uoo.web.maindata.region.vo.ResponseResult;
import common.config.PersonnelServiceConfiguration;
@FeignClient(value = "uoo-region-provider",configuration = {PersonnelServiceConfiguration.class},fallback = AreaCodeServiceHystrix.class)
public interface AreaCodeService {
    
    @GetMapping("/region/areaCode/getAreaCode/id={id}")
    public ResponseResult getAreaCode(@PathVariable(value = "id") Long id);
    
    @GetMapping("/region/areaCode/listAreaCode/pageNo={pageNo}&pageSize={pageSize}")
    public ResponseResult listAreaCode(@PathVariable(value = "pageNo") Integer pageNo, @PathVariable(value = "pageSize",required = false) Integer pageSize);
   
    //@RequestMapping(value="/region/areaCode/addAreaCode",method=RequestMethod.POST,consumes= MediaType.APPLICATION_FORM_URLENCODED_VALUE,headers = {"Content-Type=application/json"})
    @PostMapping(value="/region/areaCode/addAreaCode",headers={"Content-Type=application/json"})
    public ResponseResult addAreaCode(@RequestBody TbAreaCode areaCode);
     
    @PostMapping("/region/areaCode/updateAreaCode")
    public ResponseResult updateAreaCode(@RequestBody TbAreaCode areaCode) ;
    
    @PostMapping("/region/areaCode/deleteAreaCode")
    public ResponseResult deleteAreaCode(@RequestBody TbAreaCode areaCode);
}
