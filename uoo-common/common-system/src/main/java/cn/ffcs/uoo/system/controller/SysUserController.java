package cn.ffcs.uoo.system.controller;

import javax.annotation.Resource;

import cn.ffcs.uoo.system.service.impl.SysUserServiceImpl;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.ffcs.uoo.base.common.tool.util.StringUtils;
import cn.ffcs.uoo.base.controller.BaseController;
import cn.ffcs.uoo.system.entity.SysUser;
import cn.ffcs.uoo.system.service.SysUserService;
import cn.ffcs.uoo.system.util.ResponseResult;

import java.util.List;
import java.util.regex.Pattern;

/**
 * 系统域用户前端控制器
 * Created by liuxiaodong on 2018/11/13.
 */
@RestController
@RequestMapping(value = "/system")
public class SysUserController extends BaseController {
    @Resource
    private SysUserService sysUserService;

    /**
     * 用户登录
     * @param sysUser 接收用户信息
     * @return
     */
    @RequestMapping(value = "/sysUserLogin", method = RequestMethod.POST)
    public ResponseResult<SysUser> login(@RequestBody SysUser sysUser) {
        ResponseResult<SysUser> result = new ResponseResult<>();

        String message = sysUserService.sysUserLogin(sysUser);
        if (!StringUtils.isEmpty(message)) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage(message);
            return result;
        }

        Wrapper<SysUser> wrapper = new EntityWrapper<>();
        if (Pattern.matches(SysUserServiceImpl.REGEX_MOBILE, sysUser.getAccout())) {
            wrapper.eq("mobile", sysUser.getAccout());
        } else if (Pattern.matches(SysUserServiceImpl.REGEX_EMAIL, sysUser.getAccout())) {
            wrapper.eq("email", sysUser.getAccout());
        } else {
            wrapper.eq("accout", sysUser.getAccout());
        }
        List<SysUser> userList = sysUserService.selectList(wrapper);
        result.setState(ResponseResult.STATE_OK);
        result.setMessage("登录成功");
        result.setData(userList.get(0));
        return result;

    }

    /** 测试  */
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public ResponseResult<String> test(String pwd) {
        ResponseResult<String> result = new ResponseResult<>();
        String aesContent = DigestUtils.md5Hex(pwd);
        result.setState(ResponseResult.STATE_OK);
        result.setMessage("已获取md5加密密文");
        result.setData(aesContent);
        return result;
    }

    /**
     * 用户注册
     * @param sysUser 接收用户信息
     * @return
     */
    @RequestMapping(value = "/sysUserRegister", method = RequestMethod.POST)
    public ResponseResult<Void> register(@RequestBody SysUser sysUser) {
        ResponseResult<Void> result = new ResponseResult<>();

        String message = sysUserService.checkRegister(sysUser);
        if (!StringUtils.isEmpty(message)) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage(message);
            return result;
        }
        sysUserService.sysUserRegister(sysUser);
        result.setState(ResponseResult.STATE_OK);
        result.setMessage("注册成功");
        return result;
    }
}
