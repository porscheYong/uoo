package cn.ffcs.uoo.message.server.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcelUtil {
    private File file;

    private String[] title;

    //格式化 number String
    private static final DecimalFormat DF = new DecimalFormat("0");
    // 格式化日期字符串
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    // 格式化数字
    private static final DecimalFormat NF = new DecimalFormat("0");

    public static DecimalFormat getDf() {
        return DF;
    }

    public static SimpleDateFormat getSdf() {
        return SDF;
    }

    public static DecimalFormat getNf() {
        return NF;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String[] getTitle() {
        return title;
    }

    public void setTitle(String[] title) {
        this.title = title;
    }

    //传文件过来
    public ReadExcelUtil(File file) {
        super();
        this.file = file;
    }
    //传文件名过来的
    public ReadExcelUtil(String pathname) {
        super();
        this.file = new File(pathname);
    }
    public ReadExcelUtil() {
        super();
    }

    /**
     * 将文件中的数据写入到List<<List<Object>>中
     * List<Object>存放的是表格中一行的数据
     * @return
     * @throws IOException
     */
    public List<List<String>> readExcel() throws IOException{
        List<List<String>> list = null;
        int flag = check();
        if(flag==0)
            return null;
        list = read(flag);
        if(list==null || list.size()==0) {
            return null;
        }
        return list;
    }

    /**
     * @param file
     * @return
     * 	1:文件格式为:xls
     * 	2:文件格式为:xlsx
     * 	0:文件不存在 或 文件格式不支持
     */
    private int check() {
        if(file==null || !file.exists()) {
            return 0;
        }
        String fileName = file.getName();
        String extension = fileName.lastIndexOf(".") == -1 ? "" : fileName
                .substring(fileName.lastIndexOf(".") + 1);
        if ("xls".equals(extension)) {
            return 1;
        } else if ("xlsx".equals(extension)) {
            return 2;
        } else {
            return 0;
        }
    }
    private List<List<String>> read(int index)
            throws IOException{
        List<List<String>> list = new LinkedList<>();
        List<String> listRow = null;
        Object value = null;
        Workbook wb = null;
        Sheet sheet =null;
        Row row = null;
        Cell cell = null;

        int counter =0;

        switch (index) {
            case 1:wb = new HSSFWorkbook(new FileInputStream(file));break;
            case 2:wb = new XSSFWorkbook(new FileInputStream(file));break;
            default:break;
        }

        for (int i = 0; i < wb.getNumberOfSheets(); i++) {
            //获取sheet
            sheet = wb.getSheetAt(i);
            //获取row
            int size = 0;
            int fist = 0;
            counter = 0;
            for (int j = sheet.getFirstRowNum(); counter <sheet.getPhysicalNumberOfRows();j++) {
                //获取row
                row = sheet.getRow(j);
                if(row == null) {
                    continue;
                }
                counter ++ ;
                if(j==sheet.getFirstRowNum()) {
                    //标题栏
                    fist = row.getFirstCellNum();
                    size = row.getLastCellNum()-fist;
                    List<String> ts = new LinkedList<>();
                    //获取标题数据。
                    for (int k = fist; k < fist+size; k++) {
                        cell = row.getCell(k);
                        if(cell == null) {
                            value = "";
                        }else {
                            switch (cell.getCellType()) {
                                case XSSFCell.CELL_TYPE_STRING:
                                    value = cell.getStringCellValue();
                                    break;
                                case XSSFCell.CELL_TYPE_NUMERIC:
                                    if ("@".equals(cell.getCellStyle().getDataFormatString())) {
                                        value = DF.format(cell.getNumericCellValue());
                                    } else if ("General".equals(cell.getCellStyle()
                                            .getDataFormatString())) {
                                        value = NF.format(cell.getNumericCellValue());
                                    } else {
                                        value = SDF.format(HSSFDateUtil.getJavaDate(cell
                                                .getNumericCellValue()));
                                    }
                                    break;
                                case XSSFCell.CELL_TYPE_BOOLEAN:
                                    value = cell.getBooleanCellValue();
                                    break;
                                case XSSFCell.CELL_TYPE_BLANK:
                                    value = "";
                                    break;
                                default:
                                    value = cell.toString();
                            }
                        }
                        ts.add(value.toString());
                    }

                    if(checkTitle(ts))
                        continue;
                    return null;
                }
                listRow = new LinkedList<>();
                for (int k = fist; k < fist+size; k++) {
                    cell = row.getCell(k);
                    if(cell == null) {
                        value = "";
                    }else {
                        switch (cell.getCellType()) {
                            case XSSFCell.CELL_TYPE_STRING:
                                value = cell.getStringCellValue();
                                break;
                            case XSSFCell.CELL_TYPE_NUMERIC:
                                if ("@".equals(cell.getCellStyle().getDataFormatString())) {
                                    value = DF.format(cell.getNumericCellValue());
                                } else if ("General".equals(cell.getCellStyle()
                                        .getDataFormatString())) {
                                    value = NF.format(cell.getNumericCellValue());
                                } else {
                                    value = SDF.format(HSSFDateUtil.getJavaDate(cell
                                            .getNumericCellValue()));
                                }
                                break;
                            case XSSFCell.CELL_TYPE_BOOLEAN:
                                value = cell.getBooleanCellValue();
                                break;
                            case XSSFCell.CELL_TYPE_BLANK:
                                value = "";
                                break;
                            default:
                                value = cell.toString();
                        }
                    }
                    listRow.add(value.toString());
                }
                //去除空行
                int z =0;
                for (;z<listRow.size();z++) {
                    if(!listRow.get(z).equals(""))
                        break;
                }
                if(z!=listRow.size())
                    list.add(listRow);
            }
        }
        return list;
    }

    private boolean checkTitle(List<String> ts){
        if(ts.size() != title.length)
            return false;
        else {
            for (int i = 0; i < title.length; i++) {
                if(!title[i].equals(ts.get(i)))
                    return false;
            }
        }
        return true;
    }
}