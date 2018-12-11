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
import cn.ffcs.uoo.core.permission.entity.PrivFuncRel;
import cn.ffcs.uoo.core.permission.entity.Privilege;
import cn.ffcs.uoo.core.permission.service.FuncCompService;
import cn.ffcs.uoo.core.permission.service.FuncMenuService;
import cn.ffcs.uoo.core.permission.service.IPrivilegeService;
import cn.ffcs.uoo.core.permission.service.PrivFuncRelService;
import cn.ffcs.uoo.core.permission.vo.ResponseResult;
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
    @Autowired
    private FuncMenuService funcMenuService;
    @Autowired
    private FuncCompService funcCompService;
    @Autowired
    private IPrivilegeService privilegeService;
    @ApiOperation(value = "获取单个权限和功能菜单组件关系", notes = "获取单个权限和功能菜单组件关系")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long" ,paramType="path"),
    })
    @UooLog(key="getPrivFuncRel",value="获取单个权限和功能菜单组件关系")
    @GetMapping("getPrivFuncRel/{id}")
    public ResponseResult<PrivFuncRel>  getPrivFuncRel(@PathVariable(value="id" ,required=true) Long id){
        PrivFuncRel rel = privFuncRelService.selectById(id);
        if(rel== null || !StatusCD.VALID.equals(rel.getStatusCd())){
            return ResponseResult.createErrorResult("无效数据");
        }
        return ResponseResult.createSuccessResult(rel, "success");
    }
    @ApiOperation(value = "获取权限和功能菜单组件关系列表", notes = "获取权限和功能菜单组件关系列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "pageNo", value = "pageNo", required = true, dataType = "Long" ,paramType="path"),
        @ApiImplicitParam(name = "pageSize", value = "pageSize", required = false, dataType = "Long" ,paramType="path"),
    })
    @UooLog(key="listPrivFuncRel",value="获取权限和功能菜单组件关系列表")
    @GetMapping("/listPrivFuncRel/pageNo={pageNo}&pageSize={pageSize}")
    public ResponseResult<List<PrivFuncRel>> listPrivFuncRel(@PathVariable(value = "pageNo") Integer pageNo, @PathVariable(value = "pageSize",required = false) Integer pageSize){
        pageNo = pageNo==null?0:pageNo;
        pageSize = pageSize==null?20:pageSize;
        
        Wrapper<PrivFuncRel> wrapper = Condition.create().eq("STATUS_CD",StatusCD.VALID).orderBy("UPDATE_DATE", false);
        Page<PrivFuncRel> page = privFuncRelService.selectPage(new Page<PrivFuncRel>(pageNo, pageSize), wrapper);
        
        return ResponseResult.createSuccessResult( page,"");
    }
    
    @ApiOperation(value = "添加权限和功能菜单组件关系", notes = "添加权限和功能菜单组件关系")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "privFuncRel", value = "privFuncRel", required = true, dataType = "PrivFuncRel"  ),
    })
    @UooLog(key="addPrivFuncRel",value="添加权限和功能菜单组件关系")
    @Transactional
    @RequestMapping(value="/addPrivFuncRel",method=RequestMethod.POST)
    public ResponseResult<Void> addPrivFuncRel(@RequestBody PrivFuncRel privFuncRel){
        Long menuId = privFuncRel.getMenuId();
        if(menuId==null){
            return ResponseResult.createErrorResult("请选择功能菜单");
        }
        FuncMenu menu = funcMenuService.selectById(menuId);
        if(menu==null || !StatusCD.VALID.equals(menu.getStatusCd())){
            return ResponseResult.createErrorResult("无效的功能菜单");
        }
        Long compId = privFuncRel.getCompId();
        if(compId==null){
            return ResponseResult.createErrorResult("请选择功能组件");
        }
        FuncComp comp = funcCompService.selectById(compId);
        if(comp==null || !StatusCD.VALID.equals(comp.getStatusCd())){
            return ResponseResult.createErrorResult("无效的功能组件");
        }
        Long privId = privFuncRel.getPrivId();
        if(privId==null){
            return ResponseResult.createErrorResult("请选择权限标识");
        }
        Privilege priv = privilegeService.selectById(privId);
        if(priv==null || !StatusCD.VALID.equals(priv.getStatusCd())){
            return ResponseResult.createErrorResult("无效的权限标识");
        }
        privFuncRel.setPrivFuncId(privFuncRelService.getId());
        privFuncRel.setStatusCd(StatusCD.VALID);
        privFuncRel.setStatusDate(new Date());
        privFuncRel.setCreateDate(new Date());
        
        privFuncRelService.insert(privFuncRel);
        return ResponseResult.createSuccessResult("success");
    }
    @ApiOperation(value = "修改权限和功能菜单组件关系", notes = "修改权限和功能菜单组件关系")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "privFuncRel", value = "privFuncRel", required = true, dataType = "PrivFuncRel"  ),
    })
    @UooLog(key="updatePrivFuncRel",value="修改权限和功能菜单组件关系")
    @Transactional
    @RequestMapping(value="/updatePrivFuncRel",method=RequestMethod.POST)
    public ResponseResult<Void> updatePrivFuncRel(@RequestBody PrivFuncRel privFuncRel){
        Long menuId = privFuncRel.getMenuId();
        if(menuId==null){
            return ResponseResult.createErrorResult("请选择功能菜单");
        }
        FuncMenu menu = funcMenuService.selectById(menuId);
        if(menu==null || !StatusCD.VALID.equals(menu.getStatusCd())){
            return ResponseResult.createErrorResult("无效的功能菜单");
        }
        Long compId = privFuncRel.getCompId();
        if(compId==null){
            return ResponseResult.createErrorResult("请选择功能组件");
        }
        FuncComp comp = funcCompService.selectById(compId);
        if(comp==null || !StatusCD.VALID.equals(comp.getStatusCd())){
            return ResponseResult.createErrorResult("无效的功能组件");
        }
        Long privId = privFuncRel.getPrivId();
        if(privId==null){
            return ResponseResult.createErrorResult("请选择权限标识");
        }
        Privilege priv = privilegeService.selectById(privId);
        if(priv==null || !StatusCD.VALID.equals(priv.getStatusCd())){
            return ResponseResult.createErrorResult("无效的权限标识");
        }
        
        privFuncRel.setUpdateDate(new Date());
        privFuncRelService.updateById(privFuncRel);
        return ResponseResult.createSuccessResult("success");
    }
    @ApiOperation(value = "删除权限和功能菜单组件关系", notes = "删除权限和功能菜单组件关系")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "privFuncRel", value = "privFuncRel", required = true, dataType = "PrivFuncRel"  ),
    })
    @Transactional
    @UooLog(key="deletePrivFuncRel",value="删除权限和功能菜单组件关系")
    @RequestMapping(value="/deletePrivFuncRel",method=RequestMethod.POST)
    public ResponseResult<Void> deleteFuncComp(@RequestBody PrivFuncRel privFuncRel){
        Long id = privFuncRel.getPrivFuncId();
        if(id!=null){
            PrivFuncRel rel = privFuncRelService.selectById(id);
            rel.setStatusCd(StatusCD.INVALID);
            rel.setStatusDate(new Date());
            privFuncRelService.updateById(rel);
        }
        return ResponseResult.createSuccessResult("success");
    }
}

