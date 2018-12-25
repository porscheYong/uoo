package cn.ffcs.uoo.core.resource.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 是否为每张业务表设计历史流水记录表？？
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-12-24
 */
@TableName("TB_MODIFY_HISTORY")
public class ModifyHistory extends Model<ModifyHistory> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "MODIFY_ID")
    private Long modifyId;
    /**
     * 表标识
     */
    @TableField("TAB_ID")
    private Long tabId;
    /**
     * 业务记录主键ID
     */
    @TableField("RECORD_ID")
    private String recordId;
    /**
     * 操作类型 update/insert/delete
     */
    @TableField("OPERATE_TYPE")
    private String operateType;
    /**
     * 操作批次号
     */
    @TableField("BATCH_NUMBER")
    private String batchNumber;
    /**
     * 备注
     */
    @TableField("NOTE")
    private String note;
    /**
     * 字段
     */
    @TableField("FIELD_NAME")
    private String fieldName;
    /**
     * 字段值,存放旧值，最新值保存在业务表
     */
    @TableField("FIELD_VALUE")
    private String fieldValue;
    @TableField("STATUS_CD")
    private String statusCd;
    @TableField("CREATE_DATE")
    private Date createDate;
    @TableField("CREATE_USER")
    private Long createUser;
    @TableField("UPDATE_DATE")
    private Date updateDate;
    @TableField("UPDATE_USER")
    private Long updateUser;
    /**
     * 状态变更的时间
     */
    @TableField("STATUS_DATE")
    private Date statusDate;


    public Long getModifyId() {
        return modifyId;
    }

    public void setModifyId(Long modifyId) {
        this.modifyId = modifyId;
    }

    public Long getTabId() {
        return tabId;
    }

    public void setTabId(Long tabId) {
        this.tabId = tabId;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
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
        return this.modifyId;
    }

    @Override
    public String toString() {
        return "ModifyHistory{" +
        ", modifyId=" + modifyId +
        ", tabId=" + tabId +
        ", recordId=" + recordId +
        ", operateType=" + operateType +
        ", batchNumber=" + batchNumber +
        ", note=" + note +
        ", fieldName=" + fieldName +
        ", fieldValue=" + fieldValue +
        ", statusCd=" + statusCd +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        ", statusDate=" + statusDate +
        "}";
    }
}
