package cn.ffcs.uoo.system.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
* 记录权限下相关联的规则，包括横向、纵向的数据维度。
只有需要权限控制的表才进行登记
 
 * </p>
 *
 * @author zengxsh
 * @since 2019-01-28
 */
@TableName("SYS_DATA_RULE")
public class SysDataRule extends Model<SysDataRule> {

    private static final long serialVersionUID = 1L;

    /**
     * 权限规则标识,主键
     */
    @TableId("DATA_RULE_ID")
    private Long dataRuleId;

    /**
     * 表的名称
     */
    @TableField("TAB_NAME")
    private String tabName;

    /**
     * 记录字段名称
     */
    @TableField("COL_NAME")
    private String colName;

    /**
     * 规则操作符,包含、等于、大于、大于等于、小于、小于等于
     */
    @TableField("RULE_OPERATOR")
    private String ruleOperator;

    /**
     * 字段值,同一权限的同一业务对象属性标识的多个取值,可用逗号间隔
     */
    @TableField("COL_VALUE")
    private String colValue;

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

    /**
     * 权限规则组标识
     */
    @TableField("DATA_RULE_GROUP_ID")
    private Long dataRuleGroupId;

    /**
     * 条件操作符
     */
    @TableField("AND_OR")
    private String andOr;
    @TableField("SORT")
    private String sort;
    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public Long getDataRuleId() {
        return dataRuleId;
    }

    public void setDataRuleId(Long dataRuleId) {
        this.dataRuleId = dataRuleId;
    }
    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }
    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }
    public String getRuleOperator() {
        return ruleOperator;
    }

    public void setRuleOperator(String ruleOperator) {
        this.ruleOperator = ruleOperator;
    }
    public String getColValue() {
        return colValue;
    }

    public void setColValue(String colValue) {
        this.colValue = colValue;
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
    public Long getDataRuleGroupId() {
        return dataRuleGroupId;
    }

    public void setDataRuleGroupId(Long dataRuleGroupId) {
        this.dataRuleGroupId = dataRuleGroupId;
    }
    public String getAndOr() {
        return andOr;
    }

    public void setAndOr(String andOr) {
        this.andOr = andOr;
    }

    @Override
    protected Serializable pkVal() {
        return this.dataRuleId;
    }

    @Override
    public String toString() {
        return "SysDataRule{" +
        "dataRuleId=" + dataRuleId +
        ", tabName=" + tabName +
        ", colName=" + colName +
        ", ruleOperator=" + ruleOperator +
        ", colValue=" + colValue +
        ", statusCd=" + statusCd +
        ", statusDate=" + statusDate +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        ", dataRuleGroupId=" + dataRuleGroupId +
        ", andOr=" + andOr +
        "}";
    }
}
