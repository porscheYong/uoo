package cn.ffcs.uoo.system.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 记录系统功能实现中涉及库表的字段信息。
 * </p>
 *
 * @author zengxsh
 * @since 2018-12-21
 */
@TableName("SYS_TABLE_COLUMN")
public class SysTableColumn extends Model<SysTableColumn> {

    private static final long serialVersionUID = 1L;

    /**
     * 记录字段主键
     */
    @TableId("COL_ID")
    private Long colId;
    /**
     * 记录表的主键
     */
    @TableField("TAB_ID")
    private Long tabId;
    /**
     * 记录字段名称
     */
    @TableField("COL_NAME")
    private String colName;
    /**
     * 记录字段类型
     */
    @TableField("COL_TYPE")
    private String colType;
    /**
     * 记录字段描述
     */
    @TableField("COL_DESC")
    private String colDesc;
    /**
     * 记录表字段的长度
     */
    @TableField("COL_LENGHT")
    private Long colLenght;
    /**
     * 记录表字段是否可空
     */
    @TableField("COL_NULLABLE")
    private Integer colNullable;
    /**
     * 记录表字段是否外键
     */
    @TableField("COL_FOREIGN_KEY")
    private Integer colForeignKey;
    /**
     * 记录版本号
     */
    @TableField("VER_NUM")
    private Integer verNum;
    /**
     * 记录状态。
     */
    @TableField("STATUS_CD")
    private String statusCd;
    /**
     * 记录的创建员工
     */
    @TableField("CREATE_STAFF")
    private Long createStaff;
    /**
     * 记录的修改员工
     */
    @TableField("UPDATE_STAFF")
    private Long updateStaff;
    /**
     * 记录的创建时间
     */
    @TableField("CREATE_DATE")
    private Date createDate;
    /**
     * 记录状态修改的时间
     */
    @TableField("STATUS_DATE")
    private Date statusDate;
    /**
     * 记录的修改时间
     */
    @TableField("UPDATE_DATE")
    private Date updateDate;
    /**
     * 记录备注信息。
     */
    @TableField("REMARK")
    private String remark;


    public Long getColId() {
        return colId;
    }

    public void setColId(Long colId) {
        this.colId = colId;
    }

    public Long getTabId() {
        return tabId;
    }

    public void setTabId(Long tabId) {
        this.tabId = tabId;
    }

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public String getColType() {
        return colType;
    }

    public void setColType(String colType) {
        this.colType = colType;
    }

    public String getColDesc() {
        return colDesc;
    }

    public void setColDesc(String colDesc) {
        this.colDesc = colDesc;
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

    public Integer getColForeignKey() {
        return colForeignKey;
    }

    public void setColForeignKey(Integer colForeignKey) {
        this.colForeignKey = colForeignKey;
    }

    public Integer getVerNum() {
        return verNum;
    }

    public void setVerNum(Integer verNum) {
        this.verNum = verNum;
    }

    public String getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(String statusCd) {
        this.statusCd = statusCd;
    }

    public Long getCreateStaff() {
        return createStaff;
    }

    public void setCreateStaff(Long createStaff) {
        this.createStaff = createStaff;
    }

    public Long getUpdateStaff() {
        return updateStaff;
    }

    public void setUpdateStaff(Long updateStaff) {
        this.updateStaff = updateStaff;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    protected Serializable pkVal() {
        return this.colId;
    }

    @Override
    public String toString() {
        return "SysTableColumn{" +
        ", colId=" + colId +
        ", tabId=" + tabId +
        ", colName=" + colName +
        ", colType=" + colType +
        ", colDesc=" + colDesc +
        ", colLenght=" + colLenght +
        ", colNullable=" + colNullable +
        ", colForeignKey=" + colForeignKey +
        ", verNum=" + verNum +
        ", statusCd=" + statusCd +
        ", createStaff=" + createStaff +
        ", updateStaff=" + updateStaff +
        ", createDate=" + createDate +
        ", statusDate=" + statusDate +
        ", updateDate=" + updateDate +
        ", remark=" + remark +
        "}";
    }
}
