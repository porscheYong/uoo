package cn.ffcs.uoo.system.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统域部门
 */
@TableName("SYS_DEPT")
public class SysDept extends Model<SysDept> {

    /** 系统部门标识  */
    @TableId(value = "DEPT_ID")
    private Long deptId;

    /** 部门名称    */
    @TableField("DEPT_NAME")
    private String deptName;

    /** 编码  */
    @TableField("DEPT_CODE")
    private String deptCode;

    /** 上级部门    */
    @TableField("P_DEPT_ID")
    private Long pDeptId;

    /** 上级部门2   */
    @TableField("P_DEPT_IDS")
    private String pDeptIds;

    /** 简称  */
    @TableField("SIMPLE_NAME")
    private String simpleName;

    /** 全称  */
    @TableField("FULL_NAME")
    private String fullName;

    /** 状态  */
    @TableField("STATUS_CD")
    private String statusCd;

    /** 创建时间  */
    @TableField("CREATE_DATE")
    private Date createDate;

    /** 创建人    */
    @TableField("CREATE_USER")
    private Long createUser;

    /** 修改时间 */
    @TableField("UPDATE_DATE")
    private Date updateDate;

    /** 修改人 */
    @TableField("UPDATE_USER")
    private Long updateUser;

    /** 状态时间    */
    @TableField("STATUS_DATE")
    private Date statusDate;

    /** 备注  */
    @TableField("NOTES")
    private String notes;

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public Long getpDeptId() {
        return pDeptId;
    }

    public void setpDeptId(Long pDeptId) {
        this.pDeptId = pDeptId;
    }

    public String getpDeptIds() {
        return pDeptIds;
    }

    public void setpDeptIds(String pDeptIds) {
        this.pDeptIds = pDeptIds;
    }

    public String getSimpleName() {
        return simpleName;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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
        return this.deptId;
    }
}