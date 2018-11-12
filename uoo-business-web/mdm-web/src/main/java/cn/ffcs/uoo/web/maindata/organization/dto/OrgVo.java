package cn.ffcs.uoo.web.maindata.organization.dto;





import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018/10/18
 */
public class OrgVo {

    private Long orgId;
    private String orgName;
    private String orgCode;
    private String fullName;
    private Long locId;
    private String locName;
    private String locCode;
    private String statusCd;
    private String refCode;
    private String areaCodeId;
    private String createDate;

    private List<OrgType> orgTypeList;
    private String orgTypeSplit;
    private List<Position> positionList;
    private List<Post> postList;


    //检索
    private String search;
    private String orgRootId;
    private String orgRelCreatDate;

    public String getOrgTypeSplit() {
        return orgTypeSplit;
    }

    public void setOrgTypeSplit(String orgTypeSplit) {
        this.orgTypeSplit = orgTypeSplit;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgRelCreatDate() {
        return orgRelCreatDate;
    }

    public void setOrgRelCreatDate(String orgRelCreatDate) {
        this.orgRelCreatDate = orgRelCreatDate;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getOrgRootId() {
        return orgRootId;
    }

    public void setOrgRootId(String orgRootId) {
        this.orgRootId = orgRootId;
    }

    public List<Position> getPositionList() {
        return positionList;
    }

    public void setPositionList(List<Position> positionList) {
        this.positionList = positionList;
    }

    public List<Post> getPostList() {
        return postList;
    }

    public void setPostList(List<Post> postList) {
        this.postList = postList;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }



    public String getLocCode() {
        return locCode;
    }

    public void setLocCode(String locCode) {
        this.locCode = locCode;
    }

    public String getAreaCodeId() {
        return areaCodeId;
    }

    public void setAreaCodeId(String areaCodeId) {
        this.areaCodeId = areaCodeId;
    }

    public List<OrgType> getOrgTypeList() {
        return orgTypeList;
    }

    public void setOrgTypeList(List<OrgType> orgTypeList) {
        this.orgTypeList = orgTypeList;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Long getLocId() {
        return locId;
    }

    public void setLocId(Long locId) {
        this.locId = locId;
    }

    public String getLocName() {
        return locName;
    }

    public void setLocName(String locName) {
        this.locName = locName;
    }

    public String getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(String statusCd) {
        this.statusCd = statusCd;
    }

    public String getRefCode() {
        return refCode;
    }

    public void setRefCode(String refCode) {
        this.refCode = refCode;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
}
