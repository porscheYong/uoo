package cn.ffcs.uoo.core.organization.vo;/**
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
public class SolrOrgVo {

    private String id;

    private String orgId;

    private String orgCode;

    private String orgRelTypeId;

    private String orgName;

    private String fullName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgRelTypeId() {
        return orgRelTypeId;
    }

    public void setOrgRelTypeId(String orgRelTypeId) {
        this.orgRelTypeId = orgRelTypeId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
