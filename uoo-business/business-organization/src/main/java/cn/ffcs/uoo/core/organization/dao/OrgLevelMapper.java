package cn.ffcs.uoo.core.organization.dao;

import cn.ffcs.uoo.core.organization.entity.OrgLevel;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 组织在组织树中的层级 Mapper 接口
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-21
 */
public interface OrgLevelMapper extends BaseMapper<OrgLevel> {

    public Long getId();

    public void delete(OrgLevel orgLevel);
}
