package cn.ffcs.uoo.message.server.vo;

import cn.ffcs.uoo.message.server.pojo.TbOrg;

import java.util.Date;

public class TbSalveAcctOrgRelVo {
    private Long acctHostId;

    private Long orgId;

    private Long acctId;

    private Long sort;

    private String statusCd;

    private Date createDate;

    private Long createUser;

    private Date updateDate;

    private Long updateUser;

    private Date statusDate;

    private TbOrg tbOrg;

    public TbOrg getTbOrg() {
        return tbOrg;
    }

    public void setTbOrg(TbOrg tbOrg) {
        this.tbOrg = tbOrg;
    }

    public Long getAcctHostId() {
        return acctHostId;
    }

    public void setAcctHostId(Long acctHostId) {
        this.acctHostId = acctHostId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getAcctId() {
        return acctId;
    }

    public void setAcctId(Long acctId) {
        this.acctId = acctId;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
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

}