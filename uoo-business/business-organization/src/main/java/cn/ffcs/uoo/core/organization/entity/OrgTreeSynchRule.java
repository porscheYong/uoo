package cn.ffcs.uoo.core.organization.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
* 目标组织树A‘上部分组织是另一颗业务树A的组织镜像，那么A’上镜像部分组织基本信息和上下级关系、是否添加下级组织理论上由A业务树进行维护。
该表规则仅为此场景下使用 2019-3-4 约定，详见需求描述
 
 * </p>
 *
 * @author zengxsh
 * @since 2019-03-04
 */
@TableName("TB_ORG_TREE_SYNCH_RULE")
public class OrgTreeSynchRule extends Model<OrgTreeSynchRule> {

    private static final long serialVersionUID = 1L;

    /**
     * 组织树同步规则标识
     */
    @TableId("ORG_TREE_SYNCH_RULE_ID")
    private Long orgTreeSynchRuleId;

    /**
     * 目标组织树标识 bss
     */
    @TableField("TO_ORG_TREE_ID")
    private Long toOrgTreeId;

    /**
     * 来源组织树标识   rl yx
     */
    @TableField("FROM_ORG_TREE_ID")
    private Long fromOrgTreeId;

    /**
     * 操作类型 update/insert/delete
     */
    @TableField("OPERATE_TYPE")
    private String operateType;

    /**
     * 同步模式oneway/twoway
     */
    @TableField("SYNCH_MODEL")
    private String synchModel;

    /**
     * 生效时间
     */
    @TableField("ENABLE_DATE")
    private Date enableDate;

    /**
     * 失效时间
     */
    @TableField("DISABLE_DATE")
    private Date disableDate;

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

    public Long getOrgTreeSynchRuleId() {
        return orgTreeSynchRuleId;
    }

    public void setOrgTreeSynchRuleId(Long orgTreeSynchRuleId) {
        this.orgTreeSynchRuleId = orgTreeSynchRuleId;
    }
    public Long getToOrgTreeId() {
        return toOrgTreeId;
    }

    public void setToOrgTreeId(Long toOrgTreeId) {
        this.toOrgTreeId = toOrgTreeId;
    }
    public Long getFromOrgTreeId() {
        return fromOrgTreeId;
    }

    public void setFromOrgTreeId(Long fromOrgTreeId) {
        this.fromOrgTreeId = fromOrgTreeId;
    }
    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }
    public String getSynchModel() {
        return synchModel;
    }

    public void setSynchModel(String synchModel) {
        this.synchModel = synchModel;
    }
    public Date getEnableDate() {
        return enableDate;
    }

    public void setEnableDate(Date enableDate) {
        this.enableDate = enableDate;
    }
    public Date getDisableDate() {
        return disableDate;
    }

    public void setDisableDate(Date disableDate) {
        this.disableDate = disableDate;
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
        return this.orgTreeSynchRuleId;
    }

    @Override
    public String toString() {
        return "OrgTreeSynchRule{" +
        "orgTreeSynchRuleId=" + orgTreeSynchRuleId +
        ", toOrgTreeId=" + toOrgTreeId +
        ", fromOrgTreeId=" + fromOrgTreeId +
        ", operateType=" + operateType +
        ", synchModel=" + synchModel +
        ", enableDate=" + enableDate +
        ", disableDate=" + disableDate +
        ", statusCd=" + statusCd +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        ", statusDate=" + statusDate +
        "}";
    }
}
