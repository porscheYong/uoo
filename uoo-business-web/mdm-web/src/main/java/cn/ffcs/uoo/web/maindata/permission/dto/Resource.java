package cn.ffcs.uoo.web.maindata.permission.dto;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * <p>
 * 
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-30
 */
@TableName("TB_RESOURCE")
public class Resource extends Model<Resource> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "PK_RESOURCE", type = IdType.AUTO)
    private Long pkResource;
    @TableField("ACCOUT")
    private String accout;
    @TableField("PASSWORD")
    private String password;
    @TableField("CONNECT_TYPE")
    private String connectType;
    @TableField("SYS_VERSION")
    private String sysVersion;
    @TableField("HOST")
    private String host;
    @TableField("PORT")
    private Double port;


    public Long getPkResource() {
        return pkResource;
    }

    public void setPkResource(Long pkResource) {
        this.pkResource = pkResource;
    }

    public String getAccout() {
        return accout;
    }

    public void setAccout(String accout) {
        this.accout = accout;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConnectType() {
        return connectType;
    }

    public void setConnectType(String connectType) {
        this.connectType = connectType;
    }

    public String getSysVersion() {
        return sysVersion;
    }

    public void setSysVersion(String sysVersion) {
        this.sysVersion = sysVersion;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Double getPort() {
        return port;
    }

    public void setPort(Double port) {
        this.port = port;
    }

    @Override
    protected Serializable pkVal() {
        return this.pkResource;
    }

    @Override
    public String toString() {
        return "Resource{" +
        ", pkResource=" + pkResource +
        ", accout=" + accout +
        ", password=" + password +
        ", connectType=" + connectType +
        ", sysVersion=" + sysVersion +
        ", host=" + host +
        ", port=" + port +
        "}";
    }
}
