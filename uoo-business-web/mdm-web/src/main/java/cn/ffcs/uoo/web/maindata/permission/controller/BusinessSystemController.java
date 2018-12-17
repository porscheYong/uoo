package cn.ffcs.uoo.web.maindata.permission.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.ffcs.uoo.web.maindata.permission.dto.BusinessSystem;
import cn.ffcs.uoo.web.maindata.permission.service.BusinessSystemService;
import cn.ffcs.uoo.web.maindata.permission.vo.ResponseResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 接入系统表 前端控制器
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-30
 */
@RestController
@RequestMapping("/permission/businessSystem")
public class BusinessSystemController {
    @Autowired
    BusinessSystemService bizSysSvc;
    @ApiOperation(value = "接入系统列表-web", notes = "接入系统列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "treeId", value = "treeId", required = true, dataType = "Long" ,paramType="path"),
    })
    @RequestMapping(value = "/listBusinessSystem/{treeId}", method = RequestMethod.GET)
    public ResponseResult<List<BusinessSystem>> listBusinessSystemByOrgTree(@PathVariable(value = "treeId")  Long treeId){
        return bizSysSvc.listBusinessSystemByOrgTree(treeId);
    }
}

