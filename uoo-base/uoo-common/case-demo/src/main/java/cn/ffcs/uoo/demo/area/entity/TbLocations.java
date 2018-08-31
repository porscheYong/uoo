package cn.ffcs.uoo.demo.area.entity;

import com.baomidou.mybatisplus.enums.IdType;
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
 * @author WCNGS@QQ.COM
 * @since 2018-08-31
 */
@TableName("tb_locations")
public class TbLocations extends Model<TbLocations> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String flag;
    @TableField("local_code")
    private String localCode;
    @TableField("local_name")
    private String localName;
    private Integer lv;
    @TableField("sup_local_code")
    private String supLocalCode;
    private String url;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getLocalCode() {
        return localCode;
    }

    public void setLocalCode(String localCode) {
        this.localCode = localCode;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public Integer getLv() {
        return lv;
    }

    public void setLv(Integer lv) {
        this.lv = lv;
    }

    public String getSupLocalCode() {
        return supLocalCode;
    }

    public void setSupLocalCode(String supLocalCode) {
        this.supLocalCode = supLocalCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TbLocations{" +
        ", id=" + id +
        ", flag=" + flag +
        ", localCode=" + localCode +
        ", localName=" + localName +
        ", lv=" + lv +
        ", supLocalCode=" + supLocalCode +
        ", url=" + url +
        "}";
    }
}
