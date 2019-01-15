package cn.ffcs.interfaces.cpc.pojo;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

@TableName("Tb_Account_Org_Rel")
public class TbAccountOrgRel extends Model<TbAccountOrgRel> {

    @TableId(value="acct_Org_Rel_Id",type = IdType.INPUT)
    private Long acctOrgRelId;

    @TableField("org_Id")
    private Long orgId;

    @TableField("acct_Id")
    private Long acctId;

    @TableField("org_Tree_Id")
    private Long orgTreeId;

    @TableField("sort")
    private Long sort;

    @TableField("status_Cd")
    private String statusCd;

    @TableField("create_Date")
    private Date createDate;

    @TableField("create_User")
    private Long createUser;

    @TableField("update_Date")
    private Date updateDate;

    @TableField("update_User")
    private Long updateUser;

    @TableField("status_Date")
    private Date statusDate;

    public Long getAcctOrgRelId() {
        return acctOrgRelId;
    }

    public void setAcctOrgRelId(Long acctOrgRelId) {
        this.acctOrgRelId = acctOrgRelId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getAcctId() {
        return acctId;
    }

    public void setAcctId(Long acctId) {
        this.acctId = acctId;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
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

    public Long getOrgTreeId() {
        return orgTreeId;
    }

    public void setOrgTreeId(Long orgTreeId) {
        this.orgTreeId = orgTreeId;
    }

    @Override
    protected Serializable pkVal() {
        return this.acctOrgRelId;
    }
}