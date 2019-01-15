package cn.ffcs.uoo.web.maindata.personnel.controller;

import cn.ffcs.uoo.web.maindata.mdm.logs.OperateLog;
import cn.ffcs.uoo.web.maindata.mdm.logs.OperateType;
import cn.ffcs.uoo.web.maindata.personnel.service.CertService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/cert", produces = {"application/json;charset=UTF-8"})
@Api(value = "/cert", description = "证件信息相关操作")
public class CertController {

    @Resource
    private CertService certService;

    @ApiOperation(value="证件查询",notes="证件查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyWord", value = "关键字", required = true, dataType = "String",paramType="path"),
            @ApiImplicitParam(name = "pageNo", value = "当前页数", required = true, dataType = "Integer",paramType="path"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = true, dataType = "Integer",paramType="path")
    })
    @RequestMapping(value="/getCertInfo",method = RequestMethod.GET)
    @OperateLog(type= OperateType.SELECT, module="人员管理",methods="getCertInfo",desc="证件查询")
    public Object getCertInfo(String keyWord, Integer pageNo, Integer pageSize){
        return certService.getCertInfo(keyWord, pageNo, pageSize);
    }
}
