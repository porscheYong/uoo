package cn.ffcs.uoo.web.maindata.common.system.vo;

import java.util.Date;

public class LogDTO {
    private long logId;
    private String logName;
    private String opUser;
    private long succeed;
    private String ip;
    private String statusCd;
    private Date createDate;
    private String logEnum;
    public long getLogId() {
        return logId;
    }
    public void setLogId(long logId) {
        this.logId = logId;
    }
    public String getLogName() {
        return logName;
    }
    public void setLogName(String logName) {
        this.logName = logName;
    }
    public String getOpUser() {
        return opUser;
    }
    public void setOpUser(String opUser) {
        this.opUser = opUser;
    }
    public long getSucceed() {
        return succeed;
    }
    public void setSucceed(long succeed) {
        this.succeed = succeed;
    }
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
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
    public String getLogEnum() {
        return logEnum;
    }
    public void setLogEnum(String logEnum) {
        this.logEnum = logEnum;
    }
    
    
}
