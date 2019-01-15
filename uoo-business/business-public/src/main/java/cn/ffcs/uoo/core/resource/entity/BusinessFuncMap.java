package cn.ffcs.uoo.core.resource.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 
 * </p>
 *
 * @author zengxsh
 * @since 2019-01-15
 */
@TableName("TB_BUSINESS_FUNC_MAP")
public class BusinessFuncMap extends Model<BusinessFuncMap> {

    private static final long serialVersionUID = 1L;

    /**
     * 功能标识，主键
     */
    @TableId("MAP_ID")
    private Integer mapId;

    /**
     * 功能标识，主键
     */
    @TableField("BIZ_FUNC_ID")
    private Integer bizFuncId;

    /**
     * 授权对象类型,菜单、元素、功能
     */
    @TableField("MAP_OBJ_TYPE")
    private String mapObjType;

    /**
     * 授权对象标识，根据授权对象类型，区分是菜单ID、元素ID、功能ID
     */
    @TableField("MAP_OBJ_ID")
    private Long mapObjId;

    /**
     * 菜单状态
     */
    @TableField("STATUS_CD")
    private String statusCd;

    /**
     * 状态时间
     */
    @TableField("STATUS_DATE")
    private Date statusDate;

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

    public Integer getMapId() {
        return mapId;
    }

    public void setMapId(Integer mapId) {
        this.mapId = mapId;
    }
    public Integer getBizFuncId() {
        return bizFuncId;
    }

    public void setBizFuncId(Integer bizFuncId) {
        this.bizFuncId = bizFuncId;
    }
    public String getMapObjType() {
        return mapObjType;
    }

    public void setMapObjType(String mapObjType) {
        this.mapObjType = mapObjType;
    }
    public Long getMapObjId() {
        return mapObjId;
    }

    public void setMapObjId(Long mapObjId) {
        this.mapObjId = mapObjId;
    }
    public String getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(String statusCd) {
        this.statusCd = statusCd;
    }
    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
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

    @Override
    protected Serializable pkVal() {
        return this.mapId;
    }

    @Override
    public String toString() {
        return "BusinessFuncMap{" +
        "mapId=" + mapId +
        ", bizFuncId=" + bizFuncId +
        ", mapObjType=" + mapObjType +
        ", mapObjId=" + mapObjId +
        ", statusCd=" + statusCd +
        ", statusDate=" + statusDate +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        "}";
    }
}
