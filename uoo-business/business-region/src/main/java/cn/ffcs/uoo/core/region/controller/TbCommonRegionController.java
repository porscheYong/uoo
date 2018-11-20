package cn.ffcs.uoo.core.region.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import cn.ffcs.uoo.core.region.entity.TbAreaCode;
import cn.ffcs.uoo.core.region.entity.TbCommonRegion;
import cn.ffcs.uoo.core.region.entity.TbExch;
import cn.ffcs.uoo.core.region.entity.TbPoliticalLocation;
import cn.ffcs.uoo.core.region.entity.TbRegionLocationRel;
import cn.ffcs.uoo.core.region.service.ITbAreaCodeService;
import cn.ffcs.uoo.core.region.service.ITbCommonRegionService;
import cn.ffcs.uoo.core.region.service.ITbExchService;
import cn.ffcs.uoo.core.region.service.ITbPoliticalLocationService;
import cn.ffcs.uoo.core.region.service.ITbRegionLocationRelService;
import cn.ffcs.uoo.core.region.vo.CommonRegionDTO;
import cn.ffcs.uoo.core.region.vo.ResponseResult;
import cn.ffcs.uoo.core.region.vo.ZTreeNode;
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
public class TbCommonRegionController extends BaseController {
    @Autowired
    private ITbCommonRegionService regionService;
    @Autowired
    private ITbRegionLocationRelService regLocRelSvc;
    @Autowired
    private ITbAreaCodeService areaCodeSvc;
    @Autowired
    private ITbExchService exchSvc;
    @Autowired
    private ITbPoliticalLocationService locSvc;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @ApiOperation(value = "根据ID获取单条数据", notes = "根据ID获取单条数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long", paramType = "path"), })
    @UooLog(value = "根据ID获取单条数据", key = "getCommonRegion")
    @GetMapping("getCommonRegion/id={id}")
    public ResponseResult getCommonRegion(@PathVariable(value = "id") Long id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("statusCd", DeleteConsts.VALID);
        params.put("commonRegionId", id);
        List<Map> list = regionService.selectUnionPolLoc(params);
        if (list == null || list.isEmpty()) {
            return ResponseResult.createErrorResult("无效数据");
        }
        Map m = list.get(0);
        List<Long> locIds = new ArrayList<>();
        List<String> locCodes = new ArrayList<>();
        List<String> locNames = new ArrayList<>();
        if (m.get("LOC_ID") != null)
            locIds.add(Long.valueOf(m.get("LOC_ID").toString()));
        if (m.get("LOC_CODE") != null)
            locCodes.add(m.get("LOC_CODE").toString());
        if (m.get("LOC_NAME") != null)
            locNames.add(m.get("LOC_NAME").toString());

        if (list.size() > 1) {
            for (int i = 1; i < list.size(); i++) {
                Map map = list.get(i);
                if (map.get("LOC_ID") != null)
                    locIds.add(Long.valueOf(map.get("LOC_ID").toString()));
                if (map.get("LOC_CODE") != null)
                    locCodes.add(map.get("LOC_CODE").toString());
                if (map.get("LOC_NAME") != null)
                    locNames.add(map.get("LOC_NAME").toString());
            }
        }
        m.put("LOC_ID", locIds);
        m.put("LOC_CODE", locCodes);
        m.put("LOC_NAME", locNames);

        return ResponseResult.createSuccessResult(m, "");
    }
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @ApiOperation(value = "根据ID获取下一级信息", notes = "根据ID获取下一级信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long", paramType = "path"), })
    @UooLog(value = "根据ID获取下一级信息", key = "getChildCommonRegionInfo")
    @GetMapping("getChildCommonRegionInfo/{id}")
    public ResponseResult getChildCommonRegionInfo(@PathVariable(value="id") Long id){
        Map<String, Object> params = new HashMap<>();
        params.put("statusCd", DeleteConsts.VALID);
        params.put("upRegionId", id);
        params.put("statusCd", DeleteConsts.VALID);
        List<Map> list = regionService.getChildCommonRegionInfo(params);
        List<Map> result = new ArrayList<>();
        Map<Long, Integer> keys = new HashMap<>();
        if (list != null) {
            for (Map m : list) {
                Object key = m.get("COMMON_REGION_ID");
                Long lk = Long.valueOf(key.toString());
                if (keys.containsKey(lk)) {
                    Map map = result.get(keys.get(lk));
                    List<Long> locIds = (List<Long>) map.get("LOC_ID");
                    if (m.get("LOC_ID") != null)
                        locIds.add(Long.valueOf(m.get("LOC_ID").toString()));
                    map.put("LOC_ID", locIds);

                    List<String> locCodes = (List<String>) map.get("LOC_CODE");
                    if (m.get("LOC_CODE") != null)
                        locCodes.add(m.get("LOC_CODE").toString());
                    map.put("LOC_CODE", locCodes);

                    List<String> locNames = (List<String>) map.get("LOC_NAME");
                    if (m.get("LOC_NAME") != null)
                        locNames.add(m.get("LOC_NAME").toString());
                    map.put("LOC_NAME", locNames);

                    result.set(keys.get(lk), map);
                } else {
                    List<Long> locIds = new ArrayList<>();
                    if (m.get("LOC_ID") != null)
                        locIds.add(Long.valueOf(m.get("LOC_ID").toString()));
                    m.put("LOC_ID", locIds);
                    List<String> locCodes = new ArrayList<>();
                    if (m.get("LOC_CODE") != null)
                        locCodes.add(m.get("LOC_CODE").toString());
                    m.put("LOC_CODE", locCodes);
                    List<String> locNames = new ArrayList<>();
                    if (m.get("LOC_NAME") != null)
                        locNames.add(m.get("LOC_NAME").toString());
                    m.put("LOC_NAME", locNames);
                    result.add(m);
                    keys.put(lk, result.size() - 1);
                }

            }
        }else{
            return ResponseResult.createErrorResult("暂无数据");
        }
        return ResponseResult.createSuccessResult(result,"success");
    }
    @ApiOperation(value = "公共管理区域树", notes = "公共管理区域树")
    @UooLog(value = "公共管理区域树", key = "getTreeCommonRegion")
    @GetMapping("getTreeCommonRegion/{id}")
    public ResponseResult getTreeCommonRegion(@PathVariable(value="id") Long id ){
        Map<String, Object> params = new HashMap<>();
        params.put("statusCd", DeleteConsts.VALID);
        params.put("upRegionId", id);
        List<Map> list = regionService.getTreeCommonRegion(params);
        if(list==null || list.isEmpty()){
            return ResponseResult.createErrorResult("暂无数据");
        }
        List<ZTreeNode> ztlist=new ArrayList<>();
        
        for (Map map : list) {
            ZTreeNode n=new ZTreeNode();
            n.setId(Long.valueOf(map.get("COMMON_REGION_ID").toString()));
            Object rn = map.get("REGION_NAME");
            n.setName(rn==null?"":map.get("REGION_NAME").toString());
            Object upid = map.get("UP_REGION_ID");
            n.setpId(upid==null?0:Long.valueOf(upid.toString()));
            n.setOpen(n.getpId()==0);
            Wrapper<TbCommonRegion> w=Condition.create().eq("STATUS_CD", DeleteConsts.VALID).eq("UP_REGION_ID", n.getId());
            List<TbCommonRegion> selectList = regionService.selectList(w);
            n.setParent(selectList!=null&&!selectList.isEmpty());
            ztlist.add(n);
        }
        return ResponseResult.createSuccessResult(ztlist,"success");
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @ApiOperation(value = "公共管理区域列表", notes = "公共管理区域列表")
    @UooLog(value = "公共管理区域列表", key = "listAllCommonRegion")
    @GetMapping("listAllCommonRegion")
    public ResponseResult listAllCommonRegion() {
        // 只查询有效的
        HashMap<String, Object> params = new HashMap<>();
        params.put("statusCd", DeleteConsts.VALID);
        List<Map> list = regionService.selectUnionPolLoc(params);
        List<Map> result = new ArrayList<>();
        Map<Long, Integer> keys = new HashMap<>();
        if (list != null) {
            for (Map m : list) {
                Object key = m.get("COMMON_REGION_ID");
                Long lk = Long.valueOf(key.toString());
                if (keys.containsKey(lk)) {
                    Map map = result.get(keys.get(lk));
                    List<Long> locIds = (List<Long>) map.get("LOC_ID");
                    if (m.get("LOC_ID") != null)
                        locIds.add(Long.valueOf(m.get("LOC_ID").toString()));
                    map.put("LOC_ID", locIds);

                    List<String> locCodes = (List<String>) map.get("LOC_CODE");
                    if (m.get("LOC_CODE") != null)
                        locCodes.add(m.get("LOC_CODE").toString());
                    map.put("LOC_CODE", locCodes);

                    List<String> locNames = (List<String>) map.get("LOC_NAME");
                    if (m.get("LOC_NAME") != null)
                        locNames.add(m.get("LOC_NAME").toString());
                    map.put("LOC_NAME", locNames);

                    result.set(keys.get(lk), map);
                } else {
                    List<Long> locIds = new ArrayList<>();
                    if (m.get("LOC_ID") != null)
                        locIds.add(Long.valueOf(m.get("LOC_ID").toString()));
                    m.put("LOC_ID", locIds);
                    List<String> locCodes = new ArrayList<>();
                    if (m.get("LOC_CODE") != null)
                        locCodes.add(m.get("LOC_CODE").toString());
                    m.put("LOC_CODE", locCodes);
                    List<String> locNames = new ArrayList<>();
                    if (m.get("LOC_NAME") != null)
                        locNames.add(m.get("LOC_NAME").toString());
                    m.put("LOC_NAME", locNames);
                    result.add(m);
                    keys.put(lk, result.size() - 1);
                }

            }
        }else{
            return ResponseResult.createErrorResult("暂无数据");
        }

        ResponseResult rr = ResponseResult.createSuccessResult(result, "");
        return rr;
    }

    @ApiOperation(value = "新增公共管理区域", notes = "新增公共管理区域")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commonRegion", value = "公共管理区域信息", required = true, dataType = "CommonRegionDTO"), })
    @UooLog(value = "新增公共管理区域", key = "addCommonRegion")
    @PostMapping("addCommonRegion")
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult addCommonRegion(@RequestBody CommonRegionDTO commonRegion/*
                                                                                    * ,TbRegionLocationRel
                                                                                    * regionLocationRel
                                                                                    */) {
        // 数据校验 获取操作者
        // 查询上级是否存在
        if (commonRegion.getUpRegionId() != null && commonRegion.getUpRegionId() != 0) {
            TbCommonRegion region = regionService.selectById(commonRegion.getUpRegionId());
            if (region == null) {
                return ResponseResult.createErrorResult("选择的上一级区域不存在");
            }
        }
        // 检查行政区域
        List<Long> polLocIds = commonRegion.getPolLocIds();
        if (polLocIds != null) {
            List<TbPoliticalLocation> list = locSvc.selectBatchIds(polLocIds);
            if (list == null || list.size() != polLocIds.size()) {
                return ResponseResult.createErrorResult("选择的行政区域有无效数据");
            }
            for (TbPoliticalLocation obj : list) {
                if (!DeleteConsts.VALID.equals(obj.getStatusCd())) {
                    return ResponseResult.createErrorResult("选择的行政区域有无效数据");
                }
            }
        }

        TbCommonRegion reg = commonRegion.convertEntity();
        reg.setCreateUser(commonRegion.getOperateUser());
        reg.setStatusCd(DeleteConsts.VALID);
        reg.setCreateDate(new Date());
        reg.setUpdateDate(new Date());
        reg.setStatusDate(new Date());
        reg.setCommonRegionId(regionService.getId());
        regionService.insert(reg);

        for (Long locId : polLocIds) {
            TbRegionLocationRel rel = new TbRegionLocationRel();
            rel.setCommonRegionId(reg.getCommonRegionId());
            rel.setLocId(locId);
            rel.setRegionLocRelId(regLocRelSvc.getId());
            regLocRelSvc.insert(rel);
        }

        return ResponseResult.createSuccessResult("success");
    }

    @SuppressWarnings("unchecked")
    @ApiOperation(value = "修改公共管理区域", notes = "修改公共管理区域")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commonRegion", value = "公共管理区域信息", required = true, dataType = "CommonRegionDTO"), })
    @UooLog(value = "修改公共管理区域", key = "updateCommonRegion")
    @PostMapping("updateCommonRegion")
    @Transactional
    public ResponseResult updateCommonRegion(@RequestBody CommonRegionDTO commonRegion/*
                                                                                       * ,TbRegionLocationRel
                                                                                       * regionLocationRel
                                                                                       */) {
        Long id = commonRegion.getCommonRegionId();
        if (id == null || regionService.selectById(id) == null) {
            return ResponseResult.createErrorResult("修改数据异常");
        }
        // 数据校验 获取操作者
        if (commonRegion.getUpRegionId() != null && commonRegion.getUpRegionId() != 0) {
            TbCommonRegion region = regionService.selectById(commonRegion.getUpRegionId());
            if (region == null) {
                return ResponseResult.createErrorResult("上一级区域不存在");
            }
        }

        // 检查行政区域
        List<Long> polLocIds = commonRegion.getPolLocIds();
        if (polLocIds != null) {
            List<TbPoliticalLocation> list = locSvc.selectBatchIds(polLocIds);
            if (list == null || list.size() != polLocIds.size()) {
                return ResponseResult.createErrorResult("选择的行政区域有无效数据");
            }
            for (TbPoliticalLocation obj : list) {
                if (!DeleteConsts.VALID.equals(obj.getStatusCd())) {
                    return ResponseResult.createErrorResult("选择的行政区域有无效数据");
                }
            }
        }

        TbCommonRegion reg = commonRegion.convertEntity();
        reg.setUpdateDate(new Date());
        reg.setUpdateUser(commonRegion.getOperateUser());
        regionService.updateById(reg);

        // 先删除之前的关系 再添加新的
        Wrapper<TbRegionLocationRel> wrapper = Condition.create().eq("COMMON_REGION_ID",
                commonRegion.getCommonRegionId());
        regLocRelSvc.delete(wrapper);
        for (Long locId : polLocIds) {
            TbRegionLocationRel rel = new TbRegionLocationRel();
            rel.setCommonRegionId(reg.getCommonRegionId());
            rel.setLocId(locId);
            rel.setRegionLocRelId(regLocRelSvc.getId());
            regLocRelSvc.insert(rel);
        }

        return ResponseResult.createSuccessResult("success");
    }

    @ApiOperation(value = "删除公共管理区域", notes = "删除公共管理区域")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commonRegion", value = "公共管理区域信息", required = true, dataType = "TbCommonRegion"), })
    @UooLog(value = "删除公共管理区域", key = "deleteCommonRegion")
    @PostMapping("deleteCommonRegion")
    @Transactional(rollbackFor = Exception.class)
    @SuppressWarnings("unchecked")
    public ResponseResult deleteCommonRegion(@RequestBody TbCommonRegion commonRegion) {
        //
        if (commonRegion == null || commonRegion.getCommonRegionId() == null) {
            return ResponseResult.createErrorResult("不能删除空数据");
        }
        // 有没有下级
        List<TbCommonRegion> regionDatas = regionService.selectList(Condition.create()
                .eq("UP_REGION_ID", commonRegion.getCommonRegionId()).eq("STATUS_CD", DeleteConsts.VALID));
        if (regionDatas != null && !regionDatas.isEmpty()) {
            return ResponseResult.createErrorResult("当前区域有下级区域不能删除");
        }
        // 有没有被区域和局向依赖
        List<TbAreaCode> acDatas = areaCodeSvc.selectList(Condition.create()
                .eq("COMMON_REGION_ID", commonRegion.getCommonRegionId()).eq("STATUS_CD", DeleteConsts.VALID));
        if (acDatas != null && !acDatas.isEmpty()) {
            return ResponseResult.createErrorResult("当前区域有区号信息依赖，请先修改区号信息");
        }
        List<TbExch> exchDatas = exchSvc.selectList(Condition.create()
                .eq("COMMON_REGION_ID", commonRegion.getCommonRegionId()).eq("STATUS_CD", DeleteConsts.VALID));
        if (exchDatas != null && !exchDatas.isEmpty()) {
            return ResponseResult.createErrorResult("当前区域有局向信息依赖，请先修改局向信息");
        }
        TbCommonRegion r = new TbCommonRegion();
        r.setCommonRegionId(commonRegion.getCommonRegionId());
        r.setStatusCd(DeleteConsts.INVALID);
        r.setUpdateDate(new Date());
        r.setStatusDate(new Date());
        r.setUpdateUser(commonRegion.getUpdateUser());
        regionService.updateById(r);
        Wrapper<TbRegionLocationRel> wrapper = Condition.create().eq("COMMON_REGION_ID",
                commonRegion.getCommonRegionId());
        regLocRelSvc.delete(wrapper);
        return ResponseResult.createSuccessResult("success");
    }
}
