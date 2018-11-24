package cn.ffcs.uoo.core.position.util;

import cn.ffcs.uoo.core.position.vo.PositionNodeVo;
import cn.ffcs.uoo.core.position.vo.PostNodeVo;

import java.util.ArrayList;
import java.util.List;

/**
 * 构造树工具类
 *
 * @author zhanglu
 */
public class TreeUtil {
    /**
     * 递归查找岗位子节点
     *
     * @param treeNode
     * @param treeNodes
     * @return
     */
    public static PositionNodeVo findPositionChildren(PositionNodeVo treeNode, List<PositionNodeVo> treeNodes) {
        for (PositionNodeVo it : treeNodes) {
            if (treeNode.getPositionId().equals(it.getParentPositionId())) {
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<PositionNodeVo>());
                }
                treeNode.getChildren().add(findPositionChildren(it, treeNodes));
            }
        }
        return treeNode;
    }

    /**
     * 生成岗位树
     *
     * @param treeNodes
     * @return
     */
    public static List<PositionNodeVo> createPositionTree(List<PositionNodeVo> treeNodes) {
        List<PositionNodeVo> trees = new ArrayList<PositionNodeVo>();
        for (PositionNodeVo treeNode : treeNodes) {
            // 选取根节点
            if (treeNode.getPositionId() == 1L) {
                trees.add(findPositionChildren(treeNode, treeNodes));
            }
        }
        return trees;
    }

    /**
     * 递归查找职位子节点
     *
     * @param treeNode
     * @param treeNodes
     * @return
     */
    public static PostNodeVo findPostChildren(PostNodeVo treeNode, List<PostNodeVo> treeNodes) {
        for (PostNodeVo it : treeNodes) {
            // 查看当前迭代节点是否存在下级子节点
            if (treeNode.getPostId().equals(it.getParentPostId())) {
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<PostNodeVo>());
                }
                // 递归查找当前节点的子节点的下级节点情况
                treeNode.getChildren().add(findPostChildren(it, treeNodes));
            }
        }

        return treeNode;
    }

    /**
     * 生成职位树
     *
     * @param treeNodes
     * @return
     */
    public static List<PostNodeVo> createPostTree(List<PostNodeVo> treeNodes) {
        List<PostNodeVo> trees = new ArrayList<PostNodeVo>();
        for (PostNodeVo treeNode : treeNodes) {
            // 选取根节点
            if (treeNode.getParentPostId() == 0L) {
                // 调用方法找到下级节点树
                trees.add(findPostChildren(treeNode, treeNodes));
            }
        }

        return trees;
    }
}
