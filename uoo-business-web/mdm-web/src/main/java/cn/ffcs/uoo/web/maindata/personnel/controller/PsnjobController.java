package cn.ffcs.uoo.web.maindata.personnel.controller;


import cn.ffcs.uoo.web.maindata.mdm.logs.OperateLog;
import cn.ffcs.uoo.web.maindata.mdm.logs.OperateType;
import cn.ffcs.uoo.web.maindata.personnel.dto.TbPsnjob;
import cn.ffcs.uoo.web.maindata.personnel.service.PsnjobService;
import cn.ffcs.uoo.web.maindata.personnel.utils.SysUserInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName PsnjobController
 * @Description
 * @author wudj
 * @date 2018/11/14 14:28
 * @Version 1.0.0
 */
@RestController
@RequestMapping(value = "/psnjob", produces = {"application/json;charset=UTF-8"})
@Api(value = "/psnjob", description = "工作履历相关操作")
public class PsnjobController {

    @Resource
    private PsnjobService psnjobService;

    @ApiOperation(value="新增工作履历",notes="新增工作履历")
    @ApiImplicitParam(name = "tbPsnjob",value = "工作履历",required = true,dataType = "TbPsnjob")
    @RequestMapping(value="/saveTbPsnjob",method = RequestMethod.POST)
    @OperateLog(type= OperateType.ADD, module="人员管理",methods="saveTbPsnjob",desc="新增工作履历")
    public Object saveTbPsnjob(@RequestBody TbPsnjob tbPsnjob){
        tbPsnjob.setCreateUser(SysUserInfo.getUserId());
        tbPsnjob.setUpdateUser(SysUserInfo.getUserId());
        return psnjobService.saveTbPsnjob(tbPsnjob);
    }

    @ApiOperation(value="更新工作履历",notes="更新工作履历")
    @ApiImplicitParam(name = "tbPsnjob",value = "工作履历",required = true,dataType = "TbPsnjob")
    @RequestMapping(value="/updateTbPsnjob",method = RequestMethod.PUT)
    @OperateLog(type= OperateType.UPDATE, module="人员管理",methods="updateTbPsnjob",desc="更新工作履历")
    public Object updateTbPsnjob(@RequestBody TbPsnjob tbPsnjob){
        tbPsnjob.setUpdateUser(SysUserInfo.getUserId());
        return  psnjobService.updateTbPsnjob(tbPsnjob);
    }

    @ApiOperation(value="删除工作履历",notes="删除工作履历")
    @ApiImplicitParam(name = "psnjobId", value = "工作履历标识", required = true, dataType = "Long",paramType="path")
    @RequestMapping(value="/delTbPsnjob",method = RequestMethod.DELETE)
    @OperateLog(type= OperateType.DELETE, module="人员管理",methods="delTbPsnjob",desc="删除工作履历")
    public Object delTbPsnjob(Long psnjobId ){
        return  psnjobService.delTbPsnjob(psnjobId, SysUserInfo.getUserId());
    }

    @ApiOperation(value="工作履历查看",notes="工作履历")
    @ApiImplicitParam(name = "psnjobId", value = "工作履历标识", required = true, dataType = "Long",paramType="path")
    @RequestMapping(value="/getTbPsnjob",method = RequestMethod.GET)
    @OperateLog(type= OperateType.SELECT, module="人员管理",methods="getTbPsnjob",desc="工作履历查看")
    public Object getTbPsnjob(Long psnjobId){
        return psnjobService.getTbPsnjob(psnjobId);
    }

    @ApiOperation(value="工作履历分页查询",notes="工作履历分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "personnelId", value = "人员标识", required = true, dataType = "Long",paramType="path"),
            @ApiImplicitParam(name = "pageNo", value = "当前页数", required = true, dataType = "Integer",paramType="path"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = true, dataType = "Integer",paramType="path"),
    })
    @RequestMapping(value="/getTbPsnjobPage",method = RequestMethod.GET)
    @OperateLog(type= OperateType.SELECT, module="人员管理",methods="getTbPsnjobPage",desc="工作履历分页查询")
    public Object getTbPsnjobPage( Long personnelId, Integer pageNo, Integer pageSize){
        return psnjobService.getTbPsnjobPage(personnelId, pageNo, pageSize);
    }

}
