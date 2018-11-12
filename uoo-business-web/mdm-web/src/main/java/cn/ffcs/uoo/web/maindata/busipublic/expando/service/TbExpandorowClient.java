package cn.ffcs.uoo.web.maindata.busipublic.expando.service;

import cn.ffcs.uoo.web.maindata.busipublic.dictionary.service.fallback.TbDictionaryItemClientHystrix;
import cn.ffcs.uoo.web.maindata.busipublic.expando.dto.TbExpandorow;
import cn.ffcs.uoo.web.maindata.busipublic.expando.service.fallback.TbExpandorowClientHystrix;
import common.config.PersonnelServiceConfiguration;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "uoo-public",configuration = {PersonnelServiceConfiguration.class},fallback = TbExpandorowClientHystrix.class)
public interface TbExpandorowClient {
    @RequestMapping(value = "/tbExpandorow/getList/{tableId}/{resourceId}/{recordId}", method = RequestMethod.GET)
    List<TbExpandorow> queryRowList(@PathVariable Long tableId, @PathVariable String resourceId, @PathVariable String recordId);
}
