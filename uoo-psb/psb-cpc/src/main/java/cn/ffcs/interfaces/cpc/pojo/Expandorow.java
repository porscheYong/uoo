package cn.ffcs.interfaces.cpc.pojo;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.*;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;

import java.io.Serializable;

/**
 * <p>
 * 保留，如通讯号码就对应多个
 * </p>
 *
 * @author lxd
 * @since 2019-01-10
 */
@TableName("TB_EXPANDOROW")
@KeySequence(value = "SEQ_ROW_ID", clazz = Long.class)
public class Expandorow extends Model<Expandorow> {

    private static final long serialVersionUID = 1L;

    /**
     * 行标识
     */
    @TableId(value = "ROW_ID", type = IdType.INPUT)
    private Long rowId;
    /**
     * 系统表标识
     */
    @TableField("TABLE_ID")
    private Long tableId;
    /**
     * 资源标识
     */
    @TableField("RESOURCE_ID")
    private String resourceId;
    /**
     * 业务记录标识
     */
    @TableField("RECORD_ID")
    private String recordId;
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
     * 状态时间
     */
    @TableField("STATUS_DATE")
    private Date statusDate;

    public Expandorow() {
    }

    public Expandorow(Long tableId, String resourceId, String recordId, String statusCd, Date createDate) {
        this.tableId = tableId;
        this.resourceId = resourceId;
        this.recordId = recordId;
        this.statusCd = statusCd;
        this.createDate = createDate;
    }

    public Long getRowId() {
        return rowId;
    }

    public void setRowId(Long rowId) {
        this.rowId = rowId;
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
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
        return this.rowId;
    }

    @Override
    public String toString() {
        return "Expandorow{" +
        ", rowId=" + rowId +
        ", tableId=" + tableId +
        ", resourceId=" + resourceId +
        ", recordId=" + recordId +
        ", statusCd=" + statusCd +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        ", statusDate=" + statusDate +
        "}";
    }
}
