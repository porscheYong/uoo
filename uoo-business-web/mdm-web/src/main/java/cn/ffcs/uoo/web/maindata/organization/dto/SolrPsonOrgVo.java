package cn.ffcs.uoo.web.maindata.organization.dto;/**
 * @description:
 * @author: ffcs-gzb
 * @date: 2018-11-02
 */

/**
 * <p>
 *
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018/11/2
 */
public class SolrPsonOrgVo {

    private String id;

    private String psnName;

    private String certNo;

    private String orgRelTypeId;

    private String userId;

    private String psnFullName;

    private String acct;

    private String mobile;

    private String orgRootId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPsnName() {
        return psnName;
    }

    public void setPsnName(String psnName) {
        this.psnName = psnName;
    }

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }

    public String getOrgRelTypeId() {
        return orgRelTypeId;
    }

    public void setOrgRelTypeId(String orgRelTypeId) {
        this.orgRelTypeId = orgRelTypeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPsnFullName() {
        return psnFullName;
    }

    public void setPsnFullName(String psnFullName) {
        this.psnFullName = psnFullName;
    }

    public String getAcct() {
        return acct;
    }

    public void setAcct(String acct) {
        this.acct = acct;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOrgRootId() {
        return orgRootId;
    }

    public void setOrgRootId(String orgRootId) {
        this.orgRootId = orgRootId;
    }
}
