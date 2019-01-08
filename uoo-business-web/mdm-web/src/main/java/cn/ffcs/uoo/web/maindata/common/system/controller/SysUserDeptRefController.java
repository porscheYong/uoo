package cn.ffcs.uoo.web.maindata.common.system.controller;

import cn.ffcs.uoo.web.maindata.common.system.client.SysUserDeptRefClient;
import cn.ffcs.uoo.web.maindata.common.system.vo.SysUserDeptPositionVo;
import cn.ffcs.uoo.web.maindata.personnel.utils.SysUserInfo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/sysUserDeptRef")
public class SysUserDeptRefController {

    @Resource
    private SysUserDeptRefClient sysUserDeptRefClient;

    @ApiOperation(value = "新增用户组织职位", notes = "新增用户组织职位")
    @ApiImplicitParam(name = "sysUserDeptPositionVo", value = "新增用户组织职位", required = true, dataType = "SysUserDeptPositionVo")
    @RequestMapping(value = "/addUserDeptPositionDef", method = RequestMethod.POST)
    public Object addUserDeptPositionDef(@RequestBody SysUserDeptPositionVo userDeptPositionVo){
        userDeptPositionVo.setCreateUser(SysUserInfo.getUserId());
        userDeptPositionVo.setUpdateUser(SysUserInfo.getUserId());
        return sysUserDeptRefClient.addUserDeptPositionDef(userDeptPositionVo);
    }

    @ApiOperation(value = "更新用户组织职位", notes = "更新用户组织职位")
    @ApiImplicitParam(name = "sysUserDeptPositionVo", value = "更新用户组织职位", required = true, dataType = "SysUserDeptPositionVo")
    @RequestMapping(value = "/updateUserDeptPositionDef", method = RequestMethod.POST)
    public Object updateUserDeptPositionDef(@RequestBody SysUserDeptPositionVo userDeptPositionVo){
        userDeptPositionVo.setUpdateUser(SysUserInfo.getUserId());
        return sysUserDeptRefClient.updateUserDeptPositionDef(userDeptPositionVo);
    }

    @ApiOperation(value = "删除用户组织职位", notes = "删除用户组织职位")
    @ApiImplicitParam(name = "sysUserDeptPositionVo", value = "删除用户组织职位", required = true, dataType = "SysUserDeptPositionVo")
    @RequestMapping(value = "/delUserDeptPositionDef", method = RequestMethod.DELETE)
    public Object delUserDeptPositionDef(@RequestBody SysUserDeptPositionVo userDeptPositionVo){
        userDeptPositionVo.setUpdateUser(SysUserInfo.getUserId());
        return sysUserDeptRefClient.delUserDeptPositionDef(userDeptPositionVo);
    }

}
