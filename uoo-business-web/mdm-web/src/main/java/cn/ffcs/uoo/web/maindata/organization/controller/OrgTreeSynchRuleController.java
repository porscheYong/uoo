package cn.ffcs.uoo.web.maindata.organization.controller;


import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.ffcs.uoo.web.maindata.common.system.dto.SysUser;
import cn.ffcs.uoo.web.maindata.mdm.consts.LoginConsts;
import cn.ffcs.uoo.web.maindata.organization.dto.OrgTree;
import cn.ffcs.uoo.web.maindata.organization.dto.OrgTreeSynchRule;
import cn.ffcs.uoo.web.maindata.organization.dto.OrgTreeSynchRuleVO;
import cn.ffcs.uoo.web.maindata.organization.dto.ResponseResult;
import cn.ffcs.uoo.web.maindata.organization.service.OrgTreeSynchRuleService;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 
 * </p>
 *
 * @author zengxsh
 * @since 2019-03-04
 */
@RestController
@RequestMapping("/org/orgTreeSynchRule")
public class OrgTreeSynchRuleController {
    @Autowired
    OrgTreeSynchRuleService orgTreeSynchRuleService;
    @ApiOperation(value = "GET_ID", notes = "GET_ID")
    @ApiImplicitParams({
    })
    @RequestMapping(value="/get",method = RequestMethod.GET)
    public ResponseResult<OrgTreeSynchRule> get(@RequestParam("id")Long id){
        return orgTreeSynchRuleService.get(id);
    }
    @ApiOperation(value = "getTree", notes = "getTree")
    @ApiImplicitParams({
    })
    @RequestMapping(value="/getTree",method = RequestMethod.GET)
    public ResponseResult<List<OrgTree>> getTree(@RequestParam("id")Long id){
        return orgTreeSynchRuleService.getTree(id);
    }
    @ApiOperation(value = "LIST_PAGE", notes = "LIST_PAGE")
    @ApiImplicitParams({
    })
    @RequestMapping(value="/list",method = RequestMethod.GET)
    public ResponseResult<List<OrgTreeSynchRuleVO>> list(@RequestParam("orgTreeId")Long orgTreeId){
       return orgTreeSynchRuleService.list(orgTreeId);
    }
    @ApiOperation(value = "add", notes = "add")
    @ApiImplicitParams({
    })
    @RequestMapping(value="/add",method = RequestMethod.POST)
    public ResponseResult<Void> add(@RequestBody OrgTreeSynchRule orgTreeSynchRule){
        Subject subject=SecurityUtils.getSubject();
        SysUser currentLoginUser = (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        orgTreeSynchRule.setCreateUser(currentLoginUser.getUserId());
        return orgTreeSynchRuleService.add(orgTreeSynchRule);
    }
    @ApiOperation(value = "update", notes = "update")
    @ApiImplicitParams({
    })
    @RequestMapping(value="/update",method = RequestMethod.POST)
    public ResponseResult<Void> update(@RequestBody OrgTreeSynchRule orgTreeSynchRule){
        Subject subject=SecurityUtils.getSubject();
        SysUser currentLoginUser = (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        orgTreeSynchRule.setUpdateUser(currentLoginUser.getUserId());
        return orgTreeSynchRuleService.update(orgTreeSynchRule);
    }
    @ApiOperation(value = "delete", notes = "delete")
    @ApiImplicitParams({
    })
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    public ResponseResult<Void> delete(@RequestBody OrgTreeSynchRule orgTreeSynchRule){
        Subject subject=SecurityUtils.getSubject();
        SysUser currentLoginUser = (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        orgTreeSynchRule.setUpdateUser(currentLoginUser.getUserId());
        return orgTreeSynchRuleService.delete(orgTreeSynchRule);
    }
}
