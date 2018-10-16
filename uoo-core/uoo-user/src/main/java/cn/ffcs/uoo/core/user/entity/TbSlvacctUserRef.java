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
@TableName("TB_SLVACCT_USER_REF")
public class TbSlvacctUserRef extends Model<TbSlvacctUserRef> {

    private static final long serialVersionUID = 1L;

    /**
     * 从账号用户关系标识
     */
    @TableId("SLVACCT_USER_ID")
    private Long slvacctUserId;
    /**
     * 用户标识
     */
    @TableField("USER_ID")
    private String userId;
    /**
     * 从账号标识
     */
    @TableField("SLAVE_ACCT_ID")
    private Long slaveAcctId;
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


    public Long getSlvacctUserId() {
        return slvacctUserId;
    }

    public void setSlvacctUserId(Long slvacctUserId) {
        this.slvacctUserId = slvacctUserId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getSlaveAcctId() {
        return slaveAcctId;
    }

    public void setSlaveAcctId(Long slaveAcctId) {
        this.slaveAcctId = slaveAcctId;
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
        return this.slvacctUserId;
    }

    @Override
    public String toString() {
        return "TbSlvacctUserRef{" +
        ", slvacctUserId=" + slvacctUserId +
        ", userId=" + userId +
        ", slaveAcctId=" + slaveAcctId +
        ", statusCd=" + statusCd +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        ", statusDate=" + statusDate +
        "}";
    }
}
