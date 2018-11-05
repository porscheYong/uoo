package cn.ffcs.uoo.message.server.vo;

import java.util.List;

public class OrgRefTypeVo {
    private String refDesc;
    private String refName;
    private String refCode;
    private String supOrgRefTypeId;
    private String supOrgRefTypeName;
    private List<OrgTreeVo> orgTreeInfos;

    public String getRefDesc() {
        return refDesc;
    }

    public void setRefDesc(String refDesc) {
        this.refDesc = refDesc;
    }

    public String getRefName() {
        return refName;
    }

    public void setRefName(String refName) {
        this.refName = refName;
    }

    public String getRefCode() {
        return refCode;
    }

    public void setRefCode(String refCode) {
        this.refCode = refCode;
    }

    public String getSupOrgRefTypeId() {
        return supOrgRefTypeId;
    }

    public void setSupOrgRefTypeId(String supOrgRefTypeId) {
        this.supOrgRefTypeId = supOrgRefTypeId;
    }

    public String getSupOrgRefTypeName() {
        return supOrgRefTypeName;
    }

    public void setSupOrgRefTypeName(String supOrgRefTypeName) {
        this.supOrgRefTypeName = supOrgRefTypeName;
    }

    public List<OrgTreeVo> getOrgTreeInfos() {
        return orgTreeInfos;
    }

    public void setOrgTreeInfos(List<OrgTreeVo> orgTreeInfos) {
        this.orgTreeInfos = orgTreeInfos;
    }
}
