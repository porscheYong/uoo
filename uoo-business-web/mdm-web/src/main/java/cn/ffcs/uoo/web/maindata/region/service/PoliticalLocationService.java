package cn.ffcs.uoo.web.maindata.region.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.ffcs.uoo.web.maindata.region.dto.TbPoliticalLocation;
import cn.ffcs.uoo.web.maindata.region.service.fallback.PoliticalLocationServiceHystrix;
import cn.ffcs.uoo.web.maindata.region.vo.ResponseResult;
import io.swagger.annotations.ApiOperation;

@FeignClient(value = "uoo-region-provider",/*configuration = {PersonnelServiceConfiguration.class},*/fallback = PoliticalLocationServiceHystrix.class)
public interface PoliticalLocationService {

    
    @GetMapping("/region/politicalLocation/getPoliticalLocation/id={id}")
    public ResponseResult getPoliticalLocation(@PathVariable(value = "id") Long id);
     
    @GetMapping("/region/politicalLocation/listAllPoliticalLocation")
    public ResponseResult listAllPoliticalLocation();

    
    @RequestMapping(value="/region/politicalLocation/addPoliticalLocation",method = RequestMethod.POST,headers={"Content-Type=application/json"})
    //@PostMapping("/region/politicalLocation/addPoliticalLocation")
    public ResponseResult addPoliticalLocation(@RequestBody TbPoliticalLocation polLoc) ;
    
    @RequestMapping(value="/region/politicalLocation/updatePoliticalLocation",method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult updatePoliticalLocation(@RequestBody TbPoliticalLocation polLoc) ;
    
     
    @RequestMapping(value="/region/politicalLocation/deletePoliticalLocation",method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult deletePoliticalLocation(@RequestBody TbPoliticalLocation polLoc);
    @GetMapping("/region/politicalLocation/getChildPoliticalLocationInfo/{id}")
    public ResponseResult getChildPoliticalLocationInfo(@PathVariable(value="id") Long id);
    @GetMapping("/region/politicalLocation/getTreePoliticalLocation/{id}")
    public ResponseResult getTreePoliticalLocation(@PathVariable(value="id") Long id );
    
}
