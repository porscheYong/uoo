package cn.ffcs.uoo.web.maindata.permission.controller;


import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.ffcs.uoo.web.maindata.permission.dto.Roles;
import cn.ffcs.uoo.web.maindata.permission.service.RolesService;
import cn.ffcs.uoo.web.maindata.permission.vo.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.List;

/**
 * <p>
 * 角色
 * 描述员工的系统权限集合，是用以定义系统使用人员操作权限的实体。 前端控制器
 * </p>
 *
 * @author zxs
 * @since 2018-10-24
 */
@Api(description = "角色",value = "Roles")
@RestController
@RequestMapping("/permission/tbRoles")
public class TbRolesController   {
    @Autowired
    RolesService tbRolesService;
     
    @ApiOperation(value = "获取单个数据", notes = "获取单个数据")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long" ,paramType="path"),
    })
    @GetMapping("/get/{id}")
    public ResponseResult get(@PathVariable(value="id" ,required=true) Long id){
         
        return tbRolesService.get(id);
    }

    @ApiOperation(value = "获取分页角色列表", notes = "获取分页角色列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "pageNo", required = true, dataType = "Long" ,paramType="path"),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = false, dataType = "Long" ,paramType="path"),
    })
    @GetMapping("/listPageRoles/pageNo={pageNo}&pageSize={pageSize}")
    public ResponseResult listPageRoles(@PathVariable(value = "pageNo") Integer pageNo, @PathVariable(value = "pageSize",required = false) Integer pageSize){
        return tbRolesService.listPageRoles(pageNo,pageSize);
    }

    @ApiOperation(value = "获取角色列表", notes = "获取角色列表")
    @GetMapping("/listRoles")
    public ResponseResult listRoles(){
        return tbRolesService.listRoles();
    }

    @ApiOperation(value = "删除角色",notes = "删除角色(只需要roleId)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "role", value = "角色 ", required = true, dataType = "Roles"),
    })
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public ResponseResult removeTbRoles(@RequestBody Roles role ) {
         
        return tbRolesService.removeTbRoles(role);
    }

    @ApiOperation(value = "修改角色",notes = "修改角色")
    @ApiImplicitParam(name = "tbRoles", value = "角色", required = true, dataType = "Roles")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseResult updateTbRoles(@RequestBody Roles tbRoles) {
         
        return tbRolesService.updateTbRoles(tbRoles);
    }

    @ApiOperation(value = "新增角色",notes = "新增角色")
    @ApiImplicitParam(name = "role", value = "角色", required = true, dataType = "Roles")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseResult addTbRoles(@RequestBody Roles role) {
         
        return tbRolesService.addTbRoles(role);
    }
}

