package cn.ffcs.uoo.leaveDemo.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhanglu
 * @since 2018-09-27
 */
@TableName("ati_category")
public class AtiCategory extends Model<AtiCategory> {

    private static final long serialVersionUID = 1L;

    @TableId("ATI_CATEGORY_ID")
    private BigDecimal atiCategoryId;
    @TableField("PARENT_CATEGORY_ID")
    private BigDecimal parentCategoryId;
    @TableField("CATEGORY_CODE")
    private String categoryCode;
    @TableField("CATEGORY_NAME")
    private String categoryName;


    public BigDecimal getAtiCategoryId() {
        return atiCategoryId;
    }

    public void setAtiCategoryId(BigDecimal atiCategoryId) {
        this.atiCategoryId = atiCategoryId;
    }

    public BigDecimal getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(BigDecimal parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    protected Serializable pkVal() {
        return this.atiCategoryId;
    }

    @Override
    public String toString() {
        return "AtiCategory{" +
        ", atiCategoryId=" + atiCategoryId +
        ", parentCategoryId=" + parentCategoryId +
        ", categoryCode=" + categoryCode +
        ", categoryName=" + categoryName +
        "}";
    }
}
