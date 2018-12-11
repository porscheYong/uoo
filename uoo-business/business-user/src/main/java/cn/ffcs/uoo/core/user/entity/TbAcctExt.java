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
 * 主账号扩展
 * </p>
 *
 * @author wudj
 * @since 2018-10-29
 */
@TableName("tb_acct_ext")
@Data
public class TbAcctExt extends Model<TbAcctExt> {

    private static final long serialVersionUID = 1L;

    /**
     * 主账号扩展标识
     */
    @TableId("ACCT_EXT_ID")
    private Long acctExtId;

    /**
     * 从账号标识
     */
    @TableField("SLAVE_ACCT_ID")
    private Long slaveAcctId;
    /**
     * 姓名
     */
    @TableField("NAME")
    private String name;
    /**
     * 接收短信手机号
     */
    @TableField("CONTACT_WAY")
    private String contactWay;
    /**
     * 工作邮箱
     */
    @TableField("WORK_EMAIL")
    private String workEmail;
    /**
     * 证件类型
     */
    @TableField("CERT_TYPE")
    private String certType;
    /**
     * 证件号
     */
    @TableField("CERT_NO")
    private String certNo;
    /**
     * 性别
     */
    @TableField("GENDER")
    private String gender;
    /**
     * 民族
     */
    @TableField("NATION")
    private String nation;
    /**
     * 籍贯
     */
    @TableField("NATIVE_PLACE")
    private String nativePlace;
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
        return this.acctExtId;
    }

    @Override
    public String toString() {
        return "TbAcctExt{" +
        ", acctExtId=" + acctExtId +
        ", slaveAcctId=" + slaveAcctId +
        ", name=" + name +
        ", contactWay=" + contactWay +
        ", workEmail=" + workEmail +
        ", certType=" + certType +
        ", certNo=" + certNo +
        ", gender=" + gender +
        ", nation=" + nation +
        ", nativePlace=" + nativePlace +
        ", statusCd=" + statusCd +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        ", statusDate=" + statusDate +
        "}";
    }
}
