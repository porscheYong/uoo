package cn.ffcs.uoo.core.organization.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 描述该员工挂着到哪些专业树上
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-28
 */
@TableName("TB_ORGTREE_ORGPERSON_REL")
public class OrgtreeOrgpersonRel extends Model<OrgtreeOrgpersonRel> {

    private static final long serialVersionUID = 1L;

    /**
     * 组织上下级关系标识
     */
    @TableId(value = "ORGTREE_ORGPERSON_ID")
    private Long orgtreeOrgpersonId;
    /**
     * 组织树标识
     */
    @TableField("ORG_TREE_ID")
    private Long orgTreeId;
    /**
     * 组织人员关系标识
     */
    @TableField("ORG_PERSON_ID")
    private Long orgPersonId;
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


    public Long getOrgtreeOrgpersonId() {
        return orgtreeOrgpersonId;
    }

    public void setOrgtreeOrgpersonId(Long orgtreeOrgpersonId) {
        this.orgtreeOrgpersonId = orgtreeOrgpersonId;
    }

    public Long getOrgTreeId() {
        return orgTreeId;
    }

    public void setOrgTreeId(Long orgTreeId) {
        this.orgTreeId = orgTreeId;
    }

    public Long getOrgPersonId() {
        return orgPersonId;
    }

    public void setOrgPersonId(Long orgPersonId) {
        this.orgPersonId = orgPersonId;
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
        return this.orgtreeOrgpersonId;
    }

    @Override
    public String toString() {
        return "OrgtreeOrgpersonRel{" +
        ", orgtreeOrgpersonId=" + orgtreeOrgpersonId +
        ", orgTreeId=" + orgTreeId +
        ", orgPersonId=" + orgPersonId +
        ", statusCd=" + statusCd +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        ", statusDate=" + statusDate +
        "}";
    }
}
