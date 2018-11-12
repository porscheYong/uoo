package cn.ffcs.uoo.web.maindata.position.service;

import cn.ffcs.uoo.web.maindata.position.dto.TbPosition;
import cn.ffcs.uoo.web.maindata.position.service.fallback.TbPositionClientHystrix;
import cn.ffcs.uoo.web.maindata.position.vo.OrgPositionInfoVo;
import cn.ffcs.uoo.web.maindata.position.vo.ResponseResult;
import common.config.PersonnelServiceConfiguration;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "uoo-position",configuration = {PersonnelServiceConfiguration.class},fallback = TbPositionClientHystrix.class)
public interface TbPositionClient {
    @RequestMapping(value = "/tbPosition/add", method = RequestMethod.POST)
    ResponseResult<TbPosition> addTbPosition(@RequestBody TbPosition tbPosition);

    @RequestMapping(value = "/tbPosition/update", method = RequestMethod.POST)
    ResponseResult<TbPosition> updateTbPosition(@RequestBody TbPosition tbPosition);

    @RequestMapping(value = "/tbPosition/del", method = RequestMethod.POST)
    ResponseResult<TbPosition> removeTbPosition(@RequestBody Long positionId, @RequestBody Long updateUser);

    @RequestMapping(value = "/tbPosition/getOrgPositionInfoList/{orgId}", method = RequestMethod.GET)
    List<OrgPositionInfoVo> queryOrgPositionInfoList(@PathVariable Long orgId);

    @RequestMapping(value = "/tbPosition/get/{positionName}", method = RequestMethod.GET)
    List<TbPosition> queryPositionList(@PathVariable String positionName);

    @RequestMapping(value = "/tbPosition/getParent", method = RequestMethod.GET)
    List<TbPosition> queryParentPositionList();

    @RequestMapping(value = "/tbPosition/getChildren/{parentPositionId}", method = RequestMethod.GET)
    List<TbPosition> queryChildPositionList(@PathVariable Long parentPositionId);
}
