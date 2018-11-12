package cn.ffcs.uoo.web.maindata.busipublic.expando.service;

import cn.ffcs.uoo.web.maindata.busipublic.expando.dto.TbSystemtable;
import cn.ffcs.uoo.web.maindata.busipublic.expando.service.fallback.TbExpandovalueClientHystrix;
import cn.ffcs.uoo.web.maindata.busipublic.vo.ResponseResult;
import common.config.PersonnelServiceConfiguration;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "uoo-position",configuration = {PersonnelServiceConfiguration.class},fallback = TbExpandovalueClientHystrix.class)
public interface TbSystemtableClient {
    @RequestMapping(value = "/tbSystemtable/add", method = RequestMethod.POST)
    ResponseResult<TbSystemtable> addTbSystemtable(@RequestBody TbSystemtable tbSystemtable);

    @RequestMapping(value = "/tbSystemtable/update", method = RequestMethod.POST)
    ResponseResult<TbSystemtable> updateTbSystemtable(@RequestBody TbSystemtable tbSystemtable);

    @RequestMapping(value = "/tbSystemtable/del", method = RequestMethod.POST)
    ResponseResult<TbSystemtable> removeTbSystemtable(@RequestBody Long tableId, @RequestBody Long updateUser);
}
