package cn.ffcs.uoo.web.maindata.region.vo;

import java.util.ArrayList;
import java.util.List;

import cn.ffcs.uoo.web.maindata.region.dto.TbAreaCode;

public class CommonRegionVO {
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
    private List<String> locationNames=new ArrayList<>();
    private List<Long> locationIds=new ArrayList<>();
    private TbAreaCode areaCode;
     
     
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
     
    public TbAreaCode getAreaCode() {
        return areaCode;
    }
    public void setAreaCode(TbAreaCode areaCode) {
        this.areaCode = areaCode;
    }
    public List<String> getLocationNames() {
        return locationNames;
    }
    public void setLocationNames(List<String> locationNames) {
        this.locationNames = locationNames;
    }
    public List<Long> getLocationIds() {
        return locationIds;
    }
    public void setLocationIds(List<Long> locationIds) {
        this.locationIds = locationIds;
    }
    
}
