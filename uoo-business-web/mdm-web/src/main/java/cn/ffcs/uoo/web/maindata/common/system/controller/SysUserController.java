package cn.ffcs.uoo.web.maindata.common.system.controller;


import java.util.Date;
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

import cn.ffcs.uoo.web.maindata.common.system.client.SysLoginLogClient;
import cn.ffcs.uoo.web.maindata.common.system.client.SysUserClient;
import cn.ffcs.uoo.web.maindata.common.system.dto.AlterPwdDTO;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysLoginLog;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysUser;
import cn.ffcs.uoo.web.maindata.common.system.utils.IPUtils;
import cn.ffcs.uoo.web.maindata.common.system.vo.ResponseResult;
import cn.ffcs.uoo.web.maindata.mdm.consts.LoginConsts;
import cn.ffcs.uoo.web.maindata.mdm.logs.OperateLog;
import cn.ffcs.uoo.web.maindata.mdm.logs.OperateType;
import cn.ffcs.uoo.web.maindata.realm.exception.ServiceException;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/system")
public class SysUserController {
    @Autowired
    SysUserClient sysuserClient;
    @Autowired
    private SysLoginLogClient loginLogClient;
    @Autowired
    ShiroFilterFactoryBean shiroFilterFactoryBean;
    @OperateLog(type=OperateType.SELECT,module="平台系统用户模块",methods="获取当前登陆用户信息",desc="")
    @ApiOperation(value = " 接口", notes = " 接口")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/getCurrentLoginUserInfo", method = RequestMethod.GET)
    public ResponseResult<SysUser> getCurrentLoginUserInfo(){
        Subject subject=SecurityUtils.getSubject();
        SysUser currentLoginUser = (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        ResponseResult<SysUser> r=new ResponseResult<>();
        r.setState(ResponseResult.STATE_OK);
        r.setData(currentLoginUser);
        return r;
    }
    @OperateLog(type=OperateType.UPDATE,module="平台系统用户模块",methods="修改用户密码",desc="")
    @ApiOperation(value = "登陆接口", notes = "登陆接口")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "sysUser", value = "sysUser", required = true, dataType = "SysUser" ),
    })
    @RequestMapping(value = "/alterPwd", method = RequestMethod.POST)
    public ResponseResult<String> alterPwd(AlterPwdDTO alterPwdDTO){
        Subject sub=SecurityUtils.getSubject();
        Object primaryPrincipal = sub.getPrincipals().getPrimaryPrincipal();
        alterPwdDTO.setAccout(primaryPrincipal.toString());
        ResponseResult<String> alterPwd = sysuserClient.alterPwd(alterPwdDTO);
        return alterPwd;
    }
    
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
            //ResponseResult<SysUser> r = sysuserClient.getSysUserByAccout(t);
        } catch (ServiceException e) {
            rr.setMessage("登陆服务异常，请稍后重试");
            rr.setState(1100);
        } catch (AuthenticationException e) {
            rr.setMessage("用户密码错误");
            rr.setState(1100);
        }
        Map<String, String> filterChainDefinitionMap = shiroFilterFactoryBean.getFilterChainDefinitionMap();
        if(filterChainDefinitionMap.isEmpty()){
            rr.setMessage("系统权限未初始化完成，请稍后");
            rr.setState(1100);
        }/*else{
            if(ResponseResult.STATE_OK==rr.getState()){
                Object tbAcct2 = acctService.getTbAcct(sysUser.getAccout());
                JSONObject json=JSONObject.parseObject(JSONObject.toJSONString(tbAcct2));
                if(json.getInteger("state")  ==1000){
                    request.getSession().setAttribute(LoginConsts.LOGIN_KEY,tbAcct2);
                }else{
                    rr.setState(ResponseResult.STATE_ERROR);
                    rr.setMessage(json.getString("message"));
                }`
            }
            
        }*/
        SysLoginLog sysLoginLog=new SysLoginLog();
        sysLoginLog.setLogName("用户登录");
        sysLoginLog.setIp(IPUtils.string2Long(subject.getSession().getHost()));
        sysLoginLog.setSucceed(rr.getState()==ResponseResult.STATE_OK?1L:0L);
        sysLoginLog.setAccout(sysUser.getAccout());
        loginLogClient.add(sysLoginLog);
        return rr;
    }
}
