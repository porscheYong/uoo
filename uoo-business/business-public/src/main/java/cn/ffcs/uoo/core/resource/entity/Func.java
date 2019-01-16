package cn.ffcs.uoo.core.resource.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
* 功能
 
 * </p>
 *
 * @author zengxsh
 * @since 2019-01-15
 */
@TableName("TB_FUNC")
public class Func extends Model<Func> {

    private static final long serialVersionUID = 1L;

    /**
     * 功能标识，主键
     */
    @TableId("FUNC_ID")
    private Integer funcId;

    /**
     * 菜单标识，主键
     */
    @TableField("MENU_ID")
    private Integer menuId;

    /**
     * 功能名称
     */
    @TableField("FUNC_NAME")
    private String funcName;

    /**
     * 功能编码
     */
    @TableField("FUNC_CODE")
    private String funcCode;

    /**
     * 功能类型。系统维护类型、业务维护功能
     */
    @TableField("FUNC_TYPE")
    private String funcType;

    /**
     * 功能描述
     */
    @TableField("FUNC_DESC")
    private String funcDesc;

    /**
     * 公用管理区域标识,记录区域唯一标识
     */
    @TableField("REGION_ID")
    private Long regionId;

    /**
     * 系统用户的归属系统
     */
    @TableField("SYSTEM_INFO_ID")
    private Long systemInfoId;

    /**
     * 菜单状态
     */
    @TableField("STATUS_CD")
    private String statusCd;

    /**
     * 状态时间
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

    public Integer getFuncId() {
        return funcId;
    }

    public void setFuncId(Integer funcId) {
        this.funcId = funcId;
    }
    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }
    public String getFuncName() {
        return funcName;
    }

    public void setFuncName(String funcName) {
        this.funcName = funcName;
    }
    public String getFuncCode() {
        return funcCode;
    }

    public void setFuncCode(String funcCode) {
        this.funcCode = funcCode;
    }
    public String getFuncType() {
        return funcType;
    }

    public void setFuncType(String funcType) {
        this.funcType = funcType;
    }
    public String getFuncDesc() {
        return funcDesc;
    }

    public void setFuncDesc(String funcDesc) {
        this.funcDesc = funcDesc;
    }
    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }
    public Long getSystemInfoId() {
        return systemInfoId;
    }

    public void setSystemInfoId(Long systemInfoId) {
        this.systemInfoId = systemInfoId;
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
        return this.funcId;
    }

    @Override
    public String toString() {
        return "Func{" +
        "funcId=" + funcId +
        ", menuId=" + menuId +
        ", funcName=" + funcName +
        ", funcCode=" + funcCode +
        ", funcType=" + funcType +
        ", funcDesc=" + funcDesc +
        ", regionId=" + regionId +
        ", systemInfoId=" + systemInfoId +
        ", statusCd=" + statusCd +
        ", statusDate=" + statusDate +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        "}";
    }
}
