package cn.ffcs.uoo.web.maindata.busipublic.expando.service;

import cn.ffcs.uoo.web.maindata.busipublic.expando.dto.TbSystemtable;
import cn.ffcs.uoo.web.maindata.busipublic.expando.service.fallback.TbExpandovalueClientHystrix;
import cn.ffcs.uoo.web.maindata.busipublic.expando.service.fallback.TbSystemtableClientHystrix;
import cn.ffcs.uoo.web.maindata.busipublic.vo.ResponseResult;
import common.config.PersonnelServiceConfiguration;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "business-position",configuration = {PersonnelServiceConfiguration.class},fallback = TbSystemtableClientHystrix.class)
public interface TbSystemtableClient {
    @RequestMapping(value = "/tbSystemtable/add", method = RequestMethod.POST, headers={"Content-Type=application/json"})
    ResponseResult<TbSystemtable> addTbSystemtable(@RequestBody TbSystemtable tbSystemtable);

    @RequestMapping(value = "/tbSystemtable/update", method = RequestMethod.POST, headers={"Content-Type=application/json"})
    ResponseResult<TbSystemtable> updateTbSystemtable(@RequestBody TbSystemtable tbSystemtable);

    @RequestMapping(value = "/tbSystemtable/del", method = RequestMethod.POST)
    ResponseResult<TbSystemtable> removeTbSystemtable(@RequestParam("tableId") Long tableId, @RequestParam("updateUser") Long updateUser);
}
