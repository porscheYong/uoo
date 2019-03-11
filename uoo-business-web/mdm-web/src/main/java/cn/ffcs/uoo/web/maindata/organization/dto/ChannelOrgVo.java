package cn.ffcs.uoo.web.maindata.organization.dto;/**
 * @description:
 * @author: ffcs-gzb
 * @date: 2019-03-01
 */

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author ffcs-gzb
 * @since 2019/3/1
 */
public class ChannelOrgVo {
    /**
     * 组织树编码
     */
    private Long orgTreeId;
    /**
     * 新增节点
     */
    private List<TreeNodeVo> addNodes;
    /**
     * 删除节点
     */
    private List<TreeNodeVo> delNodes;
    /**
     * 更新节点
     */
    private List<TreeNodeVo> updateNodes;

    private Long userId;
    private String accout;


    public List<TreeNodeVo> getUpdateNodes() {
        return updateNodes;
    }

    public void setUpdateNodes(List<TreeNodeVo> updateNodes) {
        this.updateNodes = updateNodes;
    }

    public Long getOrgTreeId() {
        return orgTreeId;
    }

    public void setOrgTreeId(Long orgTreeId) {
        this.orgTreeId = orgTreeId;
    }

    public List<TreeNodeVo> getAddNodes() {
        return addNodes;
    }

    public void setAddNodes(List<TreeNodeVo> addNodes) {
        this.addNodes = addNodes;
    }

    public List<TreeNodeVo> getDelNodes() {
        return delNodes;
    }

    public void setDelNodes(List<TreeNodeVo> delNodes) {
        this.delNodes = delNodes;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAccout() {
        return accout;
    }

    public void setAccout(String accout) {
        this.accout = accout;
    }
}
