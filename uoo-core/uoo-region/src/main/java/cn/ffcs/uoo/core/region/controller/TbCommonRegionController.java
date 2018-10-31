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
import cn.ffcs.uoo.core.region.entity.TbAreaCode;
import cn.ffcs.uoo.core.region.entity.TbCommonRegion;
import cn.ffcs.uoo.core.region.entity.TbExch;
import cn.ffcs.uoo.core.region.entity.TbRegionLocationRel;
import cn.ffcs.uoo.core.region.service.ITbAreaCodeService;
import cn.ffcs.uoo.core.region.service.ITbCommonRegionService;
import cn.ffcs.uoo.core.region.service.ITbExchService;
import cn.ffcs.uoo.core.region.service.ITbRegionLocationRelService;
import cn.ffcs.uoo.core.region.vo.ResponseResult;
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
@RequestMapping("/tbCommonRegion/")
public class TbCommonRegionController extends BaseController {
    @Autowired
    private ITbCommonRegionService regionService;
    @Autowired
    private ITbRegionLocationRelService regLocRelSvc;
    @Autowired
    private ITbAreaCodeService areaCodeSvc;
    @Autowired
    private ITbExchService exchSvc;

    @ApiOperation(value = "公共管理区域列表", notes = "公共管理区域列表")
    @UooLog(value = "公共管理区域列表", key = "listAllCommonRegion")
    @GetMapping("listAllCommonRegion")
    public ResponseResult listAllCommonRegion() {
        //只查询有效的
        @SuppressWarnings("unchecked")
        Wrapper<TbCommonRegion> wrapper = Condition.create().eq("STATUS_CD",DeleteConsts.VALID);
        List<TbCommonRegion> list = regionService.selectList(wrapper);
        ResponseResult result = ResponseResult.createSuccessResult(list, "");
        return result;
    }

    @ApiOperation(value = "新增公共管理区域", notes = "新增公共管理区域")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commonRegion", value = "公共管理区域信息", required = true, dataType = "TbCommonRegion"), })
    @UooLog(value = "新增公共管理区域", key = "addCommonRegion")
    @PostMapping("addCommonRegion")
    @Transactional
    public ResponseResult addCommonRegion(TbCommonRegion commonRegion) {
        // TODO 数据校验  获取操作者
        //查询上级是否存在 -1为最顶层
        
        if(commonRegion.getUpRegionId()!=null){
            TbCommonRegion region = regionService.selectById(commonRegion.getUpRegionId());
            if(region==null){
                return ResponseResult.createErrorResult("上一级区域不存在");
            }
        }
        commonRegion.setStatusCd(DeleteConsts.VALID);
        
        commonRegion.setCreateDate(new Date());
        commonRegion.setUpdateDate(new Date());
        commonRegion.setStatusDate(new Date());
        commonRegion.setCommonRegionId(regionService.getId());
        regionService.insert(commonRegion);
        return ResponseResult.createSuccessResult("success");
    }
    @ApiOperation(value = "修改公共管理区域", notes = "修改公共管理区域")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commonRegion", value = "公共管理区域信息", required = true, dataType = "TbCommonRegion"), })
    @UooLog(value = "修改公共管理区域", key = "updateCommonRegion")
    @PostMapping("updateCommonRegion")
    @Transactional
    public ResponseResult updateCommonRegion(TbCommonRegion commonRegion) {
        // TODO 数据校验 获取操作者
        if(commonRegion.getUpRegionId()!=null){
            TbCommonRegion region = regionService.selectById(commonRegion.getUpRegionId());
            if(region==null){
                return ResponseResult.createErrorResult("上一级区域不存在");
            }
        }
        commonRegion.setUpdateDate(new Date());
        commonRegion.setStatusDate(new Date());
        
        regionService.updateById(commonRegion);
        return ResponseResult.createSuccessResult("success");
    }
    
    @ApiOperation(value = "删除公共管理区域", notes = "删除公共管理区域")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commonRegion", value = "公共管理区域信息", required = true, dataType = "TbCommonRegion"), })
    @UooLog(value = "删除公共管理区域", key = "deleteCommonRegion")
    @PostMapping("deleteCommonRegion")
    @Transactional(rollbackFor=Exception.class)
    @SuppressWarnings("unchecked")
    public ResponseResult deleteCommonRegion(TbCommonRegion commonRegion) {
        //
        if(commonRegion==null){
            return ResponseResult.createErrorResult("不能删除空数据");
        }
        //有没有下级
        List<TbCommonRegion> regionDatas=regionService.selectList(Condition.create().eq("UP_REGION_ID", commonRegion.getCommonRegionId()).eq("STATUS_CD",DeleteConsts.VALID));
        if(regionDatas!=null&&!regionDatas.isEmpty()){
            return ResponseResult.createErrorResult("当前区域有下级区域不能删除");
        }
        //有没有被区域和局向依赖
        List<TbAreaCode> acDatas=areaCodeSvc.selectList(Condition.create().eq("COMMON_REGION_ID", commonRegion.getCommonRegionId()).eq("STATUS_CD",DeleteConsts.VALID));
        if(acDatas!=null&&!acDatas.isEmpty()){
            return ResponseResult.createErrorResult("当前区域有区号信息依赖，请先修改区号信息");
        }
        List<TbExch> exchDatas=exchSvc.selectList(Condition.create().eq("COMMON_REGION_ID", commonRegion.getCommonRegionId()).eq("STATUS_CD",DeleteConsts.VALID));
        if(exchDatas!=null&&!exchDatas.isEmpty()){
            return ResponseResult.createErrorResult("当前区域有区号信息依赖，请先修改区号信息");
        }
        TbCommonRegion r=new TbCommonRegion();
        r.setCommonRegionId(commonRegion.getCommonRegionId());
        r.setStatusCd(DeleteConsts.INVALID);
        r.setUpdateDate(new Date());
        r.setStatusDate(new Date());
        r.setUpdateUser(commonRegion.getUpdateUser());
        regionService.updateById(r);
        Wrapper<TbRegionLocationRel> wrapper = Condition.create().eq("COMMON_REGION_ID", commonRegion.getCommonRegionId());
        regLocRelSvc.delete(wrapper);
        return ResponseResult.createSuccessResult("success");
    }
}
