package cn.ffcs.uoo.web.maindata.busipublic.dictionary.dto;

import java.util.Date;

/**
 * <p>
 * 字典定义
 * </p>
 *
 * @author zhanglu
 * @since 2018-10-22
 */
public class TbDictionary {
    /**
     * 字典标识
     */
    private Long dictionaryId;
    /**
     * 列名
     */
    private String dictionaryName;
    /**
     * 中文名
     */
    private String dictionaryCnname;
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
