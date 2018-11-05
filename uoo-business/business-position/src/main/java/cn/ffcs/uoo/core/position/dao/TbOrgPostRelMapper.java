package cn.ffcs.uoo.core.position.dao;

import cn.ffcs.uoo.core.position.entity.TbOrgPostRel;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 组织职位关系
 * 职位，不同组织树具有不同的职位 Mapper 接口
 * </p>
 *
 * @author zhanglu
 * @since 2018-10-30
 */
public interface TbOrgPostRelMapper extends BaseMapper<TbOrgPostRel> {
    /**
     * 新增组织职位关系
     * @param tbOrgPostRel
     * @return
     */
    int save(TbOrgPostRel tbOrgPostRel);

    /**
     * 删除组织职位关系
     * @param tbOrgPostRel
     */
    void remove(TbOrgPostRel tbOrgPostRel);
}
