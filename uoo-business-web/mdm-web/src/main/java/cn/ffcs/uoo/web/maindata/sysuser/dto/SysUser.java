package cn.ffcs.uoo.web.maindata.sysuser.dto;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.KeySequence;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统域用户
 */
@TableName("SYS_USER")
@KeySequence(value = "SEQ_SYS_USER_ID", clazz = Long.class)
public class SysUser extends Model<SysUser> {

    /** 用户标识    */
    @TableId(value = "USER_ID", type = IdType.INPUT)
    private Long userId;

    /** 账号  */
    @TableField("ACCOUT")
    private String accout;

    /** 密码  */
    @TableField("PASSWD")
    private String passwd;

    /** 盐   */
    @TableField("SALT")
    private String salt;

    /** 姓名  */
    @TableField("UNAME")
    private String uname;

    /** 性别  */
    @TableField("GENDER")
    private String gender;

    /** 编码  */
    @TableField("UCODE")
    private String ucode;

    /** 部门标识    */
    @TableField("DEPT_ID")
    private Long deptId;

    /** 角色标识    */
    @TableField("ROLE_ID")
    private Long roleId;

    /** 岗位标识    */
    @TableField("POSTION_ID")
    private Long postionId;

    /** 生日  */
    @TableField("BIRTHDAY")
    private Date birthday;

    /** 证件类型    */
    @TableField("CERT_TYPE")
    private String certType;

    /** 证件号 */
    @TableField("CERT_ID")
    private String certId;

    /** 手机  */
    @TableField("MOBILE")
    private String mobile;

    /** 邮箱  */
    @TableField("EMAIL")
    private String email;

    /** 家庭住址    */
    @TableField("ADDRESS")
    private String address;

    /** 变更原因    */
    @TableField("REASON")
    private String reason;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAccout() {
        return accout;
    }

    public void setAccout(String accout) {
        this.accout = accout;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUcode() {
        return ucode;
    }

    public void setUcode(String ucode) {
        this.ucode = ucode;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getPostionId() {
        return postionId;
    }

    public void setPostionId(Long postionId) {
        this.postionId = postionId;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getCertType() {
        return certType;
    }

    public void setCertType(String certType) {
        this.certType = certType;
    }

    public String getCertId() {
        return certId;
    }

    public void setCertId(String certId) {
        this.certId = certId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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
        return this.userId;
    }
}