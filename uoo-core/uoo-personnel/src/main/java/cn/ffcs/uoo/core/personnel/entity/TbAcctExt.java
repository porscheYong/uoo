package cn.ffcs.uoo.core.personnel.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 主账号扩展
 * </p>
 *
 * @author zhanglu
 * @since 2018-09-14
 */
@TableName("tb_acct_ext")
public class TbAcctExt extends Model<TbAcctExt> {

    private static final long serialVersionUID = 1L;

    /**
     * 主账号扩展标识
     */
    @TableId("ACCT_EXT_ID")
    private Long acctExtId;
    /**
     * 主账号标识
     */
    @TableField("PK_ACCT")
    private Long pkAcct;
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


    public Long getAcctExtId() {
        return acctExtId;
    }

    public void setAcctExtId(Long acctExtId) {
        this.acctExtId = acctExtId;
    }

    public Long getPkAcct() {
        return pkAcct;
    }

    public void setPkAcct(Long pkAcct) {
        this.pkAcct = pkAcct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactWay() {
        return contactWay;
    }

    public void setContactWay(String contactWay) {
        this.contactWay = contactWay;
    }

    public String getWorkEmail() {
        return workEmail;
    }

    public void setWorkEmail(String workEmail) {
        this.workEmail = workEmail;
    }

    public String getCertType() {
        return certType;
    }

    public void setCertType(String certType) {
        this.certType = certType;
    }

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
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
        return this.acctExtId;
    }

    @Override
    public String toString() {
        return "TbAcctExt{" +
        ", acctExtId=" + acctExtId +
        ", pkAcct=" + pkAcct +
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
