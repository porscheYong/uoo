package cn.ffcs.uoo.web.maindata.organization.dto;

import java.util.Date;


import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-09-25
 */
@TableName("TB_ORG_TREE")
public class OrgTree extends Model<OrgTree> {

    private static final long serialVersionUID = 1L;

    /**
     * 组织树标识
     */
    @TableId(value = "ORG_TREE_ID")
    private Long orgTreeId;
    /**
     * 组织树名称
     */
    @TableField("ORG_TREE_NAME")
    private String orgTreeName;
    /**
     * 标准组织树、专业组织树
     */
    @TableField("ORG_TREE_TYPE")
    private Integer orgTreeType;
    /**
     * 组织节点标识
     */
    @TableField("ORG_ID")
    private String orgId;
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
     * 排序
     */
    @TableField("SORT")
    private Double sort;



    /**
     * 组织关系类型
     */
    @TableField(exist=false)
    private List<OrgRelType> orgRelTypeList;

    /**
     * 组织类别
     */
    @TableField(exist=false)
    private List<OrgType> orgTypeList;

    /**
     * 用工性质
     */
    @TableField(exist=false)
    private List<String> userTypeList;

    /**
     * 用工性质
     */
    @TableField(exist=false)
    private String userTypeId;
    /**
     * 现有组织树
     */
    @TableField(exist=false)
    private List<TreeNodeVo> treeNodeList;
    /**
     * 目标组织树标识
     */
    @TableField(exist=false)
    private String tarOrgTreeId;


    public String getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(String userTypeId) {
        this.userTypeId = userTypeId;
    }

    public String getTarOrgTreeId() {
        return tarOrgTreeId;
    }

    public void setTarOrgTreeId(String tarOrgTreeId) {
        this.tarOrgTreeId = tarOrgTreeId;
    }

    public List<TreeNodeVo> getTreeNodeList() {
        return treeNodeList;
    }

    public void setTreeNodeList(List<TreeNodeVo> treeNodeList) {
        this.treeNodeList = treeNodeList;
    }

    public List<String> getUserTypeList() {
        return userTypeList;
    }

    public void setUserTypeList(List<String> userTypeList) {
        this.userTypeList = userTypeList;
    }

    public List<OrgRelType> getOrgRelTypeList() {
        return orgRelTypeList;
    }

    public void setOrgRelTypeList(List<OrgRelType> orgRelTypeList) {
        this.orgRelTypeList = orgRelTypeList;
    }

    public List<OrgType> getOrgTypeList() {
        return orgTypeList;
    }

    public void setOrgTypeList(List<OrgType> orgTypeList) {
        this.orgTypeList = orgTypeList;
    }

    public Double getSort() {
        return sort;
    }

    public void setSort(Double sort) {
        this.sort = sort;
    }

    public Long getOrgTreeId() {
        return orgTreeId;
    }

    public void setOrgTreeId(Long orgTreeId) {
        this.orgTreeId = orgTreeId;
    }

    public String getOrgTreeName() {
        return orgTreeName;
    }

    public void setOrgTreeName(String orgTreeName) {
        this.orgTreeName = orgTreeName;
    }

    public Integer getOrgTreeType() {
        return orgTreeType;
    }

    public void setOrgTreeType(Integer orgTreeType) {
        this.orgTreeType = orgTreeType;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
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
        return this.orgTreeId;
    }

    @Override
    public String toString() {
        return "OrgTree{" +
                ", orgTreeId=" + orgTreeId +
                ", orgTreeName=" + orgTreeName +
                ", orgTreeType=" + orgTreeType +
                ", orgId=" + orgId +
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
