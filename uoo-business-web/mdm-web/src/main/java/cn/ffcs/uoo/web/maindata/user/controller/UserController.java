package cn.ffcs.uoo.web.maindata.user.controller;

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
    @ApiImplicitParam(name = "personnelId", value = "人员标识", required = true, dataType = "Long",paramType="path")
    @RequestMapping(value = "/getUserList", method = RequestMethod.GET)
    public Object getUserList(Long personnelId){
        return userService.getUserList(personnelId);
    }

    @ApiOperation(value = "用户信息", notes = "用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "acctId", value = "主(从)账号标识", required = true, dataType = "Long",paramType="path"),
            @ApiImplicitParam(name = "userType", value = "主(从)类别", required = true, dataType = "String",paramType="path"),
            @ApiImplicitParam(name = "slaveAcctType", value = "应用类型", required = true, dataType = "String",paramType="path")
    })
    @RequestMapping(value = "/getUser", method = RequestMethod.GET)
    public Object getFormAcct(Long acctId, String userType, String slaveAcctType){
        if("1".equals(userType)){
            return userService.getFormAcct(acctId);
        }
        if("2".equals(userType)){
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
    public Object addUser(String userType, Long personnelId){
        return userService.addUser(userType, personnelId);
    }

    @ApiOperation(value = "主账号组织关系",notes = "主账号组织关系")
    @ApiImplicitParam(name = "personnelId", value = "人员号标识", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value = "/getAcctOrgByPsnId", method = RequestMethod.GET)
    public Object getAcctOrgByPsnId(Long personnelId){
        return userService.getAcctOrgByPsnId(personnelId);
    }
}
