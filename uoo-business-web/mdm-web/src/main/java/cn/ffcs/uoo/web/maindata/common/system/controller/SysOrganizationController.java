package cn.ffcs.uoo.web.maindata.common.system.controller;



import cn.ffcs.uoo.web.maindata.common.system.client.SysOrganizationClient;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysUser;
import cn.ffcs.uoo.web.maindata.common.system.vo.*;
import cn.ffcs.uoo.web.maindata.mdm.consts.LoginConsts;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
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
 * @since 2018-12-20
 */
@RestController
@RequestMapping("/sysOrganization")
public class SysOrganizationController {

    @Resource
    private SysOrganizationClient sysOrganizationClient;


    @ApiOperation(value = "查询组织树信息-web", notes = "查询组织树信息")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/getOrgRelTree", method = RequestMethod.GET)
    public ResponseResult<List<TreeNodeVo>> getOrgRelTree(@RequestParam(value = "id",required = false)String id,
                                                          @RequestParam(value = "isSync",required = false)boolean isSync,
                                                          @RequestParam(value = "userId",required = false)Long userId,
                                                          @RequestParam(value = "accout",required = false)String accout){
        Subject subject=SecurityUtils.getSubject();
        SysUser currentLoginUser = (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        userId = currentLoginUser.getUserId();
        accout = currentLoginUser.getAccout();

        return sysOrganizationClient.getOrgRelTree(id,isSync,userId,accout);
    }


    @ApiOperation(value = "检索组织信息-web", notes = "检索组织信息")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/getFuzzyOrgRelPage", method = RequestMethod.GET)
    public ResponseResult<Page<SysOrganizationVo>> getFuzzyOrgRelPage(@RequestParam(value = "search",required = false)String search,
                                                                      @RequestParam(value = "pageSize",required = false)Integer pageSize,
                                                                      @RequestParam(value = "pageNo",required = false)Integer pageNo,
                                                                      @RequestParam(value = "userId",required = false)Long userId,
                                                                      @RequestParam(value = "accout",required = false)String accout){
        Subject subject=SecurityUtils.getSubject();
        SysUser currentLoginUser = (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        userId = currentLoginUser.getUserId();
        accout = currentLoginUser.getAccout();
        return sysOrganizationClient.getFuzzyOrgRelPage(search,pageSize,pageNo,userId,accout);
    }


    @ApiOperation(value = "获取组织信息", notes = "获取组织信息")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/getRestructOrgRelTree", method = RequestMethod.GET)
    public ResponseResult<List<TreeNodeVo>> getRestructOrgRelTree(@RequestParam(value = "id",required = false)String id,
                                                                  @RequestParam(value = "userId",required = false)Long userId,
                                                                  @RequestParam(value = "accout",required = false)String accout){
        Subject subject=SecurityUtils.getSubject();
        SysUser currentLoginUser = (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        userId = currentLoginUser.getUserId();
        accout = currentLoginUser.getAccout();
        return sysOrganizationClient.getRestructOrgRelTree(id,userId,accout);
    }

    @ApiOperation(value = "获取组织关系翻页信息", notes = "获取组织关系翻页信息")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/getOrgRelPage", method = RequestMethod.GET)
    public ResponseResult<Page<SysOrganizationVo>> getOrgRelPage(@RequestParam(value = "id",required = false)String id,
                                                                 @RequestParam(value = "search",required = false)String search,
                                                                 @RequestParam(value = "pageSize",required = false)Integer pageSize,
                                                                 @RequestParam(value = "pageNo",required = false)Integer pageNo,
                                                                 @RequestParam(value = "isSearchlower",required = false)String isSearchlower,
                                                                 @RequestParam(value = "userId",required = false)Long userId,
                                                                 @RequestParam(value = "accout",required = false)String accout){
        Subject subject=SecurityUtils.getSubject();
        SysUser currentLoginUser = (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        userId = currentLoginUser.getUserId();
        accout = currentLoginUser.getAccout();
        return sysOrganizationClient.getOrgRelPage(id,search,pageSize,pageNo,isSearchlower,userId,accout);
    }


    @ApiOperation(value = "新增组织", notes = "新增组织")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/addOrg", method = RequestMethod.POST)
    public ResponseResult<TreeNodeVo> addOrg(@RequestBody SysOrganizationVo vo){
        Subject subject=SecurityUtils.getSubject();
        SysUser currentLoginUser = (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        vo.setUserId(currentLoginUser.getUserId());
        vo.setAccout(currentLoginUser.getAccout());
        return sysOrganizationClient.addOrg(vo);
    }


    @ApiOperation(value = "编辑组织", notes = "编辑组织")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/updateOrg", method = RequestMethod.POST)
    public ResponseResult<String> updateOrg(@RequestBody SysOrganizationVo vo){
        Subject subject=SecurityUtils.getSubject();
        SysUser currentLoginUser = (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        vo.setUserId(currentLoginUser.getUserId());
        vo.setAccout(currentLoginUser.getAccout());
        return sysOrganizationClient.updateOrg(vo);
    }


    @ApiOperation(value = "查询组织", notes = "查询组织")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/getOrg", method = RequestMethod.GET)
    public ResponseResult<SysOrganizationVo> getOrg(@RequestParam(value = "id",required = false)String id,
                                                    @RequestParam(value = "userId",required = false)Long userId,
                                                    @RequestParam(value = "accout",required = false)String accout){
        Subject subject=SecurityUtils.getSubject();
        SysUser currentLoginUser = (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        userId = currentLoginUser.getUserId();
        accout = currentLoginUser.getAccout();
        return sysOrganizationClient.getOrg(id,userId,accout);
    }

    @ApiOperation(value = "删除组织", notes = "删除组织")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/deleteOrg", method = RequestMethod.GET)
    public ResponseResult<String> deleteOrg(@RequestParam(value = "id",required = false)String id,
                                            @RequestParam(value = "userId",required = false)Long userId,
                                            @RequestParam(value = "accout",required = false)String accout){
        Subject subject=SecurityUtils.getSubject();
        SysUser currentLoginUser = (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        userId = currentLoginUser.getUserId();
        accout = currentLoginUser.getAccout();
        return sysOrganizationClient.deleteOrg(id,userId,accout);
    }


    @ApiOperation(value = "查询组织职位", notes = "查询组织职位")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/getOrgPositionList", method = RequestMethod.GET)
    public ResponseResult<List<SysPositionVo>> getOrgPositionList(@RequestParam(value = "id",required = false)String id,
                                                                  @RequestParam(value = "userId",required = false)Long userId,
                                                                  @RequestParam(value = "accout",required = false)String accout) throws IOException {
        Subject subject=SecurityUtils.getSubject();
        SysUser currentLoginUser = (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        userId = currentLoginUser.getUserId();
        accout = currentLoginUser.getAccout();
        return sysOrganizationClient.getOrgPositionList(id,userId,accout);
    }



    @ApiOperation(value = "查询组织人员", notes = "查询组织人员")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/getOrgUserPage", method = RequestMethod.GET)
    public ResponseResult<Page<SysUserVo>> getOrgUserPage(String id,
                                                          String search,
                                                          Integer pageSize,
                                                          Integer pageNo,
                                                          String isSearchlower,
                                                          Long userId, String accout){
        Subject subject=SecurityUtils.getSubject();
        SysUser currentLoginUser = (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        userId = currentLoginUser.getUserId();
        accout = currentLoginUser.getAccout();
        return sysOrganizationClient.getOrgUserPage(id,search,pageSize,pageNo,isSearchlower,userId,accout);
    }

    @ApiOperation(value = "查询组织人员", notes = "查询组织人员")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/getPositionUserPage", method = RequestMethod.GET)
    public ResponseResult<Page<SysUserVo>> getPositionUserPage(String id,
                                                               String search,
                                                               Integer pageSize,
                                                               Integer pageNo,
                                                               String isSearchlower,
                                                               Long userId, String accout){
        Subject subject=SecurityUtils.getSubject();
        SysUser currentLoginUser = (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        userId = currentLoginUser.getUserId();
        accout = currentLoginUser.getAccout();
        return sysOrganizationClient.getPositionUserPage(id,search,pageSize,pageNo,isSearchlower,userId,accout);
    }
}

