package cn.ffcs.uoo.web.maindata.organization.dto;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author ffcs-gzb
 * @since 2019-01-25
 */
@TableName("TB_EXCEL_ORG_IMPORT")
public class ExcelOrgImport extends Model<ExcelOrgImport> {

    private static final long serialVersionUID = 1L;

    @TableField("EXCEL_ORG_IMPORT_ID")
    private Long excelOrgImportId;
    @TableField("IMP_SEQ")
    private Long impSeq;
    @TableField("ORG_ID")
    private Long orgId;
    @TableField("PARENT_ORG_ID")
    private Long parentOrgId;
    @TableField("ORG_NAME")
    private String orgName;
    @TableField("FILE_NAME")
    private String fileName;
    @TableField("SIGN")
    private String sign;
    @TableField("CONTENT")
    private String content;
    @TableField("STATUS_CD")
    private String statusCd;
    @TableField("CREATE_DATE")
    private Date createDate;
    @TableField("CREATE_USER")
    private Long createUser;
    @TableField("UPDATE_DATE")
    private Date updateDate;
    @TableField("UPDATE_USER")
    private Long updateUser;
    @TableField("STATUS_DATE")
    private Date statusDate;


    public Long getExcelOrgImportId() {
        return excelOrgImportId;
    }

    public void setExcelOrgImportId(Long excelOrgImportId) {
        this.excelOrgImportId = excelOrgImportId;
    }

    public Long getImpSeq() {
        return impSeq;
    }

    public void setImpSeq(Long impSeq) {
        this.impSeq = impSeq;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getParentOrgId() {
        return parentOrgId;
    }

    public void setParentOrgId(Long parentOrgId) {
        this.parentOrgId = parentOrgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
        return null;
    }

    @Override
    public String toString() {
        return "ExcelOrgImport{" +
        ", excelOrgImportId=" + excelOrgImportId +
        ", impSeq=" + impSeq +
        ", orgId=" + orgId +
        ", parentOrgId=" + parentOrgId +
        ", orgName=" + orgName +
        ", fileName=" + fileName +
        ", sign=" + sign +
        ", content=" + content +
        ", statusCd=" + statusCd +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        ", statusDate=" + statusDate +
        "}";
    }
}
