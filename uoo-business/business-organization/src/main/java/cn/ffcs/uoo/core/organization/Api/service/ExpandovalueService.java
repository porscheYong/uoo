package cn.ffcs.uoo.core.organization.Api.service;/**
 * @description:
 * @author: ffcs-gzb
 * @date: 2018-11-30
 */

import cn.ffcs.uoo.core.organization.Api.service.impl.ExpandovalueServiceHystrix;
import cn.ffcs.uoo.core.organization.util.ResponseResult;
import cn.ffcs.uoo.core.organization.vo.ExpandovalueVo;
import cn.ffcs.uoo.core.organization.vo.TbExpandovalue;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018/11/30
 */
//@FeignClient(name = "business-public", url = "http://192.168.58.128:11500",fallback = ExpandovalueServiceHystrix.class)

@Service
//@FeignClient(value = "business-public")
@FeignClient(name = "business-public", url = "http://192.168.58.128:11500",fallback = ExpandovalueServiceHystrix.class)

public interface ExpandovalueService {

    @RequestMapping(value = "/tbExpandovalue/getValueVoList/{tableName}/{recordId}", method = RequestMethod.GET)
    public ResponseResult<List<ExpandovalueVo>> queryExpandovalueVoList(@PathVariable("tableName") String tableName,
                                                                        @PathVariable("recordId") String recordId);


    @RequestMapping(value = "/tbExpandovalue/update", method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult<TbExpandovalue> updateTbExpandovalue(@RequestBody TbExpandovalue tbExpandovalue);

    @RequestMapping(value = "/tbExpandovalue/addByVo", method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult<ExpandovalueVo> addExpandoInfo(@RequestBody ExpandovalueVo expandovalueVo);

    @RequestMapping(value = "/tbExpandovalue/del", method = RequestMethod.POST)
    ResponseResult<TbExpandovalue> removeTbExpandovalue(@RequestParam("valueId") Long valueId, @RequestParam("updateUser") Long updateUser);

    @RequestMapping(value = "/modifyHistory/addModifyHistory11", method = RequestMethod.POST,headers={"Content-Type=application/json"})
    ResponseResult<String> addModifyHistory11(@RequestBody Object obj);


//    @RequestMapping(value = "/modifyHistory/addModifyHistory", method = RequestMethod.POST,headers={"Content-Type=application/json"})
//    ResponseResult<String> addModifyHistory(@RequestBody HashMap<String,Object> obj);

}
