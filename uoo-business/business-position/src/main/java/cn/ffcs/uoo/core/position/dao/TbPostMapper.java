package cn.ffcs.uoo.core.position.dao;

import cn.ffcs.uoo.core.position.entity.TbPost;
import cn.ffcs.uoo.core.position.vo.OrgPositionInfoVo;
import cn.ffcs.uoo.core.position.vo.OrgPostInfoVo;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 职位表 Mapper 接口
 * </p>
 *
 * @author zhanglu
 * @since 2018-10-30
 */
public interface TbPostMapper extends BaseMapper<TbPost> {
    /**
     * 新增职位信息
     * @param tbPost
     * @return
     */
    int save(TbPost tbPost);

    /**
     * 删除职位信息
     * @param tbPost
     */
    void remove(TbPost tbPost);

    /**
     * 通过组织标识查询职位信息
     * @param orgId
     * @return
     */
    List<OrgPostInfoVo> queryPostListByOrgId(Long orgId);
}
