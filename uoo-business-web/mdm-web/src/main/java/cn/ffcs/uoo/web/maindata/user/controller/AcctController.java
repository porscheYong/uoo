package cn.ffcs.uoo.web.maindata.user.controller;

import cn.ffcs.uoo.web.maindata.user.dto.TbAccountOrgRel;
import cn.ffcs.uoo.web.maindata.user.service.AcctService;
import cn.ffcs.uoo.web.maindata.user.vo.EditFormAcctVo;
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

    @ApiOperation(value = "删除主账号与组织关系", notes = "删除主账号与组织关系")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "personnelId", value = "人员标识", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "acctID", value = "主账号标识", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "orgId", value = "组织标识", required = true, dataType = "Long", paramType = "path")
    })
    @RequestMapping(value = "/removeAcctOrg", method = RequestMethod.DELETE)
    public Object removeAcctOrg(Long personnelId, Long acctId, Long orgId) {
        return acctService.removeAcctOrg(personnelId, acctId, orgId);
    }

    @ApiOperation(value = "新增主账号与组织关系", notes = "新增主账号与组织关系")
    @ApiImplicitParam(name = "tbAccountOrgRel", value = "主账号与组织关系信息", required = true, dataType = "TbAccountOrgRel")
    @RequestMapping(value = "/addAcctOrg", method = RequestMethod.POST)
    public Object addAcctOrg(@RequestBody TbAccountOrgRel tbAccountOrgRel) {
        return acctService.addAcctOrg(tbAccountOrgRel);
    }

    @ApiOperation(value="主账号归属组织查询",notes="主账号归属组织查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "acctId", value = "主账号标识", required = true, dataType = "Long",paramType="path"),
            @ApiImplicitParam(name = "pageNo", value = "当前页数", required = true, dataType = "Integer",paramType="path"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = true, dataType = "Integer",paramType="path")
    })
    @RequestMapping(value="/getAcctOrgRelPage",method = RequestMethod.GET )
    public Object getAcctOrgRelPage(Long acctId, Integer pageNo, Integer pageSize){
        return acctService.getAcctOrgRelPage(acctId, pageNo, pageSize);
    }

    @ApiOperation(value = "主账号信息",notes = "主账号信息")
    @ApiImplicitParam(name = "acct", value = "主账号", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/getTbAcct", method = RequestMethod.GET)
    public Object getTbAcct(String acct){
        return acctService.getTbAcct(acct);
    }

}