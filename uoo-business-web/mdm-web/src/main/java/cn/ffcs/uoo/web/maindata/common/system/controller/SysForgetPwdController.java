package cn.ffcs.uoo.web.maindata.common.system.controller;


import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;import ch.qos.logback.core.net.SyslogConstants;
import cn.ffcs.uoo.web.maindata.common.system.client.SysUserClient;
import cn.ffcs.uoo.web.maindata.common.system.shortmsg.SmSendUccpService;
import cn.ffcs.uoo.web.maindata.common.system.vo.ResponseResult;

@RestController
@RequestMapping("/system/forgetpwd")
public class SysForgetPwdController {
    String sessionCheckKey="smsmsg";
    String sessionTimeEndKey="smstime";
    String sessionPhoneKey="smsphone";
    @Autowired
    SmSendUccpService smSvc;
    @Autowired
    SysUserClient userClient;
    @GetMapping("/sendcheckcode")
    public ResponseResult<Void> sendcheckcode(@RequestParam("phone")String phone){
        //检查号码
        ResponseResult<Void> checkMobile = userClient.checkMobile(phone);
         
        if(checkMobile.getState()!=ResponseResult.STATE_OK){
            return checkMobile;
        }
        int nextInt = RandomUtils.nextInt(100000, 999999);
        nextInt=111111;
        String msg="您正在重置浙江主数据中心平台账号登录密码，验证码："+nextInt+"，请勿泄露给他人";
        try {
            Subject subject = SecurityUtils.getSubject();
            subject.getSession().setAttribute(sessionCheckKey, nextInt);
            subject.getSession().setAttribute(sessionPhoneKey, phone);
            subject.getSession().setAttribute(sessionTimeEndKey, System.currentTimeMillis()+(60000L));
            smSvc.sendSms(phone, msg, SmSendUccpService.UCCP_SCENE_ID_CZ);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.createErrorResult("发送失败");
        }
        return ResponseResult.createSuccessResult("发送成功");
    }
    @GetMapping("/valid")
    public ResponseResult<Void> valid(@RequestParam("phone")String phone,@RequestParam("pwd")String pwd,@RequestParam("checkCode")String checkCode){
       //验证code
        Subject subject = SecurityUtils.getSubject();
        Object object = subject.getSession().getAttribute(sessionCheckKey);
        Object timeobj = subject.getSession().getAttribute(sessionTimeEndKey);
        Object phoneobj = subject.getSession().getAttribute(sessionPhoneKey);
        if(object==null||StringUtils.isBlank(checkCode)||timeobj==null){
            return ResponseResult.createErrorResult("验证码错误");
        }
        if(phoneobj==null || !phone.equalsIgnoreCase(phoneobj.toString())){
            return ResponseResult.createErrorResult( "手机号错误");
        }
        if(!checkCode.equalsIgnoreCase(object.toString())){
            return ResponseResult.createErrorResult( "验证码错误");
        } 
        if(System.currentTimeMillis()>=(Long)timeobj){
            return ResponseResult.createErrorResult( "验证码超时");
        }
        ResponseResult<Void> resetPwd = userClient.resetPwd(phone, pwd);
         
        if(resetPwd.getState()!=ResponseResult.STATE_OK){
            return ResponseResult.createErrorResult("修改密码失败");
        }
        //保存密码
        return ResponseResult.createSuccessResult("修改成功");
    }
}
