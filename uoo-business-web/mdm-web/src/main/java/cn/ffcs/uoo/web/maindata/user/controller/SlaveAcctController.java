package cn.ffcs.uoo.web.maindata.user.controller;

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
    public Object saveSlaveAcct(@RequestBody EditFormSlaveAcctVo editFormSlaveAcctVo){
        return slaveAcctService.saveSlaveAcct(editFormSlaveAcctVo);
    }

    @ApiOperation(value = "删除从账号信息", notes = "删除从账号信息")
    @ApiImplicitParam(name = "slaveAcctId", value = "从账号账号标识", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value = "/delTbSlaveAcct", method = RequestMethod.DELETE)
    public Object delTbSlaveAcct(Long slaveAcctId){
        return slaveAcctService.delTbSlaveAcct(slaveAcctId);
    }

    @ApiOperation(value = "更新从账号信息", notes = "更新从账号信息")
    @ApiImplicitParam(name = "editFormSlaveAcctVo", value = "从账号信息", required = true, dataType = "EditFormSlaveAcctVo")
    @RequestMapping(value = "/updateTbSlaveAcct", method = RequestMethod.POST)
    public Object updateTbSlaveAcct(@RequestBody EditFormSlaveAcctVo editFormSlaveAcctVo){
        return slaveAcctService.updateTbSlaveAcct(editFormSlaveAcctVo);
    }
}
