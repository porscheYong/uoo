package cn.ffcs.uoo.core.organization.vo;

import java.util.List;

public class DataRuleRequestVO {
    private String accout;
    private List<String> tableNames;
    private Long treeId;

    public Long getTreeId() {
        return treeId;
    }
    public void setTreeId(Long treeId) {
        this.treeId = treeId;
    }
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
