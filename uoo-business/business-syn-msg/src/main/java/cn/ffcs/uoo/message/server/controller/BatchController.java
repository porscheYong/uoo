package cn.ffcs.uoo.message.server.controller;

import cn.ffcs.uoo.message.server.dao.TbHandelMapper;
import cn.ffcs.uoo.message.server.dao.TbImportLogMapper;
import cn.ffcs.uoo.message.server.dao.TbImportMapper;
import cn.ffcs.uoo.message.server.dao.TbOrgMapper;
import cn.ffcs.uoo.message.server.pojo.TbHandel;
import cn.ffcs.uoo.message.server.pojo.TbImport;
import cn.ffcs.uoo.message.server.pojo.TbImportLog;
import cn.ffcs.uoo.message.server.util.ReadExcelUtil;
import cn.ffcs.uoo.message.server.util.WriteExcelUtil;
import cn.ffcs.uoo.message.server.vo.ImpVo;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class BatchController {

    private Logger logger = LoggerFactory.getLogger(BatchController.class);

    @Resource
    private TbHandelMapper tbHandelMapper;

    @Resource
    private TbImportLogMapper tbImportLogMapper;

    @Resource
    private TbImportMapper tbImportMapper;


    /*下载模板*/
    @GetMapping("downExcel")
    public ResponseEntity<byte[]> downLoadExcel(HttpServletRequest request) throws IOException {
        String path = request.getSession().getServletContext().getRealPath("/");
        String resourceName = "demo.xls";
        logger.info("resourceName:{}",path+resourceName);
        File file = new File(path+resourceName);
        if(!file.exists()){
            logger.warn("resourceName:{},文件不存在,创建模板中。",path+resourceName);
            file.createNewFile();
            WriteExcelUtil<ImpVo> weu = new WriteExcelUtil<>();
            String[] headers = {"类型","值"};
            OutputStream out = null;
            try {
                out = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            List<ImpVo> list = new ArrayList<>();
            ImpVo vo = new ImpVo();
            vo.setType("orgId");
            vo.setValue("10002921");
            list.add(vo);
            weu.writeExcle(headers, list, out);
        }
        HttpHeaders headers = new HttpHeaders();
        String filename = new String(resourceName.getBytes("utf-8"),"iso-8859-1");
        headers.setContentDispositionFormData("attachment", filename);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers,HttpStatus.CREATED);
    }

    /**
     * @param file 文件
     * @param name 上传文件的人名
     * @param session
     * @return
     * @throws Exception
     */
    @PostMapping("/uploadExcel")
    @ResponseBody
    public Map<String,Object> upload(@RequestParam(required = true) MultipartFile file,
                                     @RequestParam(required = true) String name, HttpSession session) throws Exception{
        String basePath = session.getServletContext().getRealPath("/upload");
        Map<String,Object> map = new HashMap<>();
        File basePathFile = new File(basePath);

        if(!basePathFile.exists() || basePathFile.isFile()){
            basePathFile.mkdirs();
        }

        //获取文件名
        String fileName = file.getOriginalFilename();
        //目标文件
        File targetFile = new File(basePath,fileName);
        //文件存在进行删除
        if(targetFile.exists()){
            //进行删除
            targetFile.delete();
        }
        targetFile.createNewFile();
        //转存文件
        file.transferTo(targetFile);
        //读取文件
        ReadExcelUtil readExcelUtil = new ReadExcelUtil(targetFile);
        String[] headers = {"类型","值"};
        readExcelUtil.setTitle(headers);
        List<List<String>> list = readExcelUtil.readExcel();

        //生成批次号
        String code = UUID.randomUUID().toString().replaceAll("-", "").trim();
        TbHandel tbHandel = new TbHandel();
        tbHandel.setBatchCode(code);
        tbHandel.setHandleDate(new Date());
        tbHandel.setHandlePerson(name);
        //[等待：0,运行：1，结束：-1]
        tbHandel.setStatusCd("0");
        tbHandelMapper.insert(tbHandel);
        if(list != null && list.size() >0){
            for(List<String> strList:list){
                TbImport tbImport = new TbImport();
                //tbImport.setImportId(0L);//通过序列生成
                tbImport.setImportType(strList.get(0));
                tbImport.setImportValue(strList.get(1));
                tbImport.setBatchCode(code);
                //[数据错误:-1,数据等待执行:0,执行成功：1，执行失败：-2]
                tbImport.setStatusCd("0");
                tbImport.setSimpleData(null);
                tbImport.setFailReason(null);
                tbImportMapper.insert(tbImport);

                TbImportLog tbImportLog = new TbImportLog();
                tbImportLog.setImportId(tbImport.getImportId());
                //tbImportLog.setImportId(0L);//通过序列生成
                //[执行成功：1，执行失败：-1]
                tbImportLog.setStatusCd("1");
                if((tbImport.getImportType() == null) || (!"orgId".equals(tbImport.getImportType())  && !"slaveAcctId".equals(tbImport.getImportType())
                        && !"personnelId".equals(tbImport.getImportType()))){
                    tbImportLog.setStatusCd("-1");
                    tbImportLog.setFailReason("类型不正确");
                }
                if((tbImport.getImportValue() == null) || !isNumeric(tbImport.getImportValue())){
                    if(tbImportLog.getStatusCd().equals("-1")){
                        tbImportLog.setFailReason("类型不正确,值不正确");
                    }else{
                        tbImportLog.setStatusCd("-1");
                        tbImportLog.setFailReason("值不正确");
                    }
                }
                tbImportLogMapper.insert(tbImportLog);
                if(tbImportLog.getStatusCd().equals("-1")){
                    tbImportMapper.updateById(tbImport);
                }
            }
        }
        map.put("status","success");
        return map;
    }

    //判断是不是数字
    private static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }
}