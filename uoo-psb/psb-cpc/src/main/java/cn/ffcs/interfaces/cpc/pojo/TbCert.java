package cn.ffcs.interfaces.cpc.pojo;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;
@TableName("Tb_Cert")
public class TbCert extends Model<TbCert> {

    @TableId(value="cert_Id",type = IdType.INPUT)
    private Long certId;

    @TableField("personnel_Id")
    private Long personnelId;

    @TableField("cert_Name")
    private String certName;

    @TableField("cert_Type")
    private String certType;

    @TableField("cert_No")
    private String certNo;

    @TableField("address")
    private String address;

    @TableField("issuing")
    private String issuing;

    @TableField("uuid")
    private String uuid;

    @TableField("is_Real")
    private String isReal;

    @TableField("source")
    private String source;

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

    public TbCert() {
    }

    public TbCert(Long personnelId, String certName, String certType, String certNo, String uuid, String isReal, String statusCd, Date createDate) {
        this.personnelId = personnelId;
        this.certName = certName;
        this.certType = certType;
        this.certNo = certNo;
        this.uuid = uuid;
        this.isReal = isReal;
        this.statusCd = statusCd;
        this.createDate = createDate;
    }

    public Long getCertId() {
        return certId;
    }

    public void setCertId(Long certId) {
        this.certId = certId;
    }

    public Long getPersonnelId() {
        return personnelId;
    }

    public void setPersonnelId(Long personnelId) {
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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
        return this.certId;
    }
}