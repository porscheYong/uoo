package cn.ffcs.uoo.system.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统域字典
 */
@TableName("SYS_DICT")
public class SysDict extends Model<SysDict> {

    /** 字典标识  */
    @TableId(value = "DICT_ID")
    private Long dictId;

    /** 字典名称    */
    @TableField("DICT_NAME")
    private String dictName;

    /** 字段编码    */
    @TableField("DICT_CODE")
    private String dictCode;

    /** 字典值 */
    @TableField("VALUE")
    private String value;

    /** 上级节点    */
    @TableField("P_DICT_ID")
    private Long pDictId;

    /** 提示  */
    @TableField("TIPS")
    private String tips;

    /** 状态  */
    @TableField("STATUS_CD")
    private String statusCd;

    /** 创建时间  */
    @TableField("CREATE_DATE")
    private Date createDate;

    /** 创建人    */
    @TableField("CREATE_USER")
    private Long createUser;

    /** 修改时间 */
    @TableField("UPDATE_DATE")
    private Date updateDate;

    /** 修改人 */
    @TableField("UPDATE_USER")
    private Long updateUser;

    /** 状态时间    */
    @TableField("STATUS_DATE")
    private Date statusDate;

    /** 备注  */
    @TableField("NOTES")
    private String notes;

    public Long getDictId() {
        return dictId;
    }

    public void setDictId(Long dictId) {
        this.dictId = dictId;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public String getDictCode() {
        return dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getpDictId() {
        return pDictId;
    }

    public void setpDictId(Long pDictId) {
        this.pDictId = pDictId;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    protected Serializable pkVal() {
        return this.dictId;
    }
}