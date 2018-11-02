package cn.ffcs.uoo.base.auth.controller;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;

import cn.ffcs.uoo.base.auth.consts.StatusCD;
import cn.ffcs.uoo.base.auth.entity.LoginAccount;
import cn.ffcs.uoo.base.auth.service.ILoginAccountService;
import cn.ffcs.uoo.base.auth.vo.ResponseResult;
import cn.ffcs.uoo.base.common.annotion.UooLog;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 记录员工登录系统使用的系统帐户，不同的系统可有不同的系统用户。 前端控制器
 * </p>
 *
 * @author zengxsh
 * @since 2018-11-01
 */
@RestController
@RequestMapping("/auth/loginAccount")
public class LoginAccountController {
    @Autowired
    private ILoginAccountService loginAccountService;
    
    @ApiOperation(value = "系统用户账号列表", notes = "系统用户账号列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "pageNo", value = "分页的序号", required = true, dataType = "Integer",paramType="path"),
        @ApiImplicitParam(name = "pageSize", value = "每页的大小", dataType = "Integer",paramType="path",defaultValue = "12")
    })
    @UooLog(value = "系统用户账号列表", key = "listLoginAccount")
    @GetMapping("listLoginAccount/pageNo={pageNo}&pageSize={pageSize}")
    public ResponseResult listLoginAccount(@PathVariable(value = "pageNo") Integer pageNo, @PathVariable(value = "pageSize",required = false) Integer pageSize) {
        pageNo = pageNo==null?0:pageNo;
        pageSize = pageSize==null?20:pageSize;
        @SuppressWarnings("unchecked")
        Wrapper<LoginAccount> wrapper = Condition.create().eq("STATUS_CD",StatusCD.VALID).orderBy("UPDATE_DATE", false);
        Page<LoginAccount> page = loginAccountService.selectPage(new Page<LoginAccount>(pageNo, pageSize), wrapper);
        ResponseResult result = ResponseResult.createSuccessResult(page.getRecords(), "", pageNo, pageSize);
        return result;
    }

    @ApiOperation(value = "新增系统用户账号", notes = "新增系统用户账号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginAccount", value = "系统用户账号信息", required = true, dataType = "LoginAccount"), })
    @UooLog(value = "新增系统用户账号", key = "addLoginAccount")
    @PostMapping("addLoginAccount")
    @Transactional
    public ResponseResult addLoginAccount(LoginAccount loginAccount) {
        //  数据校验  获取操作者
        loginAccount.setStatusCd(StatusCD.VALID);
        
        loginAccount.setCreateDate(new Date());
        loginAccount.setUpdateDate(new Date());
        loginAccount.setStatusDate(new Date());
        loginAccount.setLoginAccountId(loginAccountService.getId());
        loginAccountService.insert(loginAccount);
        return ResponseResult.createSuccessResult("success");
    }
    @ApiOperation(value = "修改系统用户账号", notes = "修改系统用户账号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginAccount", value = "系统用户账号信息", required = true, dataType = "LoginAccount"), })
    @UooLog(value = "修改系统用户账号", key = "updateLoginAccount")
    @PostMapping("updateLoginAccount")
    @Transactional
    public ResponseResult updateLoginAccount(LoginAccount loginAccount) {
        Long id = loginAccount.getLoginAccountId();
        if(id==null||loginAccountService.selectById(id)==null){
            return ResponseResult.createErrorResult("修改数据异常");
        }
        loginAccount.setUpdateDate(new Date());
        loginAccountService.updateById(loginAccount);
        return ResponseResult.createSuccessResult("success");
    }
    
    @ApiOperation(value = "删除系统用户账号", notes = "删除系统用户账号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginAccount", value = "系统用户账号信息", required = true, dataType = "LoginAccount"), })
    @UooLog(value = "删除系统用户账号", key = "deleteLoginAccount")
    @PostMapping("deleteLoginAccount")
    @Transactional(rollbackFor=Exception.class)
    public ResponseResult deleteLoginAccount(LoginAccount loginAccount) {
        //
        if(loginAccount==null||loginAccount.getLoginAccountId()==null){
            return ResponseResult.createErrorResult("不能删除空数据");
        }
        LoginAccount r=new LoginAccount();
        r.setLoginAccountId(loginAccount.getLoginAccountId());
        r.setStatusCd(StatusCD.INVALID);
        r.setUpdateDate(new Date());
        r.setStatusDate(new Date());
        r.setUpdateUser(loginAccount.getUpdateUser());
        loginAccountService.updateById(r);
        return ResponseResult.createSuccessResult("success");
    }
}

