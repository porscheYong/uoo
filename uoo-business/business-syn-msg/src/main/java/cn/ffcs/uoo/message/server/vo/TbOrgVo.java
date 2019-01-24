package cn.ffcs.uoo.message.server.vo;

import cn.ffcs.uoo.message.server.pojo.*;
import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class TbOrgVo {
    private Long orgId;

    private Long locId;

    private Long areaCodeId;

    private String orgName;

    private String orgCode;

    private String shortName;

    private String orgNameEn;

    private String fullName;

    private String cityTown;

    private String officePhone;

    private Date foundingTime;

    private String orgScale;

    private String orgLevel;

    private String orgPositionLevel;

    private BigDecimal sort;

    private String orgContent;

    private String orgDesc;

    private String address;

    private String uuid;

    private String statusCd;

    private Date createDate;

    private Long createUser;

    private Date updateDate;

    private Long updateUser;

    private Date statusDate;

    private String orgMartCode;

    //组织称谓
    private String orgBizName;

    //系统标识
    private Long businessSystemId;

    //组织树Id
    private Long orgTreeId;

    //扩展信息(从数据库获取的)
    @JSONField(serialize=false)
    private List<ExpandVo> extendInfo2;

    //扩展信息
    private List<Map<String,Object>> extendInfo;

    //组织层级
    private OrgLevelVo orgLevels;

    //组织上下级关系
    private TbOrgRelVo orgRelations;

    //组织类型
    private List<TbOrgTypeVo> orgTypes;

    //组织跨域类型
    private List<TbOrgCrossRel> orgCrossRelations;

    //组织证件
    private List<TbCert> orgCerts;

    //组织联系人
    private List<TbOrgContactRelVo> contactRelations;

    //行政编码
    private TbPoliticalLocation politicalLocationInfo;

    //区域编码
    private TbAreaCode areaCodeInfo;

    //组织所在树
    private List<TbOrgTree> orgOrgTreeRel;

    public List<TbOrgTree> getOrgOrgTreeRel() {
        return orgOrgTreeRel;
    }

    public void setOrgOrgTreeRel(List<TbOrgTree> orgOrgTreeRel) {
        this.orgOrgTreeRel = orgOrgTreeRel;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getLocId() {
        return locId;
    }

    public void setLocId(Long locId) {
        this.locId = locId;
    }

    public Long getAreaCodeId() {
        return areaCodeId;
    }

    public void setAreaCodeId(Long areaCodeId) {
        this.areaCodeId = areaCodeId;
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

    public Date getFoundingTime() {
        return foundingTime;
    }

    public void setFoundingTime(Date foundingTime) {
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

    public BigDecimal getSort() {
        return sort;
    }

    public void setSort(BigDecimal sort) {
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

    public Long getBusinessSystemId() {
        return businessSystemId;
    }

    public void setBusinessSystemId(Long businessSystemId) {
        this.businessSystemId = businessSystemId;
    }

    public Long getOrgTreeId() {
        return orgTreeId;
    }

    public void setOrgTreeId(Long orgTreeId) {
        this.orgTreeId = orgTreeId;
    }

    public List<ExpandVo> getExtendInfo2() {
        return extendInfo2;
    }

    public void setExtendInfo2(List<ExpandVo> extendInfo2) {
        this.extendInfo2 = extendInfo2;
    }

    public OrgLevelVo getOrgLevels() {
        return orgLevels;
    }

    public void setOrgLevels(OrgLevelVo orgLevels) {
        this.orgLevels = orgLevels;
    }

    public TbOrgRelVo getOrgRelations() {
        return orgRelations;
    }

    public void setOrgRelations(TbOrgRelVo orgRelations) {
        this.orgRelations = orgRelations;
    }

    public List<TbOrgTypeVo> getOrgTypes() {
        return orgTypes;
    }

    public void setOrgTypes(List<TbOrgTypeVo> orgTypes) {
        this.orgTypes = orgTypes;
    }

    public List<TbOrgCrossRel> getOrgCrossRelations() {
        return orgCrossRelations;
    }

    public void setOrgCrossRelations(List<TbOrgCrossRel> orgCrossRelations) {
        this.orgCrossRelations = orgCrossRelations;
    }

    public List<TbCert> getOrgCerts() {
        return orgCerts;
    }

    public void setOrgCerts(List<TbCert> orgCerts) {
        this.orgCerts = orgCerts;
    }

    public List<TbOrgContactRelVo> getContactRelations() {
        return contactRelations;
    }

    public void setContactRelations(List<TbOrgContactRelVo> contactRelations) {
        this.contactRelations = contactRelations;
    }

    public TbPoliticalLocation getPoliticalLocationInfo() {
        return politicalLocationInfo;
    }

    public void setPoliticalLocationInfo(TbPoliticalLocation politicalLocationInfo) {
        this.politicalLocationInfo = politicalLocationInfo;
    }

    public TbAreaCode getAreaCodeInfo() {
        return areaCodeInfo;
    }

    public void setAreaCodeInfo(TbAreaCode areaCodeInfo) {
        this.areaCodeInfo = areaCodeInfo;
    }

    public String getOrgBizName() {
        return orgBizName;
    }

    public void setOrgBizName(String orgBizName) {
        this.orgBizName = orgBizName;
    }

    public List<Map<String, Object>> getExtendInfo() {
        return extendInfo;
    }

    public void setExtendInfo(List<Map<String, Object>> extendInfo) {
        this.extendInfo = extendInfo;
    }

    public String getOrgMartCode() {
        return orgMartCode;
    }

    public void setOrgMartCode(String orgMartCode) {
        this.orgMartCode = orgMartCode;
    }
}
