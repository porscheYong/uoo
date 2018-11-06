package cn.ffcs.uoo.web.maindata.region.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.ffcs.uoo.web.maindata.region.entity.TbPoliticalLocation;
import cn.ffcs.uoo.web.maindata.region.service.fallback.PoliticalLocationServiceHystrix;
import cn.ffcs.uoo.web.maindata.region.vo.ResponseResult;
import common.config.PersonnelServiceConfiguration;

@RequestMapping("/region/politicalLocation")
@FeignClient(value = "uoo-region-provider",configuration = {PersonnelServiceConfiguration.class},fallback = PoliticalLocationServiceHystrix.class)
public interface PoliticalLocationService {

    
    @GetMapping("getPoliticalLocation/id={id}")
    public ResponseResult getPoliticalLocation(@PathVariable(value = "id") Long id);
     
    @GetMapping("listAllPoliticalLocation")
    public ResponseResult listAllPoliticalLocation();

    
    @PostMapping("addPoliticalLocation")
    public ResponseResult addPoliticalLocation(TbPoliticalLocation polLoc) ;
    
    @PostMapping("updatePoliticalLocation")
    public ResponseResult updatePoliticalLocation(TbPoliticalLocation polLoc) ;
    
     
    @PostMapping("deletePoliticalLocation")
    public ResponseResult deletePoliticalLocation(TbPoliticalLocation polLoc);

}
