package cn.ffcs.uoo.system.vo;

import java.util.List;

public class DataRuleRequestVO {
    private String accout;
    private List<String> tableNames;
    public String getAccout() {
        return accout;
    }
    public void setAccout(String accout) {
        this.accout = accout;
    }
    public List<String> getTableNames() {
        return tableNames;
    }
    public void setTableNames(List<String> tableNames) {
        this.tableNames = tableNames;
    }
}
