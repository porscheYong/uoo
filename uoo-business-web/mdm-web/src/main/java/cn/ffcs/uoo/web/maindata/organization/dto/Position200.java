package cn.ffcs.uoo.web.maindata.organization.dto;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-25
 */
@TableName("TB_POSITION200")
public class Position200 extends Model<Position200> {

    private static final long serialVersionUID = 1L;

    @TableField("UUID")
    private String uuid;
    @TableId(value = "PK_USER")
    private Long pkUser;
    @TableField("USER_NAME")
    private String userName;
    @TableField("ACCOUNT")
    private String account;
    /**
     * 类似集团主数据-人力编码
     */
    private String 人员编码;
    @TableField("ENTRY_TIME")
    private Date entryTime;
    @TableField("ENTRY_TIME2")
    private Date entryTime2;
    @TableField("REASON")
    private String reason;
    @TableField("GENDER")
    private String gender;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Long getPkUser() {
        return pkUser;
    }

    public void setPkUser(Long pkUser) {
        this.pkUser = pkUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String get人员编码() {
        return 人员编码;
    }

    public void set人员编码(String 人员编码) {
        this.人员编码 = 人员编码;
    }

    public Date getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }

    public Date getEntryTime2() {
        return entryTime2;
    }

    public void setEntryTime2(Date entryTime2) {
        this.entryTime2 = entryTime2;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    protected Serializable pkVal() {
        return this.pkUser;
    }

    @Override
    public String toString() {
        return "Position200{" +
        ", uuid=" + uuid +
        ", pkUser=" + pkUser +
        ", userName=" + userName +
        ", account=" + account +
        ", 人员编码=" + 人员编码 +
        ", entryTime=" + entryTime +
        ", entryTime2=" + entryTime2 +
        ", reason=" + reason +
        ", gender=" + gender +
        "}";
    }
}
