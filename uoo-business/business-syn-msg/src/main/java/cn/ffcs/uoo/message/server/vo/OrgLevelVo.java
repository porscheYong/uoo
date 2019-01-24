package cn.ffcs.uoo.message.server.vo;

import cn.ffcs.uoo.message.server.pojo.TbOrgTree;

import java.util.Date;

public class OrgLevelVo {

    private Long orgLevelId;

    private Long orgId;

    private Long orgTreeId;

    private Short orgLevel;

    private String statusCd;

    private Date createDate;

    private Long createUser;

    private Date updateDate;

    private Long updateUser;

    private Date statusDate;

    private Short sort;

    private TbOrgTree orgTreeInfo;

    public Long getOrgLevelId() {
        return orgLevelId;
    }

    public void setOrgLevelId(Long orgLevelId) {
        this.orgLevelId = orgLevelId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getOrgTreeId() {
        return orgTreeId;
    }

    public void setOrgTreeId(Long orgTreeId) {
        this.orgTreeId = orgTreeId;
    }

    public Short getOrgLevel() {
        return orgLevel;
    }

    public void setOrgLevel(Short orgLevel) {
        this.orgLevel = orgLevel;
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

    public TbOrgTree getOrgTreeInfo() {
        return orgTreeInfo;
    }

    public void setOrgTreeInfo(TbOrgTree orgTreeInfo) {
        this.orgTreeInfo = orgTreeInfo;
    }

    public Short getSort() {
        return sort;
    }

    public void setSort(Short sort) {
        this.sort = sort;
    }
}