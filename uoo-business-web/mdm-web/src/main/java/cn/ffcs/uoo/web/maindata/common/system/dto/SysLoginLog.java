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
 * @since 2018-12-20
 */
@TableName("SYS_LOGIN_LOG")
public class SysLoginLog extends Model<SysLoginLog> {

    private static final long serialVersionUID = 1L;

    /**
     * 登录日志标识
     */
    @TableId("LOGIN_ID")
    private Long loginId;
    /**
     * 日志名称
     */
    @TableField("LOG_NAME")
    private String logName;
    /**
     * 用户登录账号
     */
    @TableField("ACCOUT")
    private String accout;
    /**
     * 是否成功
     */
    @TableField("SUCCEED")
    private Long succeed;
    /**
     * IP
     */
    @TableField("IP")
    private String ip;
    /**
     * 具体消息
     */
    @TableField("MESSAGE")
    private String message;
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
    /**
     * 创建人
     */
    @TableField("CREATE_USER")
    private Long createUser;
    /**
     * 修改时间
     */
    @TableField("UPDATE_DATE")
    private Date updateDate;
    /**
     * 修改人
     */
    @TableField("UPDATE_USER")
    private Long updateUser;
    /**
     * 状态变更的时间
     */
    @TableField("STATUS_DATE")
    private Date statusDate;


    public Long getLoginId() {
        return loginId;
    }

    public void setLoginId(Long loginId) {
        this.loginId = loginId;
    }

    public String getLogName() {
        return logName;
    }

    public void setLogName(String logName) {
        this.logName = logName;
    }

    public String getAccout() {
        return accout;
    }

    public void setAccout(String accout) {
        this.accout = accout;
    }

    public Long getSucceed() {
        return succeed;
    }

    public void setSucceed(Long succeed) {
        this.succeed = succeed;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
        return this.loginId;
    }

    @Override
    public String toString() {
        return "SysLoginLog{" +
        ", loginId=" + loginId +
        ", logName=" + logName +
        ", accout=" + accout +
        ", succeed=" + succeed +
        ", ip=" + ip +
        ", message=" + message +
        ", notes=" + notes +
        ", statusCd=" + statusCd +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        ", statusDate=" + statusDate +
        "}";
    }
}
