package cn.ffcs.interfaces.cpc.pojo;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.*;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;

import java.io.Serializable;

/**
 * <p>
 * 扩展列
 * </p>
 *
 * @author lxd
 * @since 2019-01-10
 */
@TableName("TB_EXPANDOCOLUMN")
@KeySequence(value = "SEQ_COLUMN_ID", clazz = Long.class)
public class Expandocolumn extends Model<Expandocolumn> {

    private static final long serialVersionUID = 1L;

    /**
     * 扩展列标识
     */
    @TableId(value = "COLUMN_ID", type = IdType.INPUT)
    private Long columnId;
    /**
     * 系统表标识
     */
    @TableField("TABLE_ID")
    private Long tableId;
    /**
     * 列名
     */
    @TableField("COLUMN_NAME")
    private String columnName;
    /**
     * 中文名
     */
    @TableField("COLUMN_CNNAME")
    private String columnCnname;
    /**
     * 字段类型
     */
    @TableField("COLUM_TYPE")
    private String columType;
    /**
     * 字段长度
     */
    @TableField("COL_LENGHT")
    private Long colLenght;
    /**
     * 字段是否可以空
     */
    @TableField("COL_NULLABLE")
    private Integer colNullable;
    /**
     * 字段描述
     */
    @TableField("COL_DESC")
    private String colDesc;
    /**
     * 版本号
     */
    @TableField("VER_NUM")
    private Integer verNum;
    /**
     * 默认值
     */
    @TableField("DEFAULT_VALUE")
    private String defaultValue;
    /**
     * 排序
     */
    @TableField("SORT")
    private Long sort;
    /**
     * 可否编辑
     */
    @TableField("EDITABLE")
    private String editable;
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
     * 页面标签
     */
    @TableField("LABEL")
    private String label;
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


    public Long getColumnId() {
        return columnId;
    }

    public void setColumnId(Long columnId) {
        this.columnId = columnId;
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnCnname() {
        return columnCnname;
    }

    public void setColumnCnname(String columnCnname) {
        this.columnCnname = columnCnname;
    }

    public String getColumType() {
        return columType;
    }

    public void setColumType(String columType) {
        this.columType = columType;
    }

    public Long getColLenght() {
        return colLenght;
    }

    public void setColLenght(Long colLenght) {
        this.colLenght = colLenght;
    }

    public Integer getColNullable() {
        return colNullable;
    }

    public void setColNullable(Integer colNullable) {
        this.colNullable = colNullable;
    }

    public String getColDesc() {
        return colDesc;
    }

    public void setColDesc(String colDesc) {
        this.colDesc = colDesc;
    }

    public Integer getVerNum() {
        return verNum;
    }

    public void setVerNum(Integer verNum) {
        this.verNum = verNum;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public String getEditable() {
        return editable;
    }

    public void setEditable(String editable) {
        this.editable = editable;
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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
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
        return this.columnId;
    }

    @Override
    public String toString() {
        return "Expandocolumn{" +
        ", columnId=" + columnId +
        ", tableId=" + tableId +
        ", columnName=" + columnName +
        ", columnCnname=" + columnCnname +
        ", columType=" + columType +
        ", colLenght=" + colLenght +
        ", colNullable=" + colNullable +
        ", colDesc=" + colDesc +
        ", verNum=" + verNum +
        ", defaultValue=" + defaultValue +
        ", sort=" + sort +
        ", editable=" + editable +
        ", statusCd=" + statusCd +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", label=" + label +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        ", statusDate=" + statusDate +
        "}";
    }
}
