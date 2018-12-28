package cn.ffcs.uoo.web.maindata.organization.controller;


import cn.ffcs.uoo.web.maindata.common.system.dto.SysUser;
import cn.ffcs.uoo.web.maindata.mdm.consts.LoginConsts;
import cn.ffcs.uoo.web.maindata.organization.dto.*;
import cn.ffcs.uoo.web.maindata.organization.service.OrgRelService;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
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
@RequestMapping(value = "/orgRel")
public class OrgRelController {


    @Resource
    private OrgRelService orgRelService;

    @ApiOperation(value = "获取组织树", notes = "获取组织树")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/getOrgRelTree", method = RequestMethod.GET)
    public ResponseResult<List<TreeNodeVo>> getOrgRelTree(@RequestParam(value = "id",required = false)String id,
                                                          @RequestParam(value = "orgRootId",required = false)String orgRootId,
                                                          @RequestParam(value = "orgTreeId",required = false)String orgTreeId,
                                                          @RequestParam(value = "refCode",required = false)String refCode,
                                                          @RequestParam(value = "isOpen",required = false)boolean isOpen,
                                                          @RequestParam(value = "isAsync",required = false)boolean isAsync,
                                                          @RequestParam(value = "isRoot",required = false)boolean isRoot){

        Subject subject=SecurityUtils.getSubject();
        SysUser currentLoginUser = (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        Long userId = currentLoginUser.getUserId();
        String accout = currentLoginUser.getAccout();
        return orgRelService.getOrgRelTree(id,orgRootId,orgTreeId,refCode,isOpen, isAsync,isRoot,userId,accout);
    }



    @ApiOperation(value = "获取重构组织树", notes = "获取重构组织树")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/getRestructOrgRelTree", method = RequestMethod.GET)
    public ResponseResult<List<TreeNodeVo>> getRestructOrgRelTree(
            @RequestParam(value = "orgId",required = false)String orgId,
            @RequestParam(value = "orgTreeId",required = false)String orgTreeId,
            @RequestParam(value = "orgRootId",required = false)String orgRootId,
            @RequestParam(value = "isFull",required = false)boolean isFull){
        return orgRelService.getRestructOrgRelTree(orgId,orgTreeId,orgRootId,isFull);
    }


    @ApiOperation(value = "获取目标组织树指定层级", notes = "获取目标组织树指定层级")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/getTarOrgRelTreeAndLv", method = RequestMethod.GET)
    public ResponseResult<List<TreeNodeVo>> getTarOrgRelTreeAndLv(@RequestParam(value = "orgRootId",required = false)String orgRootId,
                                                                  @RequestParam(value = "orgTreeId",required = false)String orgTreeId,
                                                                  @RequestParam(value = "lv",required = false)String lv,
                                                                  @RequestParam(value = "curOrgid",required = false)String curOrgid,
                                                                  @RequestParam(value = "isFull",required = false)boolean isFull){
        return orgRelService.getTarOrgRelTreeAndLv(orgRootId,orgTreeId,lv,curOrgid,isFull);
    }


    @ApiOperation(value = "增加组织关系", notes = "增加组织关系")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/addOrgRel", method = RequestMethod.POST)
    public ResponseResult<TreeNodeVo> addOrgRel(@RequestBody Org org){
        Subject subject=SecurityUtils.getSubject();
        SysUser currentLoginUser = (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        Long userId = currentLoginUser.getUserId();
        org.setUpdateUser(userId);
        return orgRelService.addOrgRel(org);
    }


    @ApiOperation(value = "检索组织树", notes = "检索组织树")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/getFuzzyOrgRelPage", method = RequestMethod.GET)
    public ResponseResult<Page<OrgVo>> getFuzzyOrgRelPage(@RequestParam(value = "search",required = false)String search,
                                                          @RequestParam(value = "orgRootId",required = false)String orgRootId,
                                                          @RequestParam(value = "orgTreeId",required = false)String orgTreeId,
                                                          @RequestParam(value = "pageSize",required = false)Integer pageSize,
                                                          @RequestParam(value = "pageNo",required = false)Integer pageNo){
        Subject subject=SecurityUtils.getSubject();
        SysUser currentLoginUser = (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        Long userId = currentLoginUser.getUserId();
        String accout = currentLoginUser.getAccout();
        return orgRelService.getFuzzyOrgRelPage(search,orgRootId,orgTreeId,pageSize,pageNo,userId,accout);
    }


    @ApiOperation(value = "获取组织关系类型翻页", notes = "获取组织关系类型翻页")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/getOrgRelTypePage", method = RequestMethod.GET)
    public ResponseResult<Page<OrgRefTypeVo>> getOrgRelTypePage(@RequestParam(value = "orgId",required = false)String orgId,
                                                                @RequestParam(value = "pageSize",required = false)Integer pageSize,
                                                                @RequestParam(value = "pageNo",required = false)Integer pageNo){
        return orgRelService.getOrgRelTypePage(orgId,pageSize,pageNo);
    }

}

