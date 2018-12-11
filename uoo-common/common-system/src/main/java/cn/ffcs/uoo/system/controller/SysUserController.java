package cn.ffcs.uoo.system.controller;

import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.system.consts.StatusCD;
import cn.ffcs.uoo.system.entity.SysUser;
import cn.ffcs.uoo.system.service.SysUserService;
import cn.ffcs.uoo.system.service.impl.SysUserServiceImpl;
import cn.ffcs.uoo.system.util.MD5Util;
import cn.ffcs.uoo.system.vo.AlterPasswdVo;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import cn.ffcs.uoo.base.common.tool.util.StringUtils;
import cn.ffcs.uoo.base.controller.BaseController;
import cn.ffcs.uoo.system.util.ResponseResultBean;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 系统域用户前端控制器
 * Created by liuxiaodong on 2018/11/13.
 */
@RestController
@RequestMapping(value = "/system")
public class SysUserController extends BaseController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 用户登录
     *
     * @param sysUser 接收用户信息
     * @return
     */
    @RequestMapping(value = "/sysUserLogin", method = RequestMethod.POST)
    public ResponseResultBean<SysUser> login(@RequestBody SysUser sysUser, HttpSession session) {
        ResponseResultBean<SysUser> result = new ResponseResultBean<>();

        String message = sysUserService.sysUserLogin(sysUser);
        if (!StringUtils.isEmpty(message)) {
            result.setState(ResponseResultBean.PARAMETER_ERROR);
            result.setMessage(message);
            return result;
        }

        Wrapper<SysUser> wrapper = new EntityWrapper<>();
        if (Pattern.matches(SysUserServiceImpl.REGEX_MOBILE, sysUser.getAccout())) {
            wrapper.eq("mobile", sysUser.getAccout()).eq("status_cd", "1000");
        } else if (Pattern.matches(SysUserServiceImpl.REGEX_EMAIL, sysUser.getAccout())) {
            wrapper.eq("email", sysUser.getAccout()).eq("status_cd", "1000");
        } else {
            wrapper.eq("accout", sysUser.getAccout()).eq("status_cd", "1000");
        }
        List<SysUser> userList = sysUserService.selectList(wrapper);
        session.setAttribute("accout", userList.get(0).getAccout());
        result.setState(ResponseResultBean.STATE_OK);
        result.setMessage("登录成功");
        result.setData(userList.get(0));
        return result;

    }

    /**
     * 登出
     *
     * @param session session对象
     * @return
     */
    @RequestMapping(value = "/sysUserLogout", method = RequestMethod.POST)
    public ResponseResultBean<Void> logout(HttpSession session) {
        session.invalidate();
        ResponseResultBean<Void> result = new ResponseResultBean<>();
        result.setState(ResponseResultBean.STATE_OK);
        result.setMessage("用户已退出");
        return result;
    }

    /**
     * 修改密码
     * @param alterPasswdVo 接收修改密码参数vo
     * @return
     */
    @RequestMapping(value = "/alterPwd", method = RequestMethod.POST)
    public ResponseResultBean<Void> alterPassword(@RequestBody AlterPasswdVo alterPasswdVo) {
        ResponseResultBean<Void> result = new ResponseResultBean<>();
        if (!alterPasswdVo.getNewPwd().equals(alterPasswdVo.getNewPwd2())
                || alterPasswdVo.getPasswd().equals(alterPasswdVo.getNewPwd())) {
            result.setState(ResponseResultBean.PARAMETER_ERROR);
            result.setMessage("输入的参数有误");
            return result;
        }
        List<SysUser> userList = sysUserService.selectList(
                new EntityWrapper<SysUser>().eq("accout",alterPasswdVo.getAccout()).
                        eq("status_cd", "1000"));
        if (null == userList || userList.size() == 0) {
            result.setState(ResponseResultBean.PARAMETER_ERROR);
            result.setMessage("用户不存在");
            return result;
        }
        if (!MD5Util.verify(alterPasswdVo.getPasswd(), userList.get(0).getSalt(), userList.get(0).getPasswd())) {
            result.setState(ResponseResultBean.PARAMETER_ERROR);
            result.setMessage("旧密码不正确");
            return result;
        }
        String md5Content = MD5Util.md5Encoding(alterPasswdVo.getNewPwd(), userList.get(0).getSalt());
        userList.get(0).setPasswd(md5Content);
        sysUserService.updateById(userList.get(0));
        result.setState(ResponseResultBean.STATE_OK);
        result.setMessage("密码已修改");
        return result;
    }

    @RequestMapping(value = "/getSysUserByAccout", method = RequestMethod.POST)
    public ResponseResultBean<SysUser> getSysUserByAccout(@RequestBody SysUser sysUser) {
        ResponseResultBean<SysUser> result = new ResponseResultBean<>();

        Wrapper<SysUser> wrapper = new EntityWrapper<>();
        if (Pattern.matches(SysUserServiceImpl.REGEX_MOBILE, sysUser.getAccout())) {
            wrapper.eq("mobile", sysUser.getAccout());
        } else if (Pattern.matches(SysUserServiceImpl.REGEX_EMAIL, sysUser.getAccout())) {
            wrapper.eq("email", sysUser.getAccout());
        } else {
            wrapper.eq("accout", sysUser.getAccout());
        }

        SysUser user;
        try {
            user = sysUserService.selectOne(wrapper);
        } catch (Exception e) {
            result.setState(ResponseResultBean.PARAMETER_ERROR);
            result.setMessage("获取多个值");
            return result;
        }
        if (null == user) {
            result.setState(ResponseResultBean.PARAMETER_ERROR);
            result.setMessage("用户名不存在。");
            return result;
        }
        result.setState(ResponseResultBean.STATE_OK);
        result.setMessage("返回成功");
        result.setData(user);
        return result;
    }

    /**
     * 测试
     */
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public ResponseResultBean<String> test(String pwd) {
        ResponseResultBean<String> result = new ResponseResultBean<>();
        String aesContent = DigestUtils.md5Hex(pwd);
        result.setState(ResponseResultBean.STATE_OK);
        result.setMessage("已获取md5加密密文");
        result.setData(aesContent);
        return result;
    }

    /**
     * 用户注册
     *
     * @param sysUser 接收用户信息
     * @return
     */
    @RequestMapping(value = "/sysUserRegister", method = RequestMethod.POST)
    public ResponseResultBean<Void> register(@RequestBody SysUser sysUser) {
        ResponseResultBean<Void> result = new ResponseResultBean<>();

        String message = sysUserService.checkRegister(sysUser);
        if (!StringUtils.isEmpty(message)) {
            result.setState(ResponseResultBean.PARAMETER_ERROR);
            result.setMessage(message);
            return result;
        }
        sysUserService.sysUserRegister(sysUser);
        result.setState(ResponseResultBean.STATE_OK);
        result.setMessage("注册成功");
        return result;
    }

    @ApiOperation(value = "获取单个数据", notes = "获取单个数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long", paramType = "path"),
    })
    @UooLog(key = "getUser", value = "获取单个数据")
    @GetMapping("/getUser/{id}")
    public cn.ffcs.uoo.system.vo.ResponseResult get(@PathVariable(value = "id", required = true) Long id) {
        SysUser User = sysUserService.selectById(id);
        if (User == null) {
            return cn.ffcs.uoo.system.vo.ResponseResult.createErrorResult("无效数据");
        }
        return cn.ffcs.uoo.system.vo.ResponseResult.createSuccessResult(User, "success");
    }

    @ApiOperation(value = "获取分页列表", notes = "获取分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "pageNo", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = false, dataType = "Long", paramType = "path"),
    })
    @UooLog(key = "listPageUsers", value = "获取分页列表")
    @GetMapping("/listPageUsers/pageNo={pageNo}&pageSize={pageSize}")
    public cn.ffcs.uoo.system.vo.ResponseResult listPage(@PathVariable(value = "pageNo") Integer pageNo, @PathVariable(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 0 : pageNo;
        pageSize = pageSize == null ? 20 : pageSize;

        Wrapper<SysUser> wrapper = Condition.create().eq("STATUS_CD", StatusCD.VALID).orderBy("UPDATE_DATE", false);
        Page<SysUser> page = sysUserService.selectPage(new Page<SysUser>(pageNo, pageSize), wrapper);

        return cn.ffcs.uoo.system.vo.ResponseResult.createSuccessResult(page.getRecords(), "", page);
    }

    @ApiOperation(value = "修改", notes = "修改")
    @ApiImplicitParam(name = "sysUser", value = "修改", required = true, dataType = "Roles")
    @UooLog(value = "修改角色", key = "updateUser")
    @Transactional
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public cn.ffcs.uoo.system.vo.ResponseResult update(@RequestBody SysUser sysUser) {
        cn.ffcs.uoo.system.vo.ResponseResult responseResult = new cn.ffcs.uoo.system.vo.ResponseResult();
        // 校验必填项
        if (sysUser.getUserId() == null) {
            responseResult.setState(cn.ffcs.uoo.system.vo.ResponseResult.STATE_ERROR);
            responseResult.setMessage("请输入id");
            return responseResult;
        }

        sysUserService.updateById(sysUser);

        responseResult.setState(cn.ffcs.uoo.system.vo.ResponseResult.STATE_OK);
        responseResult.setMessage("修改成功");
        return responseResult;
    }

    @ApiOperation(value = "新增", notes = "新增")
    @ApiImplicitParam(name = "sysUser", value = "新增", required = true, dataType = "SysUser")
    @UooLog(value = "新增", key = "add")
    @Transactional
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public cn.ffcs.uoo.system.vo.ResponseResult add(@RequestBody SysUser sysUser) {
        cn.ffcs.uoo.system.vo.ResponseResult responseResult = new cn.ffcs.uoo.system.vo.ResponseResult();

        sysUser.setCreateDate(new Date());
        sysUser.setUserId((sysUserService.getId()));
        sysUser.setStatusCd(StatusCD.VALID);
        sysUser.setStatusDate(new Date());
        sysUserService.insert(sysUser);
        responseResult.setState(cn.ffcs.uoo.system.vo.ResponseResult.STATE_OK);
        responseResult.setMessage("新增成功");
        return responseResult;
    }

    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysUser", value = "sysUser", required = true, dataType = "sysUser"),
    })
    @UooLog(key = "delete=", value = "删除")
    @SuppressWarnings("unchecked")
    @Transactional
    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
    public cn.ffcs.uoo.system.vo.ResponseResult deletePrivilege(@RequestBody SysUser sysUser) {
        Long UserId = sysUser.getUserId();
        if (UserId == null) {
            return cn.ffcs.uoo.system.vo.ResponseResult.createErrorResult("无效数据");
        }
        SysUser obj = sysUserService.selectById(UserId);
        if (obj == null) {
            return cn.ffcs.uoo.system.vo.ResponseResult.createErrorResult("不能删除无效数据");
        }

        obj.setStatusCd(StatusCD.INVALID);
        obj.setStatusDate(new Date());
        obj.setUpdateDate(new Date());
        sysUserService.updateById(obj);
        return cn.ffcs.uoo.system.vo.ResponseResult.createSuccessResult("success");
    }
}
