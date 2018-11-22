package cn.ffcs.uoo.core.personnel.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 联系方式
 * </p>
 *
 * @author zhanglu
 * @since 2018-09-13
 */
@TableName("tb_contact")
public class TbContact extends Model<TbContact> {

    private static final long serialVersionUID = 1L;

    /**
     * 联系方式标识
     */
    @TableId("CONTACT_ID")
    private Long contactId;
    /**
     * 人员标识
     */
    @TableField("PERSONNEL_ID")
    private Long personnelId;
    /**
     * 联系类型 邮箱、手机等
     */
    @TableField("CONTACT_TYPE")
    private String contactType;
    /**
     * 联系内容
     */
    @TableField("CONTENT")
    private String content;
    /**
     * UUID
     */
    @TableField("UUID")
    private String uuid;
    /**
     * 是否首选
     */
    @TableField("FIRST_FLAG")
    private String firstFlag;
    /**
     * 状态
     */
    @JsonIgnore
    @TableField(value = "STATUS_CD", fill = FieldFill.INSERT_UPDATE)
    private String statusCd;
    /**
     * 创建时间
     */
    @JsonIgnore
    @TableField(value = "CREATE_DATE", fill = FieldFill.INSERT)
    private Date createDate;
    /**
     * 创建人
     */
    @JsonIgnore
    @TableField(value = "CREATE_USER", fill = FieldFill.INSERT)
    private Long createUser;
    /**
     * 修改时间
     */
    @JsonIgnore
    @TableField(value = "UPDATE_DATE", fill = FieldFill.INSERT_UPDATE)
    private Date updateDate;
    /**
     * 修改人
     */
    @JsonIgnore
    @TableField(value = "UPDATE_USER", fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;
    /**
     * 状态变更的时间
     */
    @JsonIgnore
    @TableField(value = "STATUS_DATE", fill = FieldFill.INSERT_UPDATE)
    private Date statusDate;


    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public Long getPersonnelId() {
        return personnelId;
    }

    public void setPersonnelId(Long personnelId) {
        this.personnelId = personnelId;
    }

    public String getContactType() {
        return contactType;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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
        return this.contactId;
    }

    @Override
    public String toString() {
        return "TbContact{" +
        ", contactId=" + contactId +
        ", personnelId=" + personnelId +
        ", contactType=" + contactType +
        ", content=" + content +
        ", uuid=" + uuid +
        ", statusCd=" + statusCd +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        ", statusDate=" + statusDate +
        "}";
    }
}
