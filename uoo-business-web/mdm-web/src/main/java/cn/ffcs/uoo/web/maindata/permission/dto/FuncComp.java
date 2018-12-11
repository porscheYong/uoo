package cn.ffcs.uoo.web.maindata.permission.dto;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 指系统内的系统功能菜单的最小功能单元及组件。
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-30
 */
@TableName("TB_FUNC_COMP")
public class FuncComp extends Model<FuncComp> {

    private static final long serialVersionUID = 1L;

    /**
     * 模块组件标识，主键
     */
    @TableId(value = "COMP_ID")
    private Long compId;
    /**
     * 模块组件名称
     */
    @TableField("COMP_NAME")
    private String compName;
    /**
     * 模块组件类型，LOVB=STF-C-0014。
     */
    @TableField("COMP_TYPE")
    private String compType;
    /**
     * 模块组件描述
     */
    @TableField("COMP_DESC")
    private String compDesc;
    /**
     * 模块组件URL链接地址
     */
    @TableField("URL_ADDR")
    private String urlAddr;
    /**
     * 菜单标识，主键
     */
    @TableField("MENU_ID")
    private Long menuId;
    /**
     * 模块组件状态
     */
    @TableField("STATUS_CD")
    private String statusCd;
    /**
     * 模块组件状态修改时间
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


    public Long getCompId() {
        return compId;
    }

    public void setCompId(Long compId) {
        this.compId = compId;
    }

    public String getCompName() {
        return compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }

    public String getCompType() {
        return compType;
    }

    public void setCompType(String compType) {
        this.compType = compType;
    }

    public String getCompDesc() {
        return compDesc;
    }

    public void setCompDesc(String compDesc) {
        this.compDesc = compDesc;
    }

    public String getUrlAddr() {
        return urlAddr;
    }

    public void setUrlAddr(String urlAddr) {
        this.urlAddr = urlAddr;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
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
        return this.compId;
    }

    @Override
    public String toString() {
        return "FuncComp{" +
        ", compId=" + compId +
        ", compName=" + compName +
        ", compType=" + compType +
        ", compDesc=" + compDesc +
        ", urlAddr=" + urlAddr +
        ", menuId=" + menuId +
        ", statusCd=" + statusCd +
        ", statusDate=" + statusDate +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        "}";
    }
}
