package cn.ffcs.uoo.core.resource.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 接入系统组织树引用配置
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-11-16
 */
@TableName("TB_SYSTEM_ORG_TREE")
public class TbSystemOrgTree extends Model<TbSystemOrgTree> {

    private static final long serialVersionUID = 1L;

    /**
     * 接入系统组织树引用标识
     */
    @TableId("SYSTEM_ORG_TREE_ID")
    private Long systemOrgTreeId;
    /**
     * 行业标识
     */
    @TableField("BUSINESS_SYSTEM_ID")
    private Long businessSystemId;
    /**
     * 组织树标识
     */
    @TableField("ORG_TREE_ID")
    private Long orgTreeId;
    /**
     * 是否包含专业树上人员
     */
    @TableField("INCLUDE_PSN")
    private Integer includePsn;
    /**
     * 是否包含专业树上人员
     */
    @TableField("INCLUDE_SLAVE_ACCT")
    private Integer includeSlaveAcct;
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


    public Long getSystemOrgTreeId() {
        return systemOrgTreeId;
    }

    public void setSystemOrgTreeId(Long systemOrgTreeId) {
        this.systemOrgTreeId = systemOrgTreeId;
    }

    public Long getBusinessSystemId() {
        return businessSystemId;
    }

    public void setBusinessSystemId(Long businessSystemId) {
        this.businessSystemId = businessSystemId;
    }

    public Long getOrgTreeId() {
        return orgTreeId;
    }

    public void setOrgTreeId(Long orgTreeId) {
        this.orgTreeId = orgTreeId;
    }

    public Integer getIncludePsn() {
        return includePsn;
    }

    public void setIncludePsn(Integer includePsn) {
        this.includePsn = includePsn;
    }

    public Integer getIncludeSlaveAcct() {
        return includeSlaveAcct;
    }

    public void setIncludeSlaveAcct(Integer includeSlaveAcct) {
        this.includeSlaveAcct = includeSlaveAcct;
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
        return this.systemOrgTreeId;
    }

    @Override
    public String toString() {
        return "TbSystemOrgTree{" +
        ", systemOrgTreeId=" + systemOrgTreeId +
        ", businessSystemId=" + businessSystemId +
        ", orgTreeId=" + orgTreeId +
        ", includePsn=" + includePsn +
        ", includeSlaveAcct=" + includeSlaveAcct +
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
