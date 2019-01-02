package cn.ffcs.uoo.web.maindata.common.system.dto;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 定义权限关联的文件，一个权限可包含多个文件。
 * </p>
 *
 * @author zengxsh
 * @since 2018-12-24
 */
@TableName("SYS_PRIV_FILE_REL")
public class SysPrivFileRel extends Model<SysPrivFileRel> {

    private static final long serialVersionUID = 1L;

    /**
     * 权限文件关联标识,主键
     */
    @TableId("PRIV_FILE_ID")
    private Long privFileId;
    /**
     * 权限编码
     */
    @TableField("PERMISSION_CODE")
    private String permissionCode;
    /**
     * 文件标识，主键
     */
    @TableField("FILE_ID")
    private Integer fileId;
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


    public Long getPrivFileId() {
        return privFileId;
    }

    public void setPrivFileId(Long privFileId) {
        this.privFileId = privFileId;
    }

    public String getPermissionCode() {
        return permissionCode;
    }

    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
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
        return this.privFileId;
    }

    @Override
    public String toString() {
        return "SysPrivFileRel{" +
        ", privFileId=" + privFileId +
        ", permissionCode=" + permissionCode +
        ", fileId=" + fileId +
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
