package cn.ffcs.uoo.core.permission.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.base.common.tool.util.StringUtils;
import cn.ffcs.uoo.base.controller.BaseController;
import cn.ffcs.uoo.core.permission.entity.TbUserRole;
import cn.ffcs.uoo.core.permission.service.TbUserRoleService;
import cn.ffcs.uoo.core.permission.vo.ResponseResult;
import cn.ffcs.uoo.core.permission.vo.UserPersonnelVo;
import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/tbUserRole")
public class TbUserRoleController extends BaseController {
    @Autowired
    TbUserRoleService tbUserRoleService;

    @ApiOperation(value = "删除用户角色关系", notes = "删除用户角色关系")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userRoleId", value = "用户角色标识", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "updateStaff", value = "修改人", required = true, dataType = "Long")
    })
    @UooLog(value = "删除用户角色关系", key = "removeTbUserRole")
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public ResponseResult removeTbUserRole(Long userRoleId, Long updateStaff) {
        ResponseResult responseResult = new ResponseResult();

        // 校验必填项
        if(userRoleId == null) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("请输入用户角色id");
            return responseResult;
        }
        if(updateStaff == null) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("请输入修改人");
            return responseResult;
        }

        tbUserRoleService.remove(userRoleId, updateStaff);
        responseResult.setState(ResponseResult.STATE_OK);
        responseResult.setMessage("删除成功");
        return responseResult;
    }

    @ApiOperation(value = "新增用户角色关系", notes = "删除用户角色关系")
    @ApiImplicitParam(name = "tbUserRole", value = "用户角色关系", required = true, dataType = "TbUserRole")
    @UooLog(value = "新增用户角色关系", key = "addTbUserRole")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseResult addTbUserRole(TbUserRole tbUserRole) {
        ResponseResult responseResult = new ResponseResult();

        // 校验必填项
        if(StringUtils.isEmpty(tbUserRole.getStatusCd())) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("请输入用户角色状态");
            return responseResult;
        }

        tbUserRoleService.addTbUserRole(tbUserRole);
        responseResult.setState(ResponseResult.STATE_OK);
        responseResult.setMessage("新增成功");
        return responseResult;
    }

    @ApiOperation(value = "分页查询人员用户信息", notes = "分页查询人员用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "分页的序号", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "pageSize", value = "每页的大小", dataType = "Integer", paramType = "path", defaultValue = "10"),
            @ApiImplicitParam(name = "roleId", value = "权限id",required = true, dataType = "Long", paramType = "path")
    })
    @UooLog(value = "分页查询人员用户信息", key = "getUserPersonnelVoPage")
    @RequestMapping(value = "/getPage/{pageNo}/{pageSize}/{roleId}", method = RequestMethod.GET)
    public Page<UserPersonnelVo> getUserPersonnelVoPage(@PathVariable(value = "pageNo") Integer pageNo,
                                                        @PathVariable(value = "pageSize") Integer pageSize,
                                                        @PathVariable(value = "roleId") Long roleId) {
        pageNo = pageNo == null ? 0 : pageNo;
        pageSize = pageSize == null ? 10 : pageSize;
        if (roleId == null) {
            return null;
        }
        Page<UserPersonnelVo> page = new Page<UserPersonnelVo>(pageNo, pageSize);
        return tbUserRoleService.selectUserPersonnelPage(page, roleId);
    }
}

