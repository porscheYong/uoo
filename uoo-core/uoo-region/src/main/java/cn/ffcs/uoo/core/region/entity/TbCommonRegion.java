package cn.ffcs.uoo.core.region.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

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
@TableName("TB_COMMON_REGION")
public class TbCommonRegion extends Model<TbCommonRegion> {

    private static final long serialVersionUID = 1L;

    /**
     * 公共管理区域标识
     */
    @TableId("COMMON_REGION_ID")
    private Long commonRegionId;

    /**
     * 上级区域标识
     */
    @TableField("UP_REGION_ID")
    private Long upRegionId;

    /**
     * 区域名称
     */
    @TableField("REGION_NAME")
    private String regionName;

    /**
     * 区域拼音名称
     */
    @TableField("REGION_PY_NAME")
    private String regionPyName;

    /**
     * 区域编码
     */
    @TableField("REGION_NBR")
    private String regionNbr;

    /**
     * 区域类型
     */
    @TableField("REGION_TYPE")
    private String regionType;

    /**
     * 区域描述
     */
    @TableField("REGION_DESC")
    private String regionDesc;

    /**
     * 区域级别
     */
    @TableField("REGION_LEVEL")
    private Integer regionLevel;

    /**
     * 区域排序
     */
    @TableField("REGION_SORT")
    private Integer regionSort;

    /**
     * 省分编码
     */
    @TableField("PROVINCE_NBR")
    private String provinceNbr;

    /**
     * 城乡标识
     */
    @TableField("CITY_FLAG")
    private String cityFlag;

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

    public Long getCommonRegionId() {
        return commonRegionId;
    }

    public void setCommonRegionId(Long commonRegionId) {
        this.commonRegionId = commonRegionId;
    }

    public Long getUpRegionId() {
        return upRegionId;
    }

    public void setUpRegionId(Long upRegionId) {
        this.upRegionId = upRegionId;
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
        return "TbCommonRegion [commonRegionId=" + commonRegionId + ", upRegionId=" + upRegionId + ", regionName="
                + regionName + ", regionPyName=" + regionPyName + ", regionNbr=" + regionNbr + ", regionType="
                + regionType + ", regionDesc=" + regionDesc + ", regionLevel=" + regionLevel + ", regionSort="
                + regionSort + ", provinceNbr=" + provinceNbr + ", cityFlag=" + cityFlag + ", statusCd=" + statusCd
                + ", createDate=" + createDate + ", createUser=" + createUser + ", updateDate=" + updateDate
                + ", updateUser=" + updateUser + ", statusDate=" + statusDate + "]";
    }

    @Override
    protected Serializable pkVal() {
        return this.commonRegionId;
    }
}
