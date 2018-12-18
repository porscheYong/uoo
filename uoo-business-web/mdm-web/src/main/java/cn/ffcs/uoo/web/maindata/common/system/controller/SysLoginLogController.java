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
package cn.ffcs.uoo.web.maindata.common.system.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.ffcs.uoo.web.maindata.common.system.client.SysLoginLogClient;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysLoginLog;
import cn.ffcs.uoo.web.maindata.common.system.vo.ResponseResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

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
    SysLoginLogClient sysLoginLogClient;

    /*@ApiOperation(value = "获取单个数据", notes = "获取单个数据")
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

        return ResponseResult.createSuccessResult(page, "");
    }*/

    @ApiOperation(value = "新增",notes = "新增")
    @ApiImplicitParam(name = "sysLoginLog", value = "新增", required = true, dataType = "SysLoginLog")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseResult<Void> add(@RequestBody SysLoginLog sysLoginLog) {
        ResponseResult<Void> responseResult = new ResponseResult<Void>();
 
        return responseResult;
    }

}
