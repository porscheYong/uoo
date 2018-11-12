package cn.ffcs.uoo.web.maindata.organization.controller;

import cn.ffcs.uoo.web.maindata.busipublic.vo.ResponseResult;
import cn.ffcs.uoo.web.maindata.organization.dto.Org;
import cn.ffcs.uoo.web.maindata.organization.dto.OrgVo;
import cn.ffcs.uoo.web.maindata.organization.service.OrgService;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-09-25
 */
@RestController
@RequestMapping(value = "/org" , produces = {"application/json;charset=UTF-8"})
@Api(value = "/org", description = "组织相关操作")
public class OrgController {

    @Resource
    private OrgService orgService;

    @ApiOperation(value = "新增组织", notes = "新增组织")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/addOrg", method = RequestMethod.POST)
    public ResponseResult addOrg(Org org) {
        return orgService.addOrg(org);
    }


    @ApiOperation(value = "修改组织", notes = "修改组织")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/updateOrg", method = RequestMethod.POST)
    public ResponseResult updateOrg(Org org) {
        return orgService.updateOrg(org);
    }


    @ApiOperation(value = "获取组织信息", notes = "获取组织信息")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/getOrg", method = RequestMethod.POST)
    public ResponseResult getOrg(String orgId) {
        return orgService.getOrg(orgId);
    }

    @ApiOperation(value = "获取组织关系分页", notes = "获取组织关系分页")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/getOrgRelPage", method = RequestMethod.POST)
    public ResponseResult getOrgRelPage(OrgVo orgVo) {
        return orgService.getOrgRelPage(orgVo);
    }


    @ApiOperation(value = "获取组织分页", notes = "获取组织分页")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/getOrgPage", method = RequestMethod.POST)
    public ResponseResult getOrgPage(OrgVo orgVo) {
        return orgService.getOrgPage(orgVo);
    }
}

