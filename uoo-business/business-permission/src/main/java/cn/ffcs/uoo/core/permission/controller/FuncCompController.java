package cn.ffcs.uoo.core.permission.controller;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
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
import cn.ffcs.uoo.core.permission.entity.FuncComp;
import cn.ffcs.uoo.core.permission.entity.FuncMenu;
import cn.ffcs.uoo.core.permission.service.FuncCompService;
import cn.ffcs.uoo.core.permission.service.FuncMenuService;
import cn.ffcs.uoo.core.permission.vo.ResponseResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 指系统内的系统功能菜单的最小功能单元及组件。 前端控制器
 * </p>
 *
 * @author zxs
 * @since 2018-10-30
 */
@RestController
@RequestMapping("/permission/funcComp")
public class FuncCompController {
    @Autowired
    private FuncCompService funcCompService;
    
    @Autowired
    private FuncMenuService funcMenuService;
    
    @ApiOperation(value = "获取单个功能组件", notes = "获取单个功能组件")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long" ,paramType="path"),
    })
    @UooLog(key="getDataPrivRule",value="获取单个功能组件")
    @GetMapping("/get/{id}")
    public ResponseResult<FuncComp> get(@PathVariable(value="id" ,required=true) Long id){
        FuncComp comp = funcCompService.selectById(id);
        if(comp== null || !StatusCD.VALID.equals(comp.getStatusCd())){
            return ResponseResult.createErrorResult("无效数据");
        }
        return ResponseResult.createSuccessResult(comp, "success");
    }
    
    @ApiOperation(value = "获取功能组件列表", notes = "获取功能组件列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "pageNo", value = "pageNo", required = true, dataType = "Long" ,paramType="path"),
        @ApiImplicitParam(name = "pageSize", value = "pageSize", required = false, dataType = "Long" ,paramType="path"),
    })
    @UooLog(key="listFuncComp",value="获取功能组件列表")
    @GetMapping("/listFuncComp/pageNo={pageNo}&pageSize={pageSize}")
    public ResponseResult<Page<FuncComp>> listFuncComp(@PathVariable(value = "pageNo") Integer pageNo, @PathVariable(value = "pageSize",required = false) Integer pageSize){
        pageNo = pageNo==null?0:pageNo;
        pageSize = pageSize==null?20:pageSize;
        
        @SuppressWarnings("unchecked")
        Wrapper<FuncComp> wrapper = Condition.create().eq("STATUS_CD",StatusCD.VALID).orderBy("UPDATE_DATE", false);
        Page<FuncComp> page = funcCompService.selectPage(new Page<FuncComp>(pageNo, pageSize), wrapper);
        
        return ResponseResult.createSuccessResult(  page,"");
    }
    @ApiOperation(value = "添加功能组件列表", notes = "添加功能组件列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "funcComp", value = "funcComp", required = true, dataType = "FuncComp"  ),
    })
    @UooLog(key="addFuncComp",value="添加功能组件")
    @Transactional
    @RequestMapping(value="/addFuncComp",method=RequestMethod.POST)
    public ResponseResult<Void> addFuncComp(@RequestBody FuncComp funcComp){
        Long menuId = funcComp.getMenuId();
        if(menuId==null){
            return ResponseResult.createErrorResult("请选择功能菜单");
        }
        FuncMenu menu = funcMenuService.selectById(menuId);
        if(menu==null || !StatusCD.VALID.equals(menu.getStatusCd())){
            return ResponseResult.createErrorResult("无效的功能菜单");
        }
        funcComp.setCompId(funcCompService.getId());
        funcComp.setStatusCd(StatusCD.VALID);
        funcComp.setStatusDate(new Date());
        funcComp.setCreateDate(new Date());
        
        funcCompService.insert(funcComp);
        return ResponseResult.createSuccessResult("success");
    }
    @ApiOperation(value = "修改功能组件列表", notes = "修改功能组件列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "funcComp", value = "funcComp", required = true, dataType = "FuncComp"  ),
    })
    @UooLog(key="updateFuncComp",value="修改功能组件")
    @Transactional
    @RequestMapping(value="/updateFuncComp",method=RequestMethod.POST)
    public ResponseResult<Void> updateFuncComp(@RequestBody FuncComp funcComp){
        Long menuId = funcComp.getMenuId();
        if(menuId==null){
            return ResponseResult.createErrorResult("请选择功能菜单");
        }
        FuncMenu menu = funcMenuService.selectById(menuId);
        if(menu==null || !StatusCD.VALID.equals(menu.getStatusCd())){
            return ResponseResult.createErrorResult("无效的功能菜单");
        }
        
        funcComp.setUpdateDate(new Date());
        funcCompService.updateById(funcComp);
        return ResponseResult.createSuccessResult("success");
    }
    
    @ApiOperation(value = "删除功能组件列表", notes = "删除功能组件列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "funcComp", value = "funcComp", required = true, dataType = "FuncComp"  ),
    })
    @UooLog(key="deleteFuncComp",value="删除功能组件")
    @Transactional
    @RequestMapping(value="/deleteFuncComp",method=RequestMethod.POST)
    public ResponseResult<Void> deleteFuncComp(@RequestBody FuncComp funcComp){
        Long compId = funcComp.getCompId();
        if(compId!=null){
            FuncComp comp = funcCompService.selectById(compId);
            comp.setStatusCd(StatusCD.INVALID);
            comp.setStatusDate(new Date());
            funcCompService.updateById(comp);
        }
        return ResponseResult.createSuccessResult("success");
    }
}

