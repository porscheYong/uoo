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
import cn.ffcs.uoo.base.auth.entity.SysuserLoginLimit;
import cn.ffcs.uoo.base.auth.service.ILoginAccountService;
import cn.ffcs.uoo.base.auth.service.ISysuserLoginLimitService;
import cn.ffcs.uoo.base.auth.vo.ResponseResult;
import cn.ffcs.uoo.base.common.annotion.UooLog;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 系统用户登录设置。同一系统用户可以有多种登录设置信息。 前端控制器
 * </p>
 *
 * @author zengxsh
 * @since 2018-11-01
 */
@RestController
@RequestMapping("/auth/sysuserLoginLimit")
public class SysuserLoginLimitController {
    @Autowired
    private ILoginAccountService loginAccountService;
    @Autowired
    private ISysuserLoginLimitService sysuserLoginLimitService;
    
    @ApiOperation(value = "根据ID获取单条数据", notes = "根据ID获取单条数据")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long",paramType="path"),
    })
    @UooLog(value = "根据ID获取单条数据", key = "getSysuserLoginLimit")
    @GetMapping("getSysuserLoginLimit/id={id}")
    public ResponseResult getSysuserLoginLimit(@PathVariable(value = "id") Long id){
        SysuserLoginLimit obj = sysuserLoginLimitService.selectById(id);
        if(obj==null||!StatusCD.VALID.equals(obj.getStatusCd())){
            return ResponseResult.createErrorResult("无效数据");
        }
        return ResponseResult.createSuccessResult(obj, "");
    }
    
    @ApiOperation(value = "系统用户登录设置列表", notes = "系统用户登录设置列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "pageNo", value = "分页的序号", required = true, dataType = "Integer",paramType="path"),
        @ApiImplicitParam(name = "pageSize", value = "每页的大小", dataType = "Integer",paramType="path",defaultValue = "12")
    })
    @UooLog(value = "系统用户登录设置列表", key = "listSysuserLoginLimit")
    @GetMapping("listSysuserLoginLimit/pageNo={pageNo}&pageSize={pageSize}")
    public ResponseResult listSysuserLoginLimit(@PathVariable(value = "pageNo") Integer pageNo, @PathVariable(value = "pageSize",required = false) Integer pageSize) {
        pageNo = pageNo==null?0:pageNo;
        pageSize = pageSize==null?20:pageSize;
        @SuppressWarnings("unchecked")
        Wrapper<SysuserLoginLimit> wrapper = Condition.create().eq("STATUS_CD",StatusCD.VALID).orderBy("UPDATE_DATE", false);
        Page<SysuserLoginLimit> page = sysuserLoginLimitService.selectPage(new Page<SysuserLoginLimit>(pageNo, pageSize), wrapper);
        ResponseResult result = ResponseResult.createSuccessResult(page.getRecords(), "", page);
        return result;
    }

    @ApiOperation(value = "新增系统用户登录设置", notes = "新增系统用户登录设置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysuserLoginLimit", value = "系统用户登录设置信息", required = true, dataType = "SysuserLoginLimit"), })
    @UooLog(value = "新增系统用户登录设置", key = "addSysuserLoginLimit")
    @PostMapping("addSysuserLoginLimit")
    @Transactional
    public ResponseResult addSysuserLoginLimit(SysuserLoginLimit sysuserLoginLimit) {
        //  数据校验  获取操作者
        Long loginAccountId = sysuserLoginLimit.getLoginAccountId();
        if(loginAccountId==null){
            return ResponseResult.createErrorResult("请输入系统用户标识");
        }
        LoginAccount loginAccount = loginAccountService.selectById(loginAccountId);
        if(loginAccount==null||!StatusCD.VALID.equals(loginAccount.getStatusCd())){
            return ResponseResult.createErrorResult("无效的系统用户标识");
        }
        
        sysuserLoginLimit.setStatusCd(StatusCD.VALID);
        sysuserLoginLimit.setCreateDate(new Date());
        sysuserLoginLimit.setUpdateDate(new Date());
        sysuserLoginLimit.setStatusDate(new Date());
        sysuserLoginLimit.setLoginLimitId(sysuserLoginLimitService.getId().intValue());
        sysuserLoginLimitService.insert(sysuserLoginLimit);
        return ResponseResult.createSuccessResult("success");
    }
    @ApiOperation(value = "修改系统用户登录设置", notes = "修改系统用户登录设置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysuserLoginLimit", value = "系统用户登录设置信息", required = true, dataType = "SysuserLoginLimit"), })
    @UooLog(value = "修改系统用户登录设置", key = "updateSysuserLoginLimit")
    @PostMapping("updateSysuserLoginLimit")
    @Transactional
    public ResponseResult updateSysuserLoginLimit(SysuserLoginLimit sysuserLoginLimit) {
        Integer id = sysuserLoginLimit.getLoginLimitId();
        if(id==null||sysuserLoginLimitService.selectById(id)==null){
            return ResponseResult.createErrorResult("修改数据异常");
        }
        Long loginAccountId = sysuserLoginLimit.getLoginAccountId();
        if(loginAccountId==null){
            return ResponseResult.createErrorResult("请输入系统用户标识");
        }
        LoginAccount loginAccount = loginAccountService.selectById(loginAccountId);
        if(loginAccount==null||!StatusCD.VALID.equals(loginAccount.getStatusCd())){
            return ResponseResult.createErrorResult("无效的系统用户标识");
        }
        sysuserLoginLimit.setUpdateDate(new Date());
        sysuserLoginLimitService.updateById(sysuserLoginLimit);
        return ResponseResult.createSuccessResult("success");
    }
    
    @ApiOperation(value = "删除系统用户登录设置", notes = "删除系统用户登录设置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysuserLoginLimit", value = "系统用户登录设置信息", required = true, dataType = "SysuserLoginLimit"), })
    @UooLog(value = "删除系统用户登录设置", key = "deleteSysuserLoginLimit")
    @PostMapping("deleteSysuserLoginLimit")
    @Transactional(rollbackFor=Exception.class)
    public ResponseResult deleteSysuserLoginLimit(SysuserLoginLimit sysuserLoginLimit) {
        //
        if(sysuserLoginLimit==null||sysuserLoginLimit.getLoginLimitId()==null){
            return ResponseResult.createErrorResult("不能删除空数据");
        }
        SysuserLoginLimit r=new SysuserLoginLimit();
        r.setLoginLimitId(sysuserLoginLimit.getLoginLimitId());
        r.setStatusCd(StatusCD.INVALID);
        r.setUpdateDate(new Date());
        r.setStatusDate(new Date());
        r.setUpdateUser(sysuserLoginLimit.getUpdateUser());
        sysuserLoginLimitService.updateById(r);
        return ResponseResult.createSuccessResult("success");
    }

}

