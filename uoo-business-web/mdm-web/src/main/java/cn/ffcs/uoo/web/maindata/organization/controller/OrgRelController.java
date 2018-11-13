package cn.ffcs.uoo.web.maindata.organization.controller;


import cn.ffcs.uoo.web.maindata.organization.dto.*;
import cn.ffcs.uoo.web.maindata.organization.service.OrgRelService;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping(value = "/orgRel",produces = {"application/json;charset=UTF-8"})
@Api(value = "/orgRel", description = "组织关系相关操作")
public class OrgRelController {


    @Resource
    private OrgRelService orgRelService;


    @ApiOperation(value = "获取组织树", notes = "获取组织树")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/getOrgRelTree/id={id}&orgRootId={orgRootId}&relCode={relCode}&isOpen={isOpen}&isAsync={isAsync}&isRoot={isRoot}", method = RequestMethod.POST)
    public ResponseResult<List<TreeNodeVo>> getOrgRelTree(@PathVariable(value = "id")String id,
                                                          @PathVariable(value = "orgRootId")String orgRootId,
                                                          @PathVariable(value = "relCode")String relCode,
                                                          @PathVariable(value = "isOpen")boolean isOpen,
                                                          @PathVariable(value = "isAsync")boolean isAsync,
                                                          @PathVariable(value = "isRoot")boolean isRoot){
        return orgRelService.getOrgRelTree(id,orgRootId,relCode,isOpen, isAsync,isRoot);
    }



    @ApiOperation(value = "获取重构组织树", notes = "获取重构组织树")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/getRestructOrgRelTree/id={id}&orgRootId={orgRootId}&isFull={isFull}", method = RequestMethod.GET)
    public ResponseResult<List<TreeNodeVo>> getRestructOrgRelTree(@PathVariable(value = "id")String id,@PathVariable(value = "orgRootId")String orgRootId,@PathVariable(value = "isFull")boolean isFull){
        return orgRelService.getRestructOrgRelTree(id,orgRootId,isFull);
       // return null;
    }


    @ApiOperation(value = "获取目标组织树指定层级", notes = "获取目标组织树指定层级")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/getTarOrgRelTreeAndLv/orgRootId={orgRootId}&lv={lv}&curOrgid={curOrgid}&isFull={isFull}", method = RequestMethod.GET)
    public ResponseResult<List<TreeNodeVo>> getTarOrgRelTreeAndLv(@PathVariable(value = "orgRootId")String orgRootId,
                                                                  @PathVariable(value = "lv")String lv,
                                                                  @PathVariable(value = "curOrgid")String curOrgid,
                                                                  @PathVariable(value = "isFull")boolean isFull){
        return orgRelService.getTarOrgRelTreeAndLv(orgRootId,lv,curOrgid,isFull);
    }


    @ApiOperation(value = "增加组织关系", notes = "增加组织关系")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/addOrgRel", method = RequestMethod.POST)
    public ResponseResult<String> addOrgRel(@RequestBody Org org){
        return orgRelService.addOrgRel(org);
    }


    @ApiOperation(value = "检索组织树", notes = "检索组织树")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/getFuzzyOrgRelPage", method = RequestMethod.POST)
    public ResponseResult<Page<OrgVo>> getFuzzyOrgRelPage(@RequestBody OrgVo orgVo){
        return orgRelService.getFuzzyOrgRelPage(orgVo);
    }


    @ApiOperation(value = "获取组织关系类型翻页", notes = "获取组织关系类型翻页")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/getOrgRelTypePage", method = RequestMethod.POST)
    public ResponseResult<Page<OrgRefTypeVo>> getOrgRelTypePage(@RequestBody OrgRefTypeVo orgRefTypeVo){
        return orgRelService.getOrgRelTypePage(orgRefTypeVo);
    }

}

