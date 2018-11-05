package cn.ffcs.uoo.message.server.vo;

public class OrgRelationVo {
    private String supOrgId;
    private String supOrgName;

    public String getSupOrgId() {
        return supOrgId;
    }

    public void setSupOrgId(String supOrgId) {
        this.supOrgId = supOrgId;
    }

    public String getSupOrgName() {
        return supOrgName;
    }

    public void setSupOrgName(String supOrgName) {
        this.supOrgName = supOrgName;
    }

    public OrgRefTypeVo getOrgRefType() {
        return orgRefType;
    }

    public void setOrgRefType(OrgRefTypeVo orgRefType) {
        this.orgRefType = orgRefType;
    }

    private OrgRefTypeVo orgRefType;
}
