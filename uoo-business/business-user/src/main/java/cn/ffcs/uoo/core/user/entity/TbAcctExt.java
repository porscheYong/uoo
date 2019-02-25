package cn.ffcs.uoo.core.user.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
     * 微信openID
     */
    @TableField("OPENID")
    private String openid;
    /**
     * 销售员编码
     */
    @TableField("SALE_CODE")
    private String saleCode;
    /**
     * 状态
     */
    @JsonIgnore
    @TableField(value = "STATUS_CD", fill = FieldFill.INSERT)
    private String statusCd;
    /**
     * 创建时间
     */
    @JsonIgnore
    @TableField(value = "CREATE_DATE", fill = FieldFill.INSERT)
    private Date createDate;
    /**
     * 创建人
     */
    @JsonIgnore
    @TableField(value = "CREATE_USER", fill = FieldFill.INSERT)
    private Long createUser;
    /**
     * 修改时间
     */
    @JsonIgnore
    @TableField(value = "UPDATE_DATE", fill = FieldFill.INSERT_UPDATE)
    private Date updateDate;
    /**
     * 修改人
     */
    @JsonIgnore
    @TableField(value = "UPDATE_USER", fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;
    /**
     * 状态变更的时间
     */
    @JsonIgnore
    @TableField(value = "STATUS_DATE", fill = FieldFill.INSERT)
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
