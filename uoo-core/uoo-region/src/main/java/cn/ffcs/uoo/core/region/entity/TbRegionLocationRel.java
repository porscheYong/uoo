package cn.ffcs.uoo.core.region.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 行政区域和公用管理区域关系。
 * </p>
 *
 * @author zxs
 * @since 2018-10-30
 */
// @Data
@TableName("TB_REGION_LOCATION_REL")
public class TbRegionLocationRel extends Model<TbRegionLocationRel> {

    private static final long serialVersionUID = 1L;

    /**
     * 区域关系标识
     */
    @TableId("REGION_LOC_REL_ID")
    private Long regionLocRelId;
    /**
     * 公共管理区域标识
     */
    @TableField("COMMON_REGION_ID")
    private Long commonRegionId;


    /**
     * 行政区域标识
     */
    @TableField("LOC_ID")
    private Long locId;

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
        return null;
    }

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
