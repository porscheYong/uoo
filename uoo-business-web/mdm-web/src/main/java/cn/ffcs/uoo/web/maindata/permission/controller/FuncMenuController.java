package cn.ffcs.uoo.web.maindata.permission.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.ffcs.uoo.web.maindata.permission.dto.FuncMenu;
import cn.ffcs.uoo.web.maindata.permission.service.FuncMenuService;
import cn.ffcs.uoo.web.maindata.permission.vo.ResponseResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 功能模块和功能模块之间存在上下级关系，一个功能模块可以有多个下级功能模块，一个功能模块只能属于一个上级功能模块。例如：一级菜单功能“订单管理”下挂二级菜单功能“订单受理”、“订单查询”、“订单历史”。 前端控制器
 * </p>
 *
 * @author zxs
 * @since 2018-10-30
 */
@RestController
@RequestMapping("/permission/funcMenu")
public class FuncMenuController {

    @Autowired
    private FuncMenuService funcMenuService;
/*
    @ApiOperation(value = "菜单树异步-web", notes = "菜单树")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "String" ,paramType="path"),
    })
    @UooLog(value = "菜单树", key = "getFuncMenuAsyncTree")
    @RequestMapping(value = "/getFuncMenuAsyncTree/{id}", method = RequestMethod.GET)
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult getFuncMenuAsyncTree(@PathVariable(value="id" ,required=true) String id){
        ResponseResult ret = new ResponseResult();
        List<TreeNodeVo> list = funcMenuService.selectFuncMenuAsyncTree(id);
        ret.setData(list);
        ret.setState(ResponseResult.STATE_OK);
        return ret;
    }

    @ApiOperation(value = "菜单树同步-web", notes = "菜单树")
    @ApiImplicitParams({
    })
    @UooLog(value = "菜单树", key = "getFuncMenuTree")
    @RequestMapping(value = "/getFuncMenuTree", method = RequestMethod.GET)
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult getFuncMenuSyncTree(){
        ResponseResult ret = new ResponseResult();
        List<TreeNodeVo> list = funcMenuService.selectFuncMenuSyncTree();
        ret.setData(list);
        ret.setState(ResponseResult.STATE_OK);
        return ret;
    }*/

    @ApiOperation(value = "菜单配置翻页-web", notes = "菜单配置翻页")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/getFuncMenuPage", method = RequestMethod.GET)
    public ResponseResult getFuncMenuPage(){
        
        return funcMenuService.getFuncMenuPage();
    }


    @ApiOperation(value = "新增菜单-web", notes = "新增菜单")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "funcMenu", value = "funcMenu", required = true, dataType = "FuncMenu"  ),
    })
    @RequestMapping(value = "/addFuncMenu", method = RequestMethod.POST)
    public ResponseResult addFuncMenu(@RequestBody FuncMenu funcMenu){
         
         
        return funcMenuService.addFuncMenu(funcMenu);
    }
    @ApiOperation(value = "修改菜单-web", notes = "修改菜单")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "funcMenu", value = "funcMenu", required = true, dataType = "FuncMenu"  ),
    })
    @RequestMapping(value = "/updateFuncMenu", method = RequestMethod.POST)
    public ResponseResult updateFuncMenu(@RequestBody FuncMenu funcMenu){
        
        return funcMenuService.updateFuncMenu(funcMenu);
    }
    
    @ApiOperation(value = "删除菜单-web", notes = "修改菜单")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "funcMenu", value = "funcMenu", required = true, dataType = "FuncMenu"  ),
    })
    @RequestMapping(value = "/deleteFuncMenu", method = RequestMethod.POST)
    public ResponseResult deleteFuncMenu(@RequestBody FuncMenu funcMenu){
        
        return funcMenuService.deleteFuncMenu(funcMenu);
    }
}

