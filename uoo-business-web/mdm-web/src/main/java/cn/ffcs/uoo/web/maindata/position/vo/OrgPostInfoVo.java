package cn.ffcs.uoo.web.maindata.position.vo;

/**
 * 组织职位值信息
 * @author zhanglu
 * @since 2018-11-01
 */
public class OrgPostInfoVo {
    /**
     * 组织职位关系标识
     */
    private Long orgPostId;
    /**
     * 职位标识
     */
    private Long postId;
    /**
     * 组织标识
     */
    private Long orgId;
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

    public Long getOrgPostId() {
        return orgPostId;
    }

    public void setOrgPostId(Long orgPostId) {
        this.orgPostId = orgPostId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
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
}
