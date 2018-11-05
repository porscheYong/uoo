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
import cn.ffcs.uoo.base.auth.entity.UserLoginHis;
import cn.ffcs.uoo.base.auth.service.ILoginAccountService;
import cn.ffcs.uoo.base.auth.service.IUserLoginHisService;
import cn.ffcs.uoo.base.auth.vo.ResponseResult;
import cn.ffcs.uoo.base.common.annotion.UooLog;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 系统用户登录历史 前端控制器
 * </p>
 *
 * @author zengxsh
 * @since 2018-11-01
 */
@RestController
@RequestMapping("/auth/userLoginHis")
public class UserLoginHisController {
    @Autowired
    private ILoginAccountService loginAccountService;
    @Autowired
    private IUserLoginHisService userLoginHisService;
    
    @ApiOperation(value = "根据ID获取单条数据", notes = "根据ID获取单条数据")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long",paramType="path"),
    })
    @UooLog(value = "根据ID获取单条数据", key = "getUserLoginHis")
    @GetMapping("getUserLoginHis/id={id}")
    public ResponseResult getUserLoginHis(@PathVariable(value = "id") Long id){
        UserLoginHis obj = userLoginHisService.selectById(id);
        if(obj==null||!StatusCD.VALID.equals(obj.getStatusCd())){
            return ResponseResult.createErrorResult("无效数据");
        }
        return ResponseResult.createSuccessResult(obj, "");
    }
    
    @ApiOperation(value = "系统用户登录历史列表", notes = "系统用户登录历史列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "pageNo", value = "分页的序号", required = true, dataType = "Integer",paramType="path"),
        @ApiImplicitParam(name = "pageSize", value = "每页的大小", dataType = "Integer",paramType="path",defaultValue = "12")
    })
    @UooLog(value = "系统用户登录历史列表", key = "listUserLoginHis")
    @GetMapping("listUserLoginHis/pageNo={pageNo}&pageSize={pageSize}")
    public ResponseResult listUserLoginHis(@PathVariable(value = "pageNo") Integer pageNo, @PathVariable(value = "pageSize",required = false) Integer pageSize) {
        pageNo = pageNo==null?0:pageNo;
        pageSize = pageSize==null?20:pageSize;
        @SuppressWarnings("unchecked")
        Wrapper<UserLoginHis> wrapper = Condition.create().eq("STATUS_CD",StatusCD.VALID).orderBy("CREATE_DATE", false);
        Page<UserLoginHis> page = userLoginHisService.selectPage(new Page<UserLoginHis>(pageNo, pageSize), wrapper);
        ResponseResult result = ResponseResult.createSuccessResult(page.getRecords(), "", page);
        return result;
    }

    @ApiOperation(value = "新增系统用户登录历史", notes = "新增系统用户登录历史")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userLoginHis", value = "系统用户登录历史信息", required = true, dataType = "UserLoginHis"), })
    @UooLog(value = "新增系统用户登录历史", key = "addUserLoginHis")
    @PostMapping("addUserLoginHis")
    @Transactional
    public ResponseResult addUserLoginHis(UserLoginHis userLoginHis) {
        Long loginAccountId = userLoginHis.getLoginAccountId();
        if(loginAccountId==null){
            return ResponseResult.createErrorResult("请输入系统用户标识");
        }
        LoginAccount loginAccount = loginAccountService.selectById(loginAccountId);
        if(loginAccount==null||!StatusCD.VALID.equals(loginAccount.getStatusCd())){
            return ResponseResult.createErrorResult("无效的系统用户标识");
        }
        //  数据校验  获取操作者
        userLoginHis.setStatusCd(StatusCD.VALID);
        
        userLoginHis.setCreateDate(new Date());
        /*userLoginHis.setUpdateDate(new Date());
        userLoginHis.setStatusDate(new Date());*/
        userLoginHis.setLoginHisId(userLoginHisService.getId());
        userLoginHisService.insert(userLoginHis);
        return ResponseResult.createSuccessResult("success");
    }
    @ApiOperation(value = "修改系统用户登录历史", notes = "修改系统用户登录历史")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userLoginHis", value = "系统用户登录历史信息", required = true, dataType = "UserLoginHis"), })
    @UooLog(value = "修改系统用户登录历史", key = "updateUserLoginHis")
    @PostMapping("updateUserLoginHis")
    @Transactional
    public ResponseResult updateUserLoginHis(UserLoginHis userLoginHis) {
        Long id = userLoginHis.getLoginHisId();
        if(id==null||userLoginHisService.selectById(id)==null){
            return ResponseResult.createErrorResult("修改数据异常");
        }
        Long loginAccountId = userLoginHis.getLoginAccountId();
        if(loginAccountId==null){
            return ResponseResult.createErrorResult("请输入系统用户标识");
        }
        LoginAccount loginAccount = loginAccountService.selectById(loginAccountId);
        if(loginAccount==null||!StatusCD.VALID.equals(loginAccount.getStatusCd())){
            return ResponseResult.createErrorResult("无效的系统用户标识");
        }
        //userLoginHis.setUpdateDate(new Date());
        userLoginHisService.updateById(userLoginHis);
        return ResponseResult.createSuccessResult("success");
    }
    
    @ApiOperation(value = "删除系统用户登录历史", notes = "删除系统用户登录历史")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userLoginHis", value = "系统用户登录历史信息", required = true, dataType = "UserLoginHis"), })
    @UooLog(value = "删除系统用户登录历史", key = "deleteUserLoginHis")
    @PostMapping("deleteUserLoginHis")
    @Transactional(rollbackFor=Exception.class)
    public ResponseResult deleteUserLoginHis(UserLoginHis userLoginHis) {
        //
        if(userLoginHis==null||userLoginHis.getLoginHisId()==null){
            return ResponseResult.createErrorResult("不能删除空数据");
        }
        UserLoginHis r=new UserLoginHis();
        r.setLoginHisId(userLoginHis.getLoginHisId());
        r.setStatusCd(StatusCD.INVALID);
        //r.setUpdateDate(new Date());
        //r.setStatusDate(new Date());
        //r.setUpdateUser(userLoginHis.getUpdateUser());
        userLoginHisService.updateById(r);
        return ResponseResult.createSuccessResult("success");
    }

}

