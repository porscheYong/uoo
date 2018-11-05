package cn.ffcs.uoo.core.position.dao;

import cn.ffcs.uoo.core.position.entity.TbOrgPositionRel;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 岗位组织关系，不同组织树具有不同的岗位 Mapper 接口
 * </p>
 *
 * @author zhanglu
 * @since 2018-11-01
 */
public interface TbOrgPositionRelMapper extends BaseMapper<TbOrgPositionRel> {
    /**
     * 新增岗位组织关系
     * @param tbOrgPositionRel
     * @return
     */
    int save(TbOrgPositionRel tbOrgPositionRel);

    /**
     * 删除岗位组织关系
     * @param tbOrgPositionRel
     */
    void remove(TbOrgPositionRel tbOrgPositionRel);
}
