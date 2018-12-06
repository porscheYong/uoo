package cn.ffcs.uoo.web.maindata.permission.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.ffcs.uoo.web.maindata.permission.dto.PrivDataRel;
import cn.ffcs.uoo.web.maindata.permission.service.PrivDataRelService;
import cn.ffcs.uoo.web.maindata.permission.vo.ResponseResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 记录权限与业务对象之间多对多的关系 前端控制器
 * </p>
 *
 * @author zengxsh
 * @since 2018-11-08
 */
@RestController
@RequestMapping("/permission/privDataRel")
public class PrivDataRelController {
    @Autowired
    private PrivDataRelService relSvc;
     
    
    @ApiOperation(value = "添加权限与业务对象之间多对多的关系", notes = "添加权限与业务对象之间多对多的关系")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "privDataRel", value = "privDataRel", required = true, dataType = "PrivDataRel"  ),
    })
    @RequestMapping(value="addPrivDataRel",method=RequestMethod.POST)
    public ResponseResult<Void> addPrivDataRel(@RequestBody PrivDataRel privDataRel){
         
        return relSvc.addPrivDataRel(privDataRel);
    }
    
    @ApiOperation(value = "修改权限与业务对象之间多对多的关系", notes = "修改权限与业务对象之间多对多的关系")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "privDataRel", value = "privDataRel", required = true, dataType = "PrivDataRel"  ),
    })
    @RequestMapping(value="updatePrivDataRel",method=RequestMethod.POST)
    public ResponseResult<Void> updatePrivDataRel(@RequestBody PrivDataRel privDataRel){
        return relSvc.updatePrivDataRel(privDataRel);
    }
    @ApiOperation(value = "删除权限与业务对象之间多对多的关系", notes = "删除权限与业务对象之间多对多的关系")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "privDataRel", value = "privDataRel", required = true, dataType = "PrivDataRel"  ),
    })
    @RequestMapping(value="deletePrivDataRel",method=RequestMethod.POST)
    public ResponseResult<Void> deletePrivDataRel(@RequestBody PrivDataRel privDataRel){
        return relSvc.deletePrivDataRel(privDataRel);
    }
}

