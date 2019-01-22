package cn.ffcs.uoo.web.maindata.user.controller;

import cn.ffcs.uoo.web.maindata.mdm.logs.OperateLog;
import cn.ffcs.uoo.web.maindata.mdm.logs.OperateType;
import cn.ffcs.uoo.web.maindata.personnel.utils.SysUserInfo;
import cn.ffcs.uoo.web.maindata.user.dto.TbAcctExt;
import cn.ffcs.uoo.web.maindata.user.service.SlaveAcctService;
import cn.ffcs.uoo.web.maindata.user.vo.EditFormSlaveAcctVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName SlaveAcctController
 * @Description
 * @author wudj
 * @date 2018/11/14 14:28
 * @Version 1.0.0
 */
@RestController
@RequestMapping(value = "/slaveAcct", produces = {"application/json;charset=UTF-8"})
@Api(value = "/slaveAcct", description = "用户(从账号)相关操作")
public class SlaveAcctController {
    @Resource
    private SlaveAcctService slaveAcctService;

    @ApiOperation(value = "新增从账号信息", notes = "从账号信息新增")
    @ApiImplicitParam(name = "editFormSlaveAcctVo", value = "从账号信息", required = true, dataType = "EditFormSlaveAcctVo")
    @RequestMapping(value = "/addTbSlaveAcct", method = RequestMethod.POST)
    @OperateLog(type= OperateType.ADD, module="账号管理",methods="addTbSlaveAcct",desc="新增从账号信息")
    public Object saveSlaveAcct(@RequestBody EditFormSlaveAcctVo editFormSlaveAcctVo){
        editFormSlaveAcctVo.setUserId(SysUserInfo.getUserId());
        return slaveAcctService.saveSlaveAcct(editFormSlaveAcctVo);
    }

    @ApiOperation(value = "删除从账号信息", notes = "删除从账号信息")
    @ApiImplicitParam(name = "slaveAcctId", value = "从账号账号标识", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value = "/delTbSlaveAcct", method = RequestMethod.DELETE)
    @OperateLog(type= OperateType.DELETE, module="账号管理",methods="delTbSlaveAcct",desc="删除从账号信息")
    public Object delTbSlaveAcct(Long slaveAcctId){
        return slaveAcctService.delTbSlaveAcct(slaveAcctId, SysUserInfo.getUserId());
    }

    @ApiOperation(value = "更新从账号信息", notes = "更新从账号信息")
    @ApiImplicitParam(name = "editFormSlaveAcctVo", value = "从账号信息", required = true, dataType = "EditFormSlaveAcctVo")
    @RequestMapping(value = "/updateTbSlaveAcct", method = RequestMethod.POST)
    @OperateLog(type= OperateType.UPDATE, module="账号管理",methods="updateTbSlaveAcct",desc="更新从账号信息")
    public Object updateTbSlaveAcct(@RequestBody EditFormSlaveAcctVo editFormSlaveAcctVo){
        editFormSlaveAcctVo.setUserId(SysUserInfo.getUserId());
        return slaveAcctService.updateTbSlaveAcct(editFormSlaveAcctVo);
    }

    @ApiOperation(value = "新增从账号扩展信息", notes = "新增从账号扩展信息")
    @ApiImplicitParam(name = "tbAcctExt", value = "从账号扩展信息", required = true, dataType = "TbAcctExt")
    @RequestMapping(value = "/addTbAcctExt", method = RequestMethod.POST)
    @OperateLog(type= OperateType.ADD, module="账号管理",methods="addTbAcctExt",desc="新增从账号扩展信息")
    public Object addTbAcctExt(@RequestBody TbAcctExt tbAcctExt){
        return slaveAcctService.addOrUpdateTbAcctExt(tbAcctExt);
    }

    @ApiOperation(value = "更新从账号扩展信息", notes = "更新从账号扩展信息")
    @ApiImplicitParam(name = "tbAcctExt", value = "从账号扩展信息", required = true, dataType = "TbAcctExt")
    @RequestMapping(value = "/updateTbAcctExt", method = RequestMethod.POST)
    @OperateLog(type= OperateType.UPDATE, module="账号管理",methods="addTbAcctExt",desc="更新从账号扩展信息")
    public Object updateTbAcctExt(@RequestBody TbAcctExt tbAcctExt){
        tbAcctExt.setUpdateUser(SysUserInfo.getUserId());
        return slaveAcctService.addOrUpdateTbAcctExt(tbAcctExt);
    }

    @ApiOperation(value = "删除从账号扩展信息", notes = "删除从账号扩展信息")
    @ApiImplicitParam(name = "tbAcctExt", value = "从账号扩展信息", required = true, dataType = "TbAcctExt")
    @RequestMapping(value = "/delTbAcctExt", method = RequestMethod.DELETE)
    @OperateLog(type= OperateType.DELETE, module="账号管理",methods="addTbAcctExt",desc="删除从账号扩展信息")
    public Object delTbAcctExt(@RequestBody TbAcctExt tbAcctExt){
        tbAcctExt.setUpdateUser(SysUserInfo.getUserId());
        return slaveAcctService.delTbAcctExt(tbAcctExt);
    }
}
