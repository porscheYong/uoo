package cn.ffcs.uoo.message.server.pojo;

import com.baomidou.mybatisplus.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

public class TbSystemOrgTree extends Model<TbSystemOrgTree> {
    private Long systemOrgTreeId;

    private Long businessSystemId;

    private Long orgTreeId;

    private Short includePsn;

    private Short includeSlaveAcct;

    private Date effDate;

    private Date expDate;

    private String statusCd;

    private Date statusDate;

    private Long createUser;

    private Date createDate;

    private Long updateUser;

    private Date updateDate;

    public Long getSystemOrgTreeId() {
        return systemOrgTreeId;
    }

    public void setSystemOrgTreeId(Long systemOrgTreeId) {
        this.systemOrgTreeId = systemOrgTreeId;
    }

    public Long getBusinessSystemId() {
        return businessSystemId;
    }

    public void setBusinessSystemId(Long businessSystemId) {
        this.businessSystemId = businessSystemId;
    }

    public Short getIncludePsn() {
        return includePsn;
    }

    public void setIncludePsn(Short includePsn) {
        this.includePsn = includePsn;
    }

    public Short getIncludeSlaveAcct() {
        return includeSlaveAcct;
    }

    public void setIncludeSlaveAcct(Short includeSlaveAcct) {
        this.includeSlaveAcct = includeSlaveAcct;
    }

    public Date getEffDate() {
        return effDate;
    }

    public void setEffDate(Date effDate) {
        this.effDate = effDate;
    }

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    public String getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(String statusCd) {
        this.statusCd = statusCd;
    }

    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Long getOrgTreeId() {
        return orgTreeId;
    }

    public void setOrgTreeId(Long orgTreeId) {
        this.orgTreeId = orgTreeId;
    }

    @Override
    protected Serializable pkVal() {
        return this.systemOrgTreeId;
    }
}