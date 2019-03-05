package cn.ffcs.uoo.core.organization.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.base.common.tool.util.DateUtils;
import cn.ffcs.uoo.core.organization.entity.ExcelOrgImport;
import cn.ffcs.uoo.core.organization.entity.Org;
import cn.ffcs.uoo.core.organization.entity.OrgOrgtreeRel;
import cn.ffcs.uoo.core.organization.service.*;
import cn.ffcs.uoo.core.organization.util.ResponseResult;
import cn.ffcs.uoo.core.organization.util.StrUtil;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.Iterator;
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
    @Autowired
    private OrgService orgService;
    @Autowired
    private OrgRelService orgRelService;
    @Autowired
    private ModifyHistoryService modifyHistoryService;
    @Autowired
    private OrgOrgtreeRelService orgOrgtreeRelService;


    @ApiOperation(value = "生成excel文件数据", notes = "生成excel文件数据")
    @UooLog(value = "生成excel文件数据", key = "importExcelFileData")
    @RequestMapping(value = "/importExcelFileData", method=RequestMethod.POST,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<String> importExcelFileData(@RequestPart(value="fileInfo") MultipartFile fileInfo,
                                                      @RequestParam("orgTreeId")String orgTreeId,
                                                      @RequestParam("userId")Long userId,
                                                      @RequestParam("accout")String accout,
                                                      @RequestParam("impType")String impType) throws IOException {
        ResponseResult<String> ret = new ResponseResult<String>();
        if(StrUtil.isNullOrEmpty(orgTreeId)){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织树标识不能为空");
        }
        if(StrUtil.isNullOrEmpty(impType)){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("导入类型不能为空");
        }
        String fileName = fileInfo.getOriginalFilename();
        FileInputStream is = (FileInputStream) fileInfo.getInputStream();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        Workbook wb = null;
        if(suffix.equals(".xlsx")){
            wb = new XSSFWorkbook(is);
        }else if (suffix.equals(".xls")){
            wb = new HSSFWorkbook(is);
        }
        Sheet sheet = wb.getSheetAt(0);
        int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
        if(rowCount-3>100){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织导入数量最大为100条");
        }
        long time = System.currentTimeMillis();
        String t = String.valueOf(time/1000);
        List<ExcelOrgImport> excelList = new ArrayList<>();
        /**
         * 组织上下级移动
         */
        if(impType.equals("1")){
            for(int i=3;i<rowCount+1;i++){
                Row row = sheet.getRow(i);
                String errMsg = "";
                ExcelOrgImport excelOrgImport = new ExcelOrgImport();
                for(int j=0;j<row.getLastCellNum();j++){
                    String cellStr = "";
                    if(StrUtil.isNullOrEmpty(row.getCell(j))){
                        cellStr = "";
                    }else if (!StrUtil.isNullOrEmpty(row.getCell(j)) && row.getCell(j).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                        Long longVal = Math.round(row.getCell(j).getNumericCellValue());
                        cellStr = longVal.toString();
                    }else if (!StrUtil.isNullOrEmpty(row.getCell(j)) && row.getCell(j).getCellType() == Cell.CELL_TYPE_STRING) {
                        cellStr = row.getCell(j).getStringCellValue();
                    }
                    if(j==0){
                        excelOrgImport.setImpSeq(cellStr);
                    }
                    if(j==1){
                        excelOrgImport.setOrgId(cellStr);
                    }
                    if(j==2){
                        excelOrgImport.setParentOrgId(cellStr);
                    }
                    if(j==3){
                        excelOrgImport.setOrgName(cellStr);
                    }
                }
                System.out.println("orgId:"+excelOrgImport.getOrgId()+",pOrgId:"+excelOrgImport.getParentOrgId()+",OrgName:"+excelOrgImport.getOrgName()
                        +",orgTreeId:"+orgTreeId);
                Long excelId = excelOrgImportService.getId();
                excelOrgImport.setFileName(fileName);
                excelOrgImport.setExcelOrgImportId(excelId);
                excelOrgImport.setFileSign(t);
                errMsg = orgService.JudgeMoveOrg(
                        StrUtil.isNullOrEmpty(excelOrgImport.getOrgId())?null:new Long(excelOrgImport.getOrgId()),
                        StrUtil.isNullOrEmpty(excelOrgImport.getParentOrgId())?null:new Long(excelOrgImport.getParentOrgId()),
                        excelOrgImport.getOrgName(),
                        new Long(orgTreeId));

                if(StrUtil.isNullOrEmpty(errMsg) && excelList!=null && excelList.size()>0){
                    for(ExcelOrgImport e : excelList){
                        if(e.getOrgName().equals(excelOrgImport.getOrgName()) &&
                                e.getOrgId().equals(excelOrgImport.getOrgId()) &&
                                e.getParentOrgId().equals(excelOrgImport.getParentOrgId())){
                            errMsg="数据重复导入";
                            break;
                        }
                    }
                }
                excelOrgImport.setImpType(impType);
                excelOrgImport.setSign(StrUtil.isNullOrEmpty(errMsg)?"0":"1");
                excelOrgImport.setContent(StrUtil.isNullOrEmpty(errMsg)?"":errMsg);
                excelOrgImport.setOrgTreeId(new Long(orgTreeId));
                excelOrgImport.setCreateUser(userId);
                excelOrgImportService.add(excelOrgImport);
                excelList.add(excelOrgImport);
            }
        } else if(impType.equals("2")){
            for(int i=3;i<rowCount+1;i++){
                Row row = sheet.getRow(i);
                String errMsg = "";
                ExcelOrgImport excelOrgImport = new ExcelOrgImport();
                for(int j=0;j<row.getLastCellNum();j++){
                    String cellStr = "";
                    if(StrUtil.isNullOrEmpty(row.getCell(j))){
                        cellStr = "";
                    }else if (!StrUtil.isNullOrEmpty(row.getCell(j)) && row.getCell(j).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                        Long longVal = Math.round(row.getCell(j).getNumericCellValue());
                        cellStr = longVal.toString();
                    }else if (!StrUtil.isNullOrEmpty(row.getCell(j)) && row.getCell(j).getCellType() == Cell.CELL_TYPE_STRING) {
                        cellStr = row.getCell(j).getStringCellValue();
                    }
                    if(j==0){
                        excelOrgImport.setImpSeq(cellStr);
                    }
                    if(j==1){
                        excelOrgImport.setOrgId(cellStr);
                    }
                    if(j==2){
                        excelOrgImport.setOrgName(cellStr);
                    }
                }
                Long excelId = excelOrgImportService.getId();
                excelOrgImport.setFileName(fileName);
                excelOrgImport.setExcelOrgImportId(excelId);
                excelOrgImport.setFileSign(t);
                // TODO: 2019/3/1
                errMsg = orgService.JudgeUpdateOrgName(excelOrgImport.getOrgId(),excelOrgImport.getOrgName(),new Long(orgTreeId));
                if(StrUtil.isNullOrEmpty(errMsg) && excelList!=null && excelList.size()>0){
                    for(ExcelOrgImport e : excelList){
                        if(e.getOrgId().equals(excelOrgImport.getOrgId())){
                            errMsg="数据重复导入";
                            break;
                        }
                    }
                }
                excelOrgImport.setImpType(impType);
                excelOrgImport.setSign(StrUtil.isNullOrEmpty(errMsg)?"0":"1");
                excelOrgImport.setContent(StrUtil.isNullOrEmpty(errMsg)?"":errMsg);
                excelOrgImport.setOrgTreeId(new Long(orgTreeId));
                excelOrgImport.setCreateUser(userId);
                excelOrgImportService.add(excelOrgImport);
                excelList.add(excelOrgImport);
            }
        }
        ret.setState(ResponseResult.STATE_OK);
        ret.setData(t);
        return ret;
    }




    @ApiOperation(value = "保存数据", notes = "保存数据")
    @UooLog(value = "保存数据", key = "addExcelFileData")
    @RequestMapping(value = "/addExcelFileData", method=RequestMethod.GET)
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<String> addExcelFileData(@RequestParam("fileSign")String fileSign,
                                                   @RequestParam("userId")Long userId,
                                                   @RequestParam("accout")String accout) throws IOException {
        ResponseResult<String> ret = new ResponseResult<String>();
        if(StrUtil.isNullOrEmpty(fileSign)){
            ret.setMessage("文件标识不能为空");
            ret.setState(ResponseResult.PARAMETER_ERROR);
            return ret;
        }
//        com.baomidou.mybatisplus.mapper.Wrapper excelerrWrapper = Condition.create()
//                .eq("FILE_SIGN",fileSign)
//                .eq("SIGN","1")
//                .eq("STATUS_CD","1000");
//        int errCount = excelOrgImportService.selectCount(excelerrWrapper);
//        if(errCount>0){
//            ret.setMessage("文件数据错误未处理");
//            ret.setState(ResponseResult.PARAMETER_ERROR);
//            return ret;
//        }
        com.baomidou.mybatisplus.mapper.Wrapper excelWrapper = Condition.create()
                .eq("FILE_SIGN",fileSign)
                .eq("SIGN","0")
                .eq("STATUS_CD","1000");
        List<ExcelOrgImport> excelOrgImportList = excelOrgImportService.selectList(excelWrapper);
        if(excelOrgImportList!=null){
            String batchNumber = modifyHistoryService.getBatchNumber();
            for(ExcelOrgImport exo : excelOrgImportList){
                if(exo.getImpType().equals("1")){
                    orgService.moveOrg(new Long(exo.getOrgId()),new Long(exo.getParentOrgId()),exo.getOrgTreeId(),userId,batchNumber);
                    com.baomidou.mybatisplus.mapper.Wrapper orgOrgTreeRelWrapper = Condition.create()
                            .eq("ORG_ID",exo.getOrgId())
                            .eq("STATUS_CD","1000")
                            .eq("ORG_TREE_ID",exo.getOrgTreeId());
                    OrgOrgtreeRel ootr = orgOrgtreeRelService.selectOne(orgOrgTreeRelWrapper);
                    if(ootr!=null){
                        String fullName = orgOrgtreeRelService.getFullBizOrgNameList(exo.getOrgTreeId().toString(),ootr.getOrgId().toString(),"");
                        String fullOrgId = orgOrgtreeRelService.getFullBizOrgIdList(exo.getOrgTreeId().toString(),ootr.getOrgId().toString(),",");
                        fullOrgId =","+fullOrgId+",";
                        ootr.setOrgBizFullName(fullName);
                        ootr.setOrgBizFullId(fullOrgId);
                        ootr.setUpdateUser(userId);
                        orgOrgtreeRelService.update(ootr);
                    }
                }else if(exo.getImpType().equals("2")){
                    com.baomidou.mybatisplus.mapper.Wrapper orgWrapper = Condition.create()
                            .eq("ORG_ID",exo.getOrgId())
                            .eq("STATUS_CD","1000");
                    Org org = orgService.selectOne(orgWrapper);
                    if(org!=null){
                        org.setOrgName(exo.getOrgName());
                        org.setUpdateUser(userId);
                        orgService.update(org);
                    }
                }
                exo.setUpdateUser(userId);
                exo.setStatusCd("1100");
                excelOrgImportService.update(exo);
            }
        }

        ret.setMessage("处理成功");
        ret.setState(ResponseResult.STATE_OK);
        return ret;
    }

    @ApiOperation(value = "获取文件数据", notes = "获取文件数据")
    @UooLog(value = "获取文件数据", key = "getExcelFileData")
    @RequestMapping(value = "/getExcelFileData", method=RequestMethod.GET)
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<Page<ExcelOrgImport>> getExcelFileData(String fileSign,
                                                                 String dataSign,
                                                                 Integer pageSize,
                                                                 Integer pageNo,
                                                                 Long userId,
                                                                 String accout) throws IOException {
        ResponseResult<Page<ExcelOrgImport>> ret = new ResponseResult<Page<ExcelOrgImport>>();
        if(StrUtil.isNullOrEmpty(fileSign)){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("文件标识不能为空");
        }

        com.baomidou.mybatisplus.mapper.Wrapper excelWrapper = Condition.create()
                .eq("FILE_SIGN",fileSign)
                .eq("STATUS_CD","1000");
        if(!StrUtil.isNullOrEmpty(dataSign)){
            excelWrapper.eq("SIGN",dataSign);
        }
        Page<ExcelOrgImport> excelOrgImportPage = excelOrgImportService.selectPage(new Page<ExcelOrgImport>(
                StrUtil.isNullOrEmpty(pageNo)||pageNo==0?1:pageNo,
                StrUtil.isNullOrEmpty(pageSize)||pageSize==0?10:pageSize),excelWrapper);
        ret.setData(excelOrgImportPage);
        ret.setState(ResponseResult.STATE_OK);
        return ret;
    }


    @ApiOperation(value = "模板导出", notes = "模板导出")
    @UooLog(value = "模板导出", key = "exportExcelFileData")
    @RequestMapping(value = "/exportExcelFileData", method=RequestMethod.GET)
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<String> exportExcelFileData(ExcelOrgImport excelOrgImport,
                                                      Long userId,
                                                      String accout) throws IOException {
        ResponseResult<String> ret = new ResponseResult<String>();
        ret.setData("");
        ret.setState(ResponseResult.STATE_OK);
        return ret;
    }

    @ApiOperation(value = "更新数据", notes = "更新数据")
    @UooLog(value = "更新数据", key = "updateExcelFileData")
    @RequestMapping(value = "/updateExcelFileData", method=RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<String> updateExcelFileData(ExcelOrgImport excelOrgImport,
                                                                 Long userId,
                                                                 String accout) throws IOException {
        ResponseResult<String> ret = new ResponseResult<String>();
//        com.baomidou.mybatisplus.mapper.Wrapper excelWrapper = Condition.create()
//                .eq("FILE_SIGN",fileSign)
//                .eq("SIGN",dataSign)
//                .eq("STATUS_CD","1000");
//        Page<ExcelOrgImport> excelOrgImportPage = excelOrgImportService.selectPage(new Page<ExcelOrgImport>(
//                StrUtil.isNullOrEmpty(pageNo)||pageNo==0?1:pageNo,
//                StrUtil.isNullOrEmpty(pageSize)||pageSize==0?10:pageSize));
        ret.setData("");
        ret.setState(ResponseResult.STATE_OK);
        return ret;
    }

}

