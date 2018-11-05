package cn.ffcs.uoo.core.user.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 从账号主账号关系
 * </p>
 *
 * @author zhanglu
 * @since 2018-09-14
 */
@TableName("tb_slvacct_acct_rel")
@Data
public class TbSlvacctAcctRel extends Model<TbSlvacctAcctRel> {

    private static final long serialVersionUID = 1L;

    /**
     * 从账号主账号关系标识
     */
    @TableId("SLVACCT_ACCT_ID")
    private Long slvacctAcctId;
    /**
     * 主账号标识
     */
    @TableField("ACCT_ID")
    private Long acctId;
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



    @Override
    protected Serializable pkVal() {
        return this.slvacctAcctId;
    }

    @Override
    public String toString() {
        return "TbSlvacctAcctRef{" +
                ", slvacctAcctId=" + slvacctAcctId +
                ", acctId=" + acctId +
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
