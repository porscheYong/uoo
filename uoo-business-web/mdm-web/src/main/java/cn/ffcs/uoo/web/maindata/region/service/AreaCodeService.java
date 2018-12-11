package cn.ffcs.uoo.web.maindata.region.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.feign.FeignClientProperties.FeignClientConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.ffcs.uoo.web.maindata.region.dto.TbAreaCode;
import cn.ffcs.uoo.web.maindata.region.service.fallback.AreaCodeServiceHystrix;
import cn.ffcs.uoo.web.maindata.region.vo.ResponseResult;
@FeignClient(value = "business-region",configuration = {FeignClientConfiguration.class},fallback = AreaCodeServiceHystrix.class)
public interface AreaCodeService {
    
    @GetMapping("/region/areaCode/getAreaCode/id={id}")
    public ResponseResult getAreaCode(@PathVariable(value = "id") Long id);
    
    @GetMapping("/region/areaCode/listAreaCode")
    public ResponseResult listAreaCode(@RequestParam("keyWord") String keyWord, @RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize);
   
    @RequestMapping(value="/region/areaCode/addAreaCode",method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult addAreaCode(@RequestBody TbAreaCode areaCode);
     
    @RequestMapping(value="/region/areaCode/updateAreaCode",method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult updateAreaCode(@RequestBody TbAreaCode areaCode) ;
    
    @RequestMapping(value="/region/areaCode/deleteAreaCode",method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult deleteAreaCode(@RequestBody TbAreaCode areaCode);
}
