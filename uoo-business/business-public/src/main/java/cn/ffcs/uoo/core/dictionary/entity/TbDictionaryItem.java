package cn.ffcs.uoo.core.dictionary.entity;

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
@TableName("TB_DICTIONARY_ITEM")
public class TbDictionaryItem extends Model<TbDictionaryItem> {

    private static final long serialVersionUID = 1L;

    /**
     * 字典项标识
     */
    @TableId("ITEM_ID")
    private Long itemId;
    /**
     * 字典标识
     */
    @TableField("DICTIONARY_ID")
    private Long dictionaryId;
    /**
     * 上级字典项标识
     */
    @TableField("PAR_ITEM_ID")
    private Long parItemId;
    /**
     * 字典项值
     */
    @TableField("ITEM_VALUE")
    private String itemValue;
    /**
     * 字典项中文名
     */
    @TableField("ITEM_CNNAME")
    private String itemCnname;
    /**
     * 排序
     */
    @TableField("SORT")
    private Long sort;
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
    protected Serializable pkVal() {
        return this.itemId;
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
