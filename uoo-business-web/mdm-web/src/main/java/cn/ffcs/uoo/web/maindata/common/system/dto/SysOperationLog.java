package cn.ffcs.uoo.web.maindata.common.system.dto;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zengxsh
 * @since 2018-12-17
 */
@TableName("SYS_OPERATION_LOG")
public class SysOperationLog extends Model<SysOperationLog> {

    private static final long serialVersionUID = 1L;

    /**
     * 操作日志标识
     */
    @TableId("OPERATION_LOG_ID")
    private Long operationLogId;
    /**
     * 日志类型
     */
    @TableField("LOG_TYPE")
    private Long logType;
    /**
     * 日志名称
     */
    @TableField("LOG_NAME")
    private String logName;
    /**
     * 用户
     */
    @TableField("USER_CODE")
    private Long userCode;
    /**
     * 是否成功
     */
    @TableField("SUCCEED")
    private Long succeed;
    /**
     * 备注
     */
    @TableField("NOTES")
    private String notes;
    /**
     * 状态
     */
    @TableField("STATUS_CD")
    private String statusCd;
    /**
     * 创建时间
     */
    @TableField("CREATE_DATE")
    private Date createDate;
    @TableField("MENU_CODE")
    private Long menuCode;
    @TableField("FUNC_CODE")
    private Long funcCode;
    @TableField("FORM_DATA")
    private String formData;


    public Long getOperationLogId() {
        return operationLogId;
    }

    public void setOperationLogId(Long operationLogId) {
        this.operationLogId = operationLogId;
    }

    public Long getLogType() {
        return logType;
    }

    public void setLogType(Long logType) {
        this.logType = logType;
    }

    public String getLogName() {
        return logName;
    }

    public void setLogName(String logName) {
        this.logName = logName;
    }

    public Long getUserCode() {
        return userCode;
    }

    public void setUserCode(Long userCode) {
        this.userCode = userCode;
    }

    public Long getSucceed() {
        return succeed;
    }

    public void setSucceed(Long succeed) {
        this.succeed = succeed;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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

    public Long getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(Long menuCode) {
        this.menuCode = menuCode;
    }

    public Long getFuncCode() {
        return funcCode;
    }

    public void setFuncCode(Long funcCode) {
        this.funcCode = funcCode;
    }

    public String getFormData() {
        return formData;
    }

    public void setFormData(String formData) {
        this.formData = formData;
    }

    @Override
    protected Serializable pkVal() {
        return this.operationLogId;
    }

    @Override
    public String toString() {
        return "SysOperationLog{" +
        ", operationLogId=" + operationLogId +
        ", logType=" + logType +
        ", logName=" + logName +
        ", userCode=" + userCode +
        ", succeed=" + succeed +
        ", notes=" + notes +
        ", statusCd=" + statusCd +
        ", createDate=" + createDate +
        ", menuCode=" + menuCode +
        ", funcCode=" + funcCode +
        ", formData=" + formData +
        "}";
    }
}
