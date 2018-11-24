package cn.ffcs.uoo.message.server.vo;

import cn.ffcs.uoo.message.server.pojo.TbOrgRelType;

import java.util.Date;

public class TbOrgRelVo {
    private Long orgRelId;

    private Long orgId;

    private Long supOrgId;

    private Long orgRelTypeId;

    private String statusCd;

    private Date createDate;

    private Long createUser;

    private Date updateDate;

    private Long updateUser;

    private Date statusDate;

    private String supOrgName;

    private TbOrgRelTypeVo orgRefTypeInfo;

    public TbOrgRelTypeVo getOrgRefTypeInfo() {
        return orgRefTypeInfo;
    }

    public void setOrgRefTypeInfo(TbOrgRelTypeVo orgRefTypeInfo) {
        this.orgRefTypeInfo = orgRefTypeInfo;
    }

    public Long getOrgRelId() {
        return orgRelId;
    }

    public void setOrgRelId(Long orgRelId) {
        this.orgRelId = orgRelId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getSupOrgId() {
        return supOrgId;
    }

    public void setSupOrgId(Long supOrgId) {
        this.supOrgId = supOrgId;
    }

    public Long getOrgRelTypeId() {
        return orgRelTypeId;
    }

    public void setOrgRelTypeId(Long orgRelTypeId) {
        this.orgRelTypeId = orgRelTypeId;
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

    public String getSupOrgName() {
        return supOrgName;
    }

    public void setSupOrgName(String supOrgName) {
        this.supOrgName = supOrgName;
    }
}
