package cn.ffcs.uoo.core.position.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 职位行政区域
 * </p>
 *
 * @author zhanglu
 * @since 2018-10-30
 */
@TableName("TB_POST_LOCATION")
public class TbPostLocation extends Model<TbPostLocation> {

    private static final long serialVersionUID = 1L;

    /**
     * 职位行政区域标识
     */
    @TableId("POST_LOCATION_ID")
    private Long postLocationId;
    /**
     * 职位标识
     */
    @TableField("POST_ID")
    private Long postId;
    /**
     * 区域标识
     */
    @TableField("REGION_ID")
    private Long regionId;
    /**
     * 生效时间
     */
    @TableField("EFF_DATE")
    private Date effDate;
    /**
     * 失效时间
     */
    @TableField("EXP_DATE")
    private Date expDate;
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
     * 状态时间
     */
    @TableField("STATUS_DATE")
    private Date statusDate;


    public Long getPostLocationId() {
        return postLocationId;
    }

    public void setPostLocationId(Long postLocationId) {
        this.postLocationId = postLocationId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public Date getEffDate() {
        return effDate;
    }

    public void setEffDate(Date effDate) {
        this.effDate = effDate;
    }

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
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
    protected Serializable pkVal() {
        return this.postLocationId;
    }

    @Override
    public String toString() {
        return "TbPostLocation{" +
        ", postLocationId=" + postLocationId +
        ", postId=" + postId +
        ", regionId=" + regionId +
        ", effDate=" + effDate +
        ", expDate=" + expDate +
        ", statusCd=" + statusCd +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        ", statusDate=" + statusDate +
        "}";
    }
}
