package cn.ffcs.uoo.web.maindata.personnel.dto;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.fasterxml.jackson.annotation.JsonFormat;
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
@TableName("TB_PSNJOB")
public class TbPsnjob extends Model<TbPsnjob> {

    private static final long serialVersionUID = 1L;

    /**
     * 任职履历标识
     */
    @TableId("PSNJOB_ID")
    private Long psnjobId;
    /**
     * 人员标识
     */
    @TableField("PERSONNEL_ID")
    private Long personnelId;
    /**
     * 组织标识 单位类
     */
    @TableField("ORG_ID")
    private Long orgId;
    /**
     * 任职开始时间
     */
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @TableField("BEGIN_TIME")
    private Date beginTime;
    /**
     * 任职结束时间
     */
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @TableField("END_TIME")
    private Date endTime;
    /**
     * 状态
     */
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


    public Long getPsnjobId() {
        return psnjobId;
    }

    public void setPsnjobId(Long psnjobId) {
        this.psnjobId = psnjobId;
    }

    public Long getPersonnelId() {
        return personnelId;
    }

    public void setPersonnelId(Long personnelId) {
        this.personnelId = personnelId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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
        return this.psnjobId;
    }

    @Override
    public String toString() {
        return "TbPsnjob{" +
        ", psnjobId=" + psnjobId +
        ", personnelId=" + personnelId +
        ", orgId=" + orgId +
        ", beginTime=" + beginTime +
        ", endTime=" + endTime +
        ", statusCd=" + statusCd +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        ", statusDate=" + statusDate +
        "}";
    }
}
