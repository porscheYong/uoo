/**
 * Copyright (C), 2017-2018, 中电福富信息科技有限公司
 * FileName: SysDeptController
 * Author:   linmingxu
 * Date:     2018/12/4 15:40
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.ffcs.uoo.system.controller;

import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.system.consts.StatusCD;
import cn.ffcs.uoo.system.entity.SysOperationLog;
import cn.ffcs.uoo.system.service.SysOperationLogService;
import cn.ffcs.uoo.system.vo.ResponseResult;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author linmingxu
 * @create 2018/12/4
 * @since 1.0.0
 */
public class SysOperationLogController {
    @Autowired
    SysOperationLogService sysOperationLogService;

    @ApiOperation(value = "获取单个数据", notes = "获取单个数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long" ,paramType="path"),
    })
    @UooLog(key="getOperationLog",value="获取单个数据")
    @GetMapping("/get/{id}")
    public ResponseResult get(@PathVariable(value="id" ,required=true) Long id){
        SysOperationLog OperationLog = sysOperationLogService.selectById(id);
        if(OperationLog== null ){
            return ResponseResult.createErrorResult("无效数据");
        }
        return ResponseResult.createSuccessResult(OperationLog, "success");
    }

    @ApiOperation(value = "获取分页列表", notes = "获取分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "pageNo", required = true, dataType = "Long" ,paramType="path"),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = false, dataType = "Long" ,paramType="path"),
    })
    @UooLog(key="listPageRoles",value="获取分页列表")
    @GetMapping("/listPageRoles/pageNo={pageNo}&pageSize={pageSize}")
    public ResponseResult listPage(@PathVariable(value = "pageNo") Integer pageNo, @PathVariable(value = "pageSize",required = false) Integer pageSize){
        pageNo = pageNo==null?0:pageNo;
        pageSize = pageSize==null?20:pageSize;

        Wrapper<SysOperationLog> wrapper = Condition.create().eq("STATUS_CD", StatusCD.VALID).orderBy("UPDATE_DATE", false);
        Page<SysOperationLog> page = sysOperationLogService.selectPage(new Page<SysOperationLog>(pageNo, pageSize), wrapper);

        return ResponseResult.createSuccessResult(page.getRecords(), "", page);
    }

    @ApiOperation(value = "修改",notes = "修改")
    @ApiImplicitParam(name = "sysOperationLog", value = "修改", required = true, dataType = "Roles")
    @UooLog(value = "修改角色", key = "updateTbRoles")
    @Transactional
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseResult update(@RequestBody SysOperationLog sysOperationLog) {
        ResponseResult responseResult = new ResponseResult();
        // 校验必填项
        if(sysOperationLog.getOperationLogId() == null) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("请输入id");
            return responseResult;
        }

        sysOperationLogService.updateById(sysOperationLog);

        responseResult.setState(ResponseResult.STATE_OK);
        responseResult.setMessage("修改角色成功");
        return responseResult;
    }

    @ApiOperation(value = "新增",notes = "新增")
    @ApiImplicitParam(name = "sysOperationLog", value = "新增", required = true, dataType = "SysOperationLog")
    @UooLog(value = "新增", key = "add")
    @Transactional
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseResult add(@RequestBody SysOperationLog sysOperationLog) {
        ResponseResult responseResult = new ResponseResult();

        sysOperationLog.setCreateDate(new Date());
        sysOperationLog.setOperationLogId((sysOperationLogService.getId()));
        sysOperationLog.setStatusCd(StatusCD.VALID);
        sysOperationLog.setStatusDate(new Date());
        sysOperationLogService.insert(sysOperationLog);
        responseResult.setState(ResponseResult.STATE_OK);
        responseResult.setMessage("新增角色成功");
        return responseResult;
    }

    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysOperationLog", value = "sysOperationLog", required = true, dataType = "sysOperationLog"  ),
    })
    @UooLog(key="delete=",value="删除")
    @SuppressWarnings("unchecked")
    @Transactional
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseResult deletePrivilege(@RequestBody SysOperationLog sysOperationLog) {
        Long OperationLogId = sysOperationLog.getOperationLogId();
        if (OperationLogId == null) {
            return ResponseResult.createErrorResult("无效数据");
        }
        SysOperationLog obj = sysOperationLogService.selectById(OperationLogId);
        if (obj == null ) {
            return ResponseResult.createErrorResult("不能删除无效数据");
        }

        obj.setStatusCd(StatusCD.INVALID);
        obj.setStatusDate(new Date());
        obj.setUpdateDate(new Date());
        sysOperationLogService.updateById(obj);
        return ResponseResult.createSuccessResult("success");
    }
}
