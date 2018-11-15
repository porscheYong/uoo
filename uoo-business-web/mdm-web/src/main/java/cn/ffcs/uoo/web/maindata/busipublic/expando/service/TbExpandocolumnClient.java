package cn.ffcs.uoo.web.maindata.busipublic.expando.service;

import cn.ffcs.uoo.web.maindata.busipublic.dictionary.service.fallback.TbDictionaryItemClientHystrix;
import cn.ffcs.uoo.web.maindata.busipublic.expando.dto.TbExpandocolumn;
import cn.ffcs.uoo.web.maindata.busipublic.expando.service.fallback.TbExpandocolumnClientHystrix;
import cn.ffcs.uoo.web.maindata.busipublic.vo.ResponseResult;
import common.config.PersonnelServiceConfiguration;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 扩展列客户端
 * @author zhanglu
 * @date 2018-11-09
 */
@FeignClient(value = "business-public",configuration = {PersonnelServiceConfiguration.class},fallback = TbExpandocolumnClientHystrix.class)
public interface TbExpandocolumnClient {
    @RequestMapping(value = "/tbExpandocolumn/update", method = RequestMethod.POST)
    ResponseResult<TbExpandocolumn> updateTbExpandocolumn(@RequestBody TbExpandocolumn tbExpandocolumn);

    @RequestMapping(value = "/tbExpandocolumn/add", method = RequestMethod.POST)
    ResponseResult<TbExpandocolumn> addTbExpandocolumn(@RequestBody TbExpandocolumn tbExpandocolumn);

    @RequestMapping(value = "/tbExpandocolumn/del", method = RequestMethod.POST)
    ResponseResult<TbExpandocolumn> removeTbExpandocolumn(@RequestParam("columnId") Long columnId, @RequestParam("updateUser") Long updateUser);

    @RequestMapping(value = "/tbExpandocolumn/getList/{tableId}", method = RequestMethod.GET)
    List<TbExpandocolumn> queryListByTableId(@PathVariable("tableId") Long tableId);

    @RequestMapping(value = "/tbExpandocolumn/getColumnList/{tableId}/{resourceId}", method = RequestMethod.GET)
    List<TbExpandocolumn> queryColumnList(@PathVariable("tableId") Long tableId, @PathVariable("resourceId") String resourceId);
}
