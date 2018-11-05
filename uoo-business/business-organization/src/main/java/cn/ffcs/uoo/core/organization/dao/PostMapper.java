package cn.ffcs.uoo.core.organization.dao;

import cn.ffcs.uoo.core.organization.entity.Post;
import cn.ffcs.uoo.core.organization.vo.TreeNodeVo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-25
 */
public interface PostMapper extends BaseMapper<Post> {

    public Long getId();

    public void delete(Post post);

    public List<Post> getOrgPostByOrgId(@Param("orgId")Long orgId);

    public List<TreeNodeVo> selectPostTree(@Param("postType") String postType, @Param("pid") String pid);

}
