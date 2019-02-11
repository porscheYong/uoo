package cn.ffcs.uoo.message.server.pojo;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.KeySequence;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import org.apache.ibatis.annotations.SelectKey;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("tb_import")
@KeySequence(value = "SEQ_TB_IMPORT_ID", clazz = Long.class)
public class TbImport extends Model<TbImport> {

    @TableId(value = "import_id",type = IdType.INPUT)
    private Long importId;

    @TableField("import_Type")
    private String importType;

    @TableField("import_Value")
    private String importValue;

    @TableField("batch_Code")
    private String batchCode;

    @TableField("status_Cd")
    private String statusCd;

    @TableField("simple_Data")
    private String simpleData;

    @TableField("fail_Reason")
    private String failReason;

    @TableField("HANDLE_DATE")
    private Date handleDate;

    @Override
    protected Serializable pkVal() {
        return importId;
    }
}