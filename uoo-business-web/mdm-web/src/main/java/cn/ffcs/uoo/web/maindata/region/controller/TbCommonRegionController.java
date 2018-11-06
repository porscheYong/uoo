package cn.ffcs.uoo.web.maindata.region.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.ffcs.uoo.web.maindata.region.entity.TbCommonRegion;
import cn.ffcs.uoo.web.maindata.region.service.CommonRegionService;
import cn.ffcs.uoo.web.maindata.region.vo.ResponseResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 指对于各种专业电信管理区域的共性管理区域信息的抽象表达，包括省公司、本地网、营业区。因为使用目的不同，可以定义不同使用类型的管理区域，如：CRM使用、计费使用、营销使用等。
 * 前端控制器
 * </p>
 *
 * @author zxs
 * @since 2018-10-30
 */
@RestController
@RequestMapping("/region/commonRegion/")
public class TbCommonRegionController {
    @Autowired
    private CommonRegionService regionService;

    @ApiOperation(value = "根据ID获取单条数据", notes = "根据ID获取单条数据")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long",paramType="path"),
    })
    //@UooLog(value = "根据ID获取单条数据", key = "getCommonRegion")
    @GetMapping("getCommonRegion/id={id}")
    public ResponseResult getCommonRegion(@PathVariable(value = "id") Long id){
        return regionService.getCommonRegion(id);
    }
    
    @ApiOperation(value = "公共管理区域列表", notes = "公共管理区域列表")
    //@UooLog(value = "公共管理区域列表", key = "listAllCommonRegion")
    @GetMapping("listAllCommonRegion")
    public ResponseResult listAllCommonRegion() {
        return regionService.listAllCommonRegion();
    }

    @ApiOperation(value = "新增公共管理区域", notes = "新增公共管理区域")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commonRegion", value = "公共管理区域信息", required = true, dataType = "TbCommonRegion"), })
    //@UooLog(value = "新增公共管理区域", key = "addCommonRegion")
    @PostMapping("addCommonRegion")
    //@Transactional
    public ResponseResult addCommonRegion(TbCommonRegion commonRegion) {
        return regionService.addCommonRegion(commonRegion);
    }
    @ApiOperation(value = "修改公共管理区域", notes = "修改公共管理区域")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commonRegion", value = "公共管理区域信息", required = true, dataType = "TbCommonRegion"), })
    //@UooLog(value = "修改公共管理区域", key = "updateCommonRegion")
    @PostMapping("updateCommonRegion")
    //@Transactional
    public ResponseResult updateCommonRegion(TbCommonRegion commonRegion) {
        return regionService.updateCommonRegion(commonRegion);
    }
    
    @ApiOperation(value = "删除公共管理区域", notes = "删除公共管理区域")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commonRegion", value = "公共管理区域信息", required = true, dataType = "TbCommonRegion"), })
    //@UooLog(value = "删除公共管理区域", key = "deleteCommonRegion")
    @PostMapping("deleteCommonRegion")
    //@Transactional(rollbackFor=Exception.class)
    public ResponseResult deleteCommonRegion(TbCommonRegion commonRegion) {
        
        return regionService.deleteCommonRegion(commonRegion);
    }
}
