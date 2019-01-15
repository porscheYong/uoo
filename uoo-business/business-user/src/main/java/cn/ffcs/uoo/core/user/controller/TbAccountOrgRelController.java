package cn.ffcs.uoo.core.user.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.base.controller.BaseController;
import cn.ffcs.uoo.core.user.entity.TbAccountOrgRel;
import cn.ffcs.uoo.core.user.service.TbAccountOrgRelService;
import cn.ffcs.uoo.core.user.service.TbUserService;
import cn.ffcs.uoo.core.user.util.ResultUtils;
import cn.ffcs.uoo.core.user.vo.AccountOrgRelVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wudj
 * @since 2018-11-08
 */
@RestController
@RequestMapping("/tbAccountOrgRel")
public class TbAccountOrgRelController extends BaseController {

    @Autowired
    private TbAccountOrgRelService tbAccountOrgRelService;
    @Autowired
    private TbUserService tbUserService;

    @ApiOperation(value = "删除主账号与组织关系", notes = "删除主账号与组织关系")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "personnelId", value = "人员标识", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "acctId", value = "主账号标识", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "orgId", value = "组织标识", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "orgTreeId", value = "业务树标识", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "userId", value = "操作人标识", required = true, dataType = "Long", paramType = "path")
    })
    @UooLog(value = "删除主账号与组织关系", key = "removeAcctOrg")
    @RequestMapping(value = "/removeAcctOrg", method = RequestMethod.DELETE)
    @Transactional(rollbackFor = Exception.class)
    public Object removeAcctOrg(Long personnelId, Long acctId, Long orgId, Long orgTreeId, Long userId) {
        return tbAccountOrgRelService.removeAcctOrg(personnelId, acctId, orgId, orgTreeId, userId);
    }

    @ApiOperation(value = "新增主账号与组织关系", notes = "新增主账号与组织关系")
    @ApiImplicitParam(name = "tbAccountOrgRel", value = "主账号与组织关系信息", required = true, dataType = "TbAccountOrgRel")
    @UooLog(value = "新增主账号与组织关系", key = "addAcctOrg")
    @RequestMapping(value = "/addAcctOrg", method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    public Object addAcctOrg(@RequestBody TbAccountOrgRel tbAccountOrgRel) {
        return tbAccountOrgRelService.addAcctOrg(tbAccountOrgRel);
    }

    @ApiOperation(value="主账号归属组织查询",notes="主账号归属组织查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "acctId", value = "主账号标识", required = true, dataType = "Long",paramType="path"),
            @ApiImplicitParam(name = "pageNo", value = "当前页数", required = true, dataType = "Integer",paramType="path"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = true, dataType = "Integer",paramType="path"),
            @ApiImplicitParam(name = "account", value = "账号", required = true, dataType = "String",paramType="path")
    })
    @UooLog(value = "主账号归属组织查询",key = "getAcctOrgRelPage")
    @RequestMapping(value="/getAcctOrgRelPage",method = RequestMethod.GET)
    public Object getAcctOrgRelPage(Long acctId, Integer pageNo, Integer pageSize, String account){
        return ResultUtils.success(tbUserService.getAcctOrg(acctId, pageNo, pageSize, account));
    }

    @ApiOperation(value = "修改主账号与组织关系", notes = "修改主账号与组织关系")
    @ApiImplicitParam(name = "tbAccountOrgRel", value = "主账号与组织关系信息", required = true, dataType = "AccountOrgRelVo")
    @UooLog(value = "修改主账号与组织关系", key = "updateAcctOrg")
    @RequestMapping(value = "/updateAcctOrg", method = RequestMethod.PUT)
    @Transactional(rollbackFor = Exception.class)
    public Object updateAcctOrg(@RequestBody AccountOrgRelVo tbAccountOrgRel) {
        return tbAccountOrgRelService.updateAcctOrg(tbAccountOrgRel);
    }



}

