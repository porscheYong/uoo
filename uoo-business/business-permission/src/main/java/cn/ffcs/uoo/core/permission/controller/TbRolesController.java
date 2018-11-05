package cn.ffcs.uoo.core.permission.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.base.common.tool.util.StringUtils;
import cn.ffcs.uoo.base.controller.BaseController;
import cn.ffcs.uoo.core.permission.entity.TbRoles;
import cn.ffcs.uoo.core.permission.entity.TbUserRole;
import cn.ffcs.uoo.core.permission.service.TbRolesService;
import cn.ffcs.uoo.core.permission.service.TbUserRoleService;
import cn.ffcs.uoo.core.permission.vo.ResponseResult;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 角色
 * 描述员工的系统权限集合，是用以定义系统使用人员操作权限的实体。 前端控制器
 * </p>
 *
 * @author zhanglu
 * @since 2018-10-24
 */
@Api(description = "角色",value = "Roles")
@RestController
@RequestMapping("/tbRoles")
public class TbRolesController extends BaseController {
    @Autowired
    TbRolesService tbRolesService;
    @Autowired
    TbUserRoleService tbUserRoleService;

    @ApiOperation(value = "删除角色",notes = "删除角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色标识", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "updateStaff", value = "修改人", required = true, dataType = "Long")
    })
    @UooLog(value = "删除角色", key = "removeTbRoles")
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public ResponseResult<TbRoles> removeTbRoles(Long roleId, Long updateStaff) {
        ResponseResult<TbRoles> responseResult = new ResponseResult<TbRoles>();

        // 校验必填项
        if(roleId == null) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("请输入角色id");
            return responseResult;
        }
        if(updateStaff == null) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("请输入修改人");
            return responseResult;
        }

        // 查询出是否存在用户角色关系
        Wrapper<TbUserRole> wrapper = new EntityWrapper<TbUserRole>();
        wrapper.eq("ROLE_ID", roleId);
        // 生效状态
        wrapper.eq("STATUS_CD","1000");
        List<TbUserRole> tbUserRoleList =  tbUserRoleService.selectList(wrapper);

        // 如果存在用户角色关系，不允许删除该角色
        if(tbUserRoleList != null && tbUserRoleList.size() > 0) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("存在用户角色关系，不允许删除该角色");
            return responseResult;
        }

        tbRolesService.remove(roleId, updateStaff);
        responseResult.setState(ResponseResult.STATE_OK);
        responseResult.setMessage("删除成功");
        return responseResult;
    }

    @ApiOperation(value = "修改角色",notes = "修改角色")
    @ApiImplicitParam(name = "tbRoles", value = "角色", required = true, dataType = "TbRoles")
    @UooLog(value = "修改角色", key = "updateTbRoles")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseResult<TbRoles> updateTbRoles(TbRoles tbRoles) {
        ResponseResult<TbRoles> responseResult = new ResponseResult<TbRoles>();
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
    @ApiImplicitParam(name = "tbRoles", value = "角色", required = true, dataType = "TbRoles")
    @UooLog(value = "新增角色", key = "addTbRoles")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseResult<TbRoles> addTbRoles(TbRoles tbRoles) {
        ResponseResult<TbRoles> responseResult = new ResponseResult<TbRoles>();

        // 校验必填项
        if(tbRoles.getRegionId() == null ) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("请输入地区id");
            return responseResult;
        }
        if(tbRoles.getSystemInfoId() == null) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("请输入归属系统id");
            return responseResult;
        }
        if(StringUtils.isEmpty(tbRoles.getStatusCd())) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("请输入角色状态");
            return responseResult;
        }

        tbRolesService.insertSelective(tbRoles);

        responseResult.setState(ResponseResult.STATE_OK);
        responseResult.setMessage("新增角色成功");
        return responseResult;
    }
}

