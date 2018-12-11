package cn.ffcs.uoo.core.permission.entity;

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
@TableName("TB_MEMU")
public class Memu extends Model<Memu> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "PK_MEMU", type = IdType.AUTO)
    private Long pkMemu;
    @TableField("MENU_NAME")
    private String menuName;
    @TableField("MENU_CODE")
    private String menuCode;
    @TableField("MENU_URL")
    private String menuUrl;
    @TableField("STATUS")
    private String status;


    public Long getPkMemu() {
        return pkMemu;
    }

    public void setPkMemu(Long pkMemu) {
        this.pkMemu = pkMemu;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    protected Serializable pkVal() {
        return this.pkMemu;
    }

    @Override
    public String toString() {
        return "Memu{" +
        ", pkMemu=" + pkMemu +
        ", menuName=" + menuName +
        ", menuCode=" + menuCode +
        ", menuUrl=" + menuUrl +
        ", status=" + status +
        "}";
    }
}
