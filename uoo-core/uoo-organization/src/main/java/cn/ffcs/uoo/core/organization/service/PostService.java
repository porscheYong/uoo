package cn.ffcs.uoo.core.organization.service;

import cn.ffcs.uoo.core.organization.entity.Post;
import cn.ffcs.uoo.core.organization.vo.TreeNodeVo;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-10
 */
public interface PostService extends IService<Post> {


    /**
     * 获取seq
     * @return
     */
    public Long getId();

    /**
     * 失效
     * @param post
     */
    public void delete(Post post);

    /**
     * 获取职位列表
     * @param orgId
     * @return
     */
    public List<Post> getOrgPostByOrgId(Long orgId);

    /**
     * 获取职位树
     * @param postType
     * @param pid
     * @param isRoot
     * @return
     */
    public List<TreeNodeVo> selectPostTree(String postType, String pid, boolean isRoot);

    /**
     * 判断是否存在子节点
     * @param postType
     * @param pid
     * @return
     */
    public List<TreeNodeVo> isLeaf(String postType, String pid);

}
