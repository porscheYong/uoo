package cn.ffcs.uoo.web.maindata.organization.service;



import cn.ffcs.uoo.web.maindata.mdm.logs.OperateLog;
import cn.ffcs.uoo.web.maindata.mdm.logs.OperateType;
import cn.ffcs.uoo.web.maindata.organization.dto.ExcelOrgImport;
import cn.ffcs.uoo.web.maindata.organization.dto.ResponseResult;
import cn.ffcs.uoo.web.maindata.organization.service.fallback.ExcelOrgImportServiceHystrix;
import cn.ffcs.uoo.web.maindata.organization.service.fallback.OrgContactRelServiceHystrix;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.plugins.Page;
import common.config.PersonnelImageServiceConfiguration;
import common.config.PersonnelServiceConfiguration;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ffcs-gzb
 * @since 2019-01-25
 */
@FeignClient(value = "business-organization",configuration = {PersonnelImageServiceConfiguration.class},fallback = ExcelOrgImportServiceHystrix.class)
public interface ExcelOrgImportService {



    @RequestMapping(value = "/excelOrgImport/importExcelFileData", method=RequestMethod.POST,produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseResult<String> importExcelFileData(@RequestPart(value="fileInfo",required = false) MultipartFile fileInfo,
                                                      @RequestParam(value = "userId",required = false)Long userId,
                                                      @RequestParam(value = "accout",required = false)String accout);



    @RequestMapping(value = "/excelOrgImport/addExcelFileData", method=RequestMethod.POST)
    public ResponseResult<String> addExcelFileData(@RequestParam(value = "fileSign",required = false)String fileSign,
                                                   @RequestParam(value = "userId",required = false)Long userId,
                                                   @RequestParam(value = "accout",required = false)String accout);




    @RequestMapping(value = "/excelOrgImport/getExcelFileData", method=RequestMethod.GET)
    public ResponseResult<Page<ExcelOrgImport>> getExcelFileData(@RequestParam(value = "fileSign",required = false)String fileSign,
                                                                 @RequestParam(value = "dataSign",required = false)String dataSign,
                                                                 @RequestParam(value = "pageSize",required = false)Integer pageSize,
                                                                 @RequestParam(value = "pageNo",required = false)Integer pageNo,
                                                                 @RequestParam(value = "userId",required = false)Long userId,
                                                                 @RequestParam(value = "accout",required = false)String accout);
}

