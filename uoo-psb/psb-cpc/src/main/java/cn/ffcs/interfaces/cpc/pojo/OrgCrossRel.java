package cn.ffcs.interfaces.cpc.pojo;

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
 * @author lxd
 * @since 2019-01-10
 */
@TableName("TB_ORG_CROSS_REL")
public class OrgCrossRel extends Model<OrgCrossRel> {

    private static final long serialVersionUID = 1L;

    /**
     * 组织跨域关系标识
     */
    @TableId(value = "ORG_CROSS_REL_ID", type = IdType.AUTO)
    private Long orgCrossRelId;
    /**
     * 组织标识
     */
    @TableField("ORG_ID")
    private Long orgId;
    /**
     * 跨域关系组织
     */
    @TableField("CROSS_TRAN")
    private String crossTran;
    /**
     * 关系类型
     */
    @TableField("RELA_TYPE")
    private String relaType;
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


    public Long getOrgCrossRelId() {
        return orgCrossRelId;
    }

    public void setOrgCrossRelId(Long orgCrossRelId) {
        this.orgCrossRelId = orgCrossRelId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getCrossTran() {
        return crossTran;
    }

    public void setCrossTran(String crossTran) {
        this.crossTran = crossTran;
    }

    public String getRelaType() {
        return relaType;
    }

    public void setRelaType(String relaType) {
        this.relaType = relaType;
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
        return this.orgCrossRelId;
    }

    @Override
    public String toString() {
        return "OrgCrossRel{" +
        ", orgCrossRelId=" + orgCrossRelId +
        ", orgId=" + orgId +
        ", crossTran=" + crossTran +
        ", relaType=" + relaType +
        ", statusCd=" + statusCd +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        ", statusDate=" + statusDate +
        "}";
    }
}
