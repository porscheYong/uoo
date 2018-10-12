package cn.ffcs.uoo.core.user.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author wudj
 * @since 2018-10-11
 */
@TableName("TB_ACCT_CROSS_REL")
public class TbAcctCrossRel extends Model<TbAcctCrossRel> {

    private static final long serialVersionUID = 1L;

    /**
     * 账号跨域关系标识
     */
    @TableId("ACCT_CROSS_ID")
    private Long acctCrossId;
    /**
     * 主账号标识
     */
    @TableField("ACCT_ID")
    private Long acctId;
    /**
     * 跨域账号标识
     */
    @TableField("CROSS_TRAN")
    private String crossTran;
    /**
     * 关系类型
     */
    @TableField("RELA_TYPE")
    private String relaType;
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

    @Override
    public String toString() {
        return "TbAcctCrossRel{" +
        ", acctCrossId=" + acctCrossId +
        ", acctId=" + acctId +
        ", crossTran=" + crossTran +
        ", relaType=" + relaType +
        ", statusCd=" + statusCd +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        ", statusDate=" + statusDate +
        "}";
    }
}
