package cn.ffcs.interfaces.cpc.pojo;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.*;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;

import java.io.Serializable;

/**
 * <p>
 * 扩展值
 * </p>
 *
 * @author lxd
 * @since 2019-01-10
 */
@TableName("TB_EXPANDOVALUE")
@KeySequence(value = "SEQ_VALUE_ID", clazz = Long.class)
public class Expandovalue extends Model<Expandovalue> {

    private static final long serialVersionUID = 1L;

    /**
     * 值标识
     */
    @TableId(value = "VALUE_ID", type = IdType.INPUT)
    private Long valueId;
    /**
     * 资源标识
     */
    @TableField("RESOURCE_ID")
    private String resourceId;
    /**
     * 系统表标识
     */
    @TableField("TABLE_ID")
    private Long tableId;
    /**
     * 扩展列标识
     */
    @TableField("COLUMN_ID")
    private Long columnId;
    /**
     * 行标识
     */
    @TableField("ROW_ID")
    private Long rowId;
    /**
     * 业务记录标识
     */
    @TableField("RECORD_ID")
    private String recordId;
    /**
     * 列值
     */
    @TableField("DATA_")
    private String data;
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

    public Expandovalue() {
    }

    public Expandovalue(String resourceId, Long tableId, Long columnId, Long rowId, String recordId, String data, String statusCd, Date createDate) {
        this.resourceId = resourceId;
        this.tableId = tableId;
        this.columnId = columnId;
        this.rowId = rowId;
        this.recordId = recordId;
        this.data = data;
        this.statusCd = statusCd;
        this.createDate = createDate;
    }

    public Long getValueId() {
        return valueId;
    }

    public void setValueId(Long valueId) {
        this.valueId = valueId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public Long getColumnId() {
        return columnId;
    }

    public void setColumnId(Long columnId) {
        this.columnId = columnId;
    }

    public Long getRowId() {
        return rowId;
    }

    public void setRowId(Long rowId) {
        this.rowId = rowId;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
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
        return this.valueId;
    }

    @Override
    public String toString() {
        return "Expandovalue{" +
        ", valueId=" + valueId +
        ", resourceId=" + resourceId +
        ", tableId=" + tableId +
        ", columnId=" + columnId +
        ", rowId=" + rowId +
        ", recordId=" + recordId +
        ", data=" + data +
        ", statusCd=" + statusCd +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        ", statusDate=" + statusDate +
        "}";
    }
}
