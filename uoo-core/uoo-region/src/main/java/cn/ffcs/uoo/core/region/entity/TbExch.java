package cn.ffcs.uoo.core.region.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 描述信令所指的方向,如到某个局(每局对应一个DPC)的信令,可称到某局的局向。
 * </p>
 *
 * @author zxs
 * @since 2018-10-30
 */
// @Data
@TableName("TB_EXCH")
public class TbExch extends Model<TbExch> {

    private static final long serialVersionUID = 1L;

    /**
     * 局向标识
     */
    @TableId("EXCH_ID")
    private Long exchId;

    /**
     * 公共管理区域标识
     */
    @TableField("COMMON_REGION_ID")
    private Long commonRegionId;

    /**
     * 资源局向描述
     */
    @TableField("SRC_EXCH_DESC")
    private String srcExchDesc;

    /**
     * 资源局向标识
     */
    @TableField("SRC_EXCH_ID")
    private Long srcExchId;

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

    @Override
    protected Serializable pkVal() {
        return this.exchId;
    }

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
