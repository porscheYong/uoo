package cn.ffcs.uoo.oa.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * 个性流程表单
 * Created by liuxiaodong on 2018/10/8.
 */
@TableName("ati_specific_form")
public class AtiSpecificForm extends Model<AtiSpecificForm> {

    private static final long serialVersionUID = -3181111158084471875L;

    @TableId("ATI_SPECIFIC_FORM_ID")
    private Long atiSpecificFormId;
    @TableField("ATI_BASE_FORM_ID")
    private Long atiBaseFormId;
    @TableField("ATI_ACT_CATEGORY_ID")
    private String atiActCategoryId;
    @TableField("PARAMETER")
    private String parameter;
    @TableField("PARAM_VALUE")
    private String paramValue;

    public Long getAtiSpecificFormId() {
        return atiSpecificFormId;
    }

    public void setAtiSpecificFormId(Long atiSpecificFormId) {
        this.atiSpecificFormId = atiSpecificFormId;
    }

    public Long getAtiBaseFormId() {
        return atiBaseFormId;
    }

    public void setAtiBaseFormId(Long atiBaseFormId) {
        this.atiBaseFormId = atiBaseFormId;
    }

    public String getAtiActCategoryId() {
        return atiActCategoryId;
    }

    public void setAtiActCategoryId(String atiActCategoryId) {
        this.atiActCategoryId = atiActCategoryId;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    @Override
    protected Serializable pkVal() {
        return this.atiSpecificFormId;
    }
}
