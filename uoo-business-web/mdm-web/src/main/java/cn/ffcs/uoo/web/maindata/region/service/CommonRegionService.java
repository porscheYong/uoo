package cn.ffcs.uoo.web.maindata.region.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.ffcs.uoo.web.maindata.region.dto.CommonRegionDTO;
import cn.ffcs.uoo.web.maindata.region.dto.TbCommonRegion;
import cn.ffcs.uoo.web.maindata.region.service.fallback.CommonRegionServiceHystrix;
import cn.ffcs.uoo.web.maindata.region.vo.ResponseResult;

@FeignClient(value = "business-region", /*
                                             * configuration =
                                             * {PersonnelServiceConfiguration.
                                             * class},
                                             */fallback = CommonRegionServiceHystrix.class)
public interface CommonRegionService {

    @GetMapping("/region/commonRegion/getCommonRegion/id={id}")
    public ResponseResult getCommonRegion(@PathVariable(value = "id") Long id);

    @GetMapping("/region/commonRegion/listAllCommonRegion")
    public ResponseResult listAllCommonRegion();

    @RequestMapping(value = "/region/commonRegion/addCommonRegion", method = RequestMethod.POST, headers = {
            "Content-Type=application/json" })
    public ResponseResult addCommonRegion(@RequestBody CommonRegionDTO commonRegion);

    @RequestMapping(value = "/region/commonRegion/updateCommonRegion", method = RequestMethod.POST, headers = {
            "Content-Type=application/json" })
    public ResponseResult updateCommonRegion(@RequestBody CommonRegionDTO commonRegion);

    @RequestMapping(value = "/region/commonRegion/deleteCommonRegion", method = RequestMethod.POST, headers = {
            "Content-Type=application/json" })
    public ResponseResult deleteCommonRegion(@RequestBody TbCommonRegion commonRegion);

    @GetMapping("/region/commonRegion/getChildCommonRegionInfo/{id}")
    public ResponseResult getChildCommonRegionInfo(@PathVariable(value = "id") Long id);

    @GetMapping("/region/commonRegion/getTreeCommonRegion/{id}")
    public ResponseResult getTreeCommonRegion(@PathVariable(value = "id") Long id);
}
