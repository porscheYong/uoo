package cn.ffcs.uoo.message.server.vo;

public class PoliticalLocationVo {

    private String locCode;
    private String locName;
    private String locDesc;
    private String locType;
    private String locAbbr;
    private String upLocId;

    public String getLocCode() {
        return locCode;
    }

    public void setLocCode(String locCode) {
        this.locCode = locCode;
    }

    public String getLocName() {
        return locName;
    }

    public void setLocName(String locName) {
        this.locName = locName;
    }

    public String getLocDesc() {
        return locDesc;
    }

    public void setLocDesc(String locDesc) {
        this.locDesc = locDesc;
    }

    public String getLocType() {
        return locType;
    }

    public void setLocType(String locType) {
        this.locType = locType;
    }

    public String getLocAbbr() {
        return locAbbr;
    }

    public void setLocAbbr(String locAbbr) {
        this.locAbbr = locAbbr;
    }

    public String getUpLocId() {
        return upLocId;
    }

    public void setUpLocId(String upLocId) {
        this.upLocId = upLocId;
    }
}
