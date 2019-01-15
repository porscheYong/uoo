package cn.ffcs.interfaces.cpc.pojo;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.KeySequence;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author lxd
 * @since 2019-01-10
 */
@TableName("TB_ACCOUNT_ORG_REL")
@KeySequence(value = "SEQ_ACCT_HOST_ID", clazz = Long.class)
public class AccountOrgRel extends Model<AccountOrgRel> {

    private static final long serialVersionUID = 1L;

    /**
     * 主账号组织关系标识
     */
    @TableId(value ="ACCT_ORG_REL_ID",type = IdType.INPUT)
    private Long acctOrgRelId;
    /**
     * 组织标识
     */
    @TableField("ORG_ID")
    private Long orgId;
    /**
     * 主账号标识
     */
    @TableField("ACCT_ID")
    private Long acctId;
    /**
     * 排序号
     */
    @TableField("SORT")
    private Long sort;
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
    /**
     * 组织树标识
     */
    @TableField("ORG_TREE_ID")
    private Long orgTreeId;
    /**
     * 组织人员关系类型
     */
    @TableField("REL_TYPE")
    private String relType;

    public AccountOrgRel() {
    }

    public AccountOrgRel(Long orgId, Long acctId, String statusCd, Date createDate, Long orgTreeId, String relType) {
        this.orgId = orgId;
        this.acctId = acctId;
        this.statusCd = statusCd;
        this.createDate = createDate;
        this.orgTreeId = orgTreeId;
        this.relType = relType;
    }

    public Long getAcctOrgRelId() {
        return acctOrgRelId;
    }

    public void setAcctOrgRelId(Long acctOrgRelId) {
        this.acctOrgRelId = acctOrgRelId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getAcctId() {
        return acctId;
    }

    public void setAcctId(Long acctId) {
        this.acctId = acctId;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
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

    public Long getOrgTreeId() {
        return orgTreeId;
    }

    public void setOrgTreeId(Long orgTreeId) {
        this.orgTreeId = orgTreeId;
    }

    public String getRelType() {
        return relType;
    }

    public void setRelType(String relType) {
        this.relType = relType;
    }

    @Override
    protected Serializable pkVal() {
        return null;
    }

    @Override
    public String toString() {
        return "AccountOrgRel{" +
        ", acctOrgRelId=" + acctOrgRelId +
        ", orgId=" + orgId +
        ", acctId=" + acctId +
        ", sort=" + sort +
        ", statusCd=" + statusCd +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        ", statusDate=" + statusDate +
        ", orgTreeId=" + orgTreeId +
        ", relType=" + relType +
        "}";
    }
}
