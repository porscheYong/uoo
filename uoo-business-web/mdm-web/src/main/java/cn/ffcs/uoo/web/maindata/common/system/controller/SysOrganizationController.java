package cn.ffcs.uoo.web.maindata.common.system.controller;



import cn.ffcs.uoo.web.maindata.common.system.client.SysOrganizationClient;
import cn.ffcs.uoo.web.maindata.common.system.vo.ResponseResult;
import cn.ffcs.uoo.web.maindata.common.system.vo.SysOrganizationVo;
import cn.ffcs.uoo.web.maindata.common.system.vo.TreeNodeVo;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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

        return sysOrganizationClient.getFuzzyOrgRelPage(search,pageSize,pageNo,userId,accout);
    }


    @ApiOperation(value = "获取组织信息", notes = "获取组织信息")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/getRestructOrgRelTree", method = RequestMethod.GET)
    public ResponseResult<List<TreeNodeVo>> getRestructOrgRelTree(@RequestParam(value = "id",required = false)String id,
                                                                  @RequestParam(value = "userId",required = false)Long userId,
                                                                  @RequestParam(value = "accout",required = false)String accout){

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

        return sysOrganizationClient.getOrgRelPage(id,search,pageSize,pageNo,isSearchlower,userId,accout);
    }


    @ApiOperation(value = "新增组织", notes = "新增组织")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/addOrg", method = RequestMethod.POST)
    public ResponseResult<TreeNodeVo> addOrg(@RequestBody SysOrganizationVo vo){

        return sysOrganizationClient.addOrg(vo);
    }


    @ApiOperation(value = "编辑组织", notes = "编辑组织")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/updateOrg", method = RequestMethod.POST)
    public ResponseResult<String> updateOrg(@RequestBody SysOrganizationVo vo){

        return sysOrganizationClient.updateOrg(vo);
    }


    @ApiOperation(value = "查询组织", notes = "查询组织")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/getOrg", method = RequestMethod.GET)
    public ResponseResult<SysOrganizationVo> getOrg(@RequestParam(value = "id",required = false)String id){
        return sysOrganizationClient.getOrg(id);
    }



}

