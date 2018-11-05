package cn.ffcs.uoo.core.personnel.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.base.controller.BaseController;
import cn.ffcs.uoo.core.personnel.entity.TbCert;
import cn.ffcs.uoo.core.personnel.service.TbCertService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
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
    @RequestMapping(value = "", method = RequestMethod.POST)
    public void addCert(TbCert tbCert) {
        tbCert.setCertId(tbCertService.getId());
        tbCertService.insert(tbCert);
    }

    @ApiOperation(value = "修改证件信息",notes = "证件信息修改")
    @ApiImplicitParam(name = "tbCert", value = "证件信息", required = true, dataType = "TbCert")
    @UooLog(value = "修改证件信息", key = "updateCert")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public void updateCert(TbCert tbCert) {
        tbCertService.updateAllColumnById(tbCert);
    }

    @ApiOperation(value = "删除证件信息", notes = "证件信息删除")
    @ApiImplicitParam(name = "tbCert", value = "证件信息", required = true, dataType = "TbCert")
    @UooLog(value = "删除证件信息", key = "delCert")
    @RequestMapping(value="", method = RequestMethod.DELETE)
    public void delCert(TbCert tbCert) {
        tbCertService.delete(tbCert);
    }
}

