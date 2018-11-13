package cn.ffcs.uoo.web.maindata.organization.dto;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-09-25
 */
@TableName("TB_ORG")
public class Org extends Model<Org> {

    private static final long serialVersionUID = 1L;

    /**
     * 组织标识
     */
    @TableId(value = "ORG_ID")
    private Long orgId;
    /**
     * 行政区域标识
     */
    @TableField("LOC_ID")
    private Long locId;
    /**
     * 区号标识
     */
    @TableField("AREA_CODE_ID")
    private Long areaCodeId;
    /**
     * 组织名称
     */
    @TableField("ORG_NAME")
    private String orgName;
    /**
     * 组织编码
     */
    @TableField("ORG_CODE")
    private String orgCode;
    /**
     * 组织简称
     */
    @TableField("SHORT_NAME")
    private String shortName;
    /**
     * 组织英文简称
     */
    @TableField("ORG_NAME_EN")
    private String orgNameEn;
    /**
     * 组织全称
     */
    @TableField("FULL_NAME")
    private String fullName;
    /**
     * 农村/城镇标志
     */
    @TableField("CITY_TOWN")
    private String cityTown;
    /**
     * 组织办公电话
     */
    @TableField("OFFICE_PHONE")
    private String officePhone;
    /**
     * 成立时间
     */
    @TableField("FOUNDING_TIME")
    private Date foundingTime;
    /**
     * 组织规模
     */
    @TableField("ORG_SCALE")
    private String orgScale;
    @TableField("ORG_LEVEL")
    private String orgLevel;
    /**
     * 组织行政级别 一级、二级正副、三级正副、四级正副、五级正副
     */
    @TableField("ORG_POSITION_LEVEL")
    private String orgPositionLevel;
    /**
     * 组织行政级别
     */
    @TableField("SORT")
    private Double sort;
    /**
     * 排序号
     */
    @TableField("ORG_CONTENT")
    private String orgContent;
    /**
     * 组织简介
     */
    @TableField("ORG_DESC")
    private String orgDesc;
    /**
     * 组织描述
     */
    @TableField("ADDRESS")
    private String address;
    /**
     * 地址
     */
    @TableField("UUID")
    private String uuid;
    /**
     * 状态
     */
    @TableField("STATUS_CD")
    private String statusCd;
    /**
     * 创建时间
     */
    @TableField("CREATE_DATE")
    private Date createDate;
    /**
     * 创建人
     */
    @TableField("CREATE_USER")
    private Long createUser;
    /**
     * 修改时间
     */
    @TableField("UPDATE_DATE")
    private Date updateDate;
    /**
     * 修改人
     */
    @TableField("UPDATE_USER")
    private Long updateUser;
    /**
     * 状态变更的时间
     */
    @TableField("STATUS_DATE")
    private Date statusDate;





    /**
     *  组织树ID
     */
    @TableField(exist=false)
    private Long orgTreeId;
    /**
     * 组织树根节点
     */
    @TableField(exist=false)
    private Long orgRootId;
    /**
     *  组织树编码
     */
    @TableField(exist=false)
    private String refCode;
    /**
     * 组织上级节点
     */
    @TableField(exist=false)
    private Long supOrgId;
    /**
     * 组织类别关系
     */
    @TableField(exist=false)
    private List<OrgOrgtypeRel> orgTypeRefList;
    /**
     * 组织岗位关系
     */
    @TableField(exist=false)
    private List<OrgPositionRel> orgPositionList;
    /**
     *  组织类别
     */
    @TableField(exist=false)
    private List<OrgType> orgTypeList;
    /**
     * 组织岗位
     */
    @TableField(exist=false)
    private List<Position> positionList;
    /**
     * 组织职位
     */
    @TableField(exist=false)
    private List<Post> postList;
    /**
     * 层级
     */
    @TableField(exist=false)
    private String lv;

    /**
     * 人员
     */
    @TableField(exist=false)
    private List<PsonOrgVo> psonOrgVoList;

    public List<PsonOrgVo> getPsonOrgVoList() {
        return psonOrgVoList;
    }

    public void setPsonOrgVoList(List<PsonOrgVo> psonOrgVoList) {
        this.psonOrgVoList = psonOrgVoList;
    }

    public Long getOrgRootId() {
        return orgRootId;
    }

    public void setOrgRootId(Long orgRootId) {
        this.orgRootId = orgRootId;
    }

    public String getLv() {
        return lv;
    }

    public void setLv(String lv) {
        this.lv = lv;
    }

    public Long getSupOrgId() {
        return supOrgId;
    }

    public void setSupOrgId(Long supOrgId) {
        this.supOrgId = supOrgId;
    }

    public List<Post> getPostList() {
        return postList;
    }

    public void setPostList(List<Post> postList) {
        this.postList = postList;
    }

    public List<Position> getPositionList() {
        return positionList;
    }

    public void setPositionList(List<Position> positionList) {
        this.positionList = positionList;
    }

    public String getRefCode() {
        return refCode;
    }

    public void setRefCode(String refCode) {
        this.refCode = refCode;
    }

    public List<OrgType> getOrgTypeList() {
        return orgTypeList;
    }

    public void setOrgTypeList(List<OrgType> orgTypeList) {
        this.orgTypeList = orgTypeList;
    }

    public Long getOrgTreeId() {
        return orgTreeId;
    }

    public List<OrgOrgtypeRel> getOrgTypeRefList() {
        return orgTypeRefList;
    }

    public List<OrgPositionRel> getOrgPositionList() {
        return orgPositionList;
    }

    public void setOrgTreeId(Long orgTreeId) {
        this.orgTreeId = orgTreeId;
    }

    public void setOrgTypeRefList(List<OrgOrgtypeRel> orgTypeRefList) {
        this.orgTypeRefList = orgTypeRefList;
    }

    public void setOrgPositionList(List<OrgPositionRel> orgPositionList) {
        this.orgPositionList = orgPositionList;
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

    public Double getSort() {
        return sort;
    }

    public void setSort(Double sort) {
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

    @Override
    protected Serializable pkVal() {
        return this.orgId;
    }

    @Override
    public String toString() {
        return "Org{" +
        ", orgId=" + orgId +
        ", locId=" + locId +
        ", areaCodeId=" + areaCodeId +
        ", orgName=" + orgName +
        ", orgCode=" + orgCode +
        ", shortName=" + shortName +
        ", orgNameEn=" + orgNameEn +
        ", fullName=" + fullName +
        ", cityTown=" + cityTown +
        ", officePhone=" + officePhone +
        ", foundingTime=" + foundingTime +
        ", orgScale=" + orgScale +
        ", orgLevel=" + orgLevel +
        ", orgPositionLevel=" + orgPositionLevel +
        ", sort=" + sort +
        ", orgContent=" + orgContent +
        ", orgDesc=" + orgDesc +
        ", address=" + address +
        ", uuid=" + uuid +
        ", statusCd=" + statusCd +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        ", statusDate=" + statusDate +
        "}";
    }
}
