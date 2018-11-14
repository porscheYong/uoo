package cn.ffcs.uoo.web.maindata.personnel.dto;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author WCNGS@QQ.COM
 * @since 2018-09-06
 */
public class TbPersonnel extends Model<TbPersonnel> {

    private static final long serialVersionUID = 1L;

    /**
     * 人员标识
     */
    private BigDecimal personnelId;

    /**
     * 人员姓名
     */
    private String psnName;
    /**
     * 人员编码
     */
    private String psnCode;
    /**
     * 人力编码 集团-NC人力编码
     */
    private String ncCode;
    /**
     * 员工工号
     */
    private String psnNbr;
    /**
     * 国籍
     */
    private String nationality;
    /**
     * 性别
     */
    @TableField("GENDER")
    private String gender;
    /**
     * 民族
     */
    private String nation;
    /**
     * 婚姻状况
     */
    private String marriage;
    /**
     * 政治面貌
     */
    private String pliticalStatus;
    /**
     * 人员头像
     */
    private String image;
    /**
     * 变更原因
     */
    private String reason;
    /**
     * 首次参加工作时间
     */
    private Date toWorkTime;
    /**
     * 首次参加工作时间 单位：月
     */
    private BigDecimal servingAge;
    /**
     * 司龄 单位：月
     */
    private BigDecimal lengthService;
    /**
     * 状态
     */
    private String statusCd;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 创建人
     */
    private BigDecimal createUser;
    /**
     * 修改时间
     */
    private Date updateDate;
    /**
     * 修改人
     */
    private BigDecimal updateUser;
    /**
     * 状态变更的时间
     */
    @TableField("STATUS_DATE")
    private Date statusDate;
    /**
     * UUID
     */
    @TableField("UUID")
    private String uuid;
    /**
     * 备注
     */
    @TableField("NOTES")
    private String notes;


    public BigDecimal getPersonnelId() {
        return personnelId;
    }

    public void setPersonnelId(BigDecimal personnelId) {
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

    public BigDecimal getServingAge() {
        return servingAge;
    }

    public void setServingAge(BigDecimal servingAge) {
        this.servingAge = servingAge;
    }

    public BigDecimal getLengthService() {
        return lengthService;
    }

    public void setLengthService(BigDecimal lengthService) {
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

    public BigDecimal getCreateUser() {
        return createUser;
    }

    public void setCreateUser(BigDecimal createUser) {
        this.createUser = createUser;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public BigDecimal getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(BigDecimal updateUser) {
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

    @Override
    public String toString() {
        return "TbPersonnel{" +
        ", personnelId=" + personnelId +
        ", psnName=" + psnName +
        ", psnCode=" + psnCode +
        ", ncCode=" + ncCode +
        ", psnNbr=" + psnNbr +
        ", nationality=" + nationality +
        ", gender=" + gender +
        ", nation=" + nation +
        ", marriage=" + marriage +
        ", pliticalStatus=" + pliticalStatus +
        ", image=" + image +
        ", reason=" + reason +
        ", toWorkTime=" + toWorkTime +
        ", servingAge=" + servingAge +
        ", lengthService=" + lengthService +
        ", statusCd=" + statusCd +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        ", statusDate=" + statusDate +
        ", uuid=" + uuid +
        ", notes=" + notes +
        "}";
    }
}
