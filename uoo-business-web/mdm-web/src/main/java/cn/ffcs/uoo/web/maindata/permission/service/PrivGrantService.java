package cn.ffcs.uoo.web.maindata.permission.service;


import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.ffcs.uoo.web.maindata.permission.service.fallback.PrivGrantHystrix;
import cn.ffcs.uoo.web.maindata.permission.vo.BatchAddPositionPrivGrantVO;
import cn.ffcs.uoo.web.maindata.permission.vo.BatchAddRolePrivGrantVO;
import cn.ffcs.uoo.web.maindata.permission.vo.ResponseResult;

/**
 * <p>
 * 描述将权限授权给不同的对象，包括系统用户、系统岗位、角色；
一个系统用户也可以拥有多个私有的权限，一个权限可以分配给多个系统用户。
一个系统岗位除了拥有角色所带的权限，也可以拥有私有的多个权限，一个权限可以分配给多个系统岗位。
一个角色可以包含多个权限，一个权限也可以分配给多个角色。 前端控制器
 * </p>
 *
 * @author zengxsh
 * @since 2018-11-08
 */
@FeignClient(value = "business-permission", fallback = PrivGrantHystrix.class)
public interface PrivGrantService {
     
     
    @RequestMapping(value="/permission/privGrant/batchAddRolePrivGrant",method=RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult batchAddRolePrivGrant(@RequestBody BatchAddRolePrivGrantVO batchAddRolePrivGrantVO);
   
    @RequestMapping(value="/permission/privGrant/batchAddPositionPrivGrant",method=RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult batchAddPositionPrivGrant(@RequestBody BatchAddPositionPrivGrantVO batchAddPositionPrivGrantVO);
    
    @GetMapping("/permission/privGrant/listAllPrivGrantByRole/{roleId}")
    public ResponseResult listAllPrivGrantByRole(@PathVariable(value="roleId",required=true) long roleId);
    
   
    @GetMapping("/permission/privGrant/listAllPrivGrantByPosition/{posId}")
    public ResponseResult listAllPrivGrantByPosition(@PathVariable(value="posId",required=true) long posId);
        
}

