package cn.ffcs.uoo.base.auth.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;

import cn.ffcs.uoo.base.auth.consts.StatusCD;
import cn.ffcs.uoo.base.auth.entity.LoginAccount;
import cn.ffcs.uoo.base.auth.entity.LoginConfig;
import cn.ffcs.uoo.base.auth.service.ILoginAccountService;
import cn.ffcs.uoo.base.auth.service.ILoginConfigService;
import cn.ffcs.uoo.base.auth.vo.ResponseResult;
import cn.ffcs.uoo.base.common.annotion.UooLog;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 系统用户登录配置表 前端控制器
 * </p>
 *
 * @author zengxsh
 * @since 2018-11-01
 */
@RestController
@RequestMapping("/auth/loginConfig")
public class LoginConfigController {

    @Autowired
    private ILoginConfigService loginConfigService;
    @Autowired
    private ILoginAccountService loginAccountService;
    
    @ApiOperation(value = "系统用户登录配置列表", notes = "系统用户登录配置列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "pageNo", value = "分页的序号", required = true, dataType = "Integer",paramType="path"),
        @ApiImplicitParam(name = "pageSize", value = "每页的大小", dataType = "Integer",paramType="path",defaultValue = "12")
    })
    @UooLog(value = "系统用户登录配置列表", key = "listLoginConfig")
    @GetMapping("listLoginConfig/pageNo={pageNo}&pageSize={pageSize}")
    public ResponseResult listLoginConfig(@PathVariable(value = "pageNo") Integer pageNo, @PathVariable(value = "pageSize",required = false) Integer pageSize) {
        pageNo = pageNo==null?0:pageNo;
        pageSize = pageSize==null?20:pageSize;
        //Wrapper<LoginConfig> wrapper = Condition.create().eq("STATUS_CD",StatusCD.VALID)/*.orderBy("UPDATE_DATE", false)*/;
        Page<LoginConfig> page = loginConfigService.selectPage(new Page<LoginConfig>(pageNo, pageSize)/*, wrapper*/);
        ResponseResult result = ResponseResult.createSuccessResult(page.getRecords(), "", pageNo, pageSize);
        return result;
    }

    @ApiOperation(value = "新增系统用户登录配置", notes = "新增系统用户登录配置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginConfig", value = "系统用户登录配置信息", required = true, dataType = "LoginConfig"), })
    @UooLog(value = "新增系统用户登录配置", key = "addLoginConfig")
    @PostMapping("addLoginConfig")
    @Transactional
    public ResponseResult addLoginConfig(LoginConfig loginConfig) {
        //  数据校验  
        Long loginAccountId = loginConfig.getLoginAccountId();
        if(loginAccountId==null){
            return ResponseResult.createErrorResult("请输入系统用户标识");
        }
        LoginAccount loginAccount = loginAccountService.selectById(loginAccountId);
        if(loginAccount==null||!StatusCD.VALID.equals(loginAccount.getStatusCd())){
            return ResponseResult.createErrorResult("无效的系统用户标识");
        }
        
        /*loginConfig.setStatusCd(StatusCD.VALID);
        
        loginConfig.setCreateDate(new Date());
        loginConfig.setUpdateDate(new Date());
        loginConfig.setStatusDate(new Date());*/
        loginConfig.setLoginConfId(loginConfigService.getId());
        loginConfigService.insert(loginConfig);
        return ResponseResult.createSuccessResult("success");
    }
    @ApiOperation(value = "修改系统用户登录配置", notes = "修改系统用户登录配置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginConfig", value = "系统用户登录配置信息", required = true, dataType = "LoginConfig"), })
    @UooLog(value = "修改系统用户登录配置", key = "updateLoginConfig")
    @PostMapping("updateLoginConfig")
    @Transactional
    public ResponseResult updateLoginConfig(LoginConfig loginConfig) {
        Long id = loginConfig.getLoginConfId();
        if(id==null||loginConfigService.selectById(id)==null){
            return ResponseResult.createErrorResult("修改数据异常");
        }
        Long loginAccountId = loginConfig.getLoginAccountId();
        if(loginAccountId==null){
            return ResponseResult.createErrorResult("请输入系统用户标识");
        }
        LoginAccount loginAccount = loginAccountService.selectById(loginAccountId);
        if(loginAccount==null||!StatusCD.VALID.equals(loginAccount.getStatusCd())){
            return ResponseResult.createErrorResult("无效的系统用户标识");
        }
        loginConfigService.updateById(loginConfig);
        return ResponseResult.createSuccessResult("success");
    }
    
    @ApiOperation(value = "删除系统用户登录配置", notes = "删除系统用户登录配置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginConfig", value = "系统用户登录配置信息", required = true, dataType = "LoginConfig"), })
    @UooLog(value = "删除系统用户登录配置", key = "deleteLoginConfig")
    @PostMapping("deleteLoginConfig")
    @Transactional(rollbackFor=Exception.class)
    public ResponseResult deleteLoginConfig(LoginConfig loginConfig) {
        //
        if(loginConfig==null||loginConfig.getLoginConfId()==null){
            return ResponseResult.createErrorResult("不能删除空数据");
        }
        /*LoginConfig r=new LoginConfig();
        r.setLoginConfId(loginConfig.getLoginConfId());
        loginConfigService.updateById(r);*/
        loginConfigService.deleteById(loginConfig.getLoginConfId());
        return ResponseResult.createSuccessResult("success");
    }

}

