package cn.ffcs.uoo.web.maindata.organization.dto;/**
 * @description:
 * @author: ffcs-gzb
 * @date: 2018-10-17
 */


/**
 * <p>
 *
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018/10/17
 */
public class OrgRefTypeVo extends BaseVo {

    private String orgId;
    private String supOrgId;
    private String orgName;
    private String supOrgName;
    private String refName;
    private String createDate;


    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getSupOrgId() {
        return supOrgId;
    }

    public void setSupOrgId(String supOrgId) {
        this.supOrgId = supOrgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getRefName() {
        return refName;
    }

    public void setRefName(String refName) {
        this.refName = refName;
    }

    public String getSupOrgName() {
        return supOrgName;
    }

    public void setSupOrgName(String supOrgName) {
        this.supOrgName = supOrgName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
