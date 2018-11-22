package cn.ffcs.uoo.core.position.util;

import cn.ffcs.uoo.core.position.vo.PositionNodeVo;

import java.util.ArrayList;
import java.util.List;

/**
 * 构造树工具类
 * @author zhanglu
 */
public class TreeUtil {
    /**
     * 递归查找子节点
     * @param treeNode
     * @param treeNodes
     * @return
     */
    public static PositionNodeVo findPositionChildren(PositionNodeVo treeNode, List<PositionNodeVo> treeNodes) {
        for (PositionNodeVo it : treeNodes) {
            if(treeNode.getPositionId().equals(it.getParentPositionId())) {
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<PositionNodeVo>());
                }
                treeNode.getChildren().add(findPositionChildren(it,treeNodes));
            }
        }
        return treeNode;
    }

    /**
     * 生成岗位树
     * @param treeNodes
     * @return
     */
    public static List<PositionNodeVo> createPositionTree(List<PositionNodeVo> treeNodes){
        List<PositionNodeVo> trees = new ArrayList<PositionNodeVo>();
        for (PositionNodeVo treeNode : treeNodes) {
            // 选取根节点
            if (treeNode.getPositionId() == 1) {
                trees.add(findPositionChildren(treeNode,treeNodes));
            }
        }
        return trees;
    }
}
