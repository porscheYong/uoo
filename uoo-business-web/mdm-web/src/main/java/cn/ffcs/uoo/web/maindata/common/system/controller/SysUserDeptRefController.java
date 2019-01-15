package cn.ffcs.uoo.web.maindata.common.system.controller;

import cn.ffcs.uoo.web.maindata.common.system.client.SysUserDeptRefClient;
import cn.ffcs.uoo.web.maindata.common.system.vo.SysUserDeptPositionVo;
import cn.ffcs.uoo.web.maindata.mdm.logs.OperateLog;
import cn.ffcs.uoo.web.maindata.mdm.logs.OperateType;
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
    @ApiImplicitParam(name = "userDeptPositionVo", value = "新增用户组织职位", required = true, dataType = "SysUserDeptPositionVo")
    @RequestMapping(value = "/addUserDeptPositionDef", method = RequestMethod.POST)
    @OperateLog(type= OperateType.ADD, module="平台用户",methods="addUserDeptPositionDef",desc="新增用户组织职位")
    public Object addUserDeptPositionDef(@RequestBody SysUserDeptPositionVo userDeptPositionVo){
        userDeptPositionVo.setCreateUser(SysUserInfo.getUserId());
        userDeptPositionVo.setUpdateUser(SysUserInfo.getUserId());
        return sysUserDeptRefClient.addUserDeptPositionDef(userDeptPositionVo);
    }

    @ApiOperation(value = "更新用户组织职位", notes = "更新用户组织职位")
    @ApiImplicitParam(name = "userDeptPositionVo", value = "更新用户组织职位", required = true, dataType = "SysUserDeptPositionVo")
    @RequestMapping(value = "/updateUserDeptPositionDef", method = RequestMethod.POST)
    @OperateLog(type= OperateType.UPDATE, module="平台用户",methods="updateUserDeptPositionDef",desc="更新用户组织职位")
    public Object updateUserDeptPositionDef(@RequestBody SysUserDeptPositionVo userDeptPositionVo){
        userDeptPositionVo.setUpdateUser(SysUserInfo.getUserId());
        return sysUserDeptRefClient.updateUserDeptPositionDef(userDeptPositionVo);
    }

    @ApiOperation(value = "删除用户组织职位", notes = "删除用户组织职位")
    @ApiImplicitParam(name = "userDeptPositionVo", value = "删除用户组织职位", required = true, dataType = "SysUserDeptPositionVo")
    @RequestMapping(value = "/delUserDeptPositionDef", method = RequestMethod.DELETE)
    @OperateLog(type= OperateType.DELETE, module="平台用户",methods="delUserDeptPositionDef",desc="删除用户组织职位")
    public Object delUserDeptPositionDef(@RequestBody SysUserDeptPositionVo userDeptPositionVo){
        userDeptPositionVo.setUpdateUser(SysUserInfo.getUserId());
        return sysUserDeptRefClient.delUserDeptPositionDef(userDeptPositionVo);
    }

}
