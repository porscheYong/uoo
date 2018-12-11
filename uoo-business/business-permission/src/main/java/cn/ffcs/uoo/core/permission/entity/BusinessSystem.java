package cn.ffcs.uoo.core.permission.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * <p>
 * 接入系统表
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-30
 */
@TableName("TB_BUSINESS_SYSTEM")
public class BusinessSystem extends Model<BusinessSystem> {

    private static final long serialVersionUID = 1L;

    /**
     * 行业标识
     */
    @TableId(value = "BUSINESS_SYSTEM_ID", type = IdType.AUTO)
    private Long businessSystemId;
    /**
     * 行业名称
     */
    @TableField("SYSTEM_CODE")
    private String systemCode;
    /**
     * 行业信息
     */
    @TableField("SYSTEM_NAME")
    private String systemName;
    @TableField("SYSTEM_DESC")
    private String systemDesc;
    @TableField("SYSTEM_URL")
    private String systemUrl;
    @TableField("INTF_TYPE")
    private Integer intfType;
    @TableField("UUID")
    private String uuid;
    @TableField("EFF_DATE")
    private Date effDate;
    @TableField("EXP_DATE")
    private Date expDate;
    @TableField("STATUS_CD")
    private String statusCd;
    @TableField("STATUS_DATE")
    private Date statusDate;
    @TableField("CREATE_USER")
    private Long createUser;
    @TableField("CREATE_DATE")
    private Date createDate;
    @TableField("UPDATE_USER")
    private Long updateUser;
    @TableField("UPDATE_DATE")
    private Date updateDate;


    public Long getBusinessSystemId() {
        return businessSystemId;
    }

    public void setBusinessSystemId(Long businessSystemId) {
        this.businessSystemId = businessSystemId;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getSystemDesc() {
        return systemDesc;
    }

    public void setSystemDesc(String systemDesc) {
        this.systemDesc = systemDesc;
    }

    public String getSystemUrl() {
        return systemUrl;
    }

    public void setSystemUrl(String systemUrl) {
        this.systemUrl = systemUrl;
    }

    public Integer getIntfType() {
        return intfType;
    }

    public void setIntfType(Integer intfType) {
        this.intfType = intfType;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Date getEffDate() {
        return effDate;
    }

    public void setEffDate(Date effDate) {
        this.effDate = effDate;
    }

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    public String getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(String statusCd) {
        this.statusCd = statusCd;
    }

    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    protected Serializable pkVal() {
        return this.businessSystemId;
    }

    @Override
    public String toString() {
        return "BusinessSystem{" +
        ", businessSystemId=" + businessSystemId +
        ", systemCode=" + systemCode +
        ", systemName=" + systemName +
        ", systemDesc=" + systemDesc +
        ", systemUrl=" + systemUrl +
        ", intfType=" + intfType +
        ", uuid=" + uuid +
        ", effDate=" + effDate +
        ", expDate=" + expDate +
        ", statusCd=" + statusCd +
        ", statusDate=" + statusDate +
        ", createUser=" + createUser +
        ", createDate=" + createDate +
        ", updateUser=" + updateUser +
        ", updateDate=" + updateDate +
        "}";
    }
}
