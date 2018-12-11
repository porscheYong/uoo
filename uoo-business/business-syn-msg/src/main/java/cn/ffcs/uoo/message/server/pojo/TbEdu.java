package cn.ffcs.uoo.message.server.pojo;

import com.baomidou.mybatisplus.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

public class TbEdu extends Model<TbEdu> {
    private Long eduId;

    private Long personnelId;

    private String school;

    private String schoolType;

    private String major;

    private String majortype;

    private String education;

    private Short edusystem;

    private String degree;

    private String firstEducation;

    private String lastEducation;

    private String lastDegree;

    private Date begindate;

    private Date enddate;

    private String certifcode;

    private String isFullTimeHighEdu;

    private String isInServiceHighEdu;

    private String statusCd;

    private Date createDate;

    private Long createUser;

    private Date updateDate;

    private Long updateUser;

    private Date statusDate;

    public Long getEduId() {
        return eduId;
    }

    public void setEduId(Long eduId) {
        this.eduId = eduId;
    }

    public Long getPersonnelId() {
        return personnelId;
    }

    public void setPersonnelId(Long personnelId) {
        this.personnelId = personnelId;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getSchoolType() {
        return schoolType;
    }

    public void setSchoolType(String schoolType) {
        this.schoolType = schoolType;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getMajortype() {
        return majortype;
    }

    public void setMajortype(String majortype) {
        this.majortype = majortype;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public Short getEdusystem() {
        return edusystem;
    }

    public void setEdusystem(Short edusystem) {
        this.edusystem = edusystem;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getFirstEducation() {
        return firstEducation;
    }

    public void setFirstEducation(String firstEducation) {
        this.firstEducation = firstEducation;
    }

    public String getLastEducation() {
        return lastEducation;
    }

    public void setLastEducation(String lastEducation) {
        this.lastEducation = lastEducation;
    }

    public String getLastDegree() {
        return lastDegree;
    }

    public void setLastDegree(String lastDegree) {
        this.lastDegree = lastDegree;
    }

    public Date getBegindate() {
        return begindate;
    }

    public void setBegindate(Date begindate) {
        this.begindate = begindate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public String getCertifcode() {
        return certifcode;
    }

    public void setCertifcode(String certifcode) {
        this.certifcode = certifcode;
    }

    public String getIsFullTimeHighEdu() {
        return isFullTimeHighEdu;
    }

    public void setIsFullTimeHighEdu(String isFullTimeHighEdu) {
        this.isFullTimeHighEdu = isFullTimeHighEdu;
    }

    public String getIsInServiceHighEdu() {
        return isInServiceHighEdu;
    }

    public void setIsInServiceHighEdu(String isInServiceHighEdu) {
        this.isInServiceHighEdu = isInServiceHighEdu;
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
        return this.eduId;
    }
}