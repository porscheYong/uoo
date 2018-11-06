package cn.ffcs.uoo.web.maindata.region.entity;

import java.util.Date;

/**
 * <p>
 * 描述信令所指的方向,如到某个局(每局对应一个DPC)的信令,可称到某局的局向。
 * </p>
 *
 * @author zxs
 * @since 2018-10-30
 */
public class TbExch {


    /**
     * 局向标识
     */
    private Long exchId;

    /**
     * 公共管理区域标识
     */
    private Long commonRegionId;

    /**
     * 资源局向描述
     */
    private String srcExchDesc;

    /**
     * 资源局向标识
     */
    private Long srcExchId;

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

    public Long getExchId() {
        return exchId;
    }

    public void setExchId(Long exchId) {
        this.exchId = exchId;
    }

    public Long getCommonRegionId() {
        return commonRegionId;
    }

    public void setCommonRegionId(Long commonRegionId) {
        this.commonRegionId = commonRegionId;
    }

    public String getSrcExchDesc() {
        return srcExchDesc;
    }

    public void setSrcExchDesc(String srcExchDesc) {
        this.srcExchDesc = srcExchDesc;
    }

    public Long getSrcExchId() {
        return srcExchId;
    }

    public void setSrcExchId(Long srcExchId) {
        this.srcExchId = srcExchId;
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
        return "TbExch [exchId=" + exchId + ", commonRegionId=" + commonRegionId + ", srcExchDesc=" + srcExchDesc
                + ", srcExchId=" + srcExchId + ", statusCd=" + statusCd + ", createDate=" + createDate + ", createUser="
                + createUser + ", updateDate=" + updateDate + ", updateUser=" + updateUser + ", statusDate="
                + statusDate + "]";
    }

}
