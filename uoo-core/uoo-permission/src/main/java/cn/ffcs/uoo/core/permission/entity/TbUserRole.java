package cn.ffcs.uoo.core.permission.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 描述系统用户与角色之间的对应关系，是多对多关系。一个系统用户除了拥有系统岗位所带的角色和权限，也可以拥有多个私有的角色，一个角色可以分配给多个系统用户。
 * </p>
 *
 * @author zhanglu
 * @since 2018-10-24
 */
@TableName("TB_USER_ROLE")
public class TbUserRole extends Model<TbUserRole> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户角色标识
     */
    @TableId("USER_ROLE_ID")
    private Long userRoleId;
    /**
     * 用户标识
     */
    @TableField("USER_ID")
    private String userId;
    /**
     * 角色标识
     */
    @TableField("ROLE_ID")
    private Long roleId;
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
    @TableField("CREATE_STAFF")
    private Long createStaff;
    /**
     * 修改时间
     */
    @TableField("UPDATE_DATE")
    private Date updateDate;
    /**
     * 修改人
     */
    @TableField("UPDATE_STAFF")
    private Long updateStaff;


    public Long getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Long userRoleId) {
        this.userRoleId = userRoleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
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

    public Long getCreateStaff() {
        return createStaff;
    }

    public void setCreateStaff(Long createStaff) {
        this.createStaff = createStaff;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Long getUpdateStaff() {
        return updateStaff;
    }

    public void setUpdateStaff(Long updateStaff) {
        this.updateStaff = updateStaff;
    }

    @Override
    protected Serializable pkVal() {
        return this.userRoleId;
    }

    @Override
    public String toString() {
        return "TbUserRole{" +
        ", userRoleId=" + userRoleId +
        ", userId=" + userId +
        ", roleId=" + roleId +
        ", effDate=" + effDate +
        ", expDate=" + expDate +
        ", statusCd=" + statusCd +
        ", statusDate=" + statusDate +
        ", createDate=" + createDate +
        ", createStaff=" + createStaff +
        ", updateDate=" + updateDate +
        ", updateStaff=" + updateStaff +
        "}";
    }
}
