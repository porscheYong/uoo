package cn.ffcs.uoo.message.server.vo;

import java.util.Date;

public class TbOrgRelTypeVo  {
    private Long orgRelTypeId;

    private String refDesc;

    private String refName;

    private String refCode;

    private Long supOrgRefTypeId;

    private String supOrgRefTypeName;

    private String statusCd;

    private Date createDate;

    private Long createUser;

    private Date updateDate;

    private Long updateUser;

    private Date statusDate;

    public Long getOrgRelTypeId() {
        return orgRelTypeId;
    }

    public void setOrgRelTypeId(Long orgRelTypeId) {
        this.orgRelTypeId = orgRelTypeId;
    }

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

    public String getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(String statusCd) {
        this.statusCd = statusCd;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Long getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    public String getSupOrgRefTypeName() {
        return supOrgRefTypeName;
    }

    public void setSupOrgRefTypeName(String supOrgRefTypeName) {
        this.supOrgRefTypeName = supOrgRefTypeName;
    }

    public Long getSupOrgRefTypeId() {
        return supOrgRefTypeId;
    }

    public void setSupOrgRefTypeId(Long supOrgRefTypeId) {
        this.supOrgRefTypeId = supOrgRefTypeId;
    }
}
