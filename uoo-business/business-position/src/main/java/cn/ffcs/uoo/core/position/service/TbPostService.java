package cn.ffcs.uoo.core.position.service;

import cn.ffcs.uoo.core.position.entity.TbPost;
import cn.ffcs.uoo.core.position.vo.OrgPostInfoVo;
import cn.ffcs.uoo.core.position.vo.PostNodeVo;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 职位表 服务类
 * </p>
 *
 * @author zhanglu
 * @since 2018-10-30
 */
public interface TbPostService extends IService<TbPost> {
    /**
     * 新增职位信息
     * @param tbPost
     * @return
     */
    int save(TbPost tbPost);

    /**
     * 删除职位信息
     * @param postId
     * @param updateUser
     */
    void remove(Long postId, Long updateUser);

    /**
     * 根据组织标识查询职位信息
     * @param orgId
     * @return
     */
    List<OrgPostInfoVo> queryPostListByOrgId(Long orgId);

    /**
     * 获取职位信息列表
     * @return
     */
    List<PostNodeVo> getAllPostNodeVo();
}
