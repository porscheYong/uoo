package cn.ffcs.uoo.web.maindata.sysuser.controller;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

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
    @Autowired
    ShiroFilterFactoryBean shiroFilterFactoryBean;
    @ApiOperation(value = "登陆接口", notes = "登陆接口")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "sysUser", value = "sysUser", required = true, dataType = "SysUser" ),
    })
    @RequestMapping(value = "/sysUserLogin", method = RequestMethod.POST)
    public ResponseResult<String> login(SysUser sysUser,HttpServletRequest request,HttpServletResponse response) {
        ResponseResult<String> rr=new ResponseResult<>();
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
                sysUser.getAccout(),
                sysUser.getPasswd());
        //进行验证，这里可以捕获异常，然后返回对应信息
        try {
            subject.login(usernamePasswordToken);
            rr.setMessage("登陆成功");
            rr.setState(1000);
        } catch (AuthenticationException e) {
            rr.setMessage("用户密码错误");
            rr.setState(1100);
        }
        Map<String, String> filterChainDefinitionMap = shiroFilterFactoryBean.getFilterChainDefinitionMap();
        if(filterChainDefinitionMap.isEmpty()){
            rr.setMessage("系统权限未初始化完成，请稍后");
            rr.setState(1100);
        }else{
            if(ResponseResult.STATE_OK==rr.getState()){
                Object tbAcct2 = acctService.getTbAcct(sysUser.getAccout());
                JSONObject json=JSONObject.parseObject(JSONObject.toJSONString(tbAcct2));
                if(json.getInteger("state")  ==1000){
                    request.getSession().setAttribute(LoginConsts.LOGIN_KEY,tbAcct2);
                }else{
                    rr.setState(ResponseResult.STATE_ERROR);
                    rr.setMessage(json.getString("message"));
                }
            }
            
        }
        
        return rr;
    }
}
