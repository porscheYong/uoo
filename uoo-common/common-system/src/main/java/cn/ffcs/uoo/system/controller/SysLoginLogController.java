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
import cn.ffcs.uoo.system.entity.SysLoginLog;
import cn.ffcs.uoo.system.service.SysLoginLogService;
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
@RestController
@RequestMapping("/system/sysLoginLog")
public class SysLoginLogController {

    @Autowired
    SysLoginLogService sysLoginLogService;

    @ApiOperation(value = "获取单个数据", notes = "获取单个数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long" ,paramType="path"),
    })
    @UooLog(key="getLoginLog",value="获取单个数据")
    @GetMapping("/get/{id}")
    public ResponseResult get(@PathVariable(value="id" ,required=true) Long id){
        SysLoginLog LoginLog = sysLoginLogService.selectById(id);
        if(LoginLog== null ){
            return ResponseResult.createErrorResult("无效数据");
        }
        return ResponseResult.createSuccessResult(LoginLog, "success");
    }

    @ApiOperation(value = "获取分页列表", notes = "获取分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "pageNo", required = true, dataType = "Long" ,paramType="path"),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = false, dataType = "Long" ,paramType="path"),
    })
    @UooLog(key="listPage",value="获取分页列表")
    @GetMapping("/listPage/pageNo={pageNo}&pageSize={pageSize}")
    public ResponseResult listPage(@PathVariable(value = "pageNo") Integer pageNo, @PathVariable(value = "pageSize",required = false) Integer pageSize){
        pageNo = pageNo==null?0:pageNo;
        pageSize = pageSize==null?20:pageSize;

        Wrapper<SysLoginLog> wrapper = Condition.create().eq("STATUS_CD", StatusCD.VALID).orderBy("UPDATE_DATE", false);
        Page<SysLoginLog> page = sysLoginLogService.selectPage(new Page<SysLoginLog>(pageNo, pageSize), wrapper);

        return ResponseResult.createSuccessResult(page.getRecords(), "", page);
    }

    @ApiOperation(value = "新增",notes = "新增")
    @ApiImplicitParam(name = "sysLoginLog", value = "新增", required = true, dataType = "SysLoginLog")
    @UooLog(value = "新增", key = "add")
    @Transactional
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseResult add(@RequestBody SysLoginLog sysLoginLog) {
        ResponseResult responseResult = new ResponseResult();

        sysLoginLog.setCreateDate(new Date());
        sysLoginLog.setLoginId((sysLoginLogService.getId()));
        sysLoginLog.setStatusCd(StatusCD.VALID);
        sysLoginLog.setStatusDate(new Date());
        sysLoginLogService.insert(sysLoginLog);
        responseResult.setState(ResponseResult.STATE_OK);
        responseResult.setMessage("新增成功");
        return responseResult;
    }

}
