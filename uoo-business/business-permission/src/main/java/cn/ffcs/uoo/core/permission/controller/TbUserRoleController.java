package cn.ffcs.uoo.core.permission.controller;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import cn.ffcs.uoo.base.controller.BaseController;
import cn.ffcs.uoo.core.permission.consts.StatusCD;
import cn.ffcs.uoo.core.permission.entity.UserRole;
import cn.ffcs.uoo.core.permission.service.IUserRoleService;
import cn.ffcs.uoo.core.permission.vo.BatchAddRoleUserVO;
import cn.ffcs.uoo.core.permission.vo.ResponseResult;
import cn.ffcs.uoo.core.permission.vo.UserPersonnelVo;
import cn.ffcs.uoo.core.permission.vo.UserTypeVO;
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
@RestController
@RequestMapping("/permission/tbUserRole")
 */
public class TbUserRoleController extends BaseController {
    @Autowired
    IUserRoleService userRoleService;

    @ApiOperation(value = "删除用户角色关系", notes = "删除用户角色关系")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userRoleId", value = "用户角色标识", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "updateStaff", value = "修改人", required = true, dataType = "Long")
    })
    @Transactional
    @UooLog(value = "删除用户角色关系", key = "removeTbUserRole")
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public ResponseResult<Void> removeTbUserRole(@RequestBody UserRole userRole) {
        ResponseResult<Void> responseResult = new ResponseResult<Void>();
        Long userRoleId = userRole.getUserRoleId();
        // 校验必填项
        if(userRoleId == null) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("请输入用户角色id");
            return responseResult;
        }

        userRoleService.deleteById(userRoleId);
        responseResult.setState(ResponseResult.STATE_OK);
        responseResult.setMessage("删除成功");
        return responseResult;
    }

    @ApiOperation(value = "新增用户角色关系", notes = "新增用户角色关系")
    @ApiImplicitParam(name = "tbUserRole", value = "用户角色关系", required = true, dataType = "UserRole")
    @UooLog(value = "新增用户角色关系", key = "addTbUserRole")
    @Transactional
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseResult<Void> addTbUserRole(@RequestBody UserRole tbUserRole) {
        ResponseResult<Void> responseResult = new ResponseResult<Void>();
        Long roleId = tbUserRole.getRoleId();
        if(roleId==null){
            return ResponseResult.createErrorResult("角色不能为空");
        }
        tbUserRole.setCreateDate(new Date());
        tbUserRole.setStatusCd(StatusCD.VALID);
        tbUserRole.setEffDate(new Date());
        tbUserRole.setStatusDate(new Date());
        tbUserRole.setUserRoleId(userRoleService.getId());
        userRoleService.insert(tbUserRole);
        responseResult.setState(ResponseResult.STATE_OK);
        responseResult.setMessage("新增成功");
        return responseResult;
    }
    
    @ApiOperation(value = "添加角色成员", notes = "添加角色成员")
    @ApiImplicitParam(name = "batchAddRoleUserVO", value = "用户角色关系", required = true, dataType = "BatchAddRoleUserVO")
    @UooLog(value = "新增用户角色关系", key = "addTbUserRoleBatch")
    @Transactional(rollbackFor=Exception.class)
    @RequestMapping(value = "/addTbUserRoleBatch", method = RequestMethod.POST)
    public ResponseResult<Void> addTbUserRoleBatch(@RequestBody BatchAddRoleUserVO batchAddRoleUserVO) {
        //先删除之前的关系
        Wrapper<UserRole> wrapper =Condition.create().eq("ROLE_ID", batchAddRoleUserVO.getRoleId());
        userRoleService.delete(wrapper);
        ResponseResult<Void> responseResult = new ResponseResult<Void>();
        List<UserTypeVO> users = batchAddRoleUserVO.getUsers();
        for (UserTypeVO vo : users) {
            UserRole obj= new UserRole();
            obj.setAcctId(vo.getAccountId());
            obj.setAcctType(vo.getAccountType());
            obj.setCreateDate(new Date());
            obj.setCreateUser(batchAddRoleUserVO.getOperateUser());
            obj.setEffDate(new Date());
            obj.setRoleId(batchAddRoleUserVO.getRoleId());
            obj.setStatusCd(StatusCD.VALID);
            obj.setStatusDate(new Date());
            obj.setUserRoleId(userRoleService.getId());
            userRoleService.insert(obj);
        }
        
        responseResult.setState(ResponseResult.STATE_OK);
        responseResult.setMessage("success");
        return responseResult;
    }

    @ApiOperation(value = "分页查询人员用户信息", notes = "分页查询人员用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "分页的序号", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "pageSize", value = "每页的大小", dataType = "Integer", paramType = "path", defaultValue = "10"),
            @ApiImplicitParam(name = "roleId", value = "角色id",required = true, dataType = "Long", paramType = "path")
    })
    @UooLog(value = "分页查询人员用户信息", key = "getUserPersonnelVoPage")
    @RequestMapping(value = "/getPage/{pageNo}/{pageSize}/{roleId}", method = RequestMethod.GET)
    public ResponseResult<List<UserPersonnelVo>> getUserPersonnelVoPage(@PathVariable(value = "pageNo") Integer pageNo,
                                                        @PathVariable(value = "pageSize") Integer pageSize,
                                                        @PathVariable(value = "roleId") Long roleId) {
        pageNo = pageNo == null ? 0 : pageNo;
        pageSize = pageSize == null ? 10 : pageSize;
        if (roleId == null) {
            return null;
        }
        Page<UserPersonnelVo> page = new Page<UserPersonnelVo>(pageNo, pageSize);
        page = userRoleService.selectUserPersonnelPage(page, roleId);
        return ResponseResult.createSuccessResult(page, "");
    }
}

