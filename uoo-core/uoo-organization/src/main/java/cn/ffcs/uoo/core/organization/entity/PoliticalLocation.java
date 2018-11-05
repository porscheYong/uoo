package cn.ffcs.uoo.core.organization.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-09-25
 */
@TableName("TB_POLITICAL_LOCATION")
public class PoliticalLocation extends Model<PoliticalLocation> {

    private static final long serialVersionUID = 1L;

    /**
     * 行政区域标识
     */
    @TableId(value = "LOC_ID")
    private Long locId;
    /**
     * 行政区域编码
     */
    @TableField("LOC_CODE")
    private String locCode;
    /**
     * 行政区域名称
     */
    @TableField("LOC_NAME")
    private String locName;
    /**
     * 行政区域描述
     */
    @TableField("LOC_DESC")
    private String locDesc;
    /**
     * 行政区域类型
     */
    @TableField("LOC_TYPE")
    private String locType;
    /**
     * 行政区域简拼
     */
    @TableField("LOC_ABBR")
    private String locAbbr;
    /**
     * 上级行政区域标识
     */
    @TableField("UP_LOC_ID")
    private Long upLocId;
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
    protected Serializable pkVal() {
        return this.locId;
    }

    @Override
    public String toString() {
        return "PoliticalLocation{" +
        ", locId=" + locId +
        ", locCode=" + locCode +
        ", locName=" + locName +
        ", locDesc=" + locDesc +
        ", locType=" + locType +
        ", locAbbr=" + locAbbr +
        ", upLocId=" + upLocId +
        ", statusCd=" + statusCd +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        ", statusDate=" + statusDate +
        "}";
    }
}
