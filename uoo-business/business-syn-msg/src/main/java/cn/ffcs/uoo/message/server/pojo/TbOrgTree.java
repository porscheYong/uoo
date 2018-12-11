package cn.ffcs.uoo.message.server.pojo;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

@TableName("TB_ORG_TREE")
public class TbOrgTree extends Model<TbOrgTree> {

    @TableId("org_tree_id")
    private Long orgTreeId;

    @TableField("org_tree_name")
    private String orgTreeName;

    @TableField("org_tree_type")
    private Long orgTreeType;

    @TableField("org_id")
    private String orgId;

    @TableField("status_cd")
    private String statusCd;

    @TableField("create_date")
    private Date createDate;

    @TableField("create_user")
    private Long createUser;

    @TableField("update_date")
    private Date updateDate;

    @TableField("update_user")
    private Long updateUser;

    @TableField("status_date")
    private Date statusDate;

    @TableField("sort")
    private String sort;

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

    public Long getOrgTreeType() {
        return orgTreeType;
    }

    public void setOrgTreeType(Long orgTreeType) {
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

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    @Override
    protected Serializable pkVal() {
        return this.orgTreeId;
    }
}