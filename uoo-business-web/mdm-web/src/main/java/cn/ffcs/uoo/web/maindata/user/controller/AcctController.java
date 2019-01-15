package cn.ffcs.uoo.web.maindata.user.controller;

import cn.ffcs.uoo.web.maindata.mdm.logs.OperateLog;
import cn.ffcs.uoo.web.maindata.mdm.logs.OperateType;
import cn.ffcs.uoo.web.maindata.personnel.utils.SysUserInfo;
import cn.ffcs.uoo.web.maindata.user.dto.TbAccountOrgRel;
import cn.ffcs.uoo.web.maindata.user.service.AcctService;
import cn.ffcs.uoo.web.maindata.user.vo.AcctOrgVo;
import cn.ffcs.uoo.web.maindata.user.vo.EditFormAcctVo;
import cn.ffcs.uoo.web.maindata.user.vo.SlaveAcctOrgVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
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
    @OperateLog(type= OperateType.ADD, module="账号管理",methods="addTbAcct",desc="新增主账号信息")
    public Object saveAcct(@RequestBody EditFormAcctVo editFormAcctVo){
        editFormAcctVo.setUserId(SysUserInfo.getUserId());
        return acctService.saveAcct(editFormAcctVo);
    }

    @ApiOperation(value = "删除主账号信息", notes = "主账号相关信息删除")
    @ApiImplicitParam(name = "acctID", value = "主账号标识", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value = "/deleteTbAcct", method = RequestMethod.DELETE)
    @OperateLog(type= OperateType.DELETE, module="账号管理",methods="deleteTbAcct",desc="删除主账号信息")
    public Object removeAcct(Long acctId) {
        return acctService.removeAcct(acctId, SysUserInfo.getUserId());
    }

    @ApiOperation(value = "修改主账号",notes = "主账号修改")
    @ApiImplicitParam(name = "editFormAcctVo", value = "主账号信息", required = true, dataType = "EditFormAcctVo")
    @RequestMapping(value = "/updateAcct", method = RequestMethod.PUT)
    @OperateLog(type= OperateType.UPDATE, module="账号管理",methods="updateAcct",desc="修改主账号")
    public Object updateAcct(@RequestBody EditFormAcctVo editFormAcctVo) {
        editFormAcctVo.setUserId(SysUserInfo.getUserId());
        return acctService.updateAcct(editFormAcctVo);
    }

    @ApiOperation(value = "删除主账号与组织关系", notes = "删除主账号与组织关系")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "personnelId", value = "人员标识", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "acctId", value = "主账号标识", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "orgId", value = "组织标识", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "orgTreeId", value = "业务树标识", required = true, dataType = "Long", paramType = "path")
    })
    @RequestMapping(value = "/removeAcctOrg", method = RequestMethod.DELETE)
    @OperateLog(type= OperateType.DELETE, module="账号管理",methods="removeAcctOrg",desc="删除主账号与组织关系")
    public Object removeAcctOrg(Long personnelId, Long acctId, Long orgId, Long orgTreeId) {
        return acctService.removeAcctOrg(personnelId, acctId, orgId, orgTreeId, SysUserInfo.getUserId());
    }

    @ApiOperation(value = "新增主账号与组织关系", notes = "新增主账号与组织关系")
    @ApiImplicitParam(name = "tbAccountOrgRel", value = "主账号与组织关系信息", required = true, dataType = "TbAccountOrgRel")
    @RequestMapping(value = "/addAcctOrg", method = RequestMethod.POST)
    @OperateLog(type= OperateType.ADD, module="账号管理",methods="addAcctOrg",desc="新增主账号与组织关系")
    public Object addAcctOrg(@RequestBody TbAccountOrgRel tbAccountOrgRel) {
        tbAccountOrgRel.setCreateUser(SysUserInfo.getUserId());
        tbAccountOrgRel.setUpdateUser(SysUserInfo.getUserId());
        return acctService.addAcctOrg(tbAccountOrgRel);
    }

    @ApiOperation(value="主账号归属组织查询",notes="主账号归属组织查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "acctId", value = "主账号标识", required = true, dataType = "Long",paramType="path"),
            @ApiImplicitParam(name = "pageNo", value = "当前页数", required = true, dataType = "Integer",paramType="path"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = true, dataType = "Integer",paramType="path")
    })
    @RequestMapping(value="/getAcctOrgRelPage",method = RequestMethod.GET )
    @OperateLog(type= OperateType.SELECT, module="账号管理",methods="getAcctOrgRelPage",desc="主账号归属组织查询")
    public Object getAcctOrgRelPage(Long acctId, Integer pageNo, Integer pageSize){
        return acctService.getAcctOrgRelPage(acctId, pageNo, pageSize, SysUserInfo.getAccount());
    }

    @ApiOperation(value = "修改主账号与组织关系", notes = "修改主账号与组织关系")
    @ApiImplicitParam(name = "acctOrgVo", value = "主账号与组织关系信息", required = true, dataType = "AcctOrgVo")
    @RequestMapping(value = "/updateAcctOrg", method = RequestMethod.PUT)
    @OperateLog(type= OperateType.UPDATE, module="账号管理",methods="updateAcctOrg",desc="修改主账号与组织关系")
    public Object updateAcctOrg(@RequestBody AcctOrgVo acctOrgVo){
        acctOrgVo.setUserId(SysUserInfo.getUserId());
        return acctService.updateAcctOrg(acctOrgVo);
    }

    @ApiOperation(value = "修改从账号与组织关系", notes = "修改从账号与组织关系")
    @ApiImplicitParam(name = "acctOrgVo", value = "主账号与组织关系信息", required = true, dataType = "SlaveAcctOrgVo")
    @RequestMapping(value = "/updateSlaveAcctOrg", method = RequestMethod.PUT)
    @OperateLog(type= OperateType.UPDATE, module="账号管理",methods="updateSlaveAcctOrg",desc="修改从账号与组织关系")
    public Object updateSlaveAcctOrg(@RequestBody SlaveAcctOrgVo acctOrgVo){
        acctOrgVo.setUserId(SysUserInfo.getUserId());
        return acctService.updateSlaveAcctOrg(acctOrgVo);
    }

    @ApiOperation(value = "主账号信息",notes = "主账号信息")
    @ApiImplicitParam(name = "acct", value = "主账号", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/getTbAcct", method = RequestMethod.GET)
    @OperateLog(type= OperateType.SELECT, module="账号管理",methods="getTbAcct",desc="主账号信息")
    public Object getTbAcct(String acct){
        return acctService.getTbAcct(acct);
    }

    @ApiOperation(value = "当前登陆主账号信息",notes = "当前登陆主账号信息")
    @ApiImplicitParam( )
    @RequestMapping(value = "/getCurrentAcct", method = RequestMethod.GET)
    @OperateLog(type= OperateType.SELECT, module="账号管理",methods="getCurrentAcct",desc="当前登陆主账号信息")
    public Object getCurrentAcct(){
        Subject sub=SecurityUtils.getSubject();
        return acctService.getTbAcct(sub.getPrincipal().toString());
    }

}
