package cn.ffcs.uoo.ueccUser.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 *  uecc 用户登录实体类
 * </p>
 *
 * @author liuxiaodong
 * @since 2018-09-27
 */
@TableName("ati_user")
public class AtiUser extends Model<AtiUser> {

    private static final long serialVersionUID = -8014834301880820882L;

    @TableId("ATI_USER_ID")
    private Long atiUserId;
    @TableField("NAME")
    private String name;
    @TableField("IDENTITY_ID")
    private String identityId;
    @TableField("PHONE")
    private String phone;
    @TableField("MAIL")
    private String mail;
    @TableField("NO")
    private String no;
    @TableField("PASSWORD")
    private String password;

    public Long getAtiUserId() {
        return atiUserId;
    }

    public void setAtiUserId(Long atiUserId) {
        this.atiUserId = atiUserId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentityId() {
        return identityId;
    }

    public void setIdentityId(String identityId) {
        this.identityId = identityId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    protected Serializable pkVal() {
        return this.atiUserId;
    }
}
