package cn.ffcs.uoo.message.server.pojo;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.KeySequence;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("tb_import_log")
@KeySequence(value = "SEQ_TB_IMPORT_LOG_ID", clazz = Long.class)
public class TbImportLog extends Model<TbImportLog> {

    @TableId(value ="import_Log_Id",type = IdType.INPUT)
    private Long importLogId;

    @TableField("import_Id")
    private Long importId;

    @TableField("status_Cd")
    private String statusCd;

    @TableField("fail_Reason")
    private String failReason;

    @Override
    protected Serializable pkVal() {
        return importLogId;
    }
}