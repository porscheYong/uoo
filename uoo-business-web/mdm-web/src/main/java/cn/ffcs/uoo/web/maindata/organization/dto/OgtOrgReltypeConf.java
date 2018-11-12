package cn.ffcs.uoo.web.maindata.organization.dto;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-22
 */
@TableName("TB_OGT_ORG_RELTYPE_CONF")
public class OgtOrgReltypeConf extends Model<OgtOrgReltypeConf> {

    private static final long serialVersionUID = 1L;

    /**
     * 组织树组织关系类型标识
     */
    @TableId(value = "OGT_ORG_RELTYPE_CONF_ID")
    private Long ogtOrgReltypeConfId;
    /**
     * 关系类型标识
     */
    @TableField("ORG_REL_TYPE_ID")
    private Long orgRelTypeId;
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


    public Long getOgtOrgReltypeConfId() {
        return ogtOrgReltypeConfId;
    }

    public void setOgtOrgReltypeConfId(Long ogtOrgReltypeConfId) {
        this.ogtOrgReltypeConfId = ogtOrgReltypeConfId;
    }

    public Long getOrgRelTypeId() {
        return orgRelTypeId;
    }

    public void setOrgRelTypeId(Long orgRelTypeId) {
        this.orgRelTypeId = orgRelTypeId;
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
        return this.ogtOrgReltypeConfId;
    }

    @Override
    public String toString() {
        return "OgtOrgReltypeConf{" +
        ", ogtOrgReltypeConfId=" + ogtOrgReltypeConfId +
        ", orgRelTypeId=" + orgRelTypeId +
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
