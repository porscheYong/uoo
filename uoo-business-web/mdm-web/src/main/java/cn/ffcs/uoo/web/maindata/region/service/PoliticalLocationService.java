package cn.ffcs.uoo.web.maindata.region.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import cn.ffcs.uoo.web.maindata.region.entity.TbPoliticalLocation;
import cn.ffcs.uoo.web.maindata.region.service.fallback.PoliticalLocationServiceHystrix;
import cn.ffcs.uoo.web.maindata.region.vo.ResponseResult;
import common.config.PersonnelServiceConfiguration;

@FeignClient(value = "uoo-region-provider",configuration = {PersonnelServiceConfiguration.class},fallback = PoliticalLocationServiceHystrix.class)
public interface PoliticalLocationService {

    
    @GetMapping("/region/politicalLocation/getPoliticalLocation/id={id}")
    public ResponseResult getPoliticalLocation(@PathVariable(value = "id") Long id);
     
    @GetMapping("/region/politicalLocation/listAllPoliticalLocation")
    public ResponseResult listAllPoliticalLocation();

    
    @PostMapping("/region/politicalLocation/addPoliticalLocation")
    public ResponseResult addPoliticalLocation(@RequestBody TbPoliticalLocation polLoc) ;
    
    @PostMapping("/region/politicalLocation/updatePoliticalLocation")
    public ResponseResult updatePoliticalLocation(@RequestBody TbPoliticalLocation polLoc) ;
    
     
    @PostMapping("/region/politicalLocation/deletePoliticalLocation")
    public ResponseResult deletePoliticalLocation(@RequestBody TbPoliticalLocation polLoc);

}
