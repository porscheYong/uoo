package cn.ffcs.interfaces.cpc.pojo;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.KeySequence;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.math.BigDecimal;

@TableName("tb_cpc_log")
@KeySequence(value = "SEQ_TB_CPC_LOG_ID", clazz = BigDecimal.class)
public class TbCpcLogWithBLOBs  extends Model<TbCpcLogWithBLOBs> {

    @TableId(value="CPC_LOG_ID",type = IdType.INPUT)
    private BigDecimal cpcLogId;

    @TableField("status_Cd")
    private String statusCd;

    @TableField("receive")
    private String receive;

    @TableField("revert")
    private String revert;

    public BigDecimal getCpcLogId() {
        return cpcLogId;
    }

    public void setCpcLogId(BigDecimal cpcLogId) {
        this.cpcLogId = cpcLogId;
    }

    public String getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(String statusCd) {
        this.statusCd = statusCd;
    }

    public String getReceive() {
        return receive;
    }

    public void setReceive(String receive) {
        this.receive = receive;
    }

    public String getRevert() {
        return revert;
    }

    public void setRevert(String revert) {
        this.revert = revert;
    }

    @Override
    protected Serializable pkVal() {
        return cpcLogId;
    }
}