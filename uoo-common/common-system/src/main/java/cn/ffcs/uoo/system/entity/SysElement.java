package cn.ffcs.uoo.system.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 只有需要权限控制的元素才进行登记
 * </p>
 *
 * @author zengxsh
 * @since 2018-12-24
 */
@TableName("SYS_ELEMENT")
public class SysElement extends Model<SysElement> {

    private static final long serialVersionUID = 1L;

    /**
     * 元素标识，主键
     */
    @TableField("ELEMENT_ID")
    private Integer elementId;
    /**
     * 元素名称
     */
    @TableField("ELEMENT_NAME")
    private String elementName;
    @TableField("MENU_CODE")
    private String menuCode;
    @TableField("ELEMENT_CODE")
    private String elementCode;
    /**
     * 元素类型
     */
    @TableField("ELEMENT_TYPE")
    private String elementType;
    @TableField("READONLY_FLAG")
    private Integer readonlyFlag;
    /**
     * 元素URL链接地址，image控件
     */
    @TableField("URL_ADDR")
    private String urlAddr;
    /**
     * 元素描述
     */
    @TableField("ELEMENT_DESC")
    private String elementDesc;
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
     * 状态变更的时间
     */
    @TableField("STATUS_DATE")
    private Date statusDate;


    public Integer getElementId() {
        return elementId;
    }

    public void setElementId(Integer elementId) {
        this.elementId = elementId;
    }

    public String getElementName() {
        return elementName;
    }

    public void setElementName(String elementName) {
        this.elementName = elementName;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public String getElementCode() {
        return elementCode;
    }

    public void setElementCode(String elementCode) {
        this.elementCode = elementCode;
    }

    public String getElementType() {
        return elementType;
    }

    public void setElementType(String elementType) {
        this.elementType = elementType;
    }

    public Integer getReadonlyFlag() {
        return readonlyFlag;
    }

    public void setReadonlyFlag(Integer readonlyFlag) {
        this.readonlyFlag = readonlyFlag;
    }

    public String getUrlAddr() {
        return urlAddr;
    }

    public void setUrlAddr(String urlAddr) {
        this.urlAddr = urlAddr;
    }

    public String getElementDesc() {
        return elementDesc;
    }

    public void setElementDesc(String elementDesc) {
        this.elementDesc = elementDesc;
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
        return null;
    }

    @Override
    public String toString() {
        return "SysElement{" +
        ", elementId=" + elementId +
        ", elementName=" + elementName +
        ", menuCode=" + menuCode +
        ", elementCode=" + elementCode +
        ", elementType=" + elementType +
        ", readonlyFlag=" + readonlyFlag +
        ", urlAddr=" + urlAddr +
        ", elementDesc=" + elementDesc +
        ", statusCd=" + statusCd +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        ", statusDate=" + statusDate +
        "}";
    }
}
