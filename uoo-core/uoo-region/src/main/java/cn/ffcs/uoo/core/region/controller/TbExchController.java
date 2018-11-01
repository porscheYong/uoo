package cn.ffcs.uoo.core.region.controller;


import java.util.Date;

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
import cn.ffcs.uoo.core.region.entity.TbExch;
import cn.ffcs.uoo.core.region.service.ITbExchService;
import cn.ffcs.uoo.core.region.service.ITbCommonRegionService;
import cn.ffcs.uoo.core.region.vo.ResponseResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 描述信令所指的方向,如到某个局(每局对应一个DPC)的信令,可称到某局的局向。 前端控制器
 * </p>
 *
 * @author zxs
 * @since 2018-10-30
 */
@RestController
@RequestMapping("/tbExch")
public class TbExchController extends BaseController {
    

    
    @Autowired
    private ITbExchService exchService;
    @Autowired
    private ITbCommonRegionService regionService;
    
    @ApiOperation(value = "局向列表", notes = "局向列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "pageNo", value = "分页的序号", required = true, dataType = "Integer",paramType="path"),
        @ApiImplicitParam(name = "pageSize", value = "每页的大小", dataType = "Integer",paramType="path",defaultValue = "12")
    })
    @UooLog(value = "局向列表", key = "listExch")
    @GetMapping("listExch/pageNo={pageNo}&pageSize={pageSize}")
    public ResponseResult listCommonRegion(@PathVariable(value = "pageNo") Integer pageNo, @PathVariable(value = "pageSize",required = false) Integer pageSize) {
        pageNo = pageNo==null?0:pageNo;
        pageSize = pageSize==null?20:pageSize;
        @SuppressWarnings("unchecked")
        Wrapper<TbExch> wrapper = Condition.create().eq("STATUS_CD",DeleteConsts.VALID).orderBy("UPDATE_DATE", false);
        Page<TbExch> page = exchService.selectPage(new Page<TbExch>(pageNo, pageSize), wrapper);
       //  = exchService.selectPage();
        ResponseResult result = ResponseResult.createSuccessResult(page.getRecords(), "", pageNo, pageSize);
        return result;
    }
    
    @ApiOperation(value = "新增局向", notes = "新增局向")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "exch", value = "局向信息", required = true, dataType = "TbExch"), })
    @UooLog(value = "新增局向", key = "addExch")
    @PostMapping("addExch")
    @Transactional
    public ResponseResult addExch(TbExch exch) {
        //查询公共管理区域是否存在
        if(exch.getCommonRegionId()==null){
            return ResponseResult.createErrorResult("请选择公共管理区域");
        }
        
        Long regionId = exch.getCommonRegionId();
        if(regionService.selectById(regionId)==null){
            return ResponseResult.createErrorResult("公共管理区域不存在");
        }
        
        exch.setCreateDate(new Date());
        exch.setUpdateDate(new Date());
        exch.setStatusDate(new Date());
        exch.setStatusCd(DeleteConsts.VALID);
        exch.setExchId(exchService.getId());
        exchService.insert(exch);
        return ResponseResult.createSuccessResult("success");
    }
    @ApiOperation(value = "修改局向", notes = "修改局向")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "exch", value = "局向信息", required = true, dataType = "TbreaCode"), })
    @UooLog(value = "修改局向", key = "updateExch")
    @PostMapping("updateExch")
    @Transactional
    public ResponseResult updateExch(TbExch exch) {
        Long id = exch.getExchId();
        if(id==null||exchService.selectById(id)==null){
            return ResponseResult.createErrorResult("修改数据异常");
        }
        //查询公共管理区域是否存在
        if(exch.getCommonRegionId()==null){
            return ResponseResult.createErrorResult("请选择公共管理区域");
        }
        
        Long regionId = exch.getCommonRegionId();
        if(regionService.selectById(regionId)==null){
            return ResponseResult.createErrorResult("公共管理区域不存在");
        }
        exch.setUpdateDate(new Date());
        //exch.setStatusDate(new Date());
        exchService.updateById(exch);
        return ResponseResult.createSuccessResult("success");
    }
    
    @ApiOperation(value = "删除局向", notes = "删除局向")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "exch", value = "局向信息", required = true, dataType = "TbExch"), })
    @UooLog(value = "删除局向", key = "deleteExch")
    @PostMapping("deleteExch")
    @Transactional
    public ResponseResult deleteExch(TbExch exch) {
        if(exch==null){
            return ResponseResult.createErrorResult("不能删除空数据");
        }
        TbExch ac=new TbExch();
        ac.setExchId(exch.getExchId());
        ac.setStatusCd(DeleteConsts.INVALID);
        ac.setUpdateDate(new Date());
        ac.setStatusDate(new Date());
        ac.setUpdateUser(exch.getUpdateUser());
        exchService.updateById(ac);
        return ResponseResult.createSuccessResult("success");
    }
    

}