package cn.ffcs.uoo.web.maindata.common.system.controller;


import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.ffcs.uoo.web.maindata.common.system.vo.EditSysUserDeptRefVo;
import cn.ffcs.uoo.web.maindata.common.system.vo.SysUserDeptRefVo;
import cn.ffcs.uoo.web.maindata.personnel.utils.SysUserInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

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
import cn.ffcs.uoo.web.maindata.realm.exception.AccoutLockedException;
import cn.ffcs.uoo.web.maindata.realm.exception.ServiceException;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/system")
public class SysUserController {
    @Resource
    SysUserClient sysuserClient;
    @Resource
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
        String accout = sysUser.getAccout();
        String mobile = sysUser.getMobile();
        String email = sysUser.getEmail();
        if(StringUtils.isNotBlank(email)){
            accout=email;
        }
        if(StringUtils.isNotBlank(mobile)){
            accout=mobile;
        }
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
                accout,
                sysUser.getPasswd());
        //进行验证，这里可以捕获异常，然后返回对应信息
        Map<String, String> filterChainDefinitionMap = shiroFilterFactoryBean.getFilterChainDefinitionMap();
        if(filterChainDefinitionMap.isEmpty()){
            rr.setMessage("系统权限未初始化完成，请稍后");
            rr.setState(1100);
        }else{
            try {
                subject.login(usernamePasswordToken);
                rr.setMessage("登陆成功");
                rr.setState(1000);
                //ResponseResult<SysUser> r = sysuserClient.getSysUserByAccout(t);
            } catch (ServiceException e) {
                rr.setMessage("登陆服务异常，请稍后重试");
                rr.setState(1100);
            } catch (AccoutLockedException e) {
                rr.setMessage("账号被锁定，请稍后重试");
                rr.setState(1100);
            } catch (AuthenticationException e) {
                rr.setMessage("用户密码错误");
                rr.setState(1100);
            }
        }
        SysLoginLog sysLoginLog=new SysLoginLog();
        sysLoginLog.setLogName("用户登录");
        sysLoginLog.setIp(subject.getSession().getHost());
        sysLoginLog.setSucceed(rr.getState()==ResponseResult.STATE_OK?1L:0L);
        sysLoginLog.setAccout(accout);
        String jsonString = JSONObject.toJSONString(rr);
        sysLoginLog.setNotes(jsonString!=null?jsonString.length()>=200?jsonString.substring(0, 200):jsonString:"");
        sysLoginLog.setMessage(JSONObject.toJSONString(sysUser));
        loginLogClient.add(sysLoginLog);
        return rr;
    }

    @ApiOperation(value = "新增用户组织", notes = "新增用户组织")
    @ApiImplicitParam(name = "sysUserDeptRefVo", value = "用户组织信息", required = true, dataType = "EditSysUserDeptRefVo")
    @RequestMapping(value = "/addsysUserDeptRef", method = RequestMethod.POST)
    @OperateLog(type= OperateType.ADD, module="平台用户",methods="addsysUserDeptRef",desc="新增用户组织")
    public Object addsysUserDeptRef(@RequestBody EditSysUserDeptRefVo sysUserDeptRefVo){
        sysUserDeptRefVo.setCreateUser(SysUserInfo.getUserId());
        sysUserDeptRefVo.setUpdateUser(SysUserInfo.getUserId());
        return sysuserClient.addsysUserDeptRef(sysUserDeptRefVo);
    }

    @ApiOperation(value = "更新用户信息", notes = "更新用户信息")
    @ApiImplicitParam(name = "sysUser", value = "用户信息", required = true, dataType = "SysUser")
    @RequestMapping(value = "/updateSysUser", method = RequestMethod.POST)
    @OperateLog(type= OperateType.UPDATE, module="平台用户",methods="updateSysUser",desc="更新用户信息")
    public Object updateSysUser(@RequestBody SysUser sysUser){
        sysUser.setUpdateUser(SysUserInfo.getUserId());
        return sysuserClient.updateSysUser(sysUser);
    }

    @ApiOperation(value = "用户信息查询", notes = "用户信息查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户标识", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "pageNo", value = "当前页数", required = true, dataType = "Integer",paramType="path"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = true, dataType = "Integer",paramType="path"),
    })
    @RequestMapping(value = "/getSysUserDeptPosition", method = RequestMethod.GET)
    @OperateLog(type= OperateType.SELECT, module="平台用户",methods="getSysUserDeptPosition",desc="用户信息查询")
    public ResponseResult<SysUserDeptRefVo> getSysUserDeptPosition(Long userId, Integer pageNo, Integer pageSize){
        return sysuserClient.getSysUserDeptPosition(userId, pageNo, pageSize);
    }

    @ApiOperation(value = "删除用户", notes = "删除用户")
    @ApiImplicitParam(name = "sysUser", value = "sysUser", required = true, dataType = "sysUser")
    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
    @OperateLog(type= OperateType.DELETE, module="平台用户",methods="deleteUser",desc="删除用户")
    public Object deletePrivilege(@RequestBody SysUser sysUser){
        sysUser.setUpdateUser(SysUserInfo.getUserId());
        return sysuserClient.deletePrivilege(sysUser);
    }
}
