package cn.ffcs.uoo.web.maindata.permission.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.ffcs.uoo.web.maindata.permission.service.PrivGrantService;
import cn.ffcs.uoo.web.maindata.permission.vo.BatchAddPositionPrivGrantVO;
import cn.ffcs.uoo.web.maindata.permission.vo.BatchAddRolePrivGrantVO;
import cn.ffcs.uoo.web.maindata.permission.vo.ResponseResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

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
@RestController
@RequestMapping("/permission/privGrant")
public class PrivGrantController {
     
    @Autowired
    private PrivGrantService grantSvc;
     
    
    @ApiOperation(value = "给角色授权", notes = "给角色授权")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "batchAddRolePrivGrantVO", value = "batchAddRolePrivGrantVO", required = true, dataType = "BatchAddRolePrivGrantVO"  ),
    })
    @RequestMapping(value="batchAddRolePrivGrant",method=RequestMethod.POST)
    public ResponseResult batchAddRolePrivGrant(@RequestBody BatchAddRolePrivGrantVO batchAddRolePrivGrantVO){
        return grantSvc.batchAddRolePrivGrant(batchAddRolePrivGrantVO);
    }
    @ApiOperation(value = "给职位授权", notes = "给职位授权")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "batchAddPositionPrivGrantVO", value = "batchAddPositionPrivGrantVO", required = true, dataType = "BatchAddPositionPrivGrantVO"  ),
    })
    @RequestMapping(value="batchAddPositionPrivGrant",method=RequestMethod.POST)
    public ResponseResult batchAddPositionPrivGrant(@RequestBody BatchAddPositionPrivGrantVO batchAddPositionPrivGrantVO){
        return grantSvc.batchAddPositionPrivGrant(batchAddPositionPrivGrantVO);
    }
    @ApiOperation(value = "获取角色的授权", notes = "获取角色的授权")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "roleId", value = "roleId", required = true, dataType = "Long" ,paramType="path"),
        //@ApiImplicitParam(name = "pageSize", value = "pageSize", required = false, dataType = "Long" ,paramType="path"),
    })
    @GetMapping("listAllPrivGrantByRole/{roleId}")
    public ResponseResult listAllPrivGrantByRole(@PathVariable(value="roleId",required=true) long roleId){
        return grantSvc.listAllPrivGrantByRole(roleId);
    }
    
    @ApiOperation(value = "获取职位的授权", notes = "获取职位的授权")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "roleId", value = "posId", required = true, dataType = "Long" ,paramType="path"),
        //@ApiImplicitParam(name = "pageSize", value = "pageSize", required = false, dataType = "Long" ,paramType="path"),
    })
    @GetMapping("listAllPrivGrantByPosition/{posId}")
    public ResponseResult listAllPrivGrantByPosition(@PathVariable(value="posId",required=true) long posId){
        return grantSvc.listAllPrivGrantByPosition(posId);
    }
        
}

