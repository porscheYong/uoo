package cn.ffcs.uoo.web.maindata.organization.controller;


import cn.ffcs.uoo.web.maindata.organization.dto.OrgRelType;
import cn.ffcs.uoo.web.maindata.organization.dto.OrgTree;
import cn.ffcs.uoo.web.maindata.organization.dto.ResponseResult;
import cn.ffcs.uoo.web.maindata.organization.service.OrgTreeService;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  组织树
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-09-25
 */
@RestController
@RequestMapping("/orgTree")
@Api(value = "/org", description = "组织树相关操作")
public class OrgTreeController {


    @Resource
    private OrgTreeService orgTreeService;

    @ApiOperation(value = "新增组织树", notes = "新增组织树")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/addOrgTree", method = RequestMethod.POST)
    public ResponseResult<String> addOrgTree(@RequestBody OrgTree orgTree){
        return orgTreeService.addOrgTree(orgTree);
    }


    @ApiOperation(value = "更新组织树", notes = "更新组织树")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/updateOrgTree", method = RequestMethod.POST)
    public ResponseResult<String> updateOrgTree(@RequestBody OrgTree orgTree){
        return orgTreeService.updateOrgTree(orgTree);
    }


    @ApiOperation(value = "获取组织树", notes = "获取组织树")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/getOrgTreeList", method = RequestMethod.GET)
    public ResponseResult<List<OrgTree>> getOrgTreeList(@RequestParam(value = "orgTreeId",required = false)String orgTreeId,
                                                        @RequestParam(value = "orgRootId",required = false)String orgRootId){
        return orgTreeService.getOrgTreeList(orgTreeId,orgRootId);
    }


    @ApiOperation(value = "查询组织树信息", notes = "查询组织树信息")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/getOrgTree",method = RequestMethod.GET)
    public ResponseResult<OrgTree> getOrgTree(@RequestParam(value = "orgTreeId",required = false)String orgTreeId){
        return orgTreeService.getOrgTree(orgTreeId);
    }

}
