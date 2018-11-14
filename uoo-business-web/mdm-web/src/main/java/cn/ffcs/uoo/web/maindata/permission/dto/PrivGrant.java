package cn.ffcs.uoo.web.maindata.permission.dto;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 描述将权限授权给不同的对象，包括系统用户、系统岗位、角色；
一个系统用户也可以拥有多个私有的权限，一个权限可以分配给多个系统用户。
一个系统岗位除了拥有角色所带的权限，也可以拥有私有的多个权限，一个权限可以分配给多个系统岗位。
一个角色可以包含多个权限，一个权限也可以分配给多个角色。
 * </p>
 *
 * @author zengxsh
 * @since 2018-11-08
 */
@TableName("TB_PRIV_GRANT")
public class PrivGrant extends Model<PrivGrant> {

    private static final long serialVersionUID = 1L;

    /**
     * 授权标识，主键
     */
    @TableId("PRIV_GRANT_ID")
    private Long privGrantId;
    /**
     * 权限标识
     */
    @TableField("PRIV_ID")
    private Long privId;
    /**
     * 授权对象类型。LOVB=STF-C-0016。
     */
    @TableField("GRANT_OBJ_TYPE")
    private String grantObjType;
    /**
     * 授权对象标识，根据授权对象类型，区分是系统用户ID、系统岗位ID、角色ID
     */
    @TableField("GRANT_OBJ_ID")
    private Long grantObjId;
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


    public Long getPrivGrantId() {
        return privGrantId;
    }

    public void setPrivGrantId(Long privGrantId) {
        this.privGrantId = privGrantId;
    }

    public Long getPrivId() {
        return privId;
    }

    public void setPrivId(Long privId) {
        this.privId = privId;
    }

    public String getGrantObjType() {
        return grantObjType;
    }

    public void setGrantObjType(String grantObjType) {
        this.grantObjType = grantObjType;
    }

    public Long getGrantObjId() {
        return grantObjId;
    }

    public void setGrantObjId(Long grantObjId) {
        this.grantObjId = grantObjId;
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
        return this.privGrantId;
    }

    @Override
    public String toString() {
        return "PrivGrant{" +
        ", privGrantId=" + privGrantId +
        ", privId=" + privId +
        ", grantObjType=" + grantObjType +
        ", grantObjId=" + grantObjId +
        ", effDate=" + effDate +
        ", expDate=" + expDate +
        ", statusCd=" + statusCd +
        ", statusDate=" + statusDate +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        "}";
    }
}
