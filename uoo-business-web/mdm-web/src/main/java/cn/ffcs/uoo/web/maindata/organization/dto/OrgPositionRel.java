package cn.ffcs.uoo.web.maindata.organization.dto;

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
 * 岗位，不同组织树具有不同的岗位
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-25
 */
@TableName("TB_ORG_POSITION_REL")
public class OrgPositionRel extends Model<OrgPositionRel> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ORG_POSITION_ID")
    private Long orgPositionId;
    /**
     * 组织标识
     */
    @TableField("ORG_ID")
    private Long orgId;
    @TableField("POSITION_ID")
    private Long positionId;
    /**
     * 组织树标识
     */
    @TableField("ORG_TREE_ID")
    private Long orgTreeId;
    @TableField("EFF_DATE")
    private Date effDate;
    @TableField("EXP_DATE")
    private Date expDate;
    @TableField("STATUS_CD")
    private String statusCd;
    @TableField("CREATE_DATE")
    private Date createDate;
    @TableField("CREATE_USER")
    private Long createUser;
    @TableField("UPDATE_DATE")
    private Date updateDate;
    @TableField("UPDATE_USER")
    private Long updateUser;
    /**
     * 状态变更的时间
     */
    @TableField("STATUS_DATE")
    private Date statusDate;


    public Long getOrgPositionId() {
        return orgPositionId;
    }

    public void setOrgPositionId(Long orgPositionId) {
        this.orgPositionId = orgPositionId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    public Long getOrgTreeId() {
        return orgTreeId;
    }

    public void setOrgTreeId(Long orgTreeId) {
        this.orgTreeId = orgTreeId;
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
        return this.orgPositionId;
    }

    @Override
    public String toString() {
        return "OrgPositionRel{" +
                ", orgPositionId=" + orgPositionId +
                ", orgId=" + orgId +
                ", positionId=" + positionId +
                ", orgTreeId=" + orgTreeId +
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
