package cn.ffcs.uoo.core.resource.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
* 系统资源表
 
 * </p>
 *
 * @author zengxsh
 * @since 2019-01-15
 */
@TableName("TB_REOURCE")
public class Reource extends Model<Reource> {

    private static final long serialVersionUID = 1L;

    @TableId("RESOURCE_ID")
    private Long resourceId;

    /**
     * 名称
     */
    @TableField("RS_NAME")
    private String rsName;

    /**
     * 描述
     */
    @TableField("RS_DESC")
    private String rsDesc;

    /**
     * 完整连接地址
     */
    @TableField("RS_ADDR")
    private String rsAddr;

    /**
     * 端口
     */
    @TableField("RS_PORT")
    private Double rsPort;

    /**
     * 协议信息，含版本号
     */
    @TableField("CONNECT_PROTOCOL")
    private String connectProtocol;

    /**
     * 长连接、短连接、VPN等等
     */
    @TableField("CONNECT_TYPE")
    private String connectType;

    @TableField("STATUS_CD")
    private String statusCd;

    @TableField("STATUS_DATE")
    private Date statusDate;

    @TableField("CREATE_USER")
    private Long createUser;

    @TableField("CREATE_DATE")
    private Date createDate;

    @TableField("UPDATE_USER")
    private Long updateUser;

    @TableField("UPDATE_DATE")
    private Date updateDate;

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }
    public String getRsName() {
        return rsName;
    }

    public void setRsName(String rsName) {
        this.rsName = rsName;
    }
    public String getRsDesc() {
        return rsDesc;
    }

    public void setRsDesc(String rsDesc) {
        this.rsDesc = rsDesc;
    }
    public String getRsAddr() {
        return rsAddr;
    }

    public void setRsAddr(String rsAddr) {
        this.rsAddr = rsAddr;
    }
    public Double getRsPort() {
        return rsPort;
    }

    public void setRsPort(Double rsPort) {
        this.rsPort = rsPort;
    }
    public String getConnectProtocol() {
        return connectProtocol;
    }

    public void setConnectProtocol(String connectProtocol) {
        this.connectProtocol = connectProtocol;
    }
    public String getConnectType() {
        return connectType;
    }

    public void setConnectType(String connectType) {
        this.connectType = connectType;
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
    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    public Long getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    protected Serializable pkVal() {
        return this.resourceId;
    }

    @Override
    public String toString() {
        return "Reource{" +
        "resourceId=" + resourceId +
        ", rsName=" + rsName +
        ", rsDesc=" + rsDesc +
        ", rsAddr=" + rsAddr +
        ", rsPort=" + rsPort +
        ", connectProtocol=" + connectProtocol +
        ", connectType=" + connectType +
        ", statusCd=" + statusCd +
        ", statusDate=" + statusDate +
        ", createUser=" + createUser +
        ", createDate=" + createDate +
        ", updateUser=" + updateUser +
        ", updateDate=" + updateDate +
        "}";
    }
}
