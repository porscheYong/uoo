package cn.ffcs.uoo.message.service.vo;

import java.io.Serializable;
import java.util.List;

public class OrgVo implements Serializable {
    private String pkOrg;
    private String orgName;
    private String orgCode;
    private String shortName;
    private String orgNameEn;

    private String fullName;
    private String cityTown;
    private String officePhone;
    private String foundingTime;
    private String orgScale;

    private String orgLevel;
    private String sort;
    private String orgContent;
    private String orgDesc;
    private String address;

    private String uuid;
    private String statusCd;
    private String createDate;
    private String createUser;
    private String updateDate;

    private String updateUser;
    private String statusDate;

    private List<OrgRelationVo> orgRelations;

    private List<OrgTypeRelationVo> orgTypeRelations;

    private List<OrgCrossRelationVo> orgCrossRelations;

    private List<OrgCertRelationVo> orgCertRelations;

    private List<PsndocRelationVo> psndocRelations;

    private List<ContactRelationVo> contactRelations;

    private PoliticalLocationVo PoliticalLocationInfo;

    public String getPkOrg() {
        return pkOrg;
    }

    public void setPkOrg(String pkOrg) {
        this.pkOrg = pkOrg;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getOrgNameEn() {
        return orgNameEn;
    }

    public void setOrgNameEn(String orgNameEn) {
        this.orgNameEn = orgNameEn;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCityTown() {
        return cityTown;
    }

    public void setCityTown(String cityTown) {
        this.cityTown = cityTown;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public String getFoundingTime() {
        return foundingTime;
    }

    public void setFoundingTime(String foundingTime) {
        this.foundingTime = foundingTime;
    }

    public String getOrgScale() {
        return orgScale;
    }

    public void setOrgScale(String orgScale) {
        this.orgScale = orgScale;
    }

    public String getOrgLevel() {
        return orgLevel;
    }

    public void setOrgLevel(String orgLevel) {
        this.orgLevel = orgLevel;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrgContent() {
        return orgContent;
    }

    public void setOrgContent(String orgContent) {
        this.orgContent = orgContent;
    }

    public String getOrgDesc() {
        return orgDesc;
    }

    public void setOrgDesc(String orgDesc) {
        this.orgDesc = orgDesc;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(String statusCd) {
        this.statusCd = statusCd;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(String statusDate) {
        this.statusDate = statusDate;
    }

    public List<OrgRelationVo> getOrgRelations() {
        return orgRelations;
    }

    public void setOrgRelations(List<OrgRelationVo> orgRelations) {
        this.orgRelations = orgRelations;
    }

    public List<OrgTypeRelationVo> getOrgTypeRelations() {
        return orgTypeRelations;
    }

    public void setOrgTypeRelations(List<OrgTypeRelationVo> orgTypeRelations) {
        this.orgTypeRelations = orgTypeRelations;
    }

    public List<OrgCrossRelationVo> getOrgCrossRelations() {
        return orgCrossRelations;
    }

    public void setOrgCrossRelations(List<OrgCrossRelationVo> orgCrossRelations) {
        this.orgCrossRelations = orgCrossRelations;
    }

    public List<OrgCertRelationVo> getOrgCertRelations() {
        return orgCertRelations;
    }

    public void setOrgCertRelations(List<OrgCertRelationVo> orgCertRelations) {
        this.orgCertRelations = orgCertRelations;
    }

    public List<PsndocRelationVo> getPsndocRelations() {
        return psndocRelations;
    }

    public void setPsndocRelations(List<PsndocRelationVo> psndocRelations) {
        this.psndocRelations = psndocRelations;
    }

    public List<ContactRelationVo> getContactRelations() {
        return contactRelations;
    }

    public void setContactRelations(List<ContactRelationVo> contactRelations) {
        this.contactRelations = contactRelations;
    }

    public PoliticalLocationVo getPoliticalLocationInfo() {
        return PoliticalLocationInfo;
    }

    public void setPoliticalLocationInfo(PoliticalLocationVo politicalLocationInfo) {
        PoliticalLocationInfo = politicalLocationInfo;
    }

    public AreaCodeVo getAreaCodeInfo() {
        return areaCodeInfo;
    }

    public void setAreaCodeInfo(AreaCodeVo areaCodeInfo) {
        this.areaCodeInfo = areaCodeInfo;
    }

    private AreaCodeVo areaCodeInfo;
}