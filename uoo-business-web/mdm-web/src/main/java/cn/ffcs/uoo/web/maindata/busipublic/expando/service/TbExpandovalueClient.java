package cn.ffcs.uoo.web.maindata.busipublic.expando.service;

import cn.ffcs.uoo.web.maindata.busipublic.expando.dto.ExpandovalueVo;
import cn.ffcs.uoo.web.maindata.busipublic.expando.dto.TbExpandovalue;
import cn.ffcs.uoo.web.maindata.busipublic.expando.service.fallback.TbExpandovalueClientHystrix;
import cn.ffcs.uoo.web.maindata.busipublic.vo.ResponseResult;
import common.config.PersonnelServiceConfiguration;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "business-public",configuration = {PersonnelServiceConfiguration.class},fallback = TbExpandovalueClientHystrix.class)
public interface TbExpandovalueClient {
    @RequestMapping(value = "/tbExpandovalue/add", method = RequestMethod.POST, headers={"Content-Type=application/json"})
    ResponseResult<TbExpandovalue> addTbExpandovalue(@RequestBody TbExpandovalue tbExpandovalue);

    @RequestMapping(value = "/tbExpandovalue/del", method = RequestMethod.POST)
    ResponseResult<TbExpandovalue> removeTbExpandovalue(@RequestParam("valueId") Long valueId, @RequestParam("updateUser") Long updateUser);

    @RequestMapping(value = "/tbExpandovalue/update", method = RequestMethod.POST, headers={"Content-Type=application/json"})
    ResponseResult<TbExpandovalue> updateTbExpandovalue(@RequestBody TbExpandovalue tbExpandovalue);

    @RequestMapping(value = "/tbExpandovalue/getList/{resourceId}/{tableId}/{columnId}/{recordId}", method = RequestMethod.GET)
    List<TbExpandovalue> queryValueList(@PathVariable("resourceId") String resourceId, @PathVariable("tableId") Long tableId,
                                               @PathVariable("columnId") Long columnId, @PathVariable("recordId") String recordId);

    @RequestMapping(value = "/tbExpandovalue/getValueVoList/{tableName}/{recordId}", method = RequestMethod.GET)
    ResponseResult<List<ExpandovalueVo>> queryExpandovalueVoList(@PathVariable("tableName") String tableName,
                                                                        @PathVariable("recordId") String recordId);

    @RequestMapping(value = "/tbExpandovalue/addByVo", method = RequestMethod.POST, headers={"Content-Type=application/json"})
    ResponseResult<ExpandovalueVo> addExpandoInfo(@RequestBody ExpandovalueVo expandovalueVo);
}
