package cn.ffcs.uoo.web.maindata.busipublic.dictionary.service;

import cn.ffcs.uoo.web.maindata.busipublic.dictionary.dto.TbDictionary;
import cn.ffcs.uoo.web.maindata.busipublic.dictionary.service.fallback.TbDictionaryClientHystrix;
import cn.ffcs.uoo.web.maindata.busipublic.vo.ResponseResult;
import common.config.PersonnelServiceConfiguration;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 字典前端客户端
 * @author zhanglu
 * @date 2018-11-08
 */
@FeignClient(value = "business-public",configuration = {PersonnelServiceConfiguration.class},fallback = TbDictionaryClientHystrix.class)
public interface TbDictionaryClient {
    @RequestMapping(value = "/tbDictionary/update", method = RequestMethod.POST)
    ResponseResult<TbDictionary> updateTbDictionary(@RequestBody TbDictionary tbDictionary);

    @RequestMapping(value = "/tbDictionary/add", method = RequestMethod.POST)
    ResponseResult<TbDictionary> addTbDictionary(@RequestBody TbDictionary tbDictionary);

    @RequestMapping(value = "/tbDictionary/del", method = RequestMethod.POST)
    ResponseResult<TbDictionary> removeTbDictionary(@RequestParam("dictionaryName") String dictionaryName, @RequestParam("updateUser") Long updateUser);
}
