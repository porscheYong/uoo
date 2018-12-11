package cn.ffcs.uoo.web.maindata.region.dto;

import java.util.Date;

/**
 * <p>
 * 指对于各种专业电信管理区域的共性管理区域信息的抽象表达，包括省公司、本地网、营业区。因为使用目的不同，可以定义不同使用类型的管理区域，如：CRM使用、计费使用、营销使用等。
 * </p>
 *
 * @author zxs
 * @since 2018-10-30
 */
// @Data
// @EqualsAndHashCode(callSuper = true)
// @Accessors(chain = true)
public class TbCommonRegion {


    /**
     * 公共管理区域标识
     */
    private Long commonRegionId;

    /**
     * 上级区域标识
     */
    private Long parentRegionId;

    /**
     * 区域名称
     */
    private String regionName;

    /**
     * 区域拼音名称
     */
    private String regionPyName;

    /**
     * 区域编码
     */
    private String regionNbr;

    /**
     * 区域类型
     */
    private String regionType;

    /**
     * 区域描述
     */
    private String regionDesc;

    /**
     * 区域级别
     */
    private Integer regionLevel;

    /**
     * 区域排序
     */
    private Integer regionSort;

    /**
     * 省分编码
     */
    private String provinceNbr;

    /**
     * 城乡标识
     */
    private String cityFlag;

    /**
     * 状态
     */
    private String statusCd;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 创建人
     */
    private Long createUser;

    /**
     * 修改时间
     */
    private Date updateDate;

    /**
     * 修改人
     */
    private Long updateUser;

    /**
     * 状态变更的时间
     */
    private Date statusDate;
    private Long areaCodeId;
    public Long getAreaCodeId() {
        return areaCodeId;
    }

    public void setAreaCodeId(Long areaCodeId) {
        this.areaCodeId = areaCodeId;
    }
    public Long getCommonRegionId() {
        return commonRegionId;
    }

    public void setCommonRegionId(Long commonRegionId) {
        this.commonRegionId = commonRegionId;
    }

    public Long getParentRegionId() {
        return parentRegionId;
    }

    public void setParentRegionId(Long parentRegionId) {
        this.parentRegionId = parentRegionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getRegionPyName() {
        return regionPyName;
    }

    public void setRegionPyName(String regionPyName) {
        this.regionPyName = regionPyName;
    }

    public String getRegionNbr() {
        return regionNbr;
    }

    public void setRegionNbr(String regionNbr) {
        this.regionNbr = regionNbr;
    }

    public String getRegionType() {
        return regionType;
    }

    public void setRegionType(String regionType) {
        this.regionType = regionType;
    }

    public String getRegionDesc() {
        return regionDesc;
    }

    public void setRegionDesc(String regionDesc) {
        this.regionDesc = regionDesc;
    }

    public Integer getRegionLevel() {
        return regionLevel;
    }

    public void setRegionLevel(Integer regionLevel) {
        this.regionLevel = regionLevel;
    }

    public Integer getRegionSort() {
        return regionSort;
    }

    public void setRegionSort(Integer regionSort) {
        this.regionSort = regionSort;
    }

    public String getProvinceNbr() {
        return provinceNbr;
    }

    public void setProvinceNbr(String provinceNbr) {
        this.provinceNbr = provinceNbr;
    }

    public String getCityFlag() {
        return cityFlag;
    }

    public void setCityFlag(String cityFlag) {
        this.cityFlag = cityFlag;
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
    public String toString() {
        return "TbCommonRegion [commonRegionId=" + commonRegionId + ", parentRegionId=" + parentRegionId + ", regionName="
                + regionName + ", regionPyName=" + regionPyName + ", regionNbr=" + regionNbr + ", regionType="
                + regionType + ", regionDesc=" + regionDesc + ", regionLevel=" + regionLevel + ", regionSort="
                + regionSort + ", provinceNbr=" + provinceNbr + ", cityFlag=" + cityFlag + ", statusCd=" + statusCd
                + ", createDate=" + createDate + ", createUser=" + createUser + ", updateDate=" + updateDate
                + ", updateUser=" + updateUser + ", statusDate=" + statusDate + "]";
    }

}
