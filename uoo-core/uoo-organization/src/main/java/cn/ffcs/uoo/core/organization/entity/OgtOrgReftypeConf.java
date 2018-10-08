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
@TableName("TB_OGT_ORG_REFTYPE_CONF")
public class OgtOrgReftypeConf extends Model<OgtOrgReftypeConf> {

    private static final long serialVersionUID = 1L;

    /**
     * 组织树组织关系类型标识
     */
    @TableId(value = "OGT_ORG_REFTYPE_CONF_ID", type = IdType.AUTO)
    private Long ogtOrgReftypeConfId;
    /**
     * 关系类型标识
     */
    @TableField("ORG_REF_TYPE_ID")
    private Long orgRefTypeId;
    /**
     * 组织树标识
     */
    @TableField("ORG_TREE_ID")
    private Long orgTreeId;
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


    public Long getOgtOrgReftypeConfId() {
        return ogtOrgReftypeConfId;
    }

    public void setOgtOrgReftypeConfId(Long ogtOrgReftypeConfId) {
        this.ogtOrgReftypeConfId = ogtOrgReftypeConfId;
    }

    public Long getOrgRefTypeId() {
        return orgRefTypeId;
    }

    public void setOrgRefTypeId(Long orgRefTypeId) {
        this.orgRefTypeId = orgRefTypeId;
    }

    public Long getOrgTreeId() {
        return orgTreeId;
    }

    public void setOrgTreeId(Long orgTreeId) {
        this.orgTreeId = orgTreeId;
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
        return this.ogtOrgReftypeConfId;
    }

    @Override
    public String toString() {
        return "OgtOrgReftypeConf{" +
        ", ogtOrgReftypeConfId=" + ogtOrgReftypeConfId +
        ", orgRefTypeId=" + orgRefTypeId +
        ", orgTreeId=" + orgTreeId +
        ", statusCd=" + statusCd +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        ", statusDate=" + statusDate +
        "}";
    }
}
