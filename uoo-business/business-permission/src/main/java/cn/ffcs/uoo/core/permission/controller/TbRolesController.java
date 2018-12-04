package cn.ffcs.uoo.core.permission.controller;


import java.util.Date;
import java.util.List;

import cn.ffcs.uoo.core.permission.entity.*;
import cn.ffcs.uoo.core.permission.vo.RoleSystemPermissionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;

import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.base.controller.BaseController;
import cn.ffcs.uoo.core.permission.consts.StatusCD;
import cn.ffcs.uoo.core.permission.service.IPostRoleService;
import cn.ffcs.uoo.core.permission.service.IRolesService;
import cn.ffcs.uoo.core.permission.service.IUserRoleService;
import cn.ffcs.uoo.core.permission.vo.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 角色
 * 描述员工的系统权限集合，是用以定义系统使用人员操作权限的实体。 前端控制器
 * </p>
 *
 * @author zxs
 * @since 2018-10-24
 */
//@Api(description = "Roles",value = "Roles")
@RestController
@RequestMapping("/permission/tbRoles")
public class TbRolesController extends BaseController {
    @Autowired
    IRolesService tbRolesService;
    @Autowired
    IUserRoleService tbUserRoleService;
    @Autowired
    IPostRoleService postRoleService;
    
    @ApiOperation(value = "获取单个数据", notes = "获取单个数据")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long" ,paramType="path"),
    })
    @UooLog(key="getRoles",value="获取单个角色数据")
    @GetMapping("/get/{id}")
    public ResponseResult get(@PathVariable(value="id" ,required=true) Long id){
        Roles role = tbRolesService.selectById(id);
        if(role== null || !StatusCD.VALID.equals(role.getStatusCd())){
            return ResponseResult.createErrorResult("无效数据");
        }
        return ResponseResult.createSuccessResult(role, "success");
    }
    
    @ApiOperation(value = "获取分页角色列表", notes = "获取分页角色列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "pageNo", value = "pageNo", required = true, dataType = "Long" ,paramType="path"),
        @ApiImplicitParam(name = "pageSize", value = "pageSize", required = false, dataType = "Long" ,paramType="path"),
    })
    @UooLog(key="listPageRoles",value="获取分页角色列表")
    @GetMapping("/listPageRoles/pageNo={pageNo}&pageSize={pageSize}")
    public ResponseResult listPageRoles(@PathVariable(value = "pageNo") Integer pageNo, @PathVariable(value = "pageSize",required = false) Integer pageSize){
        pageNo = pageNo==null?0:pageNo;
        pageSize = pageSize==null?20:pageSize;
        
        Wrapper<Roles> wrapper = Condition.create().eq("STATUS_CD",StatusCD.VALID).orderBy("UPDATE_DATE", false);
        Page<Roles> page = tbRolesService.selectPage(new Page<Roles>(pageNo, pageSize), wrapper);
        
        return ResponseResult.createSuccessResult(page.getRecords(), "", page);
    }

    @ApiOperation(value = "获取角色列表", notes = "获取角色列表")
    @UooLog(key="listRoles",value="获取角色列表")
    @GetMapping("/listRoles")
    public ResponseResult listRoles(){
        Wrapper<Roles> wrapper = Condition.create().eq("STATUS_CD",StatusCD.VALID).orderBy("UPDATE_DATE", false);
        List<Roles> list = tbRolesService.selectList(wrapper);
        return ResponseResult.createSuccessResult(list, "");
    }

    @ApiOperation(value = "删除角色",notes = "删除角色(只需要roleId)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "role", value = "角色 ", required = true, dataType = "Roles"),
    })
    @UooLog(value = "删除角色", key = "removeTbRoles")
    @Transactional
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public ResponseResult removeTbRoles(@RequestBody Roles role ) {
        ResponseResult responseResult = new ResponseResult();
        Long roleId = role.getRoleId();
        // 校验必填项
        if(roleId == null) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("请输入角色id");
            return responseResult;
        }

        // 查询出是否存在用户角色关系
        Wrapper<UserRole> wrapper = new EntityWrapper<UserRole>();
        wrapper.eq("ROLE_ID", roleId);
        // 生效状态
        wrapper.eq("STATUS_CD","1000");
        List<UserRole> tbUserRoleList =  tbUserRoleService.selectList(wrapper);
        // 如果存在用户角色关系，不允许删除该角色
        if(tbUserRoleList != null && tbUserRoleList.size() > 0) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("存在用户角色关系，不允许删除该角色");
            return responseResult;
        }

        //职位角色
        Wrapper<PostRole> w = new EntityWrapper<PostRole>();
        w.eq("ROLE_ID", roleId);
        // 生效状态
        w.eq("STATUS_CD","1000");
        List<PostRole> selectList = postRoleService.selectList(w);
        if(selectList != null && selectList.size() > 0) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("存在职位角色关系，不允许删除该角色");
            return responseResult;
        }
        
        Roles del=new Roles();
        del.setRoleId(roleId);
        del.setStatusCd(StatusCD.INVALID);
        del.setStatusDate(new Date());
        del.setUpdateDate(new Date());
        del.setUpdateUser(role.getUpdateUser());
        
        tbRolesService.updateById(del);
        responseResult.setState(ResponseResult.STATE_OK);
        responseResult.setMessage("删除成功");
        return responseResult;
    }

    @ApiOperation(value = "修改角色",notes = "修改角色")
    @ApiImplicitParam(name = "tbRoles", value = "角色", required = true, dataType = "Roles")
    @UooLog(value = "修改角色", key = "updateTbRoles")
    @Transactional
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseResult updateTbRoles(@RequestBody Roles tbRoles) {
        ResponseResult responseResult = new ResponseResult();
        // 校验必填项
        if(tbRoles.getRoleId() == null) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("请输入角色id");
            return responseResult;
        }

        tbRolesService.updateById(tbRoles);

        responseResult.setState(ResponseResult.STATE_OK);
        responseResult.setMessage("修改角色成功");
        return responseResult;
    }

    @ApiOperation(value = "新增角色",notes = "新增角色")
    @ApiImplicitParam(name = "role", value = "角色", required = true, dataType = "Roles")
    @UooLog(value = "新增角色", key = "addTbRoles")
    @Transactional
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseResult addTbRoles(@RequestBody Roles role) {
        ResponseResult responseResult = new ResponseResult();

        // 校验必填项
        if(role.getRegionId() == null ) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("请输入地区id");
            return responseResult;
        }
        if(role.getSystemInfoId() == null) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("请输入归属系统id");
            return responseResult;
        }
        /*if(StringUtils.isEmpty(role.getStatusCd())) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("请输入角色状态");
            return responseResult;
        }*/
        
        
        role.setCreateDate(new Date());
        role.setRoleId(tbRolesService.getId());
        role.setStatusCd(StatusCD.VALID);
        role.setStatusDate(new Date());
        tbRolesService.insert(role);
        responseResult.setState(ResponseResult.STATE_OK);
        responseResult.setMessage("新增角色成功");
        return responseResult;
    }

    @ApiOperation(value = "根据归属系统和账号查询角色权限",notes = "根据归属系统和账号查询角色权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "acctId", value = "acctId", required = true, dataType = "Long" ,paramType="path"),
            @ApiImplicitParam(name = "systemInfoId", value = "systemInfoId", required = false, dataType = "Long" ,paramType="path"),
    })
    @UooLog(value = "修改角色", key = "updateTbRoles")
    @Transactional
    @RequestMapping(value = "/getRolesPermission/{acctId}/{systemInfoId}", method = RequestMethod.POST)
    public ResponseResult getRolesPermission(@PathVariable(value = "systemInfoId") Long systemInfoId, @PathVariable(value = "acctId") Long acctId){
        ResponseResult responseResult = new ResponseResult();
        //获取根据条件查询的所有角色信息列表acctType,
        List<RoleSystemPermissionVO> rolesVOList = tbRolesService.getRoles(acctId,systemInfoId);
        //遍历角色列表，获取对应的权限信息列表
        for(RoleSystemPermissionVO roleVo : rolesVOList){
            List<Privilege> privilegeList = tbRolesService.getPermission(roleVo.getRoleId());
            roleVo.setPrivileges(privilegeList);
        }
        responseResult.setData(rolesVOList);
        responseResult.setState(1000);
        return responseResult;
    }

    @ApiOperation(value = "根据权限获取菜单",notes = "根据权限获取菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "acctId", value = "acctId", required = true, dataType = "Long" ,paramType="path"),
            @ApiImplicitParam(name = "systemInfoId", value = "systemInfoId", required = false, dataType = "Long" ,paramType="path"),
    })
    @UooLog(value = "修改角色", key = "updateTbRoles")
    @Transactional
    @RequestMapping(value = "/getPermissionMenu/{acctId}/{systemInfoId}", method = RequestMethod.POST)
    public ResponseResult getPermissionMenu(@PathVariable(value = "systemInfoId") Long systemInfoId, @PathVariable(value = "acctId") Long acctId){
        ResponseResult responseResult = new ResponseResult();
        //获取根据条件查询的所有菜单信息列表acctType,
        List<FuncMenu> permissionMenuList = tbRolesService.getPermissionMenu(acctId,systemInfoId);
        responseResult.setData(permissionMenuList);
        responseResult.setState(1000);
        return responseResult;
    }
}

