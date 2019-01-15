package cn.ffcs.uoo.web.maindata.user.controller;

import cn.ffcs.uoo.web.maindata.mdm.logs.OperateLog;
import cn.ffcs.uoo.web.maindata.mdm.logs.OperateType;
import cn.ffcs.uoo.web.maindata.personnel.utils.SysUserInfo;
import cn.ffcs.uoo.web.maindata.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName UserController
 * @Description
 * @author wudj
 * @date 2018/11/14 14:28
 * @Version 1.0.0
 */
@RestController
@RequestMapping(value = "/user", produces = {"application/json;charset=UTF-8"})
@Api(value = "/user", description = "用户相关操作")
public class UserController {

    @Resource
    private UserService userService;

    @ApiOperation(value = "选择用户信息", notes = "选择用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "personnelId", value = "人员标识", required = true, dataType = "Long",paramType="path"),
            @ApiImplicitParam(name = "pageNo", value = "当前页数", required = true, dataType = "Integer",paramType="path"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = true, dataType = "Integer",paramType="path")
    })
    @RequestMapping(value = "/getUserList", method = RequestMethod.GET)
    @OperateLog(type= OperateType.SELECT, module="账号管理",methods="getUserList",desc="选择用户信息")
    public Object getUserList(Long personnelId, Integer pageNo, Integer pageSize){
        return userService.getUserList(personnelId , pageNo, pageSize, SysUserInfo.getAccount());
    }

    @ApiOperation(value = "用户信息", notes = "用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "acctId", value = "主(从)账号标识", required = true, dataType = "Long",paramType="path"),
            @ApiImplicitParam(name = "userType", value = "主(从)类别", required = true, dataType = "String",paramType="path"),
            @ApiImplicitParam(name = "slaveAcctType", value = "应用类型", required = true, dataType = "String",paramType="path")
    })
    @RequestMapping(value = "/getUser", method = RequestMethod.GET)
    @OperateLog(type= OperateType.SELECT, module="账号管理",methods="getUser",desc="用户信息")
    public Object getFormAcct(Long acctId, String userType, String slaveAcctType){
        String acctType = "1";String sAcctType = "2";
        if(acctType.equals(userType)){
            return userService.getFormAcct(acctId, SysUserInfo.getAccount());
        }
        if(sAcctType.equals(userType)){
            return userService.getFormSlaveAcct(acctId);
        }
        return null;
    }

    @ApiOperation(value = "创建用户", notes = "创建用户")
    @ApiImplicitParams ({
            @ApiImplicitParam(name = "userType", value = "用户类型", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "personnelId", value = "人员标识", required = true, dataType = "Long", paramType = "path")
    })
    @RequestMapping(value = "/getPsnUser", method = RequestMethod.GET)
    @OperateLog(type= OperateType.SELECT, module="账号管理",methods="getPsnUser",desc="创建用户")
    public Object addUser(String userType, Long personnelId){
        return userService.addUser(userType, personnelId, SysUserInfo.getAccount());
    }


    @ApiOperation(value = "主账号组织关系",notes = "主账号组织关系")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "personnelId", value = "人员标识", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "resourceObjId", value = "应用系统标识", required = true, dataType = "Long", paramType = "path")
    })
    @RequestMapping(value = "/getAcctOrgByPsnId", method = RequestMethod.GET)
    @OperateLog(type= OperateType.SELECT, module="账号管理",methods="getAcctOrgByPsnId",desc="主账号组织关系")
    public Object getAcctOrgByPsnId(Long personnelId, Long resourceObjId){
        return userService.getAcctOrgByPsnId(personnelId, resourceObjId, SysUserInfo.getAccount());
    }
}
