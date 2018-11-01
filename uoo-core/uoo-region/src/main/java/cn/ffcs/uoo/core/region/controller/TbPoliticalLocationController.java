package cn.ffcs.uoo.core.region.controller;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;

import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.base.controller.BaseController;
import cn.ffcs.uoo.core.region.consts.DeleteConsts;
import cn.ffcs.uoo.core.region.entity.TbPoliticalLocation;
import cn.ffcs.uoo.core.region.entity.TbRegionLocationRel;
import cn.ffcs.uoo.core.region.service.ITbPoliticalLocationService;
import cn.ffcs.uoo.core.region.service.ITbRegionLocationRelService;
import cn.ffcs.uoo.core.region.vo.ResponseResult;
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
@RequestMapping("tbPoliticalLocation")
public class TbPoliticalLocationController extends BaseController {
    @Autowired
    private ITbPoliticalLocationService polLocSvc;
    @Autowired
    private ITbRegionLocationRelService regLocRelSvc;
    
    @SuppressWarnings("unchecked")
    @ApiOperation(value = "行政区域列表", notes = "行政区域列表")
    @UooLog(value = "行政区域列表", key = "listAllPoliticalLocation")
    @GetMapping("listAllPoliticalLocation")
    public ResponseResult listAllPoliticalLocation() {
        //只查询有效的
        Wrapper<TbPoliticalLocation> wrapper = Condition.create().eq("STATUS_CD",DeleteConsts.VALID);
        List<TbPoliticalLocation> list = polLocSvc.selectList(wrapper);
        ResponseResult result = ResponseResult.createSuccessResult(list, "");
        return result;
    }

    @ApiOperation(value = "新增行政区域", notes = "新增行政区域")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "polLoc", value = "行政区域信息", required = true, dataType = "TbPoliticalLocation"), })
    @UooLog(value = "新增行政区域", key = "addPoliticalLocation")
    @PostMapping("addPoliticalLocation")
    @Transactional
    public ResponseResult addPoliticalLocation(TbPoliticalLocation polLoc) {
        // 数据校验  获取操作者
        //查询上级是否存在  
        if(polLoc.getUpLocId()!=null&&polLoc.getUpLocId().longValue()!=0){
            TbPoliticalLocation region = polLocSvc.selectById(polLoc.getUpLocId());
            if(region==null){
                return ResponseResult.createErrorResult("上一级区域不存在");
            }
        }
        polLoc.setStatusCd(DeleteConsts.VALID);
        polLoc.setCreateDate(new Date());
        polLoc.setUpdateDate(new Date());
        polLoc.setStatusDate(new Date());
        polLoc.setLocId(polLocSvc.getId());
        polLocSvc.insert(polLoc);
        return ResponseResult.createSuccessResult("success");
    }
    @ApiOperation(value = "修改行政区域", notes = "修改行政区域")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "polLoc", value = "行政区域信息", required = true, dataType = "TbPoliticalLocation"), })
    @UooLog(value = "修改行政区域", key = "updatePoliticalLocation")
    @PostMapping("updatePoliticalLocation")
    @Transactional
    public ResponseResult updatePoliticalLocation(TbPoliticalLocation polLoc) {
        Long id = polLoc.getLocId();
        if(id==null||polLocSvc.selectById(id)==null){
            return ResponseResult.createErrorResult("修改数据异常");
        }
        // 数据校验 获取操作者
        if(polLoc.getUpLocId()!=null&&polLoc.getUpLocId().longValue()!=0){
            TbPoliticalLocation region = polLocSvc.selectById(polLoc.getUpLocId());
            if(region==null){
                return ResponseResult.createErrorResult("上一级区域不存在");
            }
        }
        polLoc.setUpdateDate(new Date());
        //polLoc.setStatusDate(new Date());
        polLocSvc.updateById(polLoc);
        return ResponseResult.createSuccessResult("success");
    }
    
    @SuppressWarnings("unchecked")
    @ApiOperation(value = "删除行政区域", notes = "删除行政区域")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "polLoc", value = "行政区域信息", required = true, dataType = "TbPoliticalLocation"), })
    @UooLog(value = "删除行政区域", key = "deletePoliticalLocation")
    @PostMapping("deletePoliticalLocation")
    @Transactional(rollbackFor=Exception.class)
    public ResponseResult deletePoliticalLocation(TbPoliticalLocation polLoc) {
        //
        if(polLoc==null){
            return ResponseResult.createErrorResult("不能删除空数据");
        }
        //有下级也不能删除
        List<TbPoliticalLocation> datas=polLocSvc.selectList(Condition.create().eq("UP_LOC_ID", polLoc.getLocId()).eq("STATUS_CD",DeleteConsts.VALID));
        if(datas!=null&&!datas.isEmpty()){
            return ResponseResult.createErrorResult("当前区域有下级区域不能删除");
        }
        TbPoliticalLocation r=new TbPoliticalLocation();
        r.setLocId(polLoc.getLocId());
        r.setStatusCd(DeleteConsts.INVALID);
        r.setUpdateDate(new Date());
        r.setStatusDate(new Date());
        r.setUpdateUser(polLoc.getUpdateUser());
        polLocSvc.updateById(r);
        Wrapper<TbRegionLocationRel> wrapper = Condition.create().eq("LOC_ID", polLoc.getLocId());
        regLocRelSvc.delete(wrapper);
        //polLocSvc.deleteById(polLoc.getPoliticalLocationId());
        return ResponseResult.createSuccessResult("success");
    }
}