package cn.ffcs.uoo.web.maindata.region.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.feign.FeignClientProperties.FeignClientConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.ffcs.uoo.web.maindata.region.dto.TbExch;
import cn.ffcs.uoo.web.maindata.region.service.fallback.ExchServiceHystrix;
import cn.ffcs.uoo.web.maindata.region.vo.ResponseResult;

@FeignClient(value = "business-region",configuration = {FeignClientConfiguration.class},fallback = ExchServiceHystrix.class)
public interface ExchService {
    
    @GetMapping("/region/exch/getExch/id={id}")
    public ResponseResult getExch(@PathVariable(value = "id") Long id);
    
     
    @GetMapping("/region/exch/listExch/pageNo={pageNo}&pageSize={pageSize}")
    public ResponseResult listExch(@PathVariable(value = "pageNo") Integer pageNo, @PathVariable(value = "pageSize",required = false) Integer pageSize);
    
     
    @RequestMapping(value="/region/exch/addExch",method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult addExch(@RequestBody TbExch exch);
     
    @RequestMapping(value="/region/exch/updateExch",method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult updateExch(@RequestBody TbExch exch) ;
    
    
    @RequestMapping(value="/region/exch/deleteExch",method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult deleteExch(@RequestBody TbExch exch) ;
    


}
