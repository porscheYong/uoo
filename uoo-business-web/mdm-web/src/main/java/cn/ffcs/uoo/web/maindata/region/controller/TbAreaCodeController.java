package cn.ffcs.uoo.web.maindata.region.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.ffcs.uoo.web.maindata.region.entity.TbAreaCode;
import cn.ffcs.uoo.web.maindata.region.service.AreaCodeService;
import cn.ffcs.uoo.web.maindata.region.vo.ResponseResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 区号 前端控制器
 * </p>
 *
 * @author zxs
 * @since 2018-10-30
 */
@RestController
@RequestMapping("/region/areaCode")
public class TbAreaCodeController  {
    
    @Autowired
    private AreaCodeService areaCodeService;
    
    @ApiOperation(value = "根据ID获取单条数据", notes = "根据ID获取单条数据")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long",paramType="path"),
    })
    //@UooLog(value = "根据ID获取单条数据", key = "getAreaCode")
    @GetMapping("getAreaCode/id={id}")
    public ResponseResult getAreaCode(@PathVariable(value = "id") Long id){
        return areaCodeService.getAreaCode(id);
    }
    
    @ApiOperation(value = "区号列表", notes = "区号列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "pageNo", value = "分页的序号", required = true, dataType = "Integer",paramType="path"),
        @ApiImplicitParam(name = "pageSize", value = "每页的大小", dataType = "Integer",paramType="path",defaultValue = "12")
    })
    //@UooLog(value = "区号列表", key = "listAreaCode")
    @GetMapping("listAreaCode/pageNo={pageNo}&pageSize={pageSize}")
    public ResponseResult listAreaCode(@PathVariable(value = "pageNo") Integer pageNo, @PathVariable(value = "pageSize",required = false) Integer pageSize) {
        pageNo = pageNo==null?0:pageNo;
        pageSize = pageSize==null?20:pageSize;
        return areaCodeService.listAreaCode(pageNo, pageSize);
    }
    
    @ApiOperation(value = "新增区号", notes = "新增区号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "areaCode", value = "区号信息", required = true, dataType = "TbAreaCode"), })
    //@UooLog(value = "新增区号", key = "addAreaCode")
    @PostMapping("addAreaCode")
    //@Transactional
    public ResponseResult addAreaCode(TbAreaCode areaCode) {
         
        return areaCodeService.addAreaCode(areaCode);
    }
    @ApiOperation(value = "修改区号", notes = "修改区号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "areaCode", value = "区号信息", required = true, dataType = "TbreaCode"), })
    //@UooLog(value = "修改区号", key = "updateAreaCode")
    @PostMapping("updateAreaCode")
    //@Transactional
    public ResponseResult updateAreaCode(TbAreaCode areaCode) {
         
        return areaCodeService.updateAreaCode(areaCode);
    }
    
    @ApiOperation(value = "删除区号", notes = "删除区号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "areaCode", value = "区号信息", required = true, dataType = "TbAreaCode"), })
    //@UooLog(value = "删除区号", key = "deleteAreaCode")
    @PostMapping("deleteAreaCode")
    //@Transactional
    public ResponseResult deleteAreaCode(TbAreaCode areaCode) {
        return areaCodeService.deleteAreaCode(areaCode);
    }
    
}
