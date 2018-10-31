package cn.ffcs.uoo.core.region.controller;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
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
        for (TbCommonRegion tbCommonRegion : regs) {
            if(!DeleteConsts.VALID.equals(tbCommonRegion.getStatusCd())){
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
}