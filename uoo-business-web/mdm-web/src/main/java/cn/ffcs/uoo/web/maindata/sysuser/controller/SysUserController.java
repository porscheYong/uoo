package cn.ffcs.uoo.web.maindata.sysuser.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.ffcs.uoo.web.maindata.sysuser.client.SysUserClient;
import cn.ffcs.uoo.web.maindata.sysuser.dto.SysUser;
import cn.ffcs.uoo.web.maindata.sysuser.vo.ResponseResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/system")
public class SysUserController {
    @Autowired
    SysUserClient sysuserClient;
    @ApiOperation(value = "登陆接口", notes = "登陆接口")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "sysUser", value = "sysUser", required = true, dataType = "SysUser" ),
    })
    @RequestMapping(value = "/sysUserLogin", method = RequestMethod.POST)
    public ResponseResult<Void> login(SysUser sysUser,HttpServletResponse response) {
        ResponseResult<Void> login = sysuserClient.login(sysUser);
        if(ResponseResult.STATE_OK==login.getState()){
            try {
                response.sendRedirect("/index");
            } catch (IOException e) {
            }
        }
        return login;
    }
}
