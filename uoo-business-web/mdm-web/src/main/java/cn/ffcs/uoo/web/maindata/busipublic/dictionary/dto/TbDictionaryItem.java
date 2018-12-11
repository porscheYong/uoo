package cn.ffcs.uoo.web.maindata.busipublic.dictionary.dto;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 字典项目
 * </p>
 *
 * @author zhanglu
 * @since 2018-10-22
 */
public class TbDictionaryItem {
    /**
     * 字典项标识
     */
    private Long itemId;
    /**
     * 字典标识
     */
    private Long dictionaryId;
    /**
     * 上级字典项标识
     */
    private Long parItemId;
    /**
     * 字典项值
     */
    private String itemValue;
    /**
     * 字典项中文名
     */
    private String itemCnname;
    /**
     * 排序
     */
    private Long sort;
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


    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getDictionaryId() {
        return dictionaryId;
    }

    public void setDictionaryId(Long dictionaryId) {
        this.dictionaryId = dictionaryId;
    }

    public Long getParItemId() {
        return parItemId;
    }

    public void setParItemId(Long parItemId) {
        this.parItemId = parItemId;
    }

    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }

    public String getItemCnname() {
        return itemCnname;
    }

    public void setItemCnname(String itemCnname) {
        this.itemCnname = itemCnname;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
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
    public String toString() {
        return "TbDictionaryItem{" +
        ", itemId=" + itemId +
        ", dictionaryId=" + dictionaryId +
        ", parItemId=" + parItemId +
        ", itemValue=" + itemValue +
        ", itemCnname=" + itemCnname +
        ", sort=" + sort +
        ", statusCd=" + statusCd +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        ", statusDate=" + statusDate +
        "}";
    }
}
