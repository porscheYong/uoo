package cn.ffcs.uoo.core.resource.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
* 设计目的：为了方便授权使用
 
 * </p>
 *
 * @author zengxsh
 * @since 2019-01-15
 */
@TableName("TB_BUSINESS_FUNC")
public class BusinessFunc extends Model<BusinessFunc> {

    private static final long serialVersionUID = 1L;

    /**
     * 功能标识，主键
     */
    @TableId("BIZ_FUNC_ID")
    private Integer bizFuncId;

    /**
     * 功能名称
     */
    @TableField("BIZ_FUNC_NAME")
    private String bizFuncName;

    /**
     * 功能编码
     */
    @TableField("BIZ_FUNC_CODE")
    private String bizFuncCode;

    /**
     * 功能类型。系统维护类型、业务维护功能
     */
    @TableField("BIZ_FUNC_TYPE")
    private String bizFuncType;

    /**
     * 功能描述
     */
    @TableField("BIZ_FUNC_DESC")
    private String bizFuncDesc;

    /**
     * 公用管理区域标识,记录区域唯一标识
     */
    @TableField("REGION_ID")
    private Long regionId;

    /**
     * 系统用户的归属系统
     */
    @TableField("SYSTEM_INFO_ID")
    private Long systemInfoId;

    /**
     * 菜单状态
     */
    @TableField("STATUS_CD")
    private String statusCd;

    /**
     * 状态时间
     */
    @TableField("STATUS_DATE")
    private Date statusDate;

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

    public Integer getBizFuncId() {
        return bizFuncId;
    }

    public void setBizFuncId(Integer bizFuncId) {
        this.bizFuncId = bizFuncId;
    }
    public String getBizFuncName() {
        return bizFuncName;
    }

    public void setBizFuncName(String bizFuncName) {
        this.bizFuncName = bizFuncName;
    }
    public String getBizFuncCode() {
        return bizFuncCode;
    }

    public void setBizFuncCode(String bizFuncCode) {
        this.bizFuncCode = bizFuncCode;
    }
    public String getBizFuncType() {
        return bizFuncType;
    }

    public void setBizFuncType(String bizFuncType) {
        this.bizFuncType = bizFuncType;
    }
    public String getBizFuncDesc() {
        return bizFuncDesc;
    }

    public void setBizFuncDesc(String bizFuncDesc) {
        this.bizFuncDesc = bizFuncDesc;
    }
    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }
    public Long getSystemInfoId() {
        return systemInfoId;
    }

    public void setSystemInfoId(Long systemInfoId) {
        this.systemInfoId = systemInfoId;
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

    @Override
    protected Serializable pkVal() {
        return this.bizFuncId;
    }

    @Override
    public String toString() {
        return "BusinessFunc{" +
        "bizFuncId=" + bizFuncId +
        ", bizFuncName=" + bizFuncName +
        ", bizFuncCode=" + bizFuncCode +
        ", bizFuncType=" + bizFuncType +
        ", bizFuncDesc=" + bizFuncDesc +
        ", regionId=" + regionId +
        ", systemInfoId=" + systemInfoId +
        ", statusCd=" + statusCd +
        ", statusDate=" + statusDate +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        "}";
    }
}
