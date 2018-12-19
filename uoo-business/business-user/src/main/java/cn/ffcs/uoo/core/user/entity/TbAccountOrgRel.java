package cn.ffcs.uoo.core.user.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author wudj
 * @since 2018-11-08
 */
@Data
@TableName("TB_ACCOUNT_ORG_REL")
public class TbAccountOrgRel extends Model<TbAccountOrgRel> {

    private static final long serialVersionUID = 1L;

    /**
     * 主账号组织关系标识
     */
    @TableId("ACCT_ORG_REL_ID")
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
    @JsonIgnore
    @TableField(value = "STATUS_CD", fill = FieldFill.INSERT)
    private String statusCd;
    /**
     * 创建时间
     */
    @JsonIgnore
    @TableField(value = "CREATE_DATE", fill = FieldFill.INSERT)
    private Date createDate;
    /**
     * 创建人
     */
    @JsonIgnore
    @TableField(value = "CREATE_USER", fill = FieldFill.INSERT)
    private Long createUser;
    /**
     * 修改时间
     */
    @JsonIgnore
    @TableField(value = "UPDATE_DATE", fill = FieldFill.INSERT_UPDATE)
    private Date updateDate;
    /**
     * 修改人
     */
    @JsonIgnore
    @TableField(value = "UPDATE_USER", fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;
    /**
     * 状态变更的时间
     */
    @JsonIgnore
    @TableField(value = "STATUS_DATE", fill = FieldFill.INSERT)
    private Date statusDate;

    @Override
    protected Serializable pkVal() {
        return null;
    }

    @Override
    public String toString() {
        return "TbAccountOrgRel{" +
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
                "}";
    }
}
