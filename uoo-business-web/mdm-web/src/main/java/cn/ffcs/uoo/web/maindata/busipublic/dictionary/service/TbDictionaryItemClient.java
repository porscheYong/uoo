package cn.ffcs.uoo.web.maindata.busipublic.dictionary.service;

import cn.ffcs.uoo.web.maindata.busipublic.dictionary.dto.TbDictionaryItem;
import cn.ffcs.uoo.web.maindata.busipublic.dictionary.service.fallback.TbDictionaryItemClientHystrix;
import cn.ffcs.uoo.web.maindata.busipublic.vo.ResponseResult;
import common.config.PersonnelServiceConfiguration;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 字典项调用客户端
 * @author zhanglu
 * @date 2018-11-08
 */
@FeignClient(value = "business-public",configuration = {PersonnelServiceConfiguration.class},fallback = TbDictionaryItemClientHystrix.class)
public interface TbDictionaryItemClient {
    @RequestMapping(value = "/tbDictionaryItem/update", method = RequestMethod.POST)
    ResponseResult<TbDictionaryItem> updateTbDictionaryItem(@RequestBody TbDictionaryItem tbDictionaryItem);

    @RequestMapping(value = "/tbDictionaryItem/add", method = RequestMethod.POST)
    ResponseResult<TbDictionaryItem> addTbDictionaryItem(@RequestBody TbDictionaryItem tbDictionaryItem);

    @RequestMapping(value = "/tbDictionaryItem/del", method = RequestMethod.POST)
    ResponseResult<TbDictionaryItem> removeTbDictionaryItem(@RequestParam("itemId") Long itemId, @RequestParam("updateUser") Long updateUser);

    @RequestMapping(value = "/tbDictionaryItem/getList/{dictionaryName}", method = RequestMethod.GET)
    List<TbDictionaryItem> queryListByDictionaryName(@PathVariable("dictionaryName") String dictionaryName);
}
