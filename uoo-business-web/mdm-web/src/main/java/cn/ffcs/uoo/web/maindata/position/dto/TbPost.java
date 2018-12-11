package cn.ffcs.uoo.web.maindata.position.dto;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.KeySequence;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 职位表
 * </p>
 *
 * @author zhanglu
 * @since 2018-10-30
 */
public class TbPost {
    /**
     * 职位标识
     */
    private Long postId;
    /**
     * 上级职位关系ID
     */
    private Long parentPostId;
    /**
     * 职位名称
     */
    private String postName;
    /**
     * 职位类型
     */
    private String postType;
    /**
     * 生效时间
     */
    private Date effDate;
    /**
     * 失效时间
     */
    private Date expDate;
    /**
     * 状态
     */
    private String statusCd;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 创建人
     */
    private Long createUser;
    /**
     * 修改时间
     */
    private Date updateDate;
    /**
     * 修改人
     */
    private Long updateUser;
    /**
     * 状态时间
     */
    private Date statusDate;


    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getParentPostId() {
        return parentPostId;
    }

    public void setParentPostId(Long parentPostId) {
        this.parentPostId = parentPostId;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public Date getEffDate() {
        return effDate;
    }

    public void setEffDate(Date effDate) {
        this.effDate = effDate;
    }

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    public String getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(String statusCd) {
        this.statusCd = statusCd;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Long getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    @Override
    public String toString() {
        return "TbPost{" +
                ", postId=" + postId +
                ", parentPostId=" + parentPostId +
                ", postName=" + postName +
                ", postType=" + postType +
                ", effDate=" + effDate +
                ", expDate=" + expDate +
                ", statusCd=" + statusCd +
                ", createDate=" + createDate +
                ", createUser=" + createUser +
                ", updateDate=" + updateDate +
                ", updateUser=" + updateUser +
                ", statusDate=" + statusDate +
                "}";
    }
}
