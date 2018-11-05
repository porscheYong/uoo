package cn.ffcs.uoo.core.position.service.impl;

import cn.ffcs.uoo.base.common.tool.util.DateUtils;
import cn.ffcs.uoo.core.position.entity.TbPost;
import cn.ffcs.uoo.core.position.dao.TbPostMapper;
import cn.ffcs.uoo.core.position.service.TbPostService;
import cn.ffcs.uoo.core.position.vo.OrgPostInfoVo;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 职位表 服务实现类
 * </p>
 *
 * @author zhanglu
 * @since 2018-10-30
 */
@Service
public class TbPostServiceImpl extends ServiceImpl<TbPostMapper, TbPost> implements TbPostService {

    @Override
    public int save(TbPost tbPost) {
        return baseMapper.save(tbPost);
    }

    @Override
    public void remove(Long postId, Long updateUser) {
        TbPost tbPost = new TbPost();
        tbPost.setPostId(postId);
        // 失效状态
        tbPost.setStatusCd("1100");
        tbPost.setUpdateDate(DateUtils.parseDate(DateUtils.getDateTime()));
        tbPost.setUpdateUser(updateUser);
        tbPost.setStatusDate(DateUtils.parseDate(DateUtils.getDateTime()));
        baseMapper.remove(tbPost);
    }

    @Override
    public List<OrgPostInfoVo> queryPostListByOrgId(Long orgId) {
        return baseMapper.queryPostListByOrgId(orgId);
    }
}
