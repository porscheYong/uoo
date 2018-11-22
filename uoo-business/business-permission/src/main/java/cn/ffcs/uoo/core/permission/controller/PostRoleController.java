package cn.ffcs.uoo.core.permission.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.core.permission.consts.StatusCD;
import cn.ffcs.uoo.core.permission.entity.PostRole;
import cn.ffcs.uoo.core.permission.entity.Roles;
import cn.ffcs.uoo.core.permission.entity.UserRole;
import cn.ffcs.uoo.core.permission.service.IPostRoleService;
import cn.ffcs.uoo.core.permission.vo.BatchAddRoleUserVO;
import cn.ffcs.uoo.core.permission.vo.ResponseResult;
import cn.ffcs.uoo.core.permission.vo.UserPersonnelVo;
import cn.ffcs.uoo.core.permission.vo.UserTypeVO;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 记录系统职位授予的角色关系，一个系统职位可以包含多个角色，一个角色可以分配给多个系统职位。 前端控制器
 * </p>
 *
 * @author linmingxu
 * @since 2018-11-16
 */
@RestController
@RequestMapping("/permission/postRole")
public class PostRoleController {

    @Autowired
    private IPostRoleService postRoleService;

    @ApiOperation(value = "添加职位角色关系", notes = "添加职位角色关系")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long" ,paramType="path"),
    })
    @UooLog(key="addPostRole",value="添加职位角色关系")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseResult addTbUserRole(@RequestBody PostRole postRole) {
        ResponseResult responseResult = new ResponseResult();
        Long roleId = postRole.getRoleId();
        Long postId = postRole.getPostId();
        if(roleId==null){
            return ResponseResult.createErrorResult("角色不能为空");
        }
        if(postId==null){
            return ResponseResult.createErrorResult("职位不能为空");
        }
        postRole.setCreateDate(new Date());
        postRole.setStatusCd(StatusCD.VALID);
        postRole.setEffDate(new Date());
        postRole.setStatusDate(new Date());

        postRole.setPostRoleId(postRoleService.getId());
        postRoleService.insert(postRole);
        responseResult.setState(ResponseResult.STATE_OK);
        responseResult.setMessage("新增成功");
        return responseResult;
    }

    @ApiOperation(value = "删除职位角色关系", notes = "删除职位角色关系")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "postRoleId", value = "职位角色标识", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "updateStaff", value = "修改人", required = true, dataType = "Long")
    })
    @Transactional
    @UooLog(value = "删除职位角色关系", key = "removePostRole")
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public ResponseResult removeTbUserRole(@RequestBody PostRole postRole) {
        ResponseResult responseResult = new ResponseResult();
        Long postRoleId = postRole.getPostRoleId();
        // 校验必填项
        if(postRoleId == null) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("请输入职位角色id");
            return responseResult;
        }

        postRoleService.deleteById(postRoleId);
        responseResult.setState(ResponseResult.STATE_OK);
        responseResult.setMessage("删除成功");
        return responseResult;
    }

    @ApiOperation(value = "查询角色职位信息", notes = "查询角色职位信息")
    @UooLog(value = "查询角色职位信息", key = "getPostRole")
    @RequestMapping(value = "/getPostRole", method = RequestMethod.GET)
    public List<PostRole> getPostRole() {
        return postRoleService.getPostRole();
    }

}

