package cn.ffcs.uoo.core.organization.service.impl;

import cn.ffcs.uoo.core.organization.entity.Post;
import cn.ffcs.uoo.core.organization.dao.PostMapper;
import cn.ffcs.uoo.core.organization.service.PostService;
import cn.ffcs.uoo.core.organization.util.StrUtil;
import cn.ffcs.uoo.core.organization.vo.TreeNodeVo;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.websocket.OnOpen;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-10
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {

    /**
     * 获取seq
     * @return
     */
    @Override
    public Long getId(){
        return baseMapper.getId();
    }

    /**
     * 失效
     * @param post
     */
    @Override
    public void delete(Post post){
        post.setStatusCd("1100");
        post.setStatusDate(new Date());
        post.setUpdateDate(new Date());
        post.setUpdateUser(0L);
        updateById(post);
    }

    /**
     * 获取职位列表
     * @return
     */
    @Override
    public List<Post> getOrgPostByOrgId(Long orgId){
        return baseMapper.getOrgPostByOrgId(orgId);
    }
    /**
     * 获取职位树
     * @param postType
     * @param pid
     * @param isRoot
     * @return
     */
    @Override
    public List<TreeNodeVo> selectPostTree(String postType, String pid, boolean isRoot){

        List<TreeNodeVo> volist = new ArrayList<>();
        if(StrUtil.isNullOrEmpty(pid)){ pid="0"; }
        volist = baseMapper.selectPostTree(postType,pid);
        for(TreeNodeVo vo : volist){
            List<TreeNodeVo> leafList = isLeaf(postType,vo.getId());
            if(leafList!=null && leafList.size()>0){
                vo.setParent(true);
            }else{
                vo.setParent(false);
            }
        }
        return volist;
    }
    @Override
    public List<TreeNodeVo> isLeaf(String postType, String pid){
        List<TreeNodeVo> volist = new ArrayList<>();
        volist = baseMapper.selectPostTree(postType,pid);
        return volist;
    }
}
