package cn.ffcs.uoo.web.maindata.organization.controller;


import cn.ffcs.uoo.web.maindata.organization.dto.PsonOrgVo;
import cn.ffcs.uoo.web.maindata.organization.dto.ResponseResult;
import cn.ffcs.uoo.web.maindata.organization.service.OrgContactRelService;
import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-21
 */
@RestController
@Api(value = "组织联系人", description = "组织联系人相关操作")
@RequestMapping("/orgContactRel")
public class OrgContactRelController {

    @Resource
    private OrgContactRelService orgContactRelService;

//    @ApiOperation(value = "获取组织联系人-web", notes = "获取组织联系人")
//    @ApiImplicitParams({
//    })
//    @RequestMapping(value = "/getOrgContactPage", method = RequestMethod.GET)
//    public ResponseResult getOrgContactPage(@RequestBody PsonOrgVo psonOrgVo) throws IOException {
//        return orgContactRelService.getOrgContactPage(psonOrgVo);
//    }


    @ApiOperation(value = "获取组织联系人-web", notes = "获取组织联系人")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/getOrgContactPage", method = RequestMethod.GET)
    public ResponseResult getOrgContactPage(@RequestParam(value = "orgId",required = false)String orgId,
                                            @RequestParam(value = "pageSize",required = false)Integer pageSize,
                                            @RequestParam(value = "pageNo",required = false)Integer pageNo) throws IOException {
        return orgContactRelService.getOrgContactPage(orgId,pageSize,pageNo);
    }
}

