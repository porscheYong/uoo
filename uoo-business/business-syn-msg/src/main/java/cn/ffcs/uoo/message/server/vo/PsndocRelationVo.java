package cn.ffcs.uoo.message.server.vo;

public class PsndocRelationVo {
    private String refType;
    private String property;
    private String doubleName;
    private String sort;
    private String effDate;
    private String expDate;
    private PersonalVo personalInfo;

    public String getRefType() {
        return refType;
    }

    public void setRefType(String refType) {
        this.refType = refType;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getDoubleName() {
        return doubleName;
    }

    public void setDoubleName(String doubleName) {
        this.doubleName = doubleName;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getEffDate() {
        return effDate;
    }

    public void setEffDate(String effDate) {
        this.effDate = effDate;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public PersonalVo getPersonalInfo() {
        return personalInfo;
    }

    public void setPersonalInfo(PersonalVo personalInfo) {
        this.personalInfo = personalInfo;
    }
}
