package cn.ffcs.uoo.system.entity;

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
 * 对部门可选岗位的限定
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-12-20
 */
@TableName("SYS_DEPT_POSITION_REF")
public class SysDeptPositionRef extends Model<SysDeptPositionRef> {

    private static final long serialVersionUID = 1L;

    /**
     * 系统部门可选职位关系标识
     */
    @TableId(value = "DEPT_POSITION_REF_ID", type = IdType.AUTO)
    private Long deptPositionRefId;
    /**
     * 部门编码
     */
    @TableField("ORG_CODE")
    private String orgCode;
    /**
     * 职位编码
     */
    @TableField("POSITION_CODE")
    private String positionCode;
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
    /**
     * 备注
     */
    @TableField("NOTES")
    private String notes;


    public Long getDeptPositionRefId() {
        return deptPositionRefId;
    }

    public void setDeptPositionRefId(Long deptPositionRefId) {
        this.deptPositionRefId = deptPositionRefId;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getPositionCode() {
        return positionCode;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    protected Serializable pkVal() {
        return this.deptPositionRefId;
    }

    @Override
    public String toString() {
        return "SysDeptPositionRef{" +
        ", deptPositionRefId=" + deptPositionRefId +
        ", orgCode=" + orgCode +
        ", positionCode=" + positionCode +
        ", statusCd=" + statusCd +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        ", statusDate=" + statusDate +
        ", notes=" + notes +
        "}";
    }
}
