package cn.ffcs.uoo.web.maindata.region.dto;

import java.util.Date;

/**
 * <p>
 * 行政区域
 * </p>
 *
 * @author zxs
 * @since 2018-10-30
 */
public class TbPoliticalLocation  {


    /**
     * 行政区域标识
     */
    private Long locId;

    /**
     * 行政区域编码
     */
    private String locCode;

    /**
     * 行政区域名称
     */
    private String locName;

    /**
     * 行政区域描述
     */
    private String locDesc;

    /**
     * 行政区域类型
     */
    private String locType;

    /**
     * 行政区域简拼
     */
    private String locAbbr;

    /**
     * 上级行政区域标识
     */
    private Long upLocId;

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

    public Long getLocId() {
        return locId;
    }

    public void setLocId(Long locId) {
        this.locId = locId;
    }

    public String getLocCode() {
        return locCode;
    }

    public void setLocCode(String locCode) {
        this.locCode = locCode;
    }

    public String getLocName() {
        return locName;
    }

    public void setLocName(String locName) {
        this.locName = locName;
    }

    public String getLocDesc() {
        return locDesc;
    }

    public void setLocDesc(String locDesc) {
        this.locDesc = locDesc;
    }

    public String getLocType() {
        return locType;
    }

    public void setLocType(String locType) {
        this.locType = locType;
    }

    public String getLocAbbr() {
        return locAbbr;
    }

    public void setLocAbbr(String locAbbr) {
        this.locAbbr = locAbbr;
    }

    public Long getUpLocId() {
        return upLocId;
    }

    public void setUpLocId(Long upLocId) {
        this.upLocId = upLocId;
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
        return "TbPoliticalLocation [locId=" + locId + ", locCode=" + locCode + ", locName=" + locName + ", locDesc="
                + locDesc + ", locType=" + locType + ", locAbbr=" + locAbbr + ", upLocId=" + upLocId + ", statusCd="
                + statusCd + ", createDate=" + createDate + ", createUser=" + createUser + ", updateDate=" + updateDate
                + ", updateUser=" + updateUser + ", statusDate=" + statusDate + "]";
    }

}
