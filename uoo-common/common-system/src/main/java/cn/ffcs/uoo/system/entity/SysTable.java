package cn.ffcs.uoo.system.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 记录系统功能实现中涉及的表信息。
 * </p>
 *
 * @author zengxsh
 * @since 2018-12-21
 */
@TableName("SYS_TABLE")
public class SysTable extends Model<SysTable> {

    private static final long serialVersionUID = 1L;

    /**
     * 表标识
     */
    @TableId("TAB_ID")
    private Long tabId;
    /**
     * 表的名称
     */
    @TableField("TAB_NAME")
    private String tabName;
    /**
     * 表主键
     */
    @TableField("TAB_PK")
    private Long tabPk;
    /**
     * 备注信息
     */
    @TableField("REMARK")
    private String remark;
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
     * 创建人
     */
    @TableField("CREATE_STAFF")
    private Long createStaff;
    /**
     * 创建人
     */
    @TableField("UPDATE_STAFF")
    private Long updateStaff;
    /**
     * 记录的创建时间
     */
    @TableField("CREATE_DATE")
    private Date createDate;
    /**
     * 状态的修改时间
     */
    @TableField("STATUS_DATE")
    private Date statusDate;
    /**
     * 记录的修改时间
     */
    @TableField("UPDATE_DATE")
    private Date updateDate;


    public Long getTabId() {
        return tabId;
    }

    public void setTabId(Long tabId) {
        this.tabId = tabId;
    }

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    public Long getTabPk() {
        return tabPk;
    }

    public void setTabPk(Long tabPk) {
        this.tabPk = tabPk;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public Long getCreateStaff() {
        return createStaff;
    }

    public void setCreateStaff(Long createStaff) {
        this.createStaff = createStaff;
    }

    public Long getUpdateStaff() {
        return updateStaff;
    }

    public void setUpdateStaff(Long updateStaff) {
        this.updateStaff = updateStaff;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    protected Serializable pkVal() {
        return this.tabId;
    }

    @Override
    public String toString() {
        return "SysTable{" +
        ", tabId=" + tabId +
        ", tabName=" + tabName +
        ", tabPk=" + tabPk +
        ", remark=" + remark +
        ", verNum=" + verNum +
        ", statusCd=" + statusCd +
        ", createStaff=" + createStaff +
        ", updateStaff=" + updateStaff +
        ", createDate=" + createDate +
        ", statusDate=" + statusDate +
        ", updateDate=" + updateDate +
        "}";
    }
}
