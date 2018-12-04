package cn.ffcs.uoo.web.maindata.sysuser.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import cn.ffcs.uoo.web.maindata.mdm.consts.LoginConsts;
import cn.ffcs.uoo.web.maindata.sysuser.client.SysUserClient;
import cn.ffcs.uoo.web.maindata.sysuser.dto.SysUser;
import cn.ffcs.uoo.web.maindata.sysuser.vo.ResponseResult;
import cn.ffcs.uoo.web.maindata.user.service.AcctService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/system")
public class SysUserController {
    @Autowired
    SysUserClient sysuserClient;
    @Autowired
    private AcctService acctService;
    @ApiOperation(value = "登陆接口", notes = "登陆接口")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "sysUser", value = "sysUser", required = true, dataType = "SysUser" ),
    })
    @RequestMapping(value = "/sysUserLogin", method = RequestMethod.POST)
    public ResponseResult<SysUser> login(SysUser sysUser,HttpServletRequest request,HttpServletResponse response) {
        ResponseResult<SysUser> login = sysuserClient.login(sysUser);
        if(ResponseResult.STATE_OK==login.getState()){
            Gson gson = new Gson();
            Object tbAcct2 = acctService.getTbAcct(sysUser.getAccout());
            
            JSONObject json=JSONObject.parseObject(JSONObject.toJSONString(tbAcct2));
            if(json.getInteger("state")  ==1000){
                request.getSession().setAttribute(LoginConsts.LOGIN_KEY,tbAcct2);
            }else{
                login.setState(ResponseResult.STATE_ERROR);
                login.setMessage("账号系统异常");
            }
        }
        return login;
    }
}
