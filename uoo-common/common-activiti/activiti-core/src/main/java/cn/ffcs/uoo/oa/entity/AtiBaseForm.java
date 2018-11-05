package cn.ffcs.uoo.oa.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * 流程通用表单
 * Created by liuxiaodong on 2018/10/8.
 */
@TableName("ati_base_form")
public class AtiBaseForm extends Model<AtiBaseForm> {

    private static final long serialVersionUID = -807674530144320867L;

    @TableId("ATI_BASE_FORM_ID")
    private Long atiBaseFormId;
    @TableField("FORM_SENDER")
    private String formSender;
    @TableField("FORM_THEME")
    private String formTheme;
    @TableField("FORM_CONTENT")
    private String formContent;
    @TableField("PROC_DEF_ID")
    private String procDefId;
    @TableField("PROC_INST_ID")
    private String procInstId;
    @TableField("URGENT")
    private String urgent;
    @TableField("LEVEL")
    private String level;

    public Long getAtiBaseFormId() {
        return atiBaseFormId;
    }

    public void setAtiBaseFormId(Long atiBaseFormId) {
        this.atiBaseFormId = atiBaseFormId;
    }

    public String getFormSender() {
        return formSender;
    }

    public void setFormSender(String formSender) {
        this.formSender = formSender;
    }

    public String getFormTheme() {
        return formTheme;
    }

    public void setFormTheme(String formTheme) {
        this.formTheme = formTheme;
    }

    public String getFormContent() {
        return formContent;
    }

    public void setFormContent(String formContent) {
        this.formContent = formContent;
    }

    public String getProcDefId() {
        return procDefId;
    }

    public void setProcDefId(String procDefId) {
        this.procDefId = procDefId;
    }

    public String getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }

    public String getUrgent() {
        return urgent;
    }

    public void setUrgent(String urgent) {
        this.urgent = urgent;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    protected Serializable pkVal() {
        return this.atiBaseFormId;
    }

}
