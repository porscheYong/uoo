package cn.ffcs.uoo.core.resource.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 
 * </p>
 *
 * @author zengxsh
 * @since 2019-01-15
 */
@TableName("TB_ELEMENT")
public class Element extends Model<Element> {

    private static final long serialVersionUID = 1L;

    /**
     * 元素标识，主键
     */
    @TableId("ELEMENT_ID")
    private Integer elementId;

    /**
     * 菜单标识，主键
     */
    @TableField("MENU_ID")
    private Integer menuId;

    /**
     * 元素名称
     */
    @TableField("ELEMENT_NAME")
    private String elementName;

    /**
     * 元素类型
     */
    @TableField("ELEMENT_TYPE")
    private String elementType;

    /**
     * 元素描述
     */
    @TableField("ELEMENT_DESC")
    private String elementDesc;

    /**
     * 是否为控件
     */
    @TableField("CONTROL_FLAG")
    private Integer controlFlag;

    /**
     * 元素URL链接地址
     */
    @TableField("URL_ADDR")
    private String urlAddr;

    /**
     * 元素状态
     */
    @TableField("STATUS_CD")
    private String statusCd;

    /**
     * 元素状态修改时间
     */
    @TableField("STATUS_DATE")
    private Date statusDate;

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

    public Integer getElementId() {
        return elementId;
    }

    public void setElementId(Integer elementId) {
        this.elementId = elementId;
    }
    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }
    public String getElementName() {
        return elementName;
    }

    public void setElementName(String elementName) {
        this.elementName = elementName;
    }
    public String getElementType() {
        return elementType;
    }

    public void setElementType(String elementType) {
        this.elementType = elementType;
    }
    public String getElementDesc() {
        return elementDesc;
    }

    public void setElementDesc(String elementDesc) {
        this.elementDesc = elementDesc;
    }
    public Integer getControlFlag() {
        return controlFlag;
    }

    public void setControlFlag(Integer controlFlag) {
        this.controlFlag = controlFlag;
    }
    public String getUrlAddr() {
        return urlAddr;
    }

    public void setUrlAddr(String urlAddr) {
        this.urlAddr = urlAddr;
    }
    public String getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(String statusCd) {
        this.statusCd = statusCd;
    }
    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
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

    @Override
    protected Serializable pkVal() {
        return this.elementId;
    }

    @Override
    public String toString() {
        return "Element{" +
        "elementId=" + elementId +
        ", menuId=" + menuId +
        ", elementName=" + elementName +
        ", elementType=" + elementType +
        ", elementDesc=" + elementDesc +
        ", controlFlag=" + controlFlag +
        ", urlAddr=" + urlAddr +
        ", statusCd=" + statusCd +
        ", statusDate=" + statusDate +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        "}";
    }
}
