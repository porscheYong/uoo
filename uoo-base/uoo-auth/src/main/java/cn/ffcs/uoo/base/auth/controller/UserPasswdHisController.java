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
import cn.ffcs.uoo.base.auth.entity.UserPasswdHis;
import cn.ffcs.uoo.base.auth.service.ILoginAccountService;
import cn.ffcs.uoo.base.auth.service.IUserPasswdHisService;
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
@RequestMapping("/auth/userPasswdHis")
public class UserPasswdHisController {
    @Autowired
    private ILoginAccountService loginAccountService;
    @Autowired
    private IUserPasswdHisService userPasswdHisService;
    
    @ApiOperation(value = "根据ID获取单条数据", notes = "根据ID获取单条数据")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long",paramType="path"),
    })
    @UooLog(value = "根据ID获取单条数据", key = "getUserPasswdHis")
    @GetMapping("getUserPasswdHis/id={id}")
    public ResponseResult getUserPasswdHis(@PathVariable(value = "id") Long id){
        UserPasswdHis obj = userPasswdHisService.selectById(id);
        if(obj==null){
            return ResponseResult.createErrorResult("无效数据");
        }
        return ResponseResult.createSuccessResult(obj, "");
    }
    
    @ApiOperation(value = "系统用户历史账号密码列表", notes = "系统用户历史账号密码列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "pageNo", value = "分页的序号", required = true, dataType = "Integer",paramType="path"),
        @ApiImplicitParam(name = "pageSize", value = "每页的大小", dataType = "Integer",paramType="path",defaultValue = "12")
    })
    @UooLog(value = "系统用户历史账号密码列表", key = "listUserPasswdHis")
    @GetMapping("listUserPasswdHis/pageNo={pageNo}&pageSize={pageSize}")
    public ResponseResult listUserPasswdHis(@PathVariable(value = "pageNo") Integer pageNo, @PathVariable(value = "pageSize",required = false) Integer pageSize) {
        pageNo = pageNo==null?0:pageNo;
        pageSize = pageSize==null?20:pageSize;
        @SuppressWarnings("unchecked")
        Wrapper<UserPasswdHis> wrapper = Condition.create()/*.eq("STATUS_CD",StatusCD.VALID)*/.orderBy("CREATE_DATE", false);
        Page<UserPasswdHis> page = userPasswdHisService.selectPage(new Page<UserPasswdHis>(pageNo, pageSize), wrapper);
        ResponseResult result = ResponseResult.createSuccessResult(page.getRecords(), "", page);
        return result;
    }

    @ApiOperation(value = "新增系统用户历史账号密码", notes = "新增系统用户历史账号密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userPasswdHis", value = "系统用户历史账号密码信息", required = true, dataType = "UserPasswdHis"), })
    @UooLog(value = "新增系统用户历史账号密码", key = "addUserPasswdHis")
    @PostMapping("addUserPasswdHis")
    @Transactional
    public ResponseResult addUserPasswdHis(UserPasswdHis userPasswdHis) {
        //  数据校验  获取操作者
        Long loginAccountId = userPasswdHis.getLoginAccountId();
        if(loginAccountId==null){
            return ResponseResult.createErrorResult("请输入系统用户标识");
        }
        LoginAccount loginAccount = loginAccountService.selectById(loginAccountId);
        if(loginAccount==null||!StatusCD.VALID.equals(loginAccount.getStatusCd())){
            return ResponseResult.createErrorResult("无效的系统用户标识");
        }
        userPasswdHis.setCreateDate(new Date());
        userPasswdHis.setPasswdHisId(userPasswdHisService.getId());
        userPasswdHisService.insert(userPasswdHis);
        return ResponseResult.createSuccessResult("success");
    }
    @ApiOperation(value = "修改系统用户历史账号密码", notes = "修改系统用户历史账号密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userPasswdHis", value = "系统用户历史账号密码信息", required = true, dataType = "UserPasswdHis"), })
    @UooLog(value = "修改系统用户历史账号密码", key = "updateUserPasswdHis")
    @PostMapping("updateUserPasswdHis")
    @Transactional
    public ResponseResult updateUserPasswdHis(UserPasswdHis userPasswdHis) {
        Long id = userPasswdHis.getPasswdHisId();
        if(id==null||userPasswdHisService.selectById(id)==null){
            return ResponseResult.createErrorResult("修改数据异常");
        }
        Long loginAccountId = userPasswdHis.getLoginAccountId();
        if(loginAccountId==null){
            return ResponseResult.createErrorResult("请输入系统用户标识");
        }
        LoginAccount loginAccount = loginAccountService.selectById(loginAccountId);
        if(loginAccount==null||!StatusCD.VALID.equals(loginAccount.getStatusCd())){
            return ResponseResult.createErrorResult("无效的系统用户标识");
        }
        //userPasswdHis.setUpdateDate(new Date());
        userPasswdHisService.updateById(userPasswdHis);
        return ResponseResult.createSuccessResult("success");
    }
    
    @ApiOperation(value = "删除系统用户历史账号密码", notes = "删除系统用户历史账号密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userPasswdHis", value = "系统用户历史账号密码信息", required = true, dataType = "UserPasswdHis"), })
    @UooLog(value = "删除系统用户历史账号密码", key = "deleteUserPasswdHis")
    @PostMapping("deleteUserPasswdHis")
    @Transactional(rollbackFor=Exception.class)
    public ResponseResult deleteUserPasswdHis(UserPasswdHis userPasswdHis) {
        //
        if(userPasswdHis==null||userPasswdHis.getPasswdHisId()==null){
            return ResponseResult.createErrorResult("不能删除空数据");
        }
        userPasswdHisService.deleteById(userPasswdHis.getPasswdHisId());
        return ResponseResult.createSuccessResult("success");
    }

}

