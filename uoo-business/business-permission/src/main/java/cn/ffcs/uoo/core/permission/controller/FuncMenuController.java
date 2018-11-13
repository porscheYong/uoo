package cn.ffcs.uoo.core.permission.controller;


import java.util.Date;
import java.util.List;

import org.mockito.internal.debugging.WarningsPrinterImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;

import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.core.permission.consts.StatusCD;
import cn.ffcs.uoo.core.permission.entity.FuncMenu;
import cn.ffcs.uoo.core.permission.service.FuncMenuService;
import cn.ffcs.uoo.core.permission.vo.MenuVO;
import cn.ffcs.uoo.core.permission.vo.ResponseResult;
import cn.ffcs.uoo.core.permission.vo.TreeNodeVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 功能模块和功能模块之间存在上下级关系，一个功能模块可以有多个下级功能模块，一个功能模块只能属于一个上级功能模块。例如：一级菜单功能“订单管理”下挂二级菜单功能“订单受理”、“订单查询”、“订单历史”。 前端控制器
 * </p>
 *
 * @author ffcs-gzb
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
    @UooLog(value = "菜单配置翻页", key = "getFuncMenuPage")
    @RequestMapping(value = "/getFuncMenuPage", method = RequestMethod.GET)
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult getFuncMenuPage(){
        Wrapper<FuncMenu> w=Condition.create().eq("STATUS_CD", StatusCD.VALID);
        List<FuncMenu> list = funcMenuService.selectList(w);
        return ResponseResult.createSuccessResult(list, "success");
    }


    @ApiOperation(value = "新增菜单-web", notes = "新增菜单")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "funcMenu", value = "funcMenu", required = true, dataType = "FuncMenu"  ),
    })
    @UooLog(value = "新增菜单", key = "addFuncMenu")
    @RequestMapping(value = "/addFuncMenu", method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult addFuncMenu(@RequestBody FuncMenu funcMenu){
         
        Long pmid=funcMenu.getParMenuId();
        int currentLevel = 0;
        if(pmid!=null && pmid != 0){
            //校验一下子
            FuncMenu pa=funcMenuService.selectById(pmid);
            if(pa==null||!StatusCD.VALID.equals(pa.getStatusCd())){
                return ResponseResult.createErrorResult("上一级菜单不存在");
                        
            }
            currentLevel=pa.getMenuLevel()+1;
        }
        funcMenu.setMenuLevel(currentLevel);
        funcMenu.setCreateDate(new Date());
        funcMenu.setStatusCd(StatusCD.VALID);
        funcMenuService.insert(funcMenu);
        return ResponseResult.createSuccessResult("success");
    }
    @ApiOperation(value = "修改菜单-web", notes = "修改菜单")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "funcMenu", value = "funcMenu", required = true, dataType = "FuncMenu"  ),
    })
    @UooLog(value = "修改菜单", key = "updateFuncMenu")
    @RequestMapping(value = "/updateFuncMenu", method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult updateFuncMenu(@RequestBody FuncMenu funcMenu){
         
        Long pmid=funcMenu.getParMenuId();
        int currentLevel = 0;
        if(pmid!=null && pmid != 0){
            //校验一下子
            FuncMenu pa=funcMenuService.selectById(pmid);
            if(pa==null||!StatusCD.VALID.equals(pa.getStatusCd())){
                return ResponseResult.createErrorResult("上一级菜单不存在");
                        
            }
            currentLevel=pa.getMenuLevel()+1;
        }
        funcMenu.setMenuLevel(currentLevel);
        funcMenu.setCreateDate(new Date());
        funcMenu.setMenuId(funcMenuService.getId());
        funcMenuService.insert(funcMenu);
        return ResponseResult.createSuccessResult("success");
    }
    
    @ApiOperation(value = "删除菜单-web", notes = "修改菜单")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "funcMenu", value = "funcMenu", required = true, dataType = "FuncMenu"  ),
    })
    @UooLog(value = "删除菜单", key = "deleteFuncMenu")
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/deleteFuncMenu", method = RequestMethod.POST)
    public ResponseResult deleteFuncMenu(@RequestBody FuncMenu funcMenu){
         
        //有下级不能删除
        Long menuId = funcMenu.getMenuId();
        Wrapper<FuncMenu> w=Condition.create().eq("STATUS_CD", StatusCD.VALID).eq("PAR_MENU_ID", menuId);
        List<FuncMenu> list = funcMenuService.selectList(w);
        if(list!=null&&!list.isEmpty()){
            return ResponseResult.createErrorResult("请先删除下级菜单");
        }
        FuncMenu menu = funcMenuService.selectById(menuId);
        menu.setStatusCd(StatusCD.INVALID);
        menu.setStatusDate(new Date());
        funcMenuService.updateById(menu);
        return ResponseResult.createSuccessResult("success");
    }
}

