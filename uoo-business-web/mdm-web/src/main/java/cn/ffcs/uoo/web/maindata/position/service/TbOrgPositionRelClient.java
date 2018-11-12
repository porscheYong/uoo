package cn.ffcs.uoo.web.maindata.position.service;

import cn.ffcs.uoo.web.maindata.position.dto.TbOrgPositionRel;
import cn.ffcs.uoo.web.maindata.position.service.fallback.TbOrgPositionRelClientHystrix;
import cn.ffcs.uoo.web.maindata.position.vo.ResponseResult;
import common.config.PersonnelServiceConfiguration;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "uoo-position",configuration = {PersonnelServiceConfiguration.class},fallback = TbOrgPositionRelClientHystrix.class)
public interface TbOrgPositionRelClient {
    @RequestMapping(value = "/tbOrgPositionRel/add", method = RequestMethod.POST)
    ResponseResult<TbOrgPositionRel> addTbOrgPositionRel(@RequestBody TbOrgPositionRel tbOrgPositionRel);

    @RequestMapping(value = "/tbOrgPositionRel/del", method = RequestMethod.POST)
    ResponseResult<TbOrgPositionRel> removeTbOrgPositionRel(@RequestBody Long orgPositionId, @RequestBody Long updateUser);
}
