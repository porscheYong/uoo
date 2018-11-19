package cn.ffcs.uoo.web.maindata.personnel.dto;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author wudj
 * @since 2018-10-11
 */
@TableName("TB_EDU")
public class TbEdu extends Model<TbEdu> {

    private static final long serialVersionUID = 1L;

    /**
     * 教育经历标识
     */
    @TableId("EDU_ID")
    private Long eduId;
    /**
     * 人员标识
     */
    @TableField("PERSONNEL_ID")
    private Long personnelId;
    /**
     * 学校
     */
    @TableField("SCHOOL")
    private String school;
    /**
     * 学校类别
     */
    @TableField("SCHOOL_TYPE")
    private String schoolType;
    /**
     * 专业
     */
    @TableField("MAJOR")
    private String major;
    /**
     * 学历专业类别
     */
    @TableField("MAJORTYPE")
    private String majortype;
    /**
     * 学历
     */
    @TableField("EDUCATION")
    private String education;
    /**
     * 学制
     */
    @TableField("EDUSYSTEM")
    private Integer edusystem;
    /**
     * 学位
     */
    @TableField("DEGREE")
    private String degree;
    /**
     * 是否第一学历
     */
    @TableField("FIRST_EDUCATION")
    private String firstEducation;
    /**
     * 是否最高学历
     */
    @TableField("LAST_EDUCATION")
    private String lastEducation;
    /**
     * 是否最高学位
     */
    @TableField("LAST_DEGREE")
    private String lastDegree;
    /**
     * 入学日期
     */
    @TableField("BEGINDATE")
    private Date begindate;
    /**
     * 毕业日期
     */
    @TableField("ENDDATE")
    private Date enddate;
    /**
     * 学位证书编号
     */
    @TableField("CERTIFCODE")
    private String certifcode;
    /**
     * 是否全日制最高学历
     */
    @TableField("IS_FULL_TIME_HIGH_EDU")
    private String isFullTimeHighEdu;
    /**
     * 是否在职最高学历
     */
    @TableField("IS_IN_SERVICE_HIGH_EDU")
    private String isInServiceHighEdu;
    /**
     * 状态
     */
    @JsonIgnore
    @TableField(value = "STATUS_CD", fill = FieldFill.INSERT_UPDATE)
    private String statusCd;
    /**
     * 创建时间
     */
    @JsonIgnore
    @TableField(value = "CREATE_DATE", fill = FieldFill.INSERT)
    private Date createDate;
    /**
     * 创建人
     */
    @JsonIgnore
    @TableField(value = "CREATE_USER", fill = FieldFill.INSERT)
    private Long createUser;
    /**
     * 修改时间
     */
    @JsonIgnore
    @TableField(value = "UPDATE_DATE", fill = FieldFill.INSERT_UPDATE)
    private Date updateDate;
    /**
     * 修改人
     */
    @JsonIgnore
    @TableField(value = "UPDATE_USER", fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;
    /**
     * 状态变更的时间
     */
    @JsonIgnore
    @TableField(value = "STATUS_DATE", fill = FieldFill.INSERT_UPDATE)
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

    public Integer getEdusystem() {
        return edusystem;
    }

    public void setEdusystem(Integer edusystem) {
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

    @Override
    public String toString() {
        return "TbEdu{" +
                ", eduId=" + eduId +
                ", personnelId=" + personnelId +
                ", school=" + school +
                ", schoolType=" + schoolType +
                ", major=" + major +
                ", majortype=" + majortype +
                ", education=" + education +
                ", edusystem=" + edusystem +
                ", degree=" + degree +
                ", firstEducation=" + firstEducation +
                ", lastEducation=" + lastEducation +
                ", lastDegree=" + lastDegree +
                ", begindate=" + begindate +
                ", enddate=" + enddate +
                ", certifcode=" + certifcode +
                ", isFullTimeHighEdu=" + isFullTimeHighEdu +
                ", isInServiceHighEdu=" + isInServiceHighEdu +
                ", statusCd=" + statusCd +
                ", createDate=" + createDate +
                ", createUser=" + createUser +
                ", updateDate=" + updateDate +
                ", updateUser=" + updateUser +
                ", statusDate=" + statusDate +
                "}";
    }
}
