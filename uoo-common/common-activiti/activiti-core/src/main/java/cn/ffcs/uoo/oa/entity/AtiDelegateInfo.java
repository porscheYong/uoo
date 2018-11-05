package cn.ffcs.uoo.oa.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;

/**
 * <p>
 *  任务委托信息实体类
 * </p>
 *
 * @author liuxiaodong
 * @since 2018-09-27
 */
@TableName("ati_delegate_info")
public class AtiDelegateInfo extends Model<AtiDelegateInfo> {

    private static final long serialVersionUID = 1L;

    @TableId("ati_delegate_info_id")
    private Long atiDelegateInfoId;

    @TableField("assignee")
    private String assignee;

    @TableField("attorney")
    private String attorney;

    @TableField("ati_category_id")
    private Long atiCategoryId;

    @TableField("proc_def_id")
    private String procDefId;

    @TableField("start_time")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    @TableField("end_time")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    @TableField("delegate_reason")
    private String delegateReason;

    @TableField("status_cd")
    private String statusCd;


    public Long getAtiDelegateInfoId() {
        return atiDelegateInfoId;
    }

    public void setAtiDelegateInfoId(Long atiDelegateInfoId) {
        this.atiDelegateInfoId = atiDelegateInfoId;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getAttorney() {
        return attorney;
    }

    public void setAttorney(String attorney) {
        this.attorney = attorney;
    }

    public Long getAtiCategoryId() {
        return atiCategoryId;
    }

    public void setAtiCategoryId(Long atiCategoryId) {
        this.atiCategoryId = atiCategoryId;
    }

    public String getProcDefId() {
        return procDefId;
    }

    public void setProcDefId(String procDefId) {
        this.procDefId = procDefId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getDelegateReason() {
        return delegateReason;
    }

    public void setDelegateReason(String delegateReason) {
        this.delegateReason = delegateReason;
    }

    public String getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(String statusCd) {
        this.statusCd = statusCd;
    }

    @Override
    protected Serializable pkVal() {
        return this.atiDelegateInfoId;
    }

    @Override
    public String toString() {
        return "AtiDelegateInfo{" +
        ", atiDelegateInfoId=" + atiDelegateInfoId +
        ", assignee=" + assignee +
        ", attorney=" + attorney +
        ", atiCategoryId=" + atiCategoryId +
        ", procDefId=" + procDefId +
        ", startTime=" + startTime +
        ", endTime=" + endTime +
        ", delegateReason=" + delegateReason +
        ", statusCd=" + statusCd +
        "}";
    }
}
