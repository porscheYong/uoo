package cn.ffcs.uoo.web.maindata.region.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.ffcs.uoo.web.maindata.region.dto.TbExch;
import cn.ffcs.uoo.web.maindata.region.service.ExchService;
import cn.ffcs.uoo.web.maindata.region.vo.ResponseResult;
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
@RequestMapping("region/exch")
public class TbExchController {
    

    
    @Autowired
    private ExchService exchService;
    
    @ApiOperation(value = "根据ID获取单条数据", notes = "根据ID获取单条数据")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long",paramType="path"),
    })
    //@UooLog(value = "根据ID获取单条数据", key = "getExch")
    @GetMapping("getExch/id={id}")
    public ResponseResult getExch(@PathVariable(value = "id") Long id){
        return exchService.getExch(id);
    }
    
    @ApiOperation(value = "局向列表", notes = "局向列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "pageNo", value = "分页的序号", required = true, dataType = "Integer",paramType="path"),
        @ApiImplicitParam(name = "pageSize", value = "每页的大小", dataType = "Integer",paramType="path",defaultValue = "12")
    })
    //@UooLog(value = "局向列表", key = "listExch")
    @GetMapping("listExch/pageNo={pageNo}&pageSize={pageSize}")
    public ResponseResult listExch(@PathVariable(value = "pageNo") Integer pageNo, @PathVariable(value = "pageSize",required = false) Integer pageSize) {
        pageNo = pageNo==null?0:pageNo;
        pageSize = pageSize==null?20:pageSize;
        return exchService.listExch(pageNo, pageSize);
    }
    
    @ApiOperation(value = "新增局向", notes = "新增局向")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "exch", value = "局向信息", required = true, dataType = "TbExch"), })
    //@UooLog(value = "新增局向", key = "addExch")
    @PostMapping("addExch")
    //@Transactional
    public ResponseResult addExch(TbExch exch) {
         
        return exchService.addExch(exch);
    }
    @ApiOperation(value = "修改局向", notes = "修改局向")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "exch", value = "局向信息", required = true, dataType = "TbreaCode"), })
    //@UooLog(value = "修改局向", key = "updateExch")
    @PostMapping("updateExch")
    //@Transactional
    public ResponseResult updateExch(TbExch exch) {
        return exchService.updateExch(exch);
    }
    
    @ApiOperation(value = "删除局向", notes = "删除局向")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "exch", value = "局向信息", required = true, dataType = "TbExch"), })
    //@UooLog(value = "删除局向", key = "deleteExch")
    @PostMapping("deleteExch")
    //@Transactional
    public ResponseResult deleteExch(TbExch exch) {
         
        return exchService.deleteExch(exch);
    }
    

}