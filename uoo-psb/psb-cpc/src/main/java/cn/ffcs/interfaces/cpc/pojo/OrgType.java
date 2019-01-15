package cn.ffcs.interfaces.cpc.pojo;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.*;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author lxd
 * @since 2019-01-14
 */
    @TableName("TB_ORG_TYPE")
@KeySequence(value = "SEQ_TB_ORG_TYPE_ID", clazz = Long.class)
public class OrgType extends Model<OrgType> {

    private static final long serialVersionUID = 1L;

    /**
     * 组织类别标识
     */
    @TableId(value = "ORG_TYPE_ID", type = IdType.INPUT)
    private Long orgTypeId;
    /**
     * 组织类别编码
     */
    @TableField("ORG_TYPE_CODE")
    private String orgTypeCode;
    /**
     * 组织类别名称
     */
    @TableField("ORG_TYPE_NAME")
    private String orgTypeName;
    /**
     * 组织类别描述
     */
    @TableField("ORG_TYPE_DESC")
    private String orgTypeDesc;
    /**
     * 上级组织类别标识
     */
    @TableField("PARENT_ORG_TYPE_ID")
    private Long parentOrgTypeId;
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


    public Long getOrgTypeId() {
        return orgTypeId;
    }

    public void setOrgTypeId(Long orgTypeId) {
        this.orgTypeId = orgTypeId;
    }

    public String getOrgTypeCode() {
        return orgTypeCode;
    }

    public void setOrgTypeCode(String orgTypeCode) {
        this.orgTypeCode = orgTypeCode;
    }

    public String getOrgTypeName() {
        return orgTypeName;
    }

    public void setOrgTypeName(String orgTypeName) {
        this.orgTypeName = orgTypeName;
    }

    public String getOrgTypeDesc() {
        return orgTypeDesc;
    }

    public void setOrgTypeDesc(String orgTypeDesc) {
        this.orgTypeDesc = orgTypeDesc;
    }

    public Long getParentOrgTypeId() {
        return parentOrgTypeId;
    }

    public void setParentOrgTypeId(Long parentOrgTypeId) {
        this.parentOrgTypeId = parentOrgTypeId;
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
        return this.orgTypeId;
    }

    @Override
    public String toString() {
        return "OrgType{" +
        ", orgTypeId=" + orgTypeId +
        ", orgTypeCode=" + orgTypeCode +
        ", orgTypeName=" + orgTypeName +
        ", orgTypeDesc=" + orgTypeDesc +
        ", parentOrgTypeId=" + parentOrgTypeId +
        ", statusCd=" + statusCd +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        ", statusDate=" + statusDate +
        "}";
    }
}
