package cn.ffcs.interfaces.cpc.pojo;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.*;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;

import java.io.Serializable;

/**
 * <p>
 * 所有业务表都要登记，扩展表需要登记,非扩展表无须定义
 * </p>
 *
 * @author lxd
 * @since 2019-01-10
 */
@TableName("TB_SYSTEMTABLE")
@KeySequence(value = "SEQ_TABLE_ID", clazz = Long.class)
public class Systemtable extends Model<Systemtable> {

    private static final long serialVersionUID = 1L;

    /**
     * 系统表标识
     */
    @TableId(value = "TABLE_ID", type = IdType.INPUT)
    private Long tableId;
    /**
     * 资源标识
     */
    @TableField("RESOURCE_ID")
    private String resourceId;
    /**
     * 系统表名称
     */
    @TableField("TABLE_NAME")
    private String tableName;
    /**
     * 表描述
     */
    @TableField("TABLE_DESC")
    private String tableDesc;
    /**
     * 版本号
     */
    @TableField("VER_NUM")
    private Integer verNum;
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

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableDesc() {
        return tableDesc;
    }

    public void setTableDesc(String tableDesc) {
        this.tableDesc = tableDesc;
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
        return this.tableId;
    }

    @Override
    public String toString() {
        return "Systemtable{" +
        ", tableId=" + tableId +
        ", resourceId=" + resourceId +
        ", tableName=" + tableName +
        ", tableDesc=" + tableDesc +
        ", verNum=" + verNum +
        ", statusCd=" + statusCd +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        ", statusDate=" + statusDate +
        "}";
    }
}
