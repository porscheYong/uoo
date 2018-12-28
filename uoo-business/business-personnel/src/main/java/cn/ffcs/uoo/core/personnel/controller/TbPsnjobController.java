package cn.ffcs.uoo.core.personnel.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.core.personnel.annotion.SendMqMsg;
import cn.ffcs.uoo.core.personnel.entity.TbPsnjob;
import cn.ffcs.uoo.core.personnel.service.TbPsnjobService;
import cn.ffcs.uoo.core.personnel.util.ResultUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cn.ffcs.uoo.base.controller.BaseController;



/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wudj
 * @since 2018-10-11
 */
@RestController
@RequestMapping("/tbPsnjob")
public class TbPsnjobController extends BaseController {

    @Autowired
    private TbPsnjobService tbPsnjobService;

    @ApiOperation(value="新增工作履历",notes="新增工作履历")
    @ApiImplicitParam(name = "tbPsnjob",value = "工作履历",required = true,dataType = "TbPsnjob")
    @UooLog(value = "新增工作履历",key = "saveTbPsnjob")
    @SendMqMsg(type = "person", handle ="update", column ="personnelId")
    @RequestMapping(value="/saveTbPsnjob",method = RequestMethod.POST)
    public Object saveTbPsnjob(@RequestBody TbPsnjob tbPsnjob){
        return tbPsnjobService.saveTbPsnjob(tbPsnjob);
    }

    @ApiOperation(value="更新工作履历",notes="更新工作履历")
    @ApiImplicitParam(name = "tbPsnjob",value = "工作履历",required = true,dataType = "TbPsnjob")
    @UooLog(value = "更新工作履历",key = "updateTbPsnjob")
    @SendMqMsg(type = "person", handle ="update", column ="personnelId")
    @RequestMapping(value="/updateTbPsnjob",method = RequestMethod.PUT)
    public Object updateTbPsnjob(@RequestBody TbPsnjob tbPsnjob){
        return  tbPsnjobService.updateTbPsnjob(tbPsnjob);
    }

    @ApiOperation(value="删除工作履历",notes="删除工作履历")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "psnjobId", value = "工作履历标识", required = true, dataType = "Long",paramType="path"),
            @ApiImplicitParam(name = "userId", value = "操作人标识", required = true, dataType = "Long",paramType="path")
    })
    @UooLog(value = "删除工作履历",key = "delTbPsnjob")
    @SendMqMsg(type = "person", handle ="update", column ="personnelId")
    @RequestMapping(value="/delTbPsnjob",method = RequestMethod.DELETE)
    public Object delTbPsnjob(Long psnjobId, Long userId ){
        return  tbPsnjobService.delTbPsnjob(psnjobId, userId);
    }

    @ApiOperation(value="工作履历查看",notes="工作履历")
    @ApiImplicitParam(name = "psnjobId", value = "工作履历标识", required = true, dataType = "Long",paramType="path")
    @UooLog(value = "工作履历查看",key = "getTbPsnjob")
    @RequestMapping(value="/getTbPsnjob",method = RequestMethod.GET)
    public Object getTbPsnjob(Long psnjobId){
        return tbPsnjobService.getTbPsnjobById(psnjobId);
    }

    @ApiOperation(value="工作履历分页查询",notes="工作履历分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "personnelId", value = "人员标识", required = true, dataType = "Long",paramType="path"),
            @ApiImplicitParam(name = "pageNo", value = "当前页数", required = true, dataType = "Integer",paramType="path"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = true, dataType = "Integer",paramType="path"),
    })
    @UooLog(value = "工作履历查看",key = "getTbPsnjobPage")
    @RequestMapping(value="/getTbPsnjobPage",method = RequestMethod.GET)
    public Object getTbPsnjobPage( Long personnelId, Integer pageNo, Integer pageSize){

        return ResultUtils.success(tbPsnjobService.getPsnjobPageBypsnId(personnelId, pageNo, pageSize));
    }





}

