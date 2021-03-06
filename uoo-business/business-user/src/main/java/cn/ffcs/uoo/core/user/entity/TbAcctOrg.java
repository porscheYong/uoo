package cn.ffcs.uoo.core.user.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 主账号与组织关系
 * </p>
 *
 * @author zhanglu
 * @since 2018-09-14
 */
@TableName("tb_acct_org")
public class TbAcctOrg extends Model<TbAcctOrg> {

    private static final long serialVersionUID = 1L;

    /**
     * 主账号与组织关系标识
     */
    @TableId("ACCT_ORG_ID")
    private long acctOrgId;
    /**
     * 主账号标识
     */
    @TableField("ACCT_ID")
    private long acctId;
    /**
     * 组织标识
     */
    @TableField("ORG_ID")
    private long orgId;
    /**
     * 关系类型 主归属、兼职、借调
     */
    @TableField("RELA_TYPE")
    private String relaType;
    /**
     * 排序号 人员所属组织的规则序列
     */
    @TableField("SORT")
    private Integer sort;
    /**
     * 重名称谓
     */
    @TableField("DOUBLE_NAME")
    private String doubleName;
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


    public long getAcctOrgId() {
        return acctOrgId;
    }

    public void setAcctOrgId(long acctOrgId) {
        this.acctOrgId = acctOrgId;
    }

    public long getAcctId() {
        return acctId;
    }

    public void setAcctId(long acctId) {
        this.acctId = acctId;
    }

    public long getOrgId() {
        return orgId;
    }

    public void setOrgId(long orgId) {
        this.orgId = orgId;
    }

    public String getRelaType() {
        return relaType;
    }

    public void setRelaType(String relaType) {
        this.relaType = relaType;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getDoubleName() {
        return doubleName;
    }

    public void setDoubleName(String doubleName) {
        this.doubleName = doubleName;
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
        return this.acctOrgId;
    }

    @Override
    public String toString() {
        return "TbAcctOrg{" +
        ", acctOrgId=" + acctOrgId +
        ", acctId=" + acctId +
        ", orgId=" + orgId +
        ", relaType=" + relaType +
        ", sort=" + sort +
        ", doubleName=" + doubleName +
        ", statusCd=" + statusCd +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        ", statusDate=" + statusDate +
        "}";
    }
}
