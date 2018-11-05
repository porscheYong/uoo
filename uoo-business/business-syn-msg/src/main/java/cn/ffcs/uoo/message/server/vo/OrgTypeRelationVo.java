package cn.ffcs.uoo.message.server.vo;

import java.util.List;

public class OrgTypeRelationVo {
    private String orgTypeCode;
    private String orgTypeName;
    private String orgTypeDesc;
    private String supPkOrgType;
    private List<OrgTreeVo> orgTreeInfos;

    public String getOrgTypeCode() {
        return orgTypeCode;
    }

    public void setOrgTypeCode(String orgTypeCode) {
        this.orgTypeCode = orgTypeCode;
    }

    public String getOrgTypeName() {
        return orgTypeName;
    }

    public void setOrgTypeName(String orgTypeName) {
        this.orgTypeName = orgTypeName;
    }

    public String getOrgTypeDesc() {
        return orgTypeDesc;
    }

    public void setOrgTypeDesc(String orgTypeDesc) {
        this.orgTypeDesc = orgTypeDesc;
    }

    public String getSupPkOrgType() {
        return supPkOrgType;
    }

    public void setSupPkOrgType(String supPkOrgType) {
        this.supPkOrgType = supPkOrgType;
    }

    public List<OrgTreeVo> getOrgTreeInfos() {
        return orgTreeInfos;
    }

    public void setOrgTreeInfos(List<OrgTreeVo> orgTreeInfos) {
        this.orgTreeInfos = orgTreeInfos;
    }
}
