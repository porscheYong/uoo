package cn.ffcs.uoo.system.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统登录日志
 * Created by liuxiaodong on 2018/11/12.
 */
@TableName("SYS_LOGIN_LOG")
public class SysLoginLog extends Model<SysLoginLog> {

    /** 登录日志标识   */
    @TableId(value = "LOGIN_ID")
    private Long loginId;

    /** 日志名称    */
    @TableField("LOG_NAME")
    private Long logName;

    /** 管理员 */
    @TableField("USER_ID")
    private Long userId;

    /** 是否成功    */
    @TableField("SUCCEED")
    private Long succeed;

    /** IP  */
    @TableField("IP")
    private Long ip;

    /** 具体消息    */
    @TableField("MESSAGE")
    private String message;

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

    public Long getLoginId() {
        return loginId;
    }

    public void setLoginId(Long loginId) {
        this.loginId = loginId;
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

    public Long getSucceed() {
        return succeed;
    }

    public void setSucceed(Long succeed) {
        this.succeed = succeed;
    }

    public Long getIp() {
        return ip;
    }

    public void setIp(Long ip) {
        this.ip = ip;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    protected Serializable pkVal() {
        return this.loginId;
    }
}
