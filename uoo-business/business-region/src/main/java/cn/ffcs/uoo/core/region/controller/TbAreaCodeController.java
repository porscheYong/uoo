package cn.ffcs.uoo.core.region.controller;


import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
import cn.ffcs.uoo.core.region.service.ITbAreaCodeService;
import cn.ffcs.uoo.core.region.service.ITbCommonRegionService;
import cn.ffcs.uoo.core.region.vo.AreaCodeVO;
import cn.ffcs.uoo.core.region.vo.ResponseResult;
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
public class TbAreaCodeController extends BaseController {
    
    @Autowired
    private ITbAreaCodeService areaCodeService;
    @Autowired
    private ITbCommonRegionService regionService;
    
    @ApiOperation(value = "根据ID获取单条数据", notes = "根据ID获取单条数据")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long",paramType="path"),
    })
    @UooLog(value = "根据ID获取单条数据", key = "getAreaCode")
    @GetMapping("getAreaCode/id={id}")
    public ResponseResult getAreaCode(@PathVariable(value = "id") Long id){
        TbAreaCode obj = areaCodeService.selectById(id);
        if(obj==null||!DeleteConsts.VALID.equals(obj.getStatusCd())){
            return ResponseResult.createErrorResult("无效数据");
        }
        return ResponseResult.createSuccessResult(obj, "");
    }
    
    @ApiOperation(value = "区号列表", notes = "区号列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "pageNo", value = "分页的序号", required = true, dataType = "Integer",paramType="path"),
        @ApiImplicitParam(name = "pageSize", value = "每页的大小", dataType = "Integer",paramType="path",defaultValue = "12")
    })
    @UooLog(value = "区号列表", key = "listAreaCode")
    @GetMapping("listAreaCode/pageNo={pageNo}&pageSize={pageSize}")
    public ResponseResult listAreaCode(@PathVariable(value = "pageNo") Integer pageNo, @PathVariable(value = "pageSize",required = false) Integer pageSize) {
        pageNo = pageNo==null?0:pageNo;
        pageSize = pageSize==null?20:pageSize;
        Long countListAreaCode = areaCodeService.countListAreaCode();
        HashMap<String,Object> map=new HashMap<>();
        map.put("from", (pageNo-1)*pageSize);
        map.put("end", pageNo * pageSize);
        areaCodeService.selectListAreaCode(map);
        List<AreaCodeVO> page = areaCodeService.selectListAreaCode(map);
        ResponseResult result = ResponseResult.createSuccessResult(page, "");
        result.setTotalRecords(countListAreaCode);
        result.setPageNo(pageNo);
        result.setPageSize(pageSize);
        return result;
    }
    
    @ApiOperation(value = "新增区号", notes = "新增区号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "areaCode", value = "区号信息", required = true, dataType = "TbAreaCode"), })
    @UooLog(value = "新增区号", key = "addAreaCode")
    @PostMapping("addAreaCode")
    @Transactional
    public ResponseResult addAreaCode(@RequestBody TbAreaCode areaCode) {
        areaCode.setCreateDate(new Date());
        areaCode.setUpdateDate(new Date());
        areaCode.setStatusDate(new Date());
        areaCode.setStatusCd(DeleteConsts.VALID);
        areaCode.setAreaCodeId(areaCodeService.getId());
        areaCodeService.insert(areaCode);
        return ResponseResult.createSuccessResult("success");
    }
    @ApiOperation(value = "修改区号", notes = "修改区号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "areaCode", value = "区号信息", required = true, dataType = "TbreaCode"), })
    @UooLog(value = "修改区号", key = "updateAreaCode")
    @PostMapping("updateAreaCode")
    @Transactional
    public ResponseResult updateAreaCode(@RequestBody TbAreaCode areaCode) {
        Long id = areaCode.getAreaCodeId();
        if(id==null||areaCodeService.selectById(id)==null){
            return ResponseResult.createErrorResult("修改数据异常");
        }
        
        /*//查询公共管理区域是否存在
        if(areaCode.getCommonRegionId()==null){
            return ResponseResult.createErrorResult("请选择公共管理区域");
        }
        
        Long regionId = areaCode.getCommonRegionId();
        if(regionService.selectById(regionId)==null){
            return ResponseResult.createErrorResult("公共管理区域不存在");
        }*/
        areaCode.setUpdateDate(new Date());
        //areaCode.setStatusDate(new Date());
        areaCodeService.updateById(areaCode);
        return ResponseResult.createSuccessResult("success");
    }
    
    @ApiOperation(value = "删除区号", notes = "删除区号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "areaCode", value = "区号信息", required = true, dataType = "TbAreaCode"), })
    @UooLog(value = "删除区号", key = "deleteAreaCode")
    @PostMapping("deleteAreaCode")
    @Transactional
    public ResponseResult deleteAreaCode(@RequestBody TbAreaCode areaCode) {
        if(areaCode==null||areaCode.getAreaCodeId()==null){
            return ResponseResult.createErrorResult("不能删除空数据");
        }
        Wrapper<TbCommonRegion> wrapper = Condition.create().eq("STATUS_CD", DeleteConsts.VALID).eq("AREA_CODE_ID", areaCode.getAreaCodeId());
        //查看是否被commonregion依赖
        List<TbCommonRegion> selectList = regionService.selectList(wrapper );
        if(selectList!=null&&!selectList.isEmpty()){
            return ResponseResult.createErrorResult("区号信息被电信管理区域依赖，不能删除");
        }
        TbAreaCode ac=new TbAreaCode();
        ac.setAreaCodeId(areaCode.getAreaCodeId());
        ac.setStatusCd(DeleteConsts.INVALID);
        ac.setUpdateDate(new Date());
        ac.setStatusDate(new Date());
        ac.setUpdateUser(areaCode.getUpdateUser());
        areaCodeService.updateById(ac);
        return ResponseResult.createSuccessResult("success");
    }
    
}
