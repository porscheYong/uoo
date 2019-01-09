package cn.ffcs.interfaces.cpc.pojo;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

@TableName("Tb_Acct_Cross_Rel")
public class TbAcctCrossRel  extends Model<TbAcctCrossRel> {
    @TableId("acct_Cross_Id")
    private Long acctCrossId;

    @TableField("acct_Id")
    private Long acctId;

    @TableField("cross_Tran")
    private String crossTran;

    @TableField("rela_Type")
    private String relaType;

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

    public Long getAcctCrossId() {
        return acctCrossId;
    }

    public void setAcctCrossId(Long acctCrossId) {
        this.acctCrossId = acctCrossId;
    }

    public Long getAcctId() {
        return acctId;
    }

    public void setAcctId(Long acctId) {
        this.acctId = acctId;
    }

    public String getCrossTran() {
        return crossTran;
    }

    public void setCrossTran(String crossTran) {
        this.crossTran = crossTran;
    }

    public String getRelaType() {
        return relaType;
    }

    public void setRelaType(String relaType) {
        this.relaType = relaType;
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
        return this.acctCrossId;
    }
}