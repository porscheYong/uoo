package cn.ffcs.uoo.core.organization.vo;

import java.util.Date;

/**
 * <p>
 * 是否为每张业务表设计历史流水记录表？？
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-12-24
 */
public class ModifyHistoryVo {

    private static final long serialVersionUID = 1L;


    private Long modifyId;
    /**
     * 表标识
     */

    private Long tabId;
    /**
     * 业务记录主键ID
     */

    private String recordId;
    /**
     * 操作类型 update/insert/delete
     */

    private String operateType;
    /**
     * 操作批次号
     */

    private String batchNumber;
    /**
     * 备注
     */

    private String note;
    /**
     * 字段
     */

    private String fieldName;
    /**
     * 字段值,存放旧值，最新值保存在业务表
     */

    private String fieldValue;

    private String statusCd;

    private Date createDate;

    private Long createUser;

    private Date updateDate;

    private Long updateUser;
    /**
     * 状态变更的时间
     */

    private Date statusDate;

    private String tabName;

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

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


}
