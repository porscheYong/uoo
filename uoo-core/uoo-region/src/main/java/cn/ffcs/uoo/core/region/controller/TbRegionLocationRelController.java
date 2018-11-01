package cn.ffcs.uoo.core.region.controller;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;

import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.base.controller.BaseController;
import cn.ffcs.uoo.core.region.consts.DeleteConsts;
import cn.ffcs.uoo.core.region.entity.TbCommonRegion;
import cn.ffcs.uoo.core.region.entity.TbPoliticalLocation;
import cn.ffcs.uoo.core.region.entity.TbRegionLocationRel;
import cn.ffcs.uoo.core.region.service.ITbCommonRegionService;
import cn.ffcs.uoo.core.region.service.ITbPoliticalLocationService;
import cn.ffcs.uoo.core.region.service.ITbRegionLocationRelService;
import cn.ffcs.uoo.core.region.vo.LocRegRelByLoc;
import cn.ffcs.uoo.core.region.vo.LocRegRelByReg;
import cn.ffcs.uoo.core.region.vo.ResponseResult;
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
@RequestMapping("/tbRegionLocationRel")
public class TbRegionLocationRelController extends BaseController {
    
    @Autowired
    private ITbRegionLocationRelService relSvc;
    @Autowired
    private ITbCommonRegionService regSvc;
    @Autowired
    private ITbPoliticalLocationService locSvc;
    
    
    @SuppressWarnings("unchecked")
    @ApiOperation(value = "新增行政区域和公用管理区域关系", notes = "选择某个行政区域进行添加公共区域信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "locRegRelByLoc", value = "行政区域和公用管理区域关系", required = true, dataType = "LocRegRelByReg"), })
    @UooLog(value = "新增行政区域和公用管理区域关系", key = "addLocRegRelByLoc")
    @PostMapping("addLocRegRelByLoc")
    @Transactional(rollbackFor=Exception.class)
    public ResponseResult addLocRegRelByLoc(LocRegRelByLoc locRegRelByLoc) {
        Long locId = locRegRelByLoc.getLocId();
        List<Long> regIds = locRegRelByLoc.getRegIds();
        //先校验数据
        TbPoliticalLocation location = locSvc.selectById(locId);
        if(location==null || !DeleteConsts.VALID.equals(location.getStatusCd())){
            return ResponseResult.createErrorResult("错误的行政区域数据,请刷新重试");
        }
        List<TbCommonRegion> regs = regSvc.selectBatchIds(regIds);
        if(regs==null||regs.size()!=regIds.size()){
            return ResponseResult.createErrorResult("错误的公用管理区域数据,请刷新重试");
        }
        for (TbCommonRegion r : regs) {
            if(!DeleteConsts.VALID.equals(r.getStatusCd())){
                return ResponseResult.createErrorResult("错误的公用管理区域数据,请刷新重试");
            }
        }
        //删除行政相关的id
        //再添加本次更新的id
        Wrapper<TbRegionLocationRel> wrapper=Condition.create().eq("LOC_ID", locId);
        relSvc.delete(wrapper);
        
        for (Long regId : regIds) {
            TbRegionLocationRel rel=new TbRegionLocationRel();
            rel.setCommonRegionId(regId);
            rel.setLocId(locId);
            rel.setRegionLocRelId(relSvc.getId());
            rel.setStatusCd(DeleteConsts.VALID);
            rel.setCreateUser(locRegRelByLoc.getCreateUser());
            rel.setCreateDate(new Date());
            rel.setUpdateDate(new Date());
            rel.setStatusDate(new Date());
            relSvc.insert(rel);
        }
        
        return ResponseResult.createSuccessResult("success");
    }
    @SuppressWarnings("unchecked")
    @ApiOperation(value = "新增行政区域和公用管理区域关系", notes = "选择某个公共区域进行添加行政区域信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "polLoc", value = "行政区域和公用管理区域关系", required = true, dataType = "LocRegRelByReg"), })
    @UooLog(value = "新增行政区域和公用管理区域关系", key = "addLocRegRelByReg")
    @PostMapping("addLocRegRelByReg")
    @Transactional(rollbackFor=Exception.class)
    public ResponseResult addLocRegRelByReg(LocRegRelByReg locRegRelByReg) {
        List<Long> locIds = locRegRelByReg.getLocIds();
        Long regId = locRegRelByReg.getRegId();
        TbCommonRegion commonRegion = regSvc.selectById(regId);
        if(commonRegion==null || !DeleteConsts.VALID.equals(commonRegion.getStatusCd())){
            return ResponseResult.createErrorResult("错误的公用管理区域数据,请刷新重试");
        }
        List<TbPoliticalLocation> locs = locSvc.selectBatchIds(locIds);
        if(locs==null||locs.size()!=locs.size()){
            return ResponseResult.createErrorResult("错误的行政区域数据,请刷新重试");
        }
        for (TbPoliticalLocation loc : locs) {
            if(!DeleteConsts.VALID.equals(loc.getStatusCd())){
                return ResponseResult.createErrorResult("错误的行政区域数据,请刷新重试");
            }
        }
        //删除行政相关的id
        //再添加本次更新的id
        Wrapper<TbRegionLocationRel> wrapper=Condition.create().eq("COMMON_REGION_ID", regId);
        relSvc.delete(wrapper);
        
        for (Long locId : locIds) {
            TbRegionLocationRel rel=new TbRegionLocationRel();
            rel.setCommonRegionId(regId);
            rel.setLocId(locId);
            rel.setRegionLocRelId(relSvc.getId());
            rel.setStatusCd(DeleteConsts.VALID);
            rel.setCreateUser(locRegRelByReg.getCreateUser());
            rel.setCreateDate(new Date());
            rel.setUpdateDate(new Date());
            rel.setStatusDate(new Date());
            relSvc.insert(rel);
        }
        return ResponseResult.createSuccessResult("success");
    }
    
    @ApiOperation(value = "删除行政区域和公用管理区域关系", notes = "单独删除某个关系")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "regionLocationRel", value = "行政区域和公用管理区域关系,只需要ID", required = true, dataType = "TbRegionLocationRel"), })
    @UooLog(value = "单独删除行政区域和公用管理区域关系", key = "deleteRegionLocationRel")
    @PostMapping("deleteRegionLocationRel")
    @Transactional(rollbackFor=Exception.class)
    public ResponseResult deleteRegionLocationRel(TbRegionLocationRel regionLocationRel) {
        Long id = regionLocationRel.getRegionLocRelId();
        relSvc.deleteById(id);
        return ResponseResult.createSuccessResult("success");
    }
    
    @ApiOperation(value = "行政区域和公用管理区域关系列表", notes = "行政区域和公用管理区域关系列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "pageNo", value = "分页的序号", required = true, dataType = "Integer",paramType="path"),
        @ApiImplicitParam(name = "pageSize", value = "每页的大小", dataType = "Integer",paramType="path",defaultValue = "12")
    })
    @UooLog(value = "行政区域和公用管理区域关系列表", key = "listRegionLocationRel")
    @GetMapping("listRegionLocationRel/pageNo={pageNo}&pageSize={pageSize}")
    public ResponseResult listRegionLocationRel(@PathVariable(value = "pageNo") Integer pageNo, @PathVariable(value = "pageSize",required = false) Integer pageSize) {
        pageNo = pageNo==null?0:pageNo;
        pageSize = pageSize==null?20:pageSize;
        @SuppressWarnings("unchecked")
        Wrapper<TbRegionLocationRel> wrapper = Condition.create().eq("STATUS_CD",DeleteConsts.VALID).orderBy("UPDATE_DATE", false);
        Page<TbRegionLocationRel> page = relSvc.selectPage(new Page<TbRegionLocationRel>(pageNo, pageSize), wrapper);
        ResponseResult result = ResponseResult.createSuccessResult(page.getRecords(), "", pageNo, pageSize);
        return result;
    }
    @ApiOperation(value = "行政区域和公用管理区域关系列表", notes = "根据公用管理区域获取行政区域和公用管理区域关系列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "regId", value = "公用管理区域ID", required = true, dataType = "Long",paramType="path"),
        @ApiImplicitParam(name = "pageNo", value = "分页的序号", required = true, dataType = "Integer",paramType="path"),
        @ApiImplicitParam(name = "pageSize", value = "每页的大小", dataType = "Integer",paramType="path",defaultValue = "12")
    })
    @UooLog(value = "行政区域和公用管理区域关系列表", key = "listRegionLocationRelByReg")
    @GetMapping("listRegionLocationRelByReg/regId={regId}&pageNo={pageNo}&pageSize={pageSize}")
    public ResponseResult listRegionLocationRelByReg(@PathVariable(value = "regId")Long regId,@PathVariable(value = "pageNo") Integer pageNo, @PathVariable(value = "pageSize",required = false) Integer pageSize) {
        pageNo = pageNo==null?0:pageNo;
        pageSize = pageSize==null?20:pageSize;
        @SuppressWarnings("unchecked")
        Wrapper<TbRegionLocationRel> wrapper = Condition.create().eq("STATUS_CD",DeleteConsts.VALID).eq("COMMON_REGION_ID", regId).orderBy("UPDATE_DATE", false);
        Page<TbRegionLocationRel> page = relSvc.selectPage(new Page<TbRegionLocationRel>(pageNo, pageSize), wrapper);
        ResponseResult result = ResponseResult.createSuccessResult(page.getRecords(), "", pageNo, pageSize);
        return result;
    }
    @ApiOperation(value = "行政区域和公用管理区域关系列表", notes = "根据行政区域获取行政区域和公用管理区域关系列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "locId", value = "行政区域ID", required = true, dataType = "Long",paramType="path"),
        @ApiImplicitParam(name = "pageNo", value = "分页的序号", required = true, dataType = "Integer",paramType="path"),
        @ApiImplicitParam(name = "pageSize", value = "每页的大小", dataType = "Integer",paramType="path",defaultValue = "12")
    })
    @UooLog(value = "行政区域和公用管理区域关系列表", key = "listRegionLocationRelByLoc")
    @GetMapping("listRegionLocationRelByLoc/locId={locId}&pageNo={pageNo}&pageSize={pageSize}")
    public ResponseResult listRegionLocationRelByLoc(@PathVariable(value = "locId")Long locId,@PathVariable(value = "pageNo") Integer pageNo, @PathVariable(value = "pageSize",required = false) Integer pageSize) {
        pageNo = pageNo==null?0:pageNo;
        pageSize = pageSize==null?20:pageSize;
        @SuppressWarnings("unchecked")
        Wrapper<TbRegionLocationRel> wrapper = Condition.create().eq("STATUS_CD",DeleteConsts.VALID).eq("LOC_ID", locId).orderBy("UPDATE_DATE", false);
        Page<TbRegionLocationRel> page = relSvc.selectPage(new Page<TbRegionLocationRel>(pageNo, pageSize), wrapper);
        ResponseResult result = ResponseResult.createSuccessResult(page.getRecords(), "", pageNo, pageSize);
        return result;
    }
    @ApiOperation(value = "新增行政区域和公用管理区域关系", notes = "新增行政区域和公用管理区域关系")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "regLocRel", value = "行政区域和公用管理区域关系信息", required = true, dataType = "TbRegionLocationRel"), })
    @UooLog(value = "新增行政区域和公用管理区域关系", key = "addRegionLocationRel")
    @PostMapping("addRegionLocationRel")
    @Transactional
    public ResponseResult addRegionLocationRel(TbRegionLocationRel regLocRel) {
        //查询区域是否存在
        Long commonRegionId = regLocRel.getCommonRegionId();
        Long locId = regLocRel.getLocId();
        if(commonRegionId!=null){
            TbCommonRegion commonRegion = regSvc.selectById(commonRegionId);
            if(commonRegion==null || !DeleteConsts.VALID.equals(commonRegion.getStatusCd())){
                return ResponseResult.createErrorResult("错误的公用管理区域数据,请刷新重试");
            }
        }else{
            return ResponseResult.createErrorResult("请输入公用管理区域数据");
        }
        if(locId!=null){
            TbPoliticalLocation location = locSvc.selectById(locId);
            if(location==null || !DeleteConsts.VALID.equals(location.getStatusCd())){
                return ResponseResult.createErrorResult("错误的行政区域数据,请刷新重试");
            }
        }else{
            return ResponseResult.createErrorResult("请输入行政区域数据");
        }
        //检查是否存在相同关系  
        
        @SuppressWarnings("unchecked")
        Wrapper<TbRegionLocationRel> wrapper = Condition.create().eq("STATUS_CD", DeleteConsts.VALID).eq("COMMON_REGION_ID", commonRegionId).eq("LOC_ID", locId);
        int count = relSvc.selectCount(wrapper);
        if(count>0){
            return ResponseResult.createErrorResult("已存在相同的关系数据");
        }
        regLocRel.setCreateDate(new Date());
        regLocRel.setUpdateDate(new Date());
        regLocRel.setStatusDate(new Date());
        regLocRel.setStatusCd(DeleteConsts.VALID);
        regLocRel.setRegionLocRelId(relSvc.getId());
        relSvc.insert(regLocRel);
        return ResponseResult.createSuccessResult("success");
    }
    @ApiOperation(value = "修改行政区域和公用管理区域关系", notes = "修改行政区域和公用管理区域关系")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "regLocRel", value = "行政区域和公用管理区域关系信息", required = true, dataType = "TbreaCode"), })
    @UooLog(value = "修改行政区域和公用管理区域关系", key = "updateRegionLocationRel")
    @PostMapping("updateRegionLocationRel")
    @Transactional
    public ResponseResult updateRegionLocationRel(TbRegionLocationRel regLocRel) {
        Long id = regLocRel.getRegionLocRelId();
        if(id==null||relSvc.selectById(id)==null){
            return ResponseResult.createErrorResult("修改数据异常");
        }
        
        //查询公共管理区域是否存在
        Long commonRegionId = regLocRel.getCommonRegionId();
        Long locId = regLocRel.getLocId();
        if(commonRegionId!=null){
            TbCommonRegion commonRegion = regSvc.selectById(commonRegionId);
            if(commonRegion==null || !DeleteConsts.VALID.equals(commonRegion.getStatusCd())){
                return ResponseResult.createErrorResult("错误的公用管理区域数据,请刷新重试");
            }
        }else{
            return ResponseResult.createErrorResult("请输入公用管理区域数据");
        }
        if(locId!=null){
            TbPoliticalLocation location = locSvc.selectById(locId);
            if(location==null || !DeleteConsts.VALID.equals(location.getStatusCd())){
                return ResponseResult.createErrorResult("错误的行政区域数据,请刷新重试");
            }
        }else{
            return ResponseResult.createErrorResult("请输入行政区域数据");
        }
        regLocRel.setUpdateDate(new Date());
        //regLocRel.setStatusDate(new Date());
        relSvc.updateById(regLocRel);
        return ResponseResult.createSuccessResult("success");
    }
    
}