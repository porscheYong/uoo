package cn.ffcs.uoo.message.server.pojo;

import com.baomidou.mybatisplus.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

public class TbFamily extends Model<TbFamily> {
    private Long familyId;

    private Long personnelId;

    private String memRelation;

    private String memName;

    private String relaEmail;

    private String relaPhone;

    private String relaAddr;

    private String statusCd;

    private Date createDate;

    private Long createUser;

    private Date updateDate;

    private Long updateUser;

    private Date statusDate;

    public Long getFamilyId() {
        return familyId;
    }

    public void setFamilyId(Long familyId) {
        this.familyId = familyId;
    }

    public Long getPersonnelId() {
        return personnelId;
    }

    public void setPersonnelId(Long personnelId) {
        this.personnelId = personnelId;
    }

    public String getMemRelation() {
        return memRelation;
    }

    public void setMemRelation(String memRelation) {
        this.memRelation = memRelation;
    }

    public String getMemName() {
        return memName;
    }

    public void setMemName(String memName) {
        this.memName = memName;
    }

    public String getRelaEmail() {
        return relaEmail;
    }

    public void setRelaEmail(String relaEmail) {
        this.relaEmail = relaEmail;
    }

    public String getRelaPhone() {
        return relaPhone;
    }

    public void setRelaPhone(String relaPhone) {
        this.relaPhone = relaPhone;
    }

    public String getRelaAddr() {
        return relaAddr;
    }

    public void setRelaAddr(String relaAddr) {
        this.relaAddr = relaAddr;
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
        return this.familyId;
    }
}