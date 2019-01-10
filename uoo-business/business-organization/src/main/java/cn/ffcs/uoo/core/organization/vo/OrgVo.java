package cn.ffcs.uoo.core.organization.vo;



import cn.ffcs.uoo.base.common.vo.BaseVo;
import cn.ffcs.uoo.core.organization.entity.*;

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
    private String areaCode;
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

    private Long updateUser;


    private String orgMartCode;
    private Long standardFlag;
    private String orgBizFullName;
    //组织树信息
    private String orgTreeInfos;
    //组织存在组织树标识
    private int flag;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getOrgTreeInfos() {
        return orgTreeInfos;
    }

    public void setOrgTreeInfos(String orgTreeInfos) {
        this.orgTreeInfos = orgTreeInfos;
    }

    public String getOrgBizFullName() {
        return orgBizFullName;
    }

    public void setOrgBizFullName(String orgBizFullName) {
        this.orgBizFullName = orgBizFullName;
    }

    /**
     * 组织称谓
     */
    private String orgBizName;

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

    //组织类别
    private List<OrgType> orgTypeList;

    private String orgTypeSplit;
    private List<Position> positionList;
    //组织职位
    private List<Post> postList;
    //组织联系人
    private List<PsonOrgVo> psonOrgVoList;
    //组织行政编码
    private List<PoliticalLocation> politicalLocationList;
    //组织扩展属性
    List<ExpandovalueVo> expandovalueVoList;
    //地区
    private AreaCodeVo areaCodeVo;


    //身份证信息
    private List<String> certIdList;
    //证件类型
    private List<OrgCertVo> orgCertVoList;
    //检索
    private String search;
    private String orgRelCreatDate;


    //排序字段
    private String sortField;
    //排序方式
    private String sortOrder;

    //tb_org 权限参数
    private String tabOrgParams;
    //tb_org_rel权限参数
    private String tabOrgRelParams;
    //TB_ORG_ORGTYPE_REL
    private String tabOrgOrgTypeParams;

    public String getTabOrgOrgTypeParams() {
        return tabOrgOrgTypeParams;
    }

    public void setTabOrgOrgTypeParams(String tabOrgOrgTypeParams) {
        this.tabOrgOrgTypeParams = tabOrgOrgTypeParams;
    }

    public String getTabOrgRelParams() {
        return tabOrgRelParams;
    }

    public void setTabOrgRelParams(String tabOrgRelParams) {
        this.tabOrgRelParams = tabOrgRelParams;
    }

    public String getTabOrgParams() {
        return tabOrgParams;
    }

    public void setTabOrgParams(String tabOrgParams) {
        this.tabOrgParams = tabOrgParams;
    }

    public String getSortField() {
        return sortField;
    }
    public Long getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }
    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public AreaCodeVo getAreaCodeVo() {
        return areaCodeVo;
    }

    public void setAreaCodeVo(AreaCodeVo areaCodeVo) {
        this.areaCodeVo = areaCodeVo;
    }

    public List<PoliticalLocation> getPoliticalLocationList() {
        return politicalLocationList;
    }

    public void setPoliticalLocationList(List<PoliticalLocation> politicalLocationList) {
        this.politicalLocationList = politicalLocationList;
    }


    public List<OrgCertVo> getOrgCertVoList() {
        return orgCertVoList;
    }

    public void setOrgCertVoList(List<OrgCertVo> orgCertVoList) {
        this.orgCertVoList = orgCertVoList;
    }

    public List<ExpandovalueVo> getExpandovalueVoList() {
        return expandovalueVoList;
    }

    public void setExpandovalueVoList(List<ExpandovalueVo> expandovalueVoList) {
        this.expandovalueVoList = expandovalueVoList;
    }

    public String getOrgMartCode() {
        return orgMartCode;
    }

    public void setOrgMartCode(String orgMartCode) {
        this.orgMartCode = orgMartCode;
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

    public Long getStandardFlag() {
        return standardFlag;
    }

    public void setStandardFlag(Long standardFlag) {
        this.standardFlag = standardFlag;
    }


    public String getOrgBizName() {
        return orgBizName;
    }

    public void setOrgBizName(String orgBizName) {
        this.orgBizName = orgBizName;
    }
}
