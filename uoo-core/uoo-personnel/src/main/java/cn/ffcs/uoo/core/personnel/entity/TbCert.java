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
 * 证件
 * </p>
 *
 * @author zhanglu
 * @since 2018-09-14
 */
@TableName("tb_cert")
public class TbCert extends Model<TbCert> {

    private static final long serialVersionUID = 1L;

    /**
     * 证件标识
     */
    @TableId("CERT_ID")
    private BigDecimal certId;
    /**
     * 人员标识
     */
    @TableField("PERSONNEL_ID")
    private BigDecimal personnelId;
    /**
     * 证件姓名
     */
    @TableField("CERT_NAME")
    private String certName;
    /**
     * 证件类型
     */
    @TableField("CERT_TYPE")
    private String certType;
    /**
     * 证件号码
     */
    @TableField("CERT_NO")
    private String certNo;
    /**
     * 证件地址
     */
    @TableField("ADDRESS")
    private String address;
    /**
     * 颁发机关
     */
    @TableField("ISSUING")
    private String issuing;
    /**
     * UUID
     */
    @TableField("UUID")
    private String uuid;
    /**
     * 是否实名
     */
    @TableField("IS_REAL")
    private String isReal;
    /**
     * 来源
     */
    @TableField("RESOURCE")
    private String resource;
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
    private BigDecimal createUser;
    /**
     * 修改时间
     */
    @TableField("UPDATE_DATE")
    private Date updateDate;
    /**
     * 修改人
     */
    @TableField("UPDATE_USER")
    private BigDecimal updateUser;
    /**
     * 状态变更的时间
     */
    @TableField("STATUS_DATE")
    private Date statusDate;


    public BigDecimal getCertId() {
        return certId;
    }

    public void setCertId(BigDecimal certId) {
        this.certId = certId;
    }

    public BigDecimal getPersonnelId() {
        return personnelId;
    }

    public void setPersonnelId(BigDecimal personnelId) {
        this.personnelId = personnelId;
    }

    public String getCertName() {
        return certName;
    }

    public void setCertName(String certName) {
        this.certName = certName;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIssuing() {
        return issuing;
    }

    public void setIssuing(String issuing) {
        this.issuing = issuing;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getIsReal() {
        return isReal;
    }

    public void setIsReal(String isReal) {
        this.isReal = isReal;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
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

    public BigDecimal getCreateUser() {
        return createUser;
    }

    public void setCreateUser(BigDecimal createUser) {
        this.createUser = createUser;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public BigDecimal getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(BigDecimal updateUser) {
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
        return this.certId;
    }

    @Override
    public String toString() {
        return "TbCert{" +
        ", certId=" + certId +
        ", personnelId=" + personnelId +
        ", certName=" + certName +
        ", certType=" + certType +
        ", certNo=" + certNo +
        ", address=" + address +
        ", issuing=" + issuing +
        ", uuid=" + uuid +
        ", isReal=" + isReal +
        ", resource=" + resource +
        ", statusCd=" + statusCd +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        ", statusDate=" + statusDate +
        "}";
    }
}
