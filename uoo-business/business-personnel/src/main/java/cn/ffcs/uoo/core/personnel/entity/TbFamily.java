package cn.ffcs.uoo.core.personnel.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author wudj
 * @since 2018-10-11
 */
@TableName("TB_FAMILY")
public class TbFamily extends Model<TbFamily> {

    private static final long serialVersionUID = 1L;

    /**
     * 家庭信息标识
     */
    @TableId("FAMILY_ID")
    private Long familyId;
    /**
     * 人员标识
     */
    @TableField("PERSONNEL_ID")
    private Long personnelId;
    /**
     * 与本人关系
     */
    @TableField("MEM_RELATION")
    private String memRelation;
    /**
     * 家庭成员姓名
     */
    @TableField("MEM_NAME")
    private String memName;
    /**
     * 联系邮箱
     */
    @TableField("RELA_EMAIL")
    private String relaEmail;
    /**
     * 联系电话
     */
    @TableField("RELA_PHONE")
    private String relaPhone;
    /**
     * 联系地址
     */
    @TableField("RELA_ADDR")
    private String relaAddr;
    /**
     * 状态
     */
    @JsonIgnore
    @TableField(value = "STATUS_CD", fill = FieldFill.INSERT)
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
    //@JsonIgnore
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
    //@JsonIgnore
    @TableField(value = "UPDATE_USER", fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;
    /**
     * 状态变更的时间
     */
    @JsonIgnore
    @TableField(value = "STATUS_DATE", fill = FieldFill.INSERT)
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

    @Override
    public String toString() {
        return "TbFamily{" +
        ", familyId=" + familyId +
        ", personnelId=" + personnelId +
        ", memRelation=" + memRelation +
        ", memName=" + memName +
        ", relaEmail=" + relaEmail +
        ", relaPhone=" + relaPhone +
        ", relaAddr=" + relaAddr +
        ", statusCd=" + statusCd +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        ", statusDate=" + statusDate +
        "}";
    }
}
