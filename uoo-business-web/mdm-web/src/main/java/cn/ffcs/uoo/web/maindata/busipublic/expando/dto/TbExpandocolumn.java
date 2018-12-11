package cn.ffcs.uoo.web.maindata.busipublic.expando.dto;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 扩展列
 * </p>
 *
 * @author zhanglu
 * @since 2018-10-25
 */
public class TbExpandocolumn {
    /**
     * 扩展列标识
     */
    private Long columnId;
    /**
     * 系统表标识
     */
    private Long tableId;
    /**
     * 列名
     */
    private String columnName;
    /**
     * 中文名
     */
    private String columnCnname;
    /**
     * 字段类型
     */
    private String columType;
    /**
     * 字段长度
     */
    private Long colLenght;
    /**
     * 字段是否可以空
     */
    private Integer colNullable;
    /**
     * 字段描述
     */
    private String colDesc;
    /**
     * 版本号
     */
    private Integer verNum;
    /**
     * 默认值
     */
    private String defaultValue;
    /**
     * 排序
     */
    private Long sort;
    /**
     * 可否编辑
     */
    private String editable;
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
     * 页面标签
     */
    private String label;
    /**
     * 修改时间
     */
    private Date updateDate;
    /**
     * 修改人
     */
    private Long updateUser;
    /**
     * 状态时间
     */
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
    public String toString() {
        return "TbExpandocolumn{" +
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
