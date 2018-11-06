package cn.ffcs.uoo.web.maindata.region.entity;

import java.util.Date;

/**
 * <p>
 * 行政区域和公用管理区域关系。
 * </p>
 *
 * @author zxs
 * @since 2018-10-30
 */
public class TbRegionLocationRel {


    /**
     * 区域关系标识
     */
    private Long regionLocRelId;
    /**
     * 公共管理区域标识
     */
    private Long commonRegionId;


    /**
     * 行政区域标识
     */
    private Long locId;

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

     

    public Long getCommonRegionId() {
        return commonRegionId;
    }

    public void setCommonRegionId(Long commonRegionId) {
        this.commonRegionId = commonRegionId;
    }


    public Long getRegionLocRelId() {
        return regionLocRelId;
    }

    public void setRegionLocRelId(Long regionLocRelId) {
        this.regionLocRelId = regionLocRelId;
    }

    public Long getLocId() {
        return locId;
    }

    public void setLocId(Long locId) {
        this.locId = locId;
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
        return "TbRegionLocationRel [commonRegionId=" + commonRegionId + ", regionLocRelId="
                + regionLocRelId + ", locId=" + locId + ", statusCd=" + statusCd + ", createDate=" + createDate
                + ", createUser=" + createUser + ", updateDate=" + updateDate + ", updateUser=" + updateUser
                + ", statusDate=" + statusDate + "]";
    }

}
