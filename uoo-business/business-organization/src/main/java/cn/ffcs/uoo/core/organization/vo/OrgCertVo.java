package cn.ffcs.uoo.core.organization.vo;/**
 * @description:
 * @author: ffcs-gzb
 * @date: 2018-11-13
 */

/**
 * <p>
 *
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018/11/13
 */
public class OrgCertVo {

    private Long orgCertId;
    private Long certId;
    private String certType;
    private String certNo;
    private String Issuing;
    private String effDate;

    public Long getOrgCertId() {
        return orgCertId;
    }

    public void setOrgCertId(Long orgCertId) {
        this.orgCertId = orgCertId;
    }

    public Long getCertId() {
        return certId;
    }

    public void setCertId(Long certId) {
        this.certId = certId;
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

    public String getIssuing() {
        return Issuing;
    }

    public void setIssuing(String issuing) {
        Issuing = issuing;
    }

    public String getEffDate() {
        return effDate;
    }

    public void setEffDate(String effDate) {
        this.effDate = effDate;
    }
}
