package cn.ffcs.uoo.web.maindata.permission.service;


import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.ffcs.uoo.web.maindata.permission.dto.PrivDataRel;
import cn.ffcs.uoo.web.maindata.permission.service.fallback.PrivDataRelHystrix;
import cn.ffcs.uoo.web.maindata.permission.vo.ResponseResult;

/**
 * <p>
 * 记录权限与业务对象之间多对多的关系 前端控制器
 * </p>
 *
 * @author zengxsh
 * @since 2018-11-08
 */
@FeignClient(value = "uoo-permission-provider", fallback = PrivDataRelHystrix.class)
public interface PrivDataRelService {
     
    @RequestMapping(value="/permission/privDataRel/addPrivDataRel",method=RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult addPrivDataRel(@RequestBody PrivDataRel privDataRel);
    
    @RequestMapping(value="/permission/privDataRel/updatePrivDataRel",method=RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult updatePrivDataRel(@RequestBody PrivDataRel privDataRel);
     
    @RequestMapping(value="/permission/privDataRel/deletePrivDataRel",method=RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult deletePrivDataRel(@RequestBody PrivDataRel privDataRel);
}

