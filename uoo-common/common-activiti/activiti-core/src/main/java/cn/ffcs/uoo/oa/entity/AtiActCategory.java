package cn.ffcs.uoo.oa.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 流程分类实体类
 * </p>
 *
 * @author liuxiaodong
 * @since 2018-09-27
 */
@TableName("ati_act_category")
public class AtiActCategory extends Model<AtiActCategory> {

    private static final long serialVersionUID = 1L;

    @TableId("ATI_ACT_CATEGORY_ID")
    private Long atiActCategoryId;
    @TableField("PARENT_CATEGORY_ID")
    private Long parentCategoryId;
    @TableField("CATEGORY_CODE")
    private String categoryCode;
    @TableField("NAME")
    private String name;


    public Long getAtiActCategoryId() {
        return atiActCategoryId;
    }

    public void setAtiActCategoryId(Long atiActCategoryId) {
        this.atiActCategoryId = atiActCategoryId;
    }

    public Long getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(Long parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getName() {
        return name;
    }

    public void setCategoryName(String categoryName) {
        this.name = name;
    }

    @Override
    protected Serializable pkVal() {
        return this.atiActCategoryId;
    }

    @Override
    public String toString() {
        return "AtiActCategory{" +
        ", atiActCategoryId=" + atiActCategoryId +
        ", parentCategoryId=" + parentCategoryId +
        ", categoryCode=" + categoryCode +
        ", name=" + name +
        "}";
    }
}
