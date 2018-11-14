package cn.ffcs.uoo.system.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统操作日志
 * Created by liuxiaodong on 2018/11/12.
 */
@TableName("SYS_OPERATION_LOG")
public class SysOperationLog extends Model<SysOperationLog> {

    /** 操作日志标识  */
    @TableId(value = "OPERATION_LOG_ID")
    private Long operationLogId;

    /** 日志类型    */
    @TableField("LOG_TYPE")
    private Long logType;

    /** 日志名称    */
    @TableField("LOG_NAME")
    private Long logName;

    /** 用户  */
    @TableField("USER_ID")
    private Long userId;

    /** 类名称 */
    @TableField("CLASS_NAME")
    private Long className;

    /** 方法名 */
    @TableField("METHOD_NAME")
    private Long methodName;

    /** 是否成功    */
    @TableField("SUCCEED")
    private Long succeed;

    /** 备注  */
    @TableField("NOTES")
    private String notes;

    /** 状态  */
    @TableField("STATUS_CD")
    private String statusCd;

    /** 创建时间  */
    @TableField("CREATE_DATE")
    private Date createDate;

    /** 创建人    */
    @TableField("CREATE_USER")
    private Long createUser;

    /** 修改时间 */
    @TableField("UPDATE_DATE")
    private Date updateDate;

    /** 修改人 */
    @TableField("UPDATE_USER")
    private Long updateUser;

    /** 状态时间    */
    @TableField("STATUS_DATE")
    private Date statusDate;

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

    public Long getLogName() {
        return logName;
    }

    public void setLogName(Long logName) {
        this.logName = logName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getClassName() {
        return className;
    }

    public void setClassName(Long className) {
        this.className = className;
    }

    public Long getMethodName() {
        return methodName;
    }

    public void setMethodName(Long methodName) {
        this.methodName = methodName;
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
        return this.operationLogId;
    }
}
