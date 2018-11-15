package cn.ffcs.uoo.web.maindata.position.service;

import cn.ffcs.uoo.web.maindata.position.dto.TbOrgPostRel;
import cn.ffcs.uoo.web.maindata.position.service.fallback.TbOrgPostRelClientHystrix;
import cn.ffcs.uoo.web.maindata.position.vo.ResponseResult;
import common.config.PersonnelServiceConfiguration;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "business-position",configuration = {PersonnelServiceConfiguration.class},fallback = TbOrgPostRelClientHystrix.class)
public interface TbOrgPostRelClient {
    @RequestMapping(value = "/tbOrgPostRel/add", method = RequestMethod.POST)
    ResponseResult<TbOrgPostRel> addTbOrgPostRel(@RequestBody TbOrgPostRel tbOrgPostRel);

    @RequestMapping(value = "/tbOrgPostRel/del", method = RequestMethod.POST)
    ResponseResult<TbOrgPostRel> removeTbOrgPostRel(@RequestParam("orgPostId") Long orgPostId, @RequestParam("updateUser") Long updateUser);
}
