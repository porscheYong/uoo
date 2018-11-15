package cn.ffcs.uoo.web.maindata.position.service;

import cn.ffcs.uoo.web.maindata.position.dto.TbPostLocation;
import cn.ffcs.uoo.web.maindata.position.service.fallback.TbPostLocationClientHystrix;
import cn.ffcs.uoo.web.maindata.position.vo.ResponseResult;
import common.config.PersonnelServiceConfiguration;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "business-position",configuration = {PersonnelServiceConfiguration.class},fallback = TbPostLocationClientHystrix.class)
public interface TbPostLocationClient {
    @RequestMapping(value = "/tbPostLocation/add", method = RequestMethod.POST)
    ResponseResult<TbPostLocation> addTbPostLocation(@RequestBody TbPostLocation tbPostLocation);

    @RequestMapping(value = "/tbPostLocation/del", method = RequestMethod.POST)
    ResponseResult<TbPostLocation> removeTbPostLocation(@RequestParam("postLocationId") Long postLocationId, @RequestParam("updateUser") Long updateUser);
}
