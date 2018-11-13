package cn.ffcs.uoo.web.maindata.organization.dto;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-21
 */
@TableName("TB_ORG_PERSON_REL")
public class OrgPersonRel extends Model<OrgPersonRel> {

    private static final long serialVersionUID = 1L;

    /**
     * 组织人员关系标识
     */
    @TableId(value = "ORG_PERSON_ID")
    private Long orgPersonId;
    /**
     * 组织标识
     */
    @TableField("ORG_ID")
    private Long orgId;
    /**
     * 人员标识
     */
    @TableField("PERSONNEL_ID")
    private Long personnelId;

    @TableField("POST_ID")
    private Long postId;
    /**
     * 组织人员关系类型 承包责任类；支撑类；职务归属类
     */
    @TableField("REF_TYPE")
    private String refType;
    /**
     * 用工性质【正式、外包、派遣、临时、实习】【代理商、供应商】
     */
    @TableField("PROPERTY")
    private String property;
    /**
     * 重名称呼
     */
    @TableField("DOUBLE_NAME")
    private String doubleName;
    /**
     * 排序号
     */
    @TableField("SORT")
    private Double sort;
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
     * 组织树根节点
     */
    @TableField(exist=false)
    private String orgRootId;

    public String getOrgRootId() {
        return orgRootId;
    }

    public void setOrgRootId(String orgRootId) {
        this.orgRootId = orgRootId;
    }

    public Long getOrgPersonId() {
        return orgPersonId;
    }

    public void setOrgPersonId(Long orgPersonId) {
        this.orgPersonId = orgPersonId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getPersonnelId() {
        return personnelId;
    }

    public void setPersonnelId(Long personnelId) {
        this.personnelId = personnelId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getRefType() {
        return refType;
    }

    public void setRefType(String refType) {
        this.refType = refType;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getDoubleName() {
        return doubleName;
    }

    public void setDoubleName(String doubleName) {
        this.doubleName = doubleName;
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
        return this.orgPersonId;
    }

    @Override
    public String toString() {
        return "OrgPersonRel{" +
        ", orgPersonId=" + orgPersonId +
        ", orgId=" + orgId +
        ", personnelId=" + personnelId +
        ", postId=" + postId +
        ", refType=" + refType +
        ", property=" + property +
        ", doubleName=" + doubleName +
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
