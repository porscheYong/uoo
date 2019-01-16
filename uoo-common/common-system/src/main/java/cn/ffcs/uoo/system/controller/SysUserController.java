package cn.ffcs.uoo.system.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import cn.ffcs.uoo.system.entity.SysUserDeptRef;
import cn.ffcs.uoo.system.entity.SysUserPositionRef;
import cn.ffcs.uoo.system.service.SysUserDeptRefService;
import cn.ffcs.uoo.system.service.SysUserPositionRefService;
import cn.ffcs.uoo.system.util.StrUtil;
import cn.ffcs.uoo.system.vo.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;

import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.base.common.tool.util.StringUtils;
import cn.ffcs.uoo.base.controller.BaseController;
import cn.ffcs.uoo.system.consts.StatusCD;
import cn.ffcs.uoo.system.entity.SysUser;
import cn.ffcs.uoo.system.service.SysUserService;
import cn.ffcs.uoo.system.service.impl.SysUserServiceImpl;
import cn.ffcs.uoo.system.util.MD5Util;
import cn.ffcs.uoo.system.util.ResponseResultBean;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 系统域用户前端控制器
 * Created by liuxiaodong on 2018/11/13.
 */
@RestController
@RequestMapping(value = "/system")
public class SysUserController extends BaseController {

    @Autowired
    private SysUserPositionRefService sysUserPositionRefService;
    @Autowired
    private SysUserDeptRefService sysUserDeptRefService;
    @Autowired
    private SysUserService sysUserService;

    @RequestMapping(value = "/updateLoginInfo", method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult<Void> updateLoginInfo(@RequestBody SysUser sysUser){
        sysUserService.updateById(sysUser);
        return ResponseResult.createSuccessResult("");
    }
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
     * @return 返回状态信息
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
    /**
     * 方法只可用来做登陆  不可用作他途
     * @param sysUser
     * @return
     */
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
    public ResponseResultBean<Map<String, Object>> test(String pwd, String salt) {
        ResponseResultBean<Map<String, Object>> result = new ResponseResultBean<>();
        String content = DigestUtils.md5Hex(pwd);
        String saltContent = MD5Util.md5Encoding(content, salt);
        Map<String, Object> map = new HashMap<>();
        map.put("content", content);
        map.put("saltContent", saltContent);
        result.setState(ResponseResultBean.STATE_OK);
        result.setMessage("已获取md5加密密文");
        result.setData(map);
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

        return cn.ffcs.uoo.system.vo.ResponseResult.createSuccessResult(page, "");
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
        sysUserDeptRefService.delUserDeptDefByUserCode(sysUser.getUserCode(), sysUser.getUpdateUser());
        sysUserPositionRefService.delUserPositionDefByUserCode(sysUser.getUserCode(), sysUser.getUpdateUser());
        return cn.ffcs.uoo.system.vo.ResponseResult.createSuccessResult("success");
    }


    @ApiOperation(value = "新增用户组织", notes = "新增用户组织")
    @ApiImplicitParam(name = "sysUserDeptRefVo", value = "用户组织信息", required = true, dataType = "EditSysUserDeptRefVo")
    @UooLog(value = "新增用户组织", key = "sysUserDeptRef")
    @Transactional
    @RequestMapping(value = "/addsysUserDeptRef", method = RequestMethod.POST)
    public Object addsysUserDeptRef(@RequestBody EditSysUserDeptRefVo sysUserDeptRefVo){
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(sysUserDeptRefVo, sysUser);
        //校验
        String msg = sysUserService.checkAllRegister(sysUser);
        if(!StrUtil.isNullOrEmpty(msg)){
            return ResponseResult.createErrorResult(msg);
        }
        Long userId = sysUserService.getId();
        String userCode = "S" + StrUtil.padLeading(String.valueOf(userId), 8 , "0");
        sysUser.setUserId(userId);
        sysUser.setUserCode(userCode);
        Long createUser = sysUser.getCreateUser();
        Long updateUser = sysUser.getUpdateUser();
        sysUserService.addOrUpdateUser(sysUser);

        List<SysUserDeptPositionVo> sysUserDeptPositionVoList = sysUserDeptRefVo.getSysUserDeptPositionVos();
        if(sysUserDeptPositionVoList != null && sysUserDeptPositionVoList.size() > 0){
            for(SysUserDeptPositionVo positionVo: sysUserDeptPositionVoList){
                String orgCodes = "";
                if(orgCodes.contains(positionVo.getOrgCode())){
                    return ResponseResult.createErrorResult("重复选择组织");
                }
                orgCodes = orgCodes + "," + positionVo.getOrgCode();
            }
            for(SysUserDeptPositionVo positionVo: sysUserDeptPositionVoList){
                SysUserDeptRef sysUserDeptRef = new SysUserDeptRef();
                sysUserDeptRef.setOrgCode(positionVo.getOrgCode());
                sysUserDeptRef.setUserCode(userCode);
                sysUserDeptRef.setCreateUser(createUser);
                sysUserDeptRef.setUpdateUser(updateUser);
                sysUserDeptRefService.addSysUserDeptRef(sysUserDeptRef);
                List<SysUserPositionRefVo> userPositionRefs = positionVo.getUserPositionRefList();
                for(SysUserPositionRefVo userPositionRefVo : userPositionRefs){
                    SysUserPositionRef userPositionRef = new SysUserPositionRef();
                    BeanUtils.copyProperties(userPositionRefVo, userPositionRef);
                    userPositionRef.setUserCode(userCode);
                    userPositionRef.setCreateUser(createUser);
                    userPositionRef.setUpdateUser(updateUser);
                    sysUserPositionRefService.addSysUserPositionRef(userPositionRef);
                }
            }
        }

        return ResponseResult.createSuccessResult("");
    }

    @ApiOperation(value = "更新用户信息", notes = "更新用户信息")
    @ApiImplicitParam(name = "sysUser", value = "用户信息", required = true, dataType = "SysUser")
    @UooLog(value = "更新用户信息", key = "updateSysUser")
    @Transactional
    @RequestMapping(value = "/updateSysUser", method = RequestMethod.POST)
    public Object updateSysUser(@RequestBody SysUser sysUser){
        //校验
        String msg = sysUserService.checkAllRegister(sysUser);
        if(!StrUtil.isNullOrEmpty(msg)){
            return ResponseResult.createErrorResult(msg);
        }
        sysUserService.addOrUpdateUser(sysUser);
        return ResponseResult.createSuccessResult("");
    }

    @ApiOperation(value = "用户信息查询", notes = "用户信息查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户标识", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "pageNo", value = "当前页数", required = true, dataType = "Integer",paramType="path"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = true, dataType = "Integer",paramType="path"),
    })
    @UooLog(value = "用户信息查询", key = "getSysUserDeptPosition")
    @RequestMapping(value = "/getSysUserDeptPosition", method = RequestMethod.GET)
    public Object getSysUserDeptPosition(Long userId, Integer pageNo, Integer pageSize){
        SysUser sysUser = sysUserService.getSysUserById(userId);
        if(StrUtil.isNullOrEmpty(sysUser)){
            return ResponseResult.createErrorResult("用户信息不存在");
        }
        SysUserDeptRefVo sysUserDeptRefVo = new SysUserDeptRefVo();
        BeanUtils.copyProperties(sysUser, sysUserDeptRefVo);
        sysUserDeptRefVo.setSysUserDeptPositionVos(sysUserService.getUserDeptPosition(sysUser.getUserCode(), pageNo, pageSize));

        return ResponseResult.createSuccessResult(sysUserDeptRefVo, "");
    }


}
