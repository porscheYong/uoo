package cn.ffcs.uoo.core.personnel.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.base.controller.BaseController;

import cn.ffcs.uoo.core.personnel.entity.TbCert;
import cn.ffcs.uoo.core.personnel.service.TbCertService;
import cn.ffcs.uoo.core.personnel.util.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 * 证件 前端控制器
 * </p>
 *
 * @author zhanglu
 * @since 2018-09-14
 */
@Api(description = "证件",value = "Cert")
@RestController
@RequestMapping("/cert")
public class TbCertController extends BaseController {
    @Autowired
    public TbCertService tbCertService;

    @ApiOperation(value = "新增证件信息",notes = "证件信息新增")
    @ApiImplicitParam(name = "tbCert", value = "证件信息", required = true, dataType = "TbCert")
    @UooLog(value = "新增证件信息", key = "addCert")
    @RequestMapping(value = "/addCert", method = RequestMethod.POST)
    public Object addCert(TbCert tbCert) {
        tbCert.setCertId(tbCertService.getId());
        tbCertService.insert(tbCert);
        return ResultUtils.success(tbCert);
    }

    @ApiOperation(value = "修改证件信息",notes = "证件信息修改")
    @ApiImplicitParam(name = "tbCert", value = "证件信息", required = true, dataType = "TbCert")
    @UooLog(value = "修改证件信息", key = "updateCert")
    @RequestMapping(value = "/updateCert", method = RequestMethod.PUT)
    public void updateCert(TbCert tbCert) {
        tbCertService.updateAllColumnById(tbCert);
    }

    @ApiOperation(value = "删除证件信息", notes = "证件信息删除")
    @ApiImplicitParam(name = "certId", value = "教育信息标识", required = true, dataType = "Long",paramType="path")
    @UooLog(value = "删除证件信息", key = "delCert")
    @RequestMapping(value="/delCert", method = RequestMethod.DELETE)
    public void delCert(Long certId) {
        tbCertService.delTbCertById(certId);
    }

    @ApiOperation(value="证件查询",notes="证件查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyWord", value = "关键字", required = true, dataType = "String",paramType="path"),
            @ApiImplicitParam(name = "pageNo", value = "当前页数", required = true, dataType = "Integer",paramType="path"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = true, dataType = "Integer",paramType="path")
    })
    @UooLog(value = "证件查询",key = "getCertInfo")
    @RequestMapping(value="/getCertInfo",method = RequestMethod.GET)
    public Object getCertInfo(String keyWord, Integer pageNo, Integer pageSize){
        return tbCertService.getCertInfo(keyWord, pageNo, pageSize);
    }
}

