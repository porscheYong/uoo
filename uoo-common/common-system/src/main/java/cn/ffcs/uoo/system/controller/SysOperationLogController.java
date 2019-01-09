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

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;

import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.system.consts.StatusCD;
import cn.ffcs.uoo.system.entity.SysMenu;
import cn.ffcs.uoo.system.entity.SysOperationLog;
import cn.ffcs.uoo.system.service.ModifyHistoryService;
import cn.ffcs.uoo.system.service.SysLoginLogService;
import cn.ffcs.uoo.system.service.SysOperationLogService;
import cn.ffcs.uoo.system.vo.LogDTO;
import cn.ffcs.uoo.system.vo.ResponseResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
@RequestMapping("/system/sysOperationLog")
public class SysOperationLogController {
    @Autowired
    SysOperationLogService sysOperationLogService;
    @Autowired
    SysLoginLogService sysLoginLogService;
    @Autowired
    ModifyHistoryService mhSvc;
    @ApiOperation(value = "获取单个数据", notes = "获取单个数据")
    @ApiImplicitParams({
    })
    @UooLog(key="getOperationLog",value="获取单个数据")
    @GetMapping("/get")
    public ResponseResult<Object> get(@RequestParam("id") Long id,@RequestParam("logEnum")String logEnum){
        if(id<=0){
            return ResponseResult.createErrorResult("请输入ID");
        }
        if("LOGIN".equals(logEnum)){
            return ResponseResult.createSuccessResult(sysLoginLogService.selectById(id), "success");
        }else if("OPT".equals(logEnum)){
            return ResponseResult.createSuccessResult(sysOperationLogService.selectById(id), "success");
        }else if("MODF".equals(logEnum)){
            return ResponseResult.createSuccessResult(mhSvc.selectById(id), "success");
        }else{
            return ResponseResult.createErrorResult("参数错误");
        }
        
    }

    @ApiOperation(value = "获取分页列表", notes = "获取分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "pageNo", required = true, dataType = "Long"  ),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = false, dataType = "Long"  ),
    })
    @UooLog(key="listPage",value="获取分页列表")
    @GetMapping("/listPage")
    public ResponseResult<Page<LogDTO>> listPage(@RequestParam("pageNo")Integer pageNo, @RequestParam("pageSize") Integer pageSize,@RequestParam("keyWord") String keyWord){
        pageNo = pageNo==null?0:pageNo;
        pageSize = pageSize==null?20:pageSize;
        HashMap<String,Object> map=new HashMap<>();
        if(keyWord!=null&&keyWord.trim().length()>0){
            map.put("keyWord", "%"+keyWord+"%");
        }/*
        if(parentRoleCode!=null&&parentRoleCode.trim().length()>0){
            map.put("PARENT_ROLE_CODE",  parentRoleCode );
        }*/
        map.put("from", (pageNo-1)*pageSize);
        map.put("end", pageNo * pageSize);
        Long countLog = sysOperationLogService.countLog(map);
        List<LogDTO> listLog = sysOperationLogService.listLog(map);
        Page<LogDTO> page=new Page<>(pageNo, pageSize);
        page.setTotal(countLog);
        page.setRecords(listLog);
        ResponseResult<Page<LogDTO>> createSuccessResult = ResponseResult.createSuccessResult( "");
        createSuccessResult.setTotalRecords(countLog);
        createSuccessResult.setData(page);
        return createSuccessResult;
    }

    @ApiOperation(value = "新增",notes = "新增")
    @ApiImplicitParam(name = "sysOperationLog", value = "新增", required = true, dataType = "SysOperationLog")
    @UooLog(value = "新增", key = "add")
    @Transactional
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseResult<Void> add(@RequestBody SysOperationLog sysOperationLog) {
        ResponseResult<Void> responseResult = new ResponseResult<Void>();

        sysOperationLog.setCreateDate(new Date());
        sysOperationLog.setOperationLogId((sysOperationLogService.getId()));
        sysOperationLog.setStatusCd(StatusCD.VALID);
        sysOperationLogService.insert(sysOperationLog);
        responseResult.setState(ResponseResult.STATE_OK);
        responseResult.setMessage("新增成功");
        return responseResult;
    }
}
