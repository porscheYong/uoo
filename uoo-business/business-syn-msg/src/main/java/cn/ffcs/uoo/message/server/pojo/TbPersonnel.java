package cn.ffcs.uoo.message.server.pojo;

import com.baomidou.mybatisplus.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

public class TbPersonnel extends Model<TbPersonnel> {
	private Long personnelId;

    private String psnName;

    private String psnCode;

    private String ncCode;

    private String psnNbr;

    private String nationality;

    private String gender;

    private String nation;

    private String marriage;

    private String pliticalStatus;

    private String image;

    private String reason;

    private Date toWorkTime;

    private Short servingAge;

    private Short lengthService;

    private String statusCd;

    private Date createDate;

    private Long createUser;

    private Date updateDate;

    private Long updateUser;

    private Date statusDate;

    private String uuid;

    private String notes;

    public Long getPersonnelId() {
        return personnelId;
    }

    public void setPersonnelId(Long personnelId) {
        this.personnelId = personnelId;
    }

    public String getPsnName() {
        return psnName;
    }

    public void setPsnName(String psnName) {
        this.psnName = psnName;
    }

    public String getPsnCode() {
        return psnCode;
    }

    public void setPsnCode(String psnCode) {
        this.psnCode = psnCode;
    }

    public String getNcCode() {
        return ncCode;
    }

    public void setNcCode(String ncCode) {
        this.ncCode = ncCode;
    }

    public String getPsnNbr() {
        return psnNbr;
    }

    public void setPsnNbr(String psnNbr) {
        this.psnNbr = psnNbr;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getMarriage() {
        return marriage;
    }

    public void setMarriage(String marriage) {
        this.marriage = marriage;
    }

    public String getPliticalStatus() {
        return pliticalStatus;
    }

    public void setPliticalStatus(String pliticalStatus) {
        this.pliticalStatus = pliticalStatus;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getToWorkTime() {
        return toWorkTime;
    }

    public void setToWorkTime(Date toWorkTime) {
        this.toWorkTime = toWorkTime;
    }

    public Short getServingAge() {
        return servingAge;
    }

    public void setServingAge(Short servingAge) {
        this.servingAge = servingAge;
    }

    public Short getLengthService() {
        return lengthService;
    }

    public void setLengthService(Short lengthService) {
        this.lengthService = lengthService;
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    protected Serializable pkVal() {
        return this.personnelId;
    }
}