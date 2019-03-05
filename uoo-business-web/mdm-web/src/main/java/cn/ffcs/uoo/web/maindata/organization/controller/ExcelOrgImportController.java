package cn.ffcs.uoo.web.maindata.organization.controller;



import cn.ffcs.uoo.web.maindata.common.system.dto.SysUser;
import cn.ffcs.uoo.web.maindata.mdm.consts.LoginConsts;
import cn.ffcs.uoo.web.maindata.mdm.logs.OperateLog;
import cn.ffcs.uoo.web.maindata.mdm.logs.OperateType;
import cn.ffcs.uoo.web.maindata.organization.dto.ExcelOrgImport;
import cn.ffcs.uoo.web.maindata.organization.dto.ResponseResult;
import cn.ffcs.uoo.web.maindata.organization.service.ExcelOrgImportService;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
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
@RestController
@Api(value = "excel组织", description = "excel组织")
@RequestMapping("/excelOrgImport")
public class ExcelOrgImportController {

    @Resource
    private ExcelOrgImportService excelOrgImportService;

    @ApiOperation(value = "生成excel文件数据", notes = "生成excel文件数据")
    @OperateLog(type= OperateType.ADD, module="生成excel文件数据",methods="importExcelFileData",desc="生成excel文件数据")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/importExcelFileData", method=RequestMethod.POST,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseResult<String> importExcelFileData(@RequestPart(value="fileInfo",required = false)MultipartFile fileInfo,
                                                      @RequestParam(value="orgTreeId",required = false)String orgTreeId,
                                                      @RequestParam(value = "userId",required = false)Long userId,
                                                      @RequestParam(value = "accout",required = false)String accout,
                                                      @RequestParam(value = "impType",required = false)String impType){
        Subject subject=SecurityUtils.getSubject();
        SysUser currentLoginUser = (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        userId = currentLoginUser.getUserId();
        accout = currentLoginUser.getAccout();
        return excelOrgImportService.importExcelFileData(fileInfo,orgTreeId,userId,accout,impType);
    }




    @ApiOperation(value = "保存数据", notes = "保存数据")
    @OperateLog(type= OperateType.ADD, module="保存数据",methods="addExcelFileData",desc="保存数据")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/addExcelFileData", method=RequestMethod.GET)
    public ResponseResult<String> addExcelFileData(@RequestParam(value="fileSign",required = false)String fileSign,
                                                   @RequestParam(value="userId",required = false)Long userId,
                                                   @RequestParam(value="accout",required = false)String accout){
        Subject subject=SecurityUtils.getSubject();
        SysUser currentLoginUser = (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        userId = currentLoginUser.getUserId();
        accout = currentLoginUser.getAccout();
        return excelOrgImportService.addExcelFileData(fileSign,userId,accout);
    }

    @ApiOperation(value = "获取文件数据", notes = "获取文件数据")
    @OperateLog(type= OperateType.SELECT, module="获取文件数据",methods="getExcelFileData",desc="保存数据")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/getExcelFileData", method=RequestMethod.GET)
    public ResponseResult<Page<ExcelOrgImport>> getExcelFileData(@RequestParam(value="fileSign",required = false)String fileSign,
                                                                 @RequestParam(value="dataSign",required = false)String dataSign,
                                                                 @RequestParam(value="pageSize",required = false)Integer pageSize,
                                                                 @RequestParam(value="pageNo",required = false)Integer pageNo,
                                                                 @RequestParam(value="userId",required = false)Long userId,
                                                                 @RequestParam(value="accout",required = false)String accout){
        Subject subject=SecurityUtils.getSubject();
        SysUser currentLoginUser = (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        userId = currentLoginUser.getUserId();
        accout = currentLoginUser.getAccout();
        return excelOrgImportService.getExcelFileData(fileSign,dataSign,pageSize,pageNo,userId,accout);
    }
}

