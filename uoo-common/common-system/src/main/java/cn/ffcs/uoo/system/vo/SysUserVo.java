package cn.ffcs.uoo.system.vo;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;


public class SysUserVo{

    private static final long serialVersionUID = 1L;

    /**
     * 用户标识
     */
    private Long userId;
    /**
     * 用户登录账号
     */
    private String accout;
    /**
     * 用户密码
     */
    private String passwd;
    /**
     * 密码加密的盐
     */
    private String salt;
    /**
     * 用户姓名
     */
    private String userName;
    /**
     * 性别
     */
    private String gender;
    /**
     * 人员编码
     */
    private String userCode;
    /**
     * 出生年月
     */
    private Date birthday;
    /**
     * 用户持有的证件类别
     */
    private String certType;
    /**
     * 用户持有的证件号码
     */
    private String certId;
    /**
     * 联系号码
     */
    private String mobile;
    /**
     * 邮件地址
     */
    private String email;
    /**
     * 头像图片路径
     */
    private String imageUrl;
    /**
     * 登录ip
     */
    private String lastIp;
    /**
     * 账号等级
     */
    private String acctLevel;
    /**
     * 账号是否被锁定
     */
    private Integer locked;
    /**
     * 最后一次登录时间
     */
    private Date lastLogin;
    /**
     * 状态
     */
    private String statusCd;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 创建人
     */
    private Long createUser;
    /**
     * 修改时间
     */
    private Date updateDate;
    /**
     * 修改人
     */
    private Long updateUser;
    /**
     * 状态变更的时间
     */
    private Date statusDate;

    private Long orgId;
    private String orgCode;
    private String parentOrgCode;
    private String orgName;
    private String userPositions;


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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLastIp() {
        return lastIp;
    }

    public void setLastIp(String lastIp) {
        this.lastIp = lastIp;
    }

    public String getAcctLevel() {
        return acctLevel;
    }

    public void setAcctLevel(String acctLevel) {
        this.acctLevel = acctLevel;
    }

    public Integer getLocked() {
        return locked;
    }

    public void setLocked(Integer locked) {
        this.locked = locked;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
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


}
