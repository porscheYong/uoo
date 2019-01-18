package cn.ffcs.uoo.web.maindata.organization.controller;



import cn.ffcs.uoo.web.maindata.common.system.dto.SysUser;
import cn.ffcs.uoo.web.maindata.mdm.consts.LoginConsts;
import cn.ffcs.uoo.web.maindata.organization.dto.OrgType;
import cn.ffcs.uoo.web.maindata.organization.dto.ResponseResult;
import cn.ffcs.uoo.web.maindata.organization.dto.TreeNodeVo;
import cn.ffcs.uoo.web.maindata.organization.service.OrgTypeService;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
@RequestMapping("/orgType")
@Api(value = "/orgType", description = "组织类别相关操作")
public class OrgTypeController {

    @Resource
    private OrgTypeService orgTypeService;


    @ApiOperation(value = "获取组织类别", notes = "获取组织类别")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/getOrgTypeList", method = RequestMethod.GET)
    public ResponseResult<List<OrgType>> getOrgTypeList(@RequestParam(value = "orgTypeCode",required = false)String orgTypeCode){
        return orgTypeService.getOrgTypeList(orgTypeCode);
    }

    @ApiOperation(value = "获取组织类别树", notes = "获取组织类别树")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/getOrgTypeTree", method = RequestMethod.POST)
    public ResponseResult<List<TreeNodeVo>> getOrgTypeTree(@RequestParam(value = "id",required = false)String id,
                                                           @RequestParam(value = "orgTypeCode",required = false)String orgTypeCode,
                                                           @RequestParam(value = "userId",required = false)Long userId,
                                                           @RequestParam(value = "accout",required = false)String accout){
        Subject subject=SecurityUtils.getSubject();
        SysUser currentLoginUser = (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        userId = currentLoginUser.getUserId();
        accout = currentLoginUser.getAccout();
        return orgTypeService.getOrgTypeTree(id,orgTypeCode,userId,accout);
    }

    @ApiOperation(value = "获取完整组织类别树", notes = "获取完整组织类别树")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/getFullOrgTypeTree", method = RequestMethod.GET)
    public ResponseResult<List<TreeNodeVo>> getFullOrgTypeTree(@RequestParam(value = "id",required = false)String id,
                                                               @RequestParam(value = "orgTypeCode",required = false)String orgTypeCode,
                                                               @RequestParam(value = "orgId",required = false)String orgId,
                                                               @RequestParam(value = "userId",required = false)Long userId,
                                                               @RequestParam(value = "accout",required = false)String accout){
        Subject subject=SecurityUtils.getSubject();
        SysUser currentLoginUser = (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        userId = currentLoginUser.getUserId();
        accout = currentLoginUser.getAccout();
        return orgTypeService.getFullOrgTypeTree(id,orgTypeCode,orgId,userId,accout);
    }


}

