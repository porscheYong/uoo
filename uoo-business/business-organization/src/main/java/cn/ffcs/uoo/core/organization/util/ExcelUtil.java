package cn.ffcs.uoo.core.organization.util;/**
 * @description:
 * @author: ffcs-gzb
 * @date: 2019-01-22
 */

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;

/**
 * <p>
 *
 * </p>
 *
 * @author ffcs-gzb
 * @since 2019/1/22
 */
public class ExcelUtil {

//    public static void exportExcel(String title, String[] headers,String filePath) throws IOException {
//        HSSFWorkbook wb = new HSSFWorkbook();//创建工作薄
//        //表头样式
//        HSSFCellStyle style = wb.createCellStyle();
//        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
//        //字体样式
//        HSSFFont fontStyle = wb.createFont();
//        fontStyle.setFontName("微软雅黑");
//        fontStyle.setFontHeightInPoints((short)12);
//        fontStyle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//        style.setFont(fontStyle);
//
//        //新建sheet
//        HSSFSheet sheet1 = wb.createSheet("Sheet1");
//
//        //生成sheet1内容
//        HSSFRow rowFirst = sheet1.createRow(0);//第一个sheet的第一行为标题
//        //写标题
//        for(int i=0;i<headers.length;i++){
//            HSSFCell cell = rowFirst.createCell(i); //获取第一行的每个单元格
//            sheet1.setColumnWidth(i, 4000); //设置每列的列宽
//            cell.setCellStyle(style); //加样式
//            cell.setCellValue(headers[i]); //往单元格里写数据
//        }
//        File f = new File(filePath); //写文件
//        //不存在则新增
//        if(!f.getParentFile().exists()){
//            f.getParentFile().mkdirs();
//        }
//        if(!f.exists()){
//            f.createNewFile();
//        }
//        FileOutputStream out = new FileOutputStream(f);
//        out.flush();
//        wb.write(out);
//        out.close();
//    }

}
