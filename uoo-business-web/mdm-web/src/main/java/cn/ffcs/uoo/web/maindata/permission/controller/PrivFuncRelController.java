package cn.ffcs.uoo.web.maindata.permission.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.ffcs.uoo.web.maindata.permission.dto.PrivFuncRel;
import cn.ffcs.uoo.web.maindata.permission.service.PrivFuncRelService;
import cn.ffcs.uoo.web.maindata.permission.vo.ResponseResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 定义权限关联的功能菜单、功能组件，一个权限可包含多个功能菜单或功能组件。 前端控制器
 * </p>
 *
 */
@RestController
@RequestMapping("/permission/privFuncRel")
public class PrivFuncRelController {
    @Autowired
    private PrivFuncRelService privFuncRelService;
     
    @ApiOperation(value = "获取单个权限和功能菜单组件关系", notes = "获取单个权限和功能菜单组件关系")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long" ,paramType="path"),
    })
    @GetMapping("getPrivFuncRel/{id}")
    public ResponseResult  getPrivFuncRel(@PathVariable(value="id" ,required=true) Long id){
        
        return privFuncRelService.getPrivFuncRel(id);
    }
    @ApiOperation(value = "获取权限和功能菜单组件关系列表", notes = "获取权限和功能菜单组件关系列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "pageNo", value = "pageNo", required = true, dataType = "Long" ,paramType="path"),
        @ApiImplicitParam(name = "pageSize", value = "pageSize", required = false, dataType = "Long" ,paramType="path"),
    })
    @GetMapping("/listPrivFuncRel/pageNo={pageNo}&pageSize={pageSize}")
    public ResponseResult listPrivFuncRel(@PathVariable(value = "pageNo") Integer pageNo, @PathVariable(value = "pageSize",required = false) Integer pageSize){
        return privFuncRelService.listPrivFuncRel(pageNo, pageSize);
    }
    
    @ApiOperation(value = "添加权限和功能菜单组件关系", notes = "添加权限和功能菜单组件关系")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "privFuncRel", value = "privFuncRel", required = true, dataType = "PrivFuncRel"  ),
    })
    @RequestMapping(value="/addPrivFuncRel",method=RequestMethod.POST)
    public ResponseResult addPrivFuncRel(@RequestBody PrivFuncRel privFuncRel){
        return privFuncRelService.addPrivFuncRel(privFuncRel);
    }
    @ApiOperation(value = "修改权限和功能菜单组件关系", notes = "修改权限和功能菜单组件关系")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "privFuncRel", value = "privFuncRel", required = true, dataType = "PrivFuncRel"  ),
    })
    @RequestMapping(value="/updatePrivFuncRel",method=RequestMethod.POST)
    public ResponseResult updatePrivFuncRel(@RequestBody PrivFuncRel privFuncRel){
        return privFuncRelService.updatePrivFuncRel(privFuncRel);
    }
    @ApiOperation(value = "删除权限和功能菜单组件关系", notes = "删除权限和功能菜单组件关系")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "privFuncRel", value = "privFuncRel", required = true, dataType = "PrivFuncRel"  ),
    })
    @RequestMapping(value="/deletePrivFuncRel",method=RequestMethod.POST)
    public ResponseResult deleteFuncComp(@RequestBody PrivFuncRel privFuncRel){
        return privFuncRelService.deleteFuncComp(privFuncRel);
    }
}

