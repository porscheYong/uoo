package cn.ffcs.uoo.core.position.vo;

import com.baomidou.mybatisplus.annotations.TableField;

import java.util.List;

/**
 * 职位树节点类
 * @author zhanglu
 */
public class PostNodeVo {
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

    private List<PostNodeVo> children;

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

    public List<PostNodeVo> getChildren() {
        return children;
    }

    public void setChildren(List<PostNodeVo> children) {
        this.children = children;
    }
}
