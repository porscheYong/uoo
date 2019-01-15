package cn.ffcs.interfaces.cpc.pojo;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

@TableName("Tb_Personnel")
public class TbPersonnel extends Model<TbPersonnel> {
    @TableId(value = "personnel_Id",type = IdType.INPUT)
	private Long personnelId;

    @TableField("psn_Name")
    private String psnName;

    @TableField("psn_Code")
    private String psnCode;

    @TableField("nc_Code")
    private String ncCode;

    @TableField("psn_Nbr")
    private String psnNbr;

    @TableField("nationality")
    private String nationality;

    @TableField("gender")
    private String gender;

    @TableField("nation")
    private String nation;

    @TableField("marriage")
    private String marriage;

    @TableField("plitical_Status")
    private String pliticalStatus;

    @TableField("image")
    private String image;

    @TableField("reason")
    private String reason;

    @TableField("to_Work_Time")
    private Date toWorkTime;

    @TableField("serving_Age")
    private Short servingAge;

    @TableField("length_Service")
    private Short lengthService;

    @TableField("status_Cd")
    private String statusCd;

    @TableField("create_Date")
    private Date createDate;

    @TableField("create_User")
    private Long createUser;

    @TableField("update_Date")
    private Date updateDate;

    @TableField("update_User")
    private Long updateUser;

    @TableField("status_Date")
    private Date statusDate;

    @TableField("uuid")
    private String uuid;

    @TableField("notes")
    private String notes;

    public TbPersonnel() {
    }

    public TbPersonnel(String psnName, String psnCode, String psnNbr, String notes) {
        this.psnName = psnName;
        this.psnCode = psnCode;
        this.psnNbr = psnNbr;
        this.notes = notes;
    }

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