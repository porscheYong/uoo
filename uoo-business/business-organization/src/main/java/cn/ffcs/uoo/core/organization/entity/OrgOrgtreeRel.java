package cn.ffcs.uoo.core.organization.entity;

import java.util.Date;
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
 * @author ffcs-gzb
 * @since 2018-10-21
 */
@TableName("TB_ORG_ORGTREE_REL")
public class OrgOrgtreeRel extends Model<OrgOrgtreeRel> {

    private static final long serialVersionUID = 1L;

    /**
     * 组织标识
     */
    @TableId("ORG_ORGTREE_ID")
    private Long orgOrgtreeId;
    /**
     * 组织标识
     */
    @TableField("ORG_ID")
    private Long orgId;
    /**
     * 组织树标识
     */
    @TableField("ORG_TREE_ID")
    private Long orgTreeId;
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


    @TableField("ORG_BIZ_NAME")
    private String orgBizName;


    @TableField("ORG_BIZ_FULL_NAME")
    private String orgBizFullName;

    /**
     * 排序
     */
    @TableField("SORT")
    private Integer sort;


    @TableField("ORG_BIZ_FULL_ID")
    private String orgBizFullId;


    @TableField(exist=false)
    private String lv;


    public String getOrgBizFullId() {
        return orgBizFullId;
    }

    public void setOrgBizFullId(String orgBizFullId) {
        this.orgBizFullId = orgBizFullId;
    }

    public String getLv() {
        return lv;
    }

    public void setLv(String lv) {
        this.lv = lv;
    }

    public String getOrgBizFullName() {
        return orgBizFullName;
    }

    public void setOrgBizFullName(String orgBizFullName) {
        this.orgBizFullName = orgBizFullName;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getOrgBizName() {
        return orgBizName;
    }

    public void setOrgBizName(String orgBizName) {
        this.orgBizName = orgBizName;
    }

    public Long getOrgOrgtreeId() {
        return orgOrgtreeId;
    }

    public void setOrgOrgtreeId(Long orgOrgtreeId) {
        this.orgOrgtreeId = orgOrgtreeId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getOrgTreeId() {
        return orgTreeId;
    }

    public void setOrgTreeId(Long orgTreeId) {
        this.orgTreeId = orgTreeId;
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
        return null;
    }

    @Override
    public String toString() {
        return "OrgOrgtreeRel{" +
        ", orgOrgtreeId=" + orgOrgtreeId +
        ", orgId=" + orgId +
        ", orgTreeId=" + orgTreeId +
        ", orgBizName=" + orgBizName +
        ", statusCd=" + statusCd +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        ", statusDate=" + statusDate +
        ",orgBizFullId="+orgBizFullId+
        "}";
    }
}
