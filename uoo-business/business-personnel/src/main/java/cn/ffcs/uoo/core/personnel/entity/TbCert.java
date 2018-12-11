package cn.ffcs.uoo.core.personnel.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 证件
 * </p>
 *
 * @author zhanglu
 * @since 2018-09-14
 */
@TableName("tb_cert")
@Data
public class TbCert extends Model<TbCert> {

    private static final long serialVersionUID = 1L;

    /**
     * 证件标识
     */
    @TableId("CERT_ID")
    private Long certId;
    /**
     * 人员标识
     */
    @TableField("PERSONNEL_ID")
    private Long personnelId;
    /**
     * 证件姓名
     */
    @TableField("CERT_NAME")
    private String certName;
    /**
     * 证件类型
     */
    @TableField("CERT_TYPE")
    private String certType;
    /**
     * 证件号码
     */
    @TableField("CERT_NO")
    private String certNo;
    /**
     * 证件地址
     */
    @TableField("ADDRESS")
    private String address;
    /**
     * 颁发机关
     */
    @TableField("ISSUING")
    private String issuing;
    /**
     * UUID
     */
    @TableField("UUID")
    private String uuid;
    /**
     * 是否实名
     */
    @TableField("IS_REAL")
    private String isReal;
    /**
     * 来源
     */
    @TableField("SOURCE")
    private String source;
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
    private BigDecimal createUser;
    /**
     * 修改时间
     */
    @TableField("UPDATE_DATE")
    private Date updateDate;
    /**
     * 修改人
     */
    @TableField("UPDATE_USER")
    private BigDecimal updateUser;
    /**
     * 状态变更的时间
     */
    @TableField("STATUS_DATE")
    private Date statusDate;




    @Override
    protected Serializable pkVal() {
        return this.certId;
    }

    @Override
    public String toString() {
        return "TbCert{" +
        ", certId=" + certId +
        ", personnelId=" + personnelId +
        ", certName=" + certName +
        ", certType=" + certType +
        ", certNo=" + certNo +
        ", address=" + address +
        ", issuing=" + issuing +
        ", uuid=" + uuid +
        ", isReal=" + isReal +
        ", resource=" + source +
        ", statusCd=" + statusCd +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        ", statusDate=" + statusDate +
        "}";
    }
}
