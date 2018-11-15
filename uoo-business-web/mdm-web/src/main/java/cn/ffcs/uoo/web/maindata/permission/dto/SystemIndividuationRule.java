package cn.ffcs.uoo.web.maindata.permission.dto;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * <p>
 * 
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-30
 */
@TableName("TB_SYSTEM_INDIVIDUATION_RULE")
public class SystemIndividuationRule extends Model<SystemIndividuationRule> {

    private static final long serialVersionUID = 1L;

    /**
     * 个性化规则标识
     */
    @TableId(value = "INDIVIDUATION_RULE_ID", type = IdType.AUTO)
    private Long individuationRuleId;
    /**
     * 行业标识
     */
    @TableField("SYSTEM_ORG_TREE_ID")
    private Long systemOrgTreeId;
    /**
     * 业务对象标识（组织、人员）
     */
    @TableField("BUSI_OBJ_ID")
    private Long busiObjId;
    /**
     * 业务对象属性标识（组织：区号标识AREA_CODE_ID，农村/城镇标志CITY_TOWN，成立时间FOUNDING_TIME，组织规模ORG_SCALE，组织层级ORG_LEVEL，组织岗位级别ORG_POSITION_LEVEL）（人员：职位标识OST_ID，组织人员关系类型REF_TYPE，用工性质PROPERTY）
     */
    @TableField("BUSI_OBJ_ATTR_ID")
    private Long busiObjAttrId;
    /**
     * 规则操作符,包含、等于、大于、大于等于、小于、小于等于
     */
    @TableField("RULE_OPERATOR")
    private String ruleOperator;
    /**
     * 属性值,同一权限的同一业务对象属性标识的多个取值,可用逗号间隔
     */
    @TableField("ATTR_VALUE")
    private String attrValue;
    /**
     * 状态
     */
    @TableField("STATUS_CD")
    private String statusCd;
    /**
     * 状态变更的时间
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


    public Long getIndividuationRuleId() {
        return individuationRuleId;
    }

    public void setIndividuationRuleId(Long individuationRuleId) {
        this.individuationRuleId = individuationRuleId;
    }

    public Long getSystemOrgTreeId() {
        return systemOrgTreeId;
    }

    public void setSystemOrgTreeId(Long systemOrgTreeId) {
        this.systemOrgTreeId = systemOrgTreeId;
    }

    public Long getBusiObjId() {
        return busiObjId;
    }

    public void setBusiObjId(Long busiObjId) {
        this.busiObjId = busiObjId;
    }

    public Long getBusiObjAttrId() {
        return busiObjAttrId;
    }

    public void setBusiObjAttrId(Long busiObjAttrId) {
        this.busiObjAttrId = busiObjAttrId;
    }

    public String getRuleOperator() {
        return ruleOperator;
    }

    public void setRuleOperator(String ruleOperator) {
        this.ruleOperator = ruleOperator;
    }

    public String getAttrValue() {
        return attrValue;
    }

    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue;
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
        return this.individuationRuleId;
    }

    @Override
    public String toString() {
        return "SystemIndividuationRule{" +
        ", individuationRuleId=" + individuationRuleId +
        ", systemOrgTreeId=" + systemOrgTreeId +
        ", busiObjId=" + busiObjId +
        ", busiObjAttrId=" + busiObjAttrId +
        ", ruleOperator=" + ruleOperator +
        ", attrValue=" + attrValue +
        ", statusCd=" + statusCd +
        ", statusDate=" + statusDate +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        "}";
    }
}
