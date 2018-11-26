package cn.ffcs.uoo.web.maindata.permission.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.ffcs.uoo.web.maindata.permission.dto.DataPrivRule;
import cn.ffcs.uoo.web.maindata.permission.service.DataPrivRuleService;
import cn.ffcs.uoo.web.maindata.permission.vo.ResponseResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 记录权限下相关联的规则，包括横向、纵向的数据维度。
 前端控制器
 * </p>
 *
 * @author zengxsh
 * @since 2018-11-08
 */
@RestController
@RequestMapping("/permission/dataPrivRule")
public class DataPrivRuleController {
    @Autowired
    private DataPrivRuleService ruleSvc;
    
    @ApiOperation(value = "添加规则", notes = "添加规则")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "dataPrivRule", value = "规则", required = true, dataType = "DataPrivRule" ),
    })
    @RequestMapping(value="addDataPrivRule",method=RequestMethod.POST)
    public ResponseResult addDataPrivRule(@RequestBody DataPrivRule dataPrivRule){
         
        return ruleSvc.addDataPrivRule(dataPrivRule);
    }
    @ApiOperation(value = "修改规则", notes = "修改规则")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "dataPrivRule", value = "规则", required = true, dataType = "DataPrivRule" ),
    })
    @RequestMapping(value="updateDataPrivRule",method=RequestMethod.POST)
    public ResponseResult updateDataPrivRule(@RequestBody DataPrivRule dataPrivRule){
         
        return ruleSvc.updateDataPrivRule(dataPrivRule);
    }
    
    @ApiOperation(value = "删除规则", notes = "删除规则")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "dataPrivRule", value = "规则", required = true, dataType = "DataPrivRule" ),
    })
    @RequestMapping(value="deleteDataPrivRule",method=RequestMethod.POST)
    public ResponseResult deleteDataPrivRule(@RequestBody DataPrivRule dataPrivRule){
        return ruleSvc.deleteDataPrivRule(dataPrivRule);
    }
}

