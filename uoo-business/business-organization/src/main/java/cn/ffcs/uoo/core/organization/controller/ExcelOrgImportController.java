package cn.ffcs.uoo.core.organization.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.core.organization.entity.ExcelOrgImport;
import cn.ffcs.uoo.core.organization.service.ExcelOrgImportService;
import cn.ffcs.uoo.core.organization.util.ResponseResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ffcs-gzb
 * @since 2019-01-25
 */
@RestController
@RequestMapping("/excelOrgImport")
public class ExcelOrgImportController {

    @Autowired
    private ExcelOrgImportService excelOrgImportService;

    @ApiOperation(value = "组织导入", notes = "组织导入")
    @UooLog(value = "组织导入", key = "addExcelOrg")
    @RequestMapping(value = "/addExcelOrg", method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<String> addExcelOrg(List<ExcelOrgImport> excelOrgImports){
        ResponseResult<String> ret = new ResponseResult<String>();
        List<String> list = new ArrayList<>();
        //excelOrgImportService.
        //ExcelUtil.exportExcel("测试",);
        return ret;
    }


}

