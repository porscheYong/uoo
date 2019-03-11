package cn.ffcs.uoo.web.maindata.organization.controller;

import cn.ffcs.uoo.web.maindata.common.system.dto.SysUser;
import cn.ffcs.uoo.web.maindata.mdm.consts.LoginConsts;
import cn.ffcs.uoo.web.maindata.mdm.logs.OperateLog;
import cn.ffcs.uoo.web.maindata.mdm.logs.OperateType;
import cn.ffcs.uoo.web.maindata.organization.dto.ChannelOrgVo;
import cn.ffcs.uoo.web.maindata.organization.dto.Org;
import cn.ffcs.uoo.web.maindata.organization.dto.OrgVo;
import cn.ffcs.uoo.web.maindata.organization.dto.ResponseResult;
import cn.ffcs.uoo.web.maindata.organization.service.OrgService;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-09-25
 */
@RestController
@RequestMapping(value = "/org")
public class OrgController {

    @Resource
    private OrgService orgService;

    @ApiOperation(value = "新增组织", notes = "新增组织")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/addOrg", method = RequestMethod.POST)
    @OperateLog(type= OperateType.ADD, module="组织新增",methods="addOrg",desc="组织新增")
    public ResponseResult addOrg(@RequestBody OrgVo org) {
        Subject subject=SecurityUtils.getSubject();
        SysUser currentLoginUser = (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        Long userId = currentLoginUser.getUserId();
        org.setUpdateUser(userId);
        return orgService.addOrg(org);
    }


    @ApiOperation(value = "修改组织", notes = "修改组织")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/updateOrg", method = RequestMethod.POST)
    @OperateLog(type= OperateType.UPDATE, module="组织编辑",methods="updateOrg",desc="组织编辑")
    public ResponseResult updateOrg(@RequestBody OrgVo org) {
        Subject subject=SecurityUtils.getSubject();
        SysUser currentLoginUser = (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        Long userId = currentLoginUser.getUserId();
        org.setUpdateUser(userId);
        return orgService.updateOrg(org);
    }


    @ApiOperation(value = "获取组织信息", notes = "获取组织信息")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/getOrg", method = RequestMethod.GET)
    public ResponseResult getOrg(@RequestParam(value = "orgTreeId",required = false)String orgTreeId,
                                 @RequestParam(value = "orgId",required = false) String orgId) {
        return orgService.getOrg(orgId,orgTreeId);
    }


    @ApiOperation(value = "获取组织关系分页", notes = "获取组织关系分页")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/getOrgRelPage", method = RequestMethod.GET)
    public ResponseResult getOrgRelPage(@RequestParam(value = "orgRootId",required = false)String orgRootId,
                                        @RequestParam(value = "orgTreeId",required = false)String orgTreeId,
                                        @RequestParam(value = "orgId",required = false)String orgId,
                                        @RequestParam(value = "sortField",required = false)String sortField,
                                        @RequestParam(value = "sortOrder",required = false)String sortOrder,
                                        @RequestParam(value = "pageSize",required = false)Integer pageSize,
                                        @RequestParam(value = "pageNo",required = false)Integer pageNo) {
        Subject subject=SecurityUtils.getSubject();
        SysUser currentLoginUser = (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        Long userId = currentLoginUser.getUserId();
        String accout = currentLoginUser.getAccout();

        return orgService.getOrgRelPage(orgRootId,orgTreeId,orgId,sortField,sortOrder,pageSize,pageNo,userId,accout);
    }


    @ApiOperation(value = "获取组织分页", notes = "获取组织分页")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/getOrgPage", method = RequestMethod.GET)
    public ResponseResult getOrgPage(@RequestParam(value = "search",required = false)String search,
                                     @RequestParam(value = "orgTreeId",required = false)String orgTreeId,
                                     @RequestParam(value = "pageSize",required = false)Integer pageSize,
                                     @RequestParam(value = "pageNo",required = false)Integer pageNo) {
        return orgService.getOrgPage(search,orgTreeId,pageSize,pageNo);
    }


    @ApiOperation(value = "查询组织扩展信息", notes = "查询组织扩展信息")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/getOrgExtByOrgId", method = RequestMethod.GET)
    public ResponseResult<HashMap<String,String>> getOrgExtByOrgId(@RequestParam(value = "orgTreeId",required = false)String orgTreeId ,
                                                                   @RequestParam(value = "orgRootId",required = false)String orgRootId ,
                                                                   @RequestParam(value = "orgId",required = false) String orgId){
        return orgService.getOrgExtByOrgId(orgTreeId,orgRootId,orgId);
    }


    @ApiOperation(value = "组织删除", notes = "组织删除")
    @ApiImplicitParams({

    })
    @RequestMapping(value = "/deleteOrg", method = RequestMethod.GET)
    @OperateLog(type= OperateType.DELETE, module="组织删除",methods="deleteOrg",desc="组织删除")
    public ResponseResult<String> deleteOrg(@RequestParam(value = "orgTreeId",required = false)String orgTreeId,
                                            @RequestParam(value = "orgId",required = false)String orgId,
                                            @RequestParam(value = "supOrgId",required = false)String supOrgId){
        Subject subject=SecurityUtils.getSubject();
        SysUser currentLoginUser = (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        Long userId = currentLoginUser.getUserId();
        return orgService.deleteOrg(orgTreeId,orgId,supOrgId,userId);
    }

    @ApiOperation(value = "获取渠道组织翻页", notes = "获取渠道组织翻页")
    @ApiImplicitParams({
    })
    @OperateLog(type= OperateType.SELECT, module="渠道查询翻页",methods="getChannelOrgPage",desc="渠道查询翻页")
    @RequestMapping(value = "/getChannelOrgPage", method = RequestMethod.GET)
    public ResponseResult<Page<OrgVo>> getChannelOrgPage(@RequestParam(value = "search",required = false)String search,
                                                         @RequestParam(value = "orgTreeId",required = false)String orgTreeId,
                                                         @RequestParam(value = "pageSize",required = false)Integer pageSize,
                                                         @RequestParam(value = "pageNo",required = false)Integer pageNo,
                                                         @RequestParam(value = "userId",required = false)Long userId,
                                                         @RequestParam(value = "accout",required = false)String accout){
        Subject subject=SecurityUtils.getSubject();
        SysUser currentLoginUser = (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        userId = currentLoginUser.getUserId();
        accout = currentLoginUser.getAccout();
        return orgService.getChannelOrgPage(search,orgTreeId,pageSize,pageNo,userId,accout);
    }


    @ApiOperation(value = "获取渠道组织额外信息", notes = "获取渠道组织额外信息")
    @ApiImplicitParams({
    })
    @OperateLog(type= OperateType.SELECT, module="获取渠道组织额外信息",methods="getChannelOrgExtInfo",desc="获取渠道组织额外信息")
    @RequestMapping(value = "/getChannelOrgExtInfo", method = RequestMethod.GET)
    public ResponseResult<HashMap<String,String>> getChannelOrgExtInfo(@RequestParam(value = "orgTreeId",required = false)Long orgTreeId){
        return orgService.getChannelOrgExtInfo(orgTreeId);
    }



    @ApiOperation(value = "保存渠道组织", notes = "保存渠道组织")
    @ApiImplicitParams({
    })
    @OperateLog(type= OperateType.ADD,  module="保存渠道组织",methods="addChannelOrg",desc="保存渠道组织")
    @RequestMapping(value = "/addChannelOrg", method = RequestMethod.POST)
    public ResponseResult<String> addChannelOrg(@RequestBody ChannelOrgVo channelOrgVo){
        Subject subject=SecurityUtils.getSubject();
        SysUser currentLoginUser = (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        Long userId = currentLoginUser.getUserId();
        String accout = currentLoginUser.getAccout();
        channelOrgVo.setUserId(userId);
        channelOrgVo.setAccout(accout);
        return orgService.addChannelOrg(channelOrgVo);
    }

}
