package cn.ffcs.uoo.web.maindata.organization.controller;



import cn.ffcs.uoo.web.maindata.common.system.dto.SysUser;
import cn.ffcs.uoo.web.maindata.mdm.consts.LoginConsts;
import cn.ffcs.uoo.web.maindata.organization.dto.PsonOrgVo;
import cn.ffcs.uoo.web.maindata.organization.dto.ResponseResult;
import cn.ffcs.uoo.web.maindata.organization.service.OrgPersonRelService;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-21
 */
@RestController
@RequestMapping(value = "/orgPersonRel")
public class OrgPersonRelController {

    @Resource
    private OrgPersonRelService orgPersonRelService;

    @ApiOperation(value = "新增组织人员关系", notes = "新增组织人员关系")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/addOrgPsn", method = RequestMethod.POST)
    public ResponseResult addOrgPsn(@RequestBody List<PsonOrgVo> psonOrgVo){
        Subject subject=SecurityUtils.getSubject();
        SysUser currentLoginUser = (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        Long userId = currentLoginUser.getUserId();
        String accout = currentLoginUser.getAccout();
        if(psonOrgVo!=null && psonOrgVo.size()>0){
            for(PsonOrgVo vo: psonOrgVo){
                vo.setSysUserId(userId);
                vo.setAccout(accout);
            }
        }
        return orgPersonRelService.addOrgPsn(psonOrgVo);
    }

    @ApiOperation(value = "新增组织人员关系2", notes = "新增组织人员关系")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/addOrgPsn2", method = RequestMethod.POST)
    public ResponseResult addOrgPsn2(@RequestBody List<PsonOrgVo> psonOrgVo){
        Subject subject=SecurityUtils.getSubject();
        SysUser currentLoginUser = (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        Long userId = currentLoginUser.getUserId();
        String accout = currentLoginUser.getAccout();
        if(psonOrgVo!=null && psonOrgVo.size()>0){
            for(PsonOrgVo vo: psonOrgVo){
                vo.setSysUserId(userId);
                vo.setAccout(accout);
            }
        }
        return orgPersonRelService.addOrgPsn2(psonOrgVo);
    }

    @ApiOperation(value = "更新组织人员关系", notes = "新增组织人员关系")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/updateOrgPsn", method = RequestMethod.POST)
    public ResponseResult<String> updateOrgPsn(@RequestBody PsonOrgVo psonOrgVo){
        Subject subject=SecurityUtils.getSubject();
        SysUser currentLoginUser = (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        Long userId = currentLoginUser.getUserId();
        String accout = currentLoginUser.getAccout();
        psonOrgVo.setSysUserId(userId);
        psonOrgVo.setAccout(accout);
        return orgPersonRelService.updateOrgPsn(psonOrgVo);
    }

    @ApiOperation(value = "删除组织人员关系", notes = "删除组织人员关系")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/deleteOrgPsn", method = RequestMethod.POST)
    public ResponseResult<String> deleteOrgPsn(@RequestBody PsonOrgVo psonOrgVo){
        Subject subject=SecurityUtils.getSubject();
        SysUser currentLoginUser = (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        Long userId = currentLoginUser.getUserId();
        String accout = currentLoginUser.getAccout();
        psonOrgVo.setSysUserId(userId);
        psonOrgVo.setAccout(accout);
        return orgPersonRelService.deleteOrgPsn(psonOrgVo);
    }

    @ApiOperation(value = "删除人员所有相关信息", notes = "删除人员所有相关信息")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/deletePsnRel", method = RequestMethod.POST)
    public ResponseResult<String> deletePsnRel(@RequestBody PsonOrgVo psonOrgVo){
        Subject subject=SecurityUtils.getSubject();
        SysUser currentLoginUser = (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        Long userId = currentLoginUser.getUserId();
        String accout = currentLoginUser.getAccout();
        psonOrgVo.setSysUserId(userId);
        psonOrgVo.setAccout(accout);
        return orgPersonRelService.deletePsnRel(psonOrgVo);
    }


    @ApiOperation(value = "获取组织人员关系列表", notes = "获取组织人员关系列表")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/getPerOrgRelList", method = RequestMethod.GET)
    public ResponseResult<List<PsonOrgVo>> getPerOrgRelList(
            @RequestParam(value = "orgTreeId",required = false)String orgTreeId,
            @RequestParam(value = "personnelId",required = false)String personnelId){
        Subject subject=SecurityUtils.getSubject();
        SysUser currentLoginUser = (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        Long userId = currentLoginUser.getUserId();
        String accout = currentLoginUser.getAccout();
        return orgPersonRelService.getPerOrgRelList(orgTreeId,personnelId,userId,accout);
    }

    @ApiOperation(value = "获取组织人员关系翻页", notes = "获取组织人员关系翻页")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/getPerOrgRelPage", method = RequestMethod.GET)
    public ResponseResult<Page<PsonOrgVo>> getPerOrgRelPage(@RequestParam(value = "orgId",required = false)String orgId,
                                                            @RequestParam(value = "orgTreeId",required = false)String orgTreeId,
                                                            @RequestParam(value = "refCode",required = false)String refCode,
                                                            @RequestParam(value = "orgRootId",required = false)String orgRootId,
                                                            @RequestParam(value = "personnelId",required = false)String personnelId,
                                                            @RequestParam(value = "isSearchlower",required = false)String isSearchlower,
                                                            @RequestParam(value = "search",required = false)String search,
                                                            @RequestParam(value = "pageSize",required = false)Integer pageSize,
                                                            @RequestParam(value = "pageNo",required = false)Integer pageNo){
        Subject subject=SecurityUtils.getSubject();
        SysUser currentLoginUser = (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        Long userId = currentLoginUser.getUserId();
        String accout = currentLoginUser.getAccout();
        return orgPersonRelService.getPerOrgRelPage(orgId,orgTreeId,refCode,orgRootId,personnelId,isSearchlower,search,pageSize,pageNo,userId,accout);
    }


    @ApiOperation(value = "获取组织用户关系翻页", notes = "获取组织用户关系翻页")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/getUserOrgRelPage", method = RequestMethod.GET)
    public ResponseResult<Page<PsonOrgVo>> getUserOrgRelPage(@RequestParam(value = "orgId",required = false)String orgId,
                                                             @RequestParam(value = "orgTreeId",required = false)String orgTreeId,
                                                             @RequestParam(value = "refCode",required = false)String refCode,
                                                             @RequestParam(value = "isSearchlower",required = false)String isSearchlower,
                                                             @RequestParam(value = "search",required = false)String search,
                                                             @RequestParam(value = "pageSize",required = false)Integer pageSize,
                                                             @RequestParam(value = "pageNo",required = false)Integer pageNo){
        Subject subject=SecurityUtils.getSubject();
        SysUser currentLoginUser = (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        Long userId = currentLoginUser.getUserId();
        String accout = currentLoginUser.getAccout();
        return orgPersonRelService.getUserOrgRelPage(orgId,orgTreeId,refCode,isSearchlower,search,pageSize,pageNo,userId,accout);
    }


}

