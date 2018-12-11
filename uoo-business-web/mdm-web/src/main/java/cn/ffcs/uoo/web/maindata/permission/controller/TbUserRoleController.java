package cn.ffcs.uoo.web.maindata.permission.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;

import cn.ffcs.uoo.web.maindata.permission.dto.UserRole;
import cn.ffcs.uoo.web.maindata.permission.service.UserRoleService;
import cn.ffcs.uoo.web.maindata.permission.vo.BatchAddRoleUserVO;
import cn.ffcs.uoo.web.maindata.permission.vo.ResponseResult;
import cn.ffcs.uoo.web.maindata.permission.vo.UserPersonnelVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 用户角色关系
 * 描述系统用户与角色之间的对应关系，是多对多关系。一个系统用户除了拥有系统岗位所带的角色和权限，也可以拥有多个私有的角色，一个角色可以分配给多个系统用户。 前端控制器
 * </p>
 *
 * @author zhanglu
 * @since 2018-10-24
 */
@RestController
@RequestMapping("/permission/tbUserRole")
public class TbUserRoleController  {
    @Autowired
    UserRoleService userRoleService;

    @ApiOperation(value = "删除用户角色关系", notes = "删除用户角色关系")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userRoleId", value = "用户角色标识", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "updateStaff", value = "修改人", required = true, dataType = "Long")
    })
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public ResponseResult<Void> removeTbUserRole(@RequestBody UserRole userRole) {
        
        return userRoleService.removeTbUserRole(userRole);
    }

    @ApiOperation(value = "新增用户角色关系", notes = "新增用户角色关系")
    @ApiImplicitParam(name = "tbUserRole", value = "用户角色关系", required = true, dataType = "UserRole")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseResult<Void> addTbUserRole(@RequestBody UserRole tbUserRole) {
        return userRoleService.addTbUserRole(tbUserRole);
    }
    
    @ApiOperation(value = "添加角色成员", notes = "添加角色成员")
    @ApiImplicitParam(name = "batchAddRoleUserVO", value = "用户角色关系", required = true, dataType = "BatchAddRoleUserVO")
    @RequestMapping(value = "/addTbUserRoleBatch", method = RequestMethod.POST)
    public ResponseResult<Void> addTbUserRoleBatch(@RequestBody BatchAddRoleUserVO batchAddRoleUserVO) {
         
        return userRoleService.addTbUserRoleBatch(batchAddRoleUserVO);
    }

    @ApiOperation(value = "分页查询人员用户信息", notes = "分页查询人员用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "分页的序号", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "pageSize", value = "每页的大小", dataType = "Integer", paramType = "path", defaultValue = "10"),
            @ApiImplicitParam(name = "roleId", value = "角色id",required = true, dataType = "Long", paramType = "path")
    })
    @RequestMapping(value = "/getPage/{pageNo}/{pageSize}/{roleId}", method = RequestMethod.GET)
    public ResponseResult<List<UserPersonnelVo>> getUserPersonnelVoPage(@PathVariable(value = "pageNo") Integer pageNo,
                                                        @PathVariable(value = "pageSize") Integer pageSize,
                                                        @PathVariable(value = "roleId") Long roleId) {
        return userRoleService.getUserPersonnelVoPage(pageNo, pageSize, roleId);
         
    }
}

