package cn.ffcs.uoo.core.region.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;

import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.base.controller.BaseController;
import cn.ffcs.uoo.core.region.consts.DeleteConsts;
import cn.ffcs.uoo.core.region.entity.TbCommonRegion;
import cn.ffcs.uoo.core.region.entity.TbPoliticalLocation;
import cn.ffcs.uoo.core.region.entity.TbRegionLocationRel;
import cn.ffcs.uoo.core.region.service.ITbPoliticalLocationService;
import cn.ffcs.uoo.core.region.service.ITbRegionLocationRelService;
import cn.ffcs.uoo.core.region.vo.ResponseResult;
import cn.ffcs.uoo.core.region.vo.ZTreeNode;
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
public class TbPoliticalLocationController extends BaseController {
    @Autowired
    private ITbPoliticalLocationService polLocSvc;
    @Autowired
    private ITbRegionLocationRelService regLocRelSvc;
    
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @ApiOperation(value = "根据ID获取下一级信息", notes = "根据ID获取下一级信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long", paramType = "path"), })
    @UooLog(value = "根据ID获取下一级信息", key = "getChildPoliticalLocationInfo")
    @GetMapping("getChildPoliticalLocationInfo/{id}")
    public ResponseResult getChildPoliticalLocationInfo(@PathVariable(value="id") Long id){
        Map<String, Object> params = new HashMap<>();
        params.put("statusCd", DeleteConsts.VALID);
        params.put("upLocId", id);
        params.put("statusCd", DeleteConsts.VALID);
        List<Map> list = polLocSvc.getChildPoliticalLocationInfo(params);
        if (list == null) {
            return ResponseResult.createErrorResult("暂无数据");
        }
        for (Map map : list) {
            if(map.get("LOC_CODE")==null){
                map.put("LOC_CODE", "");
            }
            if(map.get("LOC_NAME")==null){
                map.put("LOC_NAME", "");
            }
            if(map.get("LOC_TYPE")==null){
                map.put("LOC_TYPE", "");
            }
            if(map.get("LOC_ABBR")==null){
                map.put("LOC_ABBR", "");
            }
        }
        return ResponseResult.createSuccessResult(list,"success");
    }
    @ApiOperation(value = "行政区域树", notes = "行政区域树")
    @UooLog(value = "行政区域树", key = "getTreePoliticalLocation")
    @GetMapping("getTreePoliticalLocation/{id}")
    public ResponseResult getTreePoliticalLocation(@PathVariable(value="id") Long id ){
        Map<String, Object> params = new HashMap<>();
        List<TbPoliticalLocation> list = polLocSvc.getTreePoliticalLocation(params);
        List<ZTreeNode> ztlist = new ArrayList<>();
        for (TbPoliticalLocation polloc : list) {
            ZTreeNode n=new ZTreeNode();
            ztlist.add(n);
            n.setId(polloc.getLocId());
            n.setName(polloc.getLocName());
            n.setpId(polloc.getUpLocId()==null||polloc.getUpLocId()<1?0:polloc.getUpLocId());
            for (TbPoliticalLocation tmp : list) {
                if(polloc.getLocId().equals(tmp.getUpLocId())){
                    n.setParent(true);
                    break;
                }
            }
        }
        ResponseResult r = ResponseResult.createSuccessResult(ztlist,"success");
        r.setTotalRecords(list.size());
        return r;
    }
    @ApiOperation(value = "根据ID获取单条数据", notes = "根据ID获取单条数据")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long",paramType="path"),
    })
    @UooLog(value = "根据ID获取单条数据", key = "getPoliticalLocation")
    @GetMapping("getPoliticalLocation/id={id}")
    public ResponseResult getPoliticalLocation(@PathVariable(value = "id") Long id){
        TbPoliticalLocation loc = polLocSvc.selectById(id);
        if(loc==null||!DeleteConsts.VALID.equals(loc.getStatusCd())){
            return ResponseResult.createErrorResult("无效数据");
        }
        return ResponseResult.createSuccessResult(loc, "");
    }
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
    public ResponseResult addPoliticalLocation(@RequestBody TbPoliticalLocation polLoc) {
        // 数据校验  获取操作者
        //查询上级是否存在  
        if(polLoc.getUpLocId()!=null&&polLoc.getUpLocId().longValue()!=0){
            TbPoliticalLocation region = polLocSvc.selectById(polLoc.getUpLocId());
            if(region==null){
                return ResponseResult.createErrorResult("上一级区域不存在");
            }
        }
        if(StringUtils.isBlank(polLoc.getLocName())){
            return ResponseResult.createErrorResult("请输入区域名称");
        }
        if(StringUtils.isBlank(polLoc.getLocCode())){
            return ResponseResult.createErrorResult("请输入区域编码");
        }
        if(StringUtils.isBlank(polLoc.getLocAbbr())){
            return ResponseResult.createErrorResult("请输入区域简称");
        }
        polLoc.setStatusCd(DeleteConsts.VALID);
        polLoc.setCreateDate(new Date());
        polLoc.setUpdateDate(new Date());
        polLoc.setStatusDate(new Date());
        polLoc.setLocId(polLocSvc.getId());
        polLocSvc.insert(polLoc);
        return ResponseResult.createSuccessResult(polLoc,"success");
    }
    @ApiOperation(value = "修改行政区域", notes = "修改行政区域")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "polLoc", value = "行政区域信息", required = true, dataType = "TbPoliticalLocation"), })
    @UooLog(value = "修改行政区域", key = "updatePoliticalLocation")
    @PostMapping("updatePoliticalLocation")
    @Transactional
    public ResponseResult updatePoliticalLocation(@RequestBody TbPoliticalLocation polLoc) {
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
        if(StringUtils.isBlank(polLoc.getLocName())){
            return ResponseResult.createErrorResult("请输入区域名称");
        }
        if(StringUtils.isBlank(polLoc.getLocCode())){
            return ResponseResult.createErrorResult("请输入区域编码");
        }
        if(StringUtils.isBlank(polLoc.getLocAbbr())){
            return ResponseResult.createErrorResult("请输入区域简称");
        }
        polLoc.setUpdateDate(new Date());
        //polLoc.setStatusDate(new Date());
        polLocSvc.updateById(polLoc);
        return ResponseResult.createSuccessResult(polLoc,"success");
    }
    
    @SuppressWarnings("unchecked")
    @ApiOperation(value = "删除行政区域", notes = "删除行政区域")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "polLoc", value = "行政区域信息", required = true, dataType = "TbPoliticalLocation"), })
    @UooLog(value = "删除行政区域", key = "deletePoliticalLocation")
    @PostMapping("deletePoliticalLocation")
    @Transactional(rollbackFor=Exception.class)
    public ResponseResult deletePoliticalLocation(@RequestBody TbPoliticalLocation polLoc) {
        //
        if(polLoc==null||polLoc.getLocId()==null){
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