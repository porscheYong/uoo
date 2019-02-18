package cn.ffcs.uoo.system.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 
 * </p>
 *
 * @author zengxsh
 * @since 2019-01-28
 */
@TableName("SYS_DATA_RULE_GROUP")
public class SysDataRuleGroup extends Model<SysDataRuleGroup> {

    private static final long serialVersionUID = 1L;

    /**
     * 权限规则组标识,主键
     */
    @TableId("DATA_RULE_GROUP_ID")
    private Long dataRuleGroupId;

    /**
     * 组名称
     */
    @TableField("GROUP_NAME")
    private String groupName;

    /**
     * 规则操作符,and or
     */
    @TableField("AND_OR")
    private String andOr;

    /**
     * 排序
     */
    @TableField("SORT")
    private Double sort;

    /**
     * 状态
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
    @TableField("ORG_TREE_ID")
    private Long orgTreeId;

    @TableField("PARENT_RULE_GROUP_ID")
    private Long parentRuleGroupId;

    public Long getDataRuleGroupId() {
        return dataRuleGroupId;
    }

    public Long getOrgTreeId() {
        return orgTreeId;
    }

    public void setOrgTreeId(Long orgTreeId) {
        this.orgTreeId = orgTreeId;
    }

    public void setDataRuleGroupId(Long dataRuleGroupId) {
        this.dataRuleGroupId = dataRuleGroupId;
    }
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    public String getAndOr() {
        return andOr;
    }

    public void setAndOr(String andOr) {
        this.andOr = andOr;
    }
    public Double getSort() {
        return sort;
    }

    public void setSort(Double sort) {
        this.sort = sort;
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
    public Long getParentRuleGroupId() {
        return parentRuleGroupId;
    }

    public void setParentRuleGroupId(Long parentRuleGroupId) {
        this.parentRuleGroupId = parentRuleGroupId;
    }

    @Override
    protected Serializable pkVal() {
        return this.dataRuleGroupId;
    }

    @Override
    public String toString() {
        return "SysDataRuleGroup{" +
        "dataRuleGroupId=" + dataRuleGroupId +
        ", groupName=" + groupName +
        ", andOr=" + andOr +
        ", sort=" + sort +
        ", statusCd=" + statusCd +
        ", statusDate=" + statusDate +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        ", parentRuleGroupId=" + parentRuleGroupId +
        "}";
    }
}
