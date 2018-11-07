package cn.ffcs.uoo.web.maindata.region.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.ffcs.uoo.web.maindata.region.dto.TbPoliticalLocation;
import cn.ffcs.uoo.web.maindata.region.service.PoliticalLocationService;
import cn.ffcs.uoo.web.maindata.region.vo.ResponseResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 行政区域 前端控制器
 * </p>
 *
 * @author zxs
 * @since 2018-10-30
 */
@RestController
@RequestMapping("/region/politicalLocation")
public class TbPoliticalLocationController  {
    @Autowired
    private PoliticalLocationService polLocSvc;
    
    @ApiOperation(value = "根据ID获取单条数据", notes = "根据ID获取单条数据")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long",paramType="path"),
    })
    //@UooLog(value = "根据ID获取单条数据", key = "getPoliticalLocation")
    @GetMapping("getPoliticalLocation/id={id}")
    public ResponseResult getPoliticalLocation(@PathVariable(value = "id") Long id){
         
        return polLocSvc.getPoliticalLocation(id);
    }
    
    @ApiOperation(value = "行政区域列表", notes = "行政区域列表")
    //@UooLog(value = "行政区域列表", key = "listAllPoliticalLocation")
    @GetMapping("listAllPoliticalLocation")
    public ResponseResult listAllPoliticalLocation() {
        //只查询有效的
        return polLocSvc.listAllPoliticalLocation();
    }

    @ApiOperation(value = "新增行政区域", notes = "新增行政区域")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "polLoc", value = "行政区域信息", required = true, dataType = "TbPoliticalLocation"), })
    //@UooLog(value = "新增行政区域", key = "addPoliticalLocation")
    @PostMapping("addPoliticalLocation")
    //@Transactional
    public ResponseResult addPoliticalLocation(TbPoliticalLocation polLoc) {
         
        return polLocSvc.addPoliticalLocation(polLoc);
    }
    
    @ApiOperation(value = "修改行政区域", notes = "修改行政区域")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "polLoc", value = "行政区域信息", required = true, dataType = "TbPoliticalLocation"), })
    //@UooLog(value = "修改行政区域", key = "updatePoliticalLocation")
    @PostMapping("updatePoliticalLocation")
    //@Transactional
    public ResponseResult updatePoliticalLocation(TbPoliticalLocation polLoc) {
         
        return polLocSvc.updatePoliticalLocation(polLoc);
    }
    
    @ApiOperation(value = "删除行政区域", notes = "删除行政区域")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "polLoc", value = "行政区域信息", required = true, dataType = "TbPoliticalLocation"), })
    //@UooLog(value = "删除行政区域", key = "deletePoliticalLocation")
    @PostMapping("deletePoliticalLocation")
    //@Transactional(rollbackFor=Exception.class)
    public ResponseResult deletePoliticalLocation(TbPoliticalLocation polLoc) {
         
        return polLocSvc.deletePoliticalLocation(polLoc);
    }
}