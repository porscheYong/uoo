package cn.ffcs.uoo.web.maindata.busipublic.expando.dto;

/**
 * 扩展值值对象
 * @author zhanglu
 */
public class ExpandovalueVo {
    private String recordId;
    private String columnName;
    private String columnCnname;
    private String data;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnCnname() {
        return columnCnname;
    }

    public void setColumnCnname(String columnCnname) {
        this.columnCnname = columnCnname;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
