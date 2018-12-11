package cn.ffcs.uoo.message.server.pojo;
import com.baomidou.mybatisplus.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

public class TbOrgCrossRel extends Model<TbOrgCrossRel>{
    private Long orgCrossRelId;

    private Long orgId;

    private String crossTran;

    private String relaType;

    private String statusCd;

    private Date createDate;

    private Long createUser;

    private Date updateDate;

    private Long updateUser;

    private Date statusDate;

    public Long getOrgCrossRelId() {
        return orgCrossRelId;
    }

    public void setOrgCrossRelId(Long orgCrossRelId) {
        this.orgCrossRelId = orgCrossRelId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getCrossTran() {
        return crossTran;
    }

    public void setCrossTran(String crossTran) {
        this.crossTran = crossTran;
    }

    public String getRelaType() {
        return relaType;
    }

    public void setRelaType(String relaType) {
        this.relaType = relaType;
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

    @Override
    protected Serializable pkVal() {
        return this.orgCrossRelId;
    }
}