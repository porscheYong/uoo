package cn.ffcs.uoo.core.organization.vo;



import cn.ffcs.uoo.base.common.vo.BaseVo;
import cn.ffcs.uoo.core.organization.entity.*;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018/10/18
 */
public class OrgVo extends BaseVo{

    private Long orgId;
    private Long locId;
    private String areaCodeId;
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
    private String orgPositionLevel;
    private String sort;
    private String orgContent;
    private String orgDesc;
    private String address;
    private String uuid;
    private String statusCd;
    private String createDate;

    private String orgMartCode;
    //组织树
    private Long orgTreeId;
    /**
     * 组织树根节点
     */
    private String orgRootId;
    /**
     * 组织树编码
     */
    private String refCode;
    /**
     * 组织上级节点
     */
    private Long supOrgId;
    /**
     * 层级
     */
    private String lv;

    private String locName;
    private String locCode;


    private List<OrgType> orgTypeList;
    private String orgTypeSplit;
    private List<Position> positionList;
    private List<Post> postList;
    private List<PsonOrgVo> psonOrgVoList;
    private List<PoliticalLocation> politicalLocationList;


    //身份证信息
    private List<String> certIdList;
    private List<OrgCertVo> OrgCertList;
    //检索
    private String search;
    private String orgRelCreatDate;

    public List<PoliticalLocation> getPoliticalLocationList() {
        return politicalLocationList;
    }

    public void setPoliticalLocationList(List<PoliticalLocation> politicalLocationList) {
        this.politicalLocationList = politicalLocationList;
    }

    public List<OrgCertVo> getOrgCertList() {
        return OrgCertList;
    }

    public String getOrgMartCode() {
        return orgMartCode;
    }

    public void setOrgMartCode(String orgMartCode) {
        this.orgMartCode = orgMartCode;
    }

    public void setOrgCertList(List<OrgCertVo> orgCertList) {
        OrgCertList = orgCertList;
    }

    public Long getOrgTreeId() {
        return orgTreeId;
    }

    public void setOrgTreeId(Long orgTreeId) {
        this.orgTreeId = orgTreeId;
    }

    public Long getSupOrgId() {
        return supOrgId;
    }

    public void setSupOrgId(Long supOrgId) {
        this.supOrgId = supOrgId;
    }

    public String getLv() {
        return lv;
    }

    public void setLv(String lv) {
        this.lv = lv;
    }

    public List<PsonOrgVo> getPsonOrgVoList() {
        return psonOrgVoList;
    }

    public void setPsonOrgVoList(List<PsonOrgVo> psonOrgVoList) {
        this.psonOrgVoList = psonOrgVoList;
    }

    public List<String> getCertIdList() {
        return certIdList;
    }

    public void setCertIdList(List<String> certIdList) {
        this.certIdList = certIdList;
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

    public String getOrgPositionLevel() {
        return orgPositionLevel;
    }

    public void setOrgPositionLevel(String orgPositionLevel) {
        this.orgPositionLevel = orgPositionLevel;
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
