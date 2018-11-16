package cn.ffcs.uoo.web.maindata.user.controller;

import cn.ffcs.uoo.web.maindata.user.service.AcctService;
import cn.ffcs.uoo.web.maindata.user.vo.EditFormAcctVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName AcctController
 * @Description
 * @author wudj
 * @date 2018/11/14 14:28
 * @Version 1.0.0
 */
@RestController
@RequestMapping(value = "/acct", produces = {"application/json;charset=UTF-8"})
@Api(value = "/acct", description = "用户(主账号)相关操作")
public class AcctController {

    @Resource
    private AcctService acctService;

    @ApiOperation(value = "新增主账号信息", notes = "主账号信息新增")
    @ApiImplicitParam(name = "editFormAcctVo", value = "主账号信息", required = true, dataType = "EditFormAcctVo")
    @RequestMapping(value = "/addTbAcct", method = RequestMethod.POST)
    public Object saveAcct(@RequestBody EditFormAcctVo editFormAcctVo){
        return acctService.saveAcct(editFormAcctVo);
    }

    @ApiOperation(value = "删除主账号信息", notes = "主账号相关信息删除")
    @ApiImplicitParam(name = "acctID", value = "主账号标识", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value = "/deleteTbAcct", method = RequestMethod.DELETE)
    public Object removeAcct(Long acctId) {
        return acctService.removeAcct(acctId);
    }

    @ApiOperation(value = "修改主账号",notes = "主账号修改")
    @ApiImplicitParam(name = "editFormAcctVo", value = "主账号信息", required = true, dataType = "EditFormAcctVo")
    @RequestMapping(value = "/updateAcct", method = RequestMethod.PUT)
    public Object updateAcct(@RequestBody EditFormAcctVo editFormAcctVo) {
        return acctService.updateAcct(editFormAcctVo);
    }

}
