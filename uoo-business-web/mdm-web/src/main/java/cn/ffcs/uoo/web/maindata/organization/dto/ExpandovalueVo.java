package cn.ffcs.uoo.web.maindata.organization.dto;


public class ExpandovalueVo {
    private String tableName;
    private String recordId;
    private String columnName;
    private String columnCnname;
    private Long valueId;
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

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Long getValueId() {
        return valueId;
    }

    public void setValueId(Long valueId) {
        this.valueId = valueId;
    }
}
