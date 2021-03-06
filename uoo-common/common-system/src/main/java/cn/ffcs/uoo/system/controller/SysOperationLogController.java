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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;

import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.system.consts.StatusCD;
import cn.ffcs.uoo.system.entity.ModifyHistory;
import cn.ffcs.uoo.system.entity.SysMenu;
import cn.ffcs.uoo.system.entity.SysOperationLog;
import cn.ffcs.uoo.system.service.ModifyHistoryService;
import cn.ffcs.uoo.system.service.SysLoginLogService;
import cn.ffcs.uoo.system.service.SysOperationLogService;
import cn.ffcs.uoo.system.vo.LogDTO;
import cn.ffcs.uoo.system.vo.ResponseResult;
import cn.ffcs.uoo.system.vo.SysOperationLogVO;
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
            SysOperationLogVO vo = sysOperationLogService.getVO(id);
            if(vo==null){
                return ResponseResult.createErrorResult("数据不存在");
            }
            Long logType = vo.getLogType();
            switch (logType.intValue()) {
            case 1:
                vo.setOperateName("查询");
                break;
            case 2:
                vo.setOperateName("新增");
                break;
            case 3:
                vo.setOperateName("修改");
                break;
            case 4:
                vo.setOperateName("删除");
                break;
            default:
                vo.setOperateName("其他");
                break;
            }
            return ResponseResult.createSuccessResult(vo, "success");
        }else if("MODF".equals(logEnum)){
            return ResponseResult.createSuccessResult(mhSvc.selectById(id), "success");
        }else{
            return ResponseResult.createErrorResult("参数错误");
        }
    }
    @ApiOperation(value = "关联本次操作日志的变更履历", notes = "获取单个数据")
    @ApiImplicitParams({
    })
    @UooLog(key="getOperatModifyHistory",value="获取单个数据")
    @GetMapping("/getOperatModifyHistory")
    public ResponseResult<List<ModifyHistory>> getOperatModifyHistory(@RequestParam("id") Long id,@RequestParam("userCode")String userCode,@RequestParam("userId")Long userId){
        SysOperationLog log = sysOperationLogService.selectById(id);
        Date createDate = log.getCreateDate();
        Page<SysOperationLog> page=new Page<>(1,1);
        Wrapper<SysOperationLog> wp=Condition.create().eq("STATUS_CD", StatusCD.VALID).eq("USER_CODE", userCode).lt("CREATE_DATE", createDate);
        
        page = sysOperationLogService.selectPage(page,wp);
        if(page.getRecords()==null||page.getRecords().size()!=1){
            return ResponseResult.createSuccessResult(new ArrayList<ModifyHistory>(),"");
        }
        SysOperationLog before = page.getRecords().get(0);//操作之后的时间
        Date afterDate=createDate;
        Date beforeDate=before.getCreateDate();
        Wrapper<ModifyHistory> wrapper=Condition.create().eq("STATUS_CD", StatusCD.VALID);
        wrapper.ge("CREATE_DATE", beforeDate).le("CREATE_DATE", afterDate);
        wrapper.eq("CREATE_USER", userId);
        List<ModifyHistory> selectList = mhSvc.selectList(wrapper);
        return ResponseResult.createSuccessResult(selectList,"");
    }
    @ApiOperation(value = "获取分页列表", notes = "获取分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "pageNo", required = true, dataType = "Long"  ),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = false, dataType = "Long"  ),
    })
    @UooLog(key="listPage",value="获取分页列表")
    @GetMapping("/listPage")
    public ResponseResult<Page<LogDTO>> listPage(@RequestParam("pageNo")Integer pageNo, @RequestParam("pageSize") Integer pageSize,@RequestParam("keyWord") String keyWord,@RequestParam("logEnum")String logEnum){
        pageNo = pageNo==null?0:pageNo;
        pageSize = pageSize==null?20:pageSize;
        HashMap<String,Object> map=new HashMap<>();
        if(keyWord!=null&&keyWord.trim().length()>0){
            map.put("keyWord", "%"+keyWord+"%");
        } 
        if(logEnum!=null&&logEnum.trim().length()>0){
            map.put("logEnum", logEnum);
        } 
        map.put("from", (pageNo-1)*pageSize);
        map.put("end", pageNo * pageSize);
        ResponseResult<Page<LogDTO>> createSuccessResult = ResponseResult.createSuccessResult( "");
        Page<LogDTO> page=new Page<>(pageNo, pageSize);
        List<LogDTO> listLog = sysOperationLogService.listLog(page,map);
        page.setRecords(listLog);
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
