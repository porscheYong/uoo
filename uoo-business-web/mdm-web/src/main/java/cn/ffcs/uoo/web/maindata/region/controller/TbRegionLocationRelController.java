package cn.ffcs.uoo.web.maindata.region.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.ffcs.uoo.web.maindata.region.entity.TbRegionLocationRel;
import cn.ffcs.uoo.web.maindata.region.service.RegionLocationRelService;
import cn.ffcs.uoo.web.maindata.region.vo.LocRegRelByLoc;
import cn.ffcs.uoo.web.maindata.region.vo.LocRegRelByReg;
import cn.ffcs.uoo.web.maindata.region.vo.ResponseResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 行政区域和公用管理区域关系。 前端控制器
 * </p>
 *
 * @author zxs
 * @since 2018-10-30
 */
@RestController
@RequestMapping("/region/regionLocationRel")
public class TbRegionLocationRelController {

    @Autowired
    private RegionLocationRelService relSvc;

    @ApiOperation(value = "新增行政区域和公用管理区域关系", notes = "选择某个行政区域进行添加公共区域信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "locRegRelByLoc", value = "行政区域和公用管理区域关系", required = true, dataType = "LocRegRelByReg"), })
    // @UooLog(value = "新增行政区域和公用管理区域关系", key = "addLocRegRelByLoc")
    @PostMapping("addLocRegRelByLoc")
    // @Transactional(rollbackFor=Exception.class)
    public ResponseResult addLocRegRelByLoc(LocRegRelByLoc locRegRelByLoc) {
        return relSvc.addLocRegRelByLoc(locRegRelByLoc);
    }

    @ApiOperation(value = "新增行政区域和公用管理区域关系", notes = "选择某个公共区域进行添加行政区域信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "locRegRelByReg", value = "行政区域和公用管理区域关系", required = true, dataType = "LocRegRelByReg"), })
    // @UooLog(value = "新增行政区域和公用管理区域关系", key = "addLocRegRelByReg")
    @PostMapping("addLocRegRelByReg")
    // @Transactional(rollbackFor=Exception.class)
    public ResponseResult addLocRegRelByReg(LocRegRelByReg locRegRelByReg) {
        return relSvc.addLocRegRelByReg(locRegRelByReg);
    }

    @ApiOperation(value = "删除行政区域和公用管理区域关系", notes = "单独删除某个关系")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "regionLocationRel", value = "行政区域和公用管理区域关系,只需要ID", required = true, dataType = "TbRegionLocationRel"), })
    // @UooLog(value = "单独删除行政区域和公用管理区域关系", key = "deleteRegionLocationRel")
    @PostMapping("deleteRegionLocationRel")
    // @Transactional(rollbackFor=Exception.class)
    public ResponseResult deleteRegionLocationRel(TbRegionLocationRel regionLocationRel) {
        return relSvc.deleteRegionLocationRel(regionLocationRel);
    }

    @ApiOperation(value = "行政区域和公用管理区域关系列表", notes = "行政区域和公用管理区域关系列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "分页的序号", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "pageSize", value = "每页的大小", dataType = "Integer", paramType = "path", defaultValue = "12") })
    // @UooLog(value = "行政区域和公用管理区域关系列表", key = "listRegionLocationRel")
    @GetMapping("listRegionLocationRel/pageNo={pageNo}&pageSize={pageSize}")
    public ResponseResult listRegionLocationRel(@PathVariable(value = "pageNo") Integer pageNo,
            @PathVariable(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 0 : pageNo;
        pageSize = pageSize == null ? 20 : pageSize;
        return relSvc.listRegionLocationRel(pageNo, pageSize);
    }

    @ApiOperation(value = "行政区域和公用管理区域关系列表", notes = "根据公用管理区域获取行政区域和公用管理区域关系列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "regId", value = "公用管理区域ID", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "pageNo", value = "分页的序号", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "pageSize", value = "每页的大小", dataType = "Integer", paramType = "path", defaultValue = "12") })
    // @UooLog(value = "行政区域和公用管理区域关系列表", key = "listRegionLocationRelByReg")
    @GetMapping("listRegionLocationRelByReg/regId={regId}&pageNo={pageNo}&pageSize={pageSize}")
    public ResponseResult listRegionLocationRelByReg(@PathVariable(value = "regId") Long regId,
            @PathVariable(value = "pageNo") Integer pageNo,
            @PathVariable(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 0 : pageNo;
        pageSize = pageSize == null ? 20 : pageSize;

        return relSvc.listRegionLocationRelByReg(regId, pageNo, pageSize);
    }

    @ApiOperation(value = "行政区域和公用管理区域关系列表", notes = "根据行政区域获取行政区域和公用管理区域关系列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "locId", value = "行政区域ID", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "pageNo", value = "分页的序号", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "pageSize", value = "每页的大小", dataType = "Integer", paramType = "path", defaultValue = "12") })
    // @UooLog(value = "行政区域和公用管理区域关系列表", key = "listRegionLocationRelByLoc")
    @GetMapping("listRegionLocationRelByLoc/locId={locId}&pageNo={pageNo}&pageSize={pageSize}")
    public ResponseResult listRegionLocationRelByLoc(@PathVariable(value = "locId") Long locId,
            @PathVariable(value = "pageNo") Integer pageNo,
            @PathVariable(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 0 : pageNo;
        pageSize = pageSize == null ? 20 : pageSize;
        return relSvc.listRegionLocationRelByLoc(locId, pageNo, pageSize);
    }

    @ApiOperation(value = "新增行政区域和公用管理区域关系", notes = "新增行政区域和公用管理区域关系")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "regLocRel", value = "行政区域和公用管理区域关系信息", required = true, dataType = "TbRegionLocationRel"), })
    // @UooLog(value = "新增行政区域和公用管理区域关系", key = "addRegionLocationRel")
    @PostMapping("addRegionLocationRel")
    // @Transactional
    public ResponseResult addRegionLocationRel(TbRegionLocationRel regLocRel) {

        return relSvc.addRegionLocationRel(regLocRel);
    }

    @ApiOperation(value = "修改行政区域和公用管理区域关系", notes = "修改行政区域和公用管理区域关系")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "regLocRel", value = "行政区域和公用管理区域关系信息", required = true, dataType = "TbreaCode"), })
    // @UooLog(value = "修改行政区域和公用管理区域关系", key = "updateRegionLocationRel")
    @PostMapping("updateRegionLocationRel")
    // @Transactional
    public ResponseResult updateRegionLocationRel(TbRegionLocationRel regLocRel) {

        return relSvc.updateRegionLocationRel(regLocRel);
    }

    @ApiOperation(value = "根据ID获取单条数据", notes = "根据ID获取单条数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long", paramType = "path"), })
    // @UooLog(value = "根据ID获取单条数据", key = "getRegionLocationRel")
    @GetMapping("getRegionLocationRel/id={id}")
    public ResponseResult getRegionLocationRel(@PathVariable(value = "id") Long id) {
        return relSvc.getRegionLocationRel(id);
    }
}