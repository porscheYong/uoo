package cn.ffcs.uoo.core.permission.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 记录权限下相关联的规则，包括横向、纵向的数据维度。

 * </p>
 *
 * @author zengxsh
 * @since 2018-11-08
 */
@TableName("TB_DATA_PRIV_RULE")
public class DataPrivRule extends Model<DataPrivRule> {

    private static final long serialVersionUID = 1L;

    /**
     * 权限规则标识,主键
     */
    @TableId("PRIV_RULE_ID")
    private Long privRuleId;
    /**
     * 权限功能关联标识,主键
     */
    @TableField("PRIV_DATA_REL_ID")
    private Long privDataRelId;
    /**
     * 业务对象标识
     */
    @TableField("BUSI_OBJ_ID")
    private Long busiObjId;
    /**
     * 业务对象属性标识
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
     * 规则操作类型，操作（所有）、查看（列）、取值（行）。
     */
    @TableField("OPER_TYPE")
    private String operType;
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


    public Long getPrivRuleId() {
        return privRuleId;
    }

    public void setPrivRuleId(Long privRuleId) {
        this.privRuleId = privRuleId;
    }

    public Long getPrivDataRelId() {
        return privDataRelId;
    }

    public void setPrivDataRelId(Long privDataRelId) {
        this.privDataRelId = privDataRelId;
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

    public String getOperType() {
        return operType;
    }

    public void setOperType(String operType) {
        this.operType = operType;
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
        return this.privRuleId;
    }

    @Override
    public String toString() {
        return "DataPrivRule{" +
        ", privRuleId=" + privRuleId +
        ", privDataRelId=" + privDataRelId +
        ", busiObjId=" + busiObjId +
        ", busiObjAttrId=" + busiObjAttrId +
        ", ruleOperator=" + ruleOperator +
        ", attrValue=" + attrValue +
        ", operType=" + operType +
        ", statusCd=" + statusCd +
        ", statusDate=" + statusDate +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        "}";
    }
}
