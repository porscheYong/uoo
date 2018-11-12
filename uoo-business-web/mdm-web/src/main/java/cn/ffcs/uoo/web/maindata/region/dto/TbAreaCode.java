package cn.ffcs.uoo.web.maindata.region.dto;

import java.util.Date;

/**
 * <p>
 * 区号
 * </p>
 *
 * @author zxs
 * @since 2018-10-30
 */
public class TbAreaCode  {


    /**
     * 区号标识
     */
    private Long areaCodeId;

    /**
     * 公共管理区域标识
     */
    private Long commonRegionId;

    /**
     * 区号编码
     */
    private String areaNbr;

    /**
     * 区号
     */
    private String areaCode;

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

    public String getAreaNbr() {
        return areaNbr;
    }

    public void setAreaNbr(String areaNbr) {
        this.areaNbr = areaNbr;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
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
        return "TbAreaCode [areaCodeId=" + areaCodeId + ", commonRegionId=" + commonRegionId + ", areaNbr=" + areaNbr
                + ", areaCode=" + areaCode + ", statusCd=" + statusCd + ", createDate=" + createDate + ", createUser="
                + createUser + ", updateDate=" + updateDate + ", updateUser=" + updateUser + ", statusDate="
                + statusDate + "]";
    }

}
