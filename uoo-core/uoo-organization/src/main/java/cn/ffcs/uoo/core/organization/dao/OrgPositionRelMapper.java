package cn.ffcs.uoo.core.organization.dao;

import cn.ffcs.uoo.core.organization.entity.OrgPositionRel;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 岗位，不同组织树具有不同的岗位 Mapper 接口
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-10
 */
public interface OrgPositionRelMapper extends BaseMapper<OrgPositionRel> {
    public Long getId();

    public void delete(OrgPositionRel orgPositionRel);
}
