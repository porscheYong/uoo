package cn.ffcs.uoo.core.dictionary.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 字典定义
 * </p>
 *
 * @author zhanglu
 * @since 2018-10-22
 */
@TableName("TB_DICTIONARY")
public class TbDictionary extends Model<TbDictionary> {

    private static final long serialVersionUID = 1L;

    /**
     * 字典标识
     */
    @TableId("DICTIONARY_ID")
    private Long dictionaryId;
    /**
     * 列名
     */
    @TableField("DICTIONARY_NAME")
    private String dictionaryName;
    /**
     * 中文名
     */
    @TableField("DICTIONARY_CNNAME")
    private String dictionaryCnname;
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


    public Long getDictionaryId() {
        return dictionaryId;
    }

    public void setDictionaryId(Long dictionaryId) {
        this.dictionaryId = dictionaryId;
    }

    public String getDictionaryName() {
        return dictionaryName;
    }

    public void setDictionaryName(String dictionaryName) {
        this.dictionaryName = dictionaryName;
    }

    public String getDictionaryCnname() {
        return dictionaryCnname;
    }

    public void setDictionaryCnname(String dictionaryCnname) {
        this.dictionaryCnname = dictionaryCnname;
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
        return this.dictionaryId;
    }

    @Override
    public String toString() {
        return "TbDictionary{" +
        ", dictionaryId=" + dictionaryId +
        ", dictionaryName=" + dictionaryName +
        ", dictionaryCnname=" + dictionaryCnname +
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
