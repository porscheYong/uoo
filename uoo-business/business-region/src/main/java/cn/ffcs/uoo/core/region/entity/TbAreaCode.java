package cn.ffcs.uoo.core.region.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 区号
 * </p>
 *
 * @author zxs
 * @since 2018-10-30
 */
// @Data
// @EqualsAndHashCode(callSuper = true)
// @Accessors(chain = true)
@TableName("TB_AREA_CODE")
public class TbAreaCode extends Model<TbAreaCode> {

    private static final long serialVersionUID = 1L;

    /**
     * 区号标识
     */
    @TableId("AREA_CODE_ID")
    private Long areaCodeId;

    /**
     * 公共管理区域标识
     */
    /*@TableField("COMMON_REGION_ID")
    private Long commonRegionId;
*/
    /**
     * 区号编码
     */
    @TableField("AREA_NBR")
    private String areaNbr;

    /**
     * 区号
     */
    @TableField("AREA_CODE")
    private String areaCode;

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
        return this.areaCodeId;
    }

    public Long getAreaCodeId() {
        return areaCodeId;
    }

    public void setAreaCodeId(Long areaCodeId) {
        this.areaCodeId = areaCodeId;
    }

   /* public Long getCommonRegionId() {
        return commonRegionId;
    }

    public void setCommonRegionId(Long commonRegionId) {
        this.commonRegionId = commonRegionId;
    }*/

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
        return "TbAreaCode [areaCodeId=" + areaCodeId + /*", commonRegionId=" + commonRegionId +*/ ", areaNbr=" + areaNbr
                + ", areaCode=" + areaCode + ", statusCd=" + statusCd + ", createDate=" + createDate + ", createUser="
                + createUser + ", updateDate=" + updateDate + ", updateUser=" + updateUser + ", statusDate="
                + statusDate + "]";
    }

}
