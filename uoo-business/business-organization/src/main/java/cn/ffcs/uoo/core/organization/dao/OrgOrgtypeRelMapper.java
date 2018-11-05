package cn.ffcs.uoo.core.organization.dao;

import cn.ffcs.uoo.core.organization.entity.OrgOrgtypeRel;
import cn.ffcs.uoo.core.organization.entity.OrgRelType;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-09-25
 */
public interface OrgOrgtypeRelMapper extends BaseMapper<OrgOrgtypeRel> {
    public Long getId();
    public void delete(OrgOrgtypeRel orgOrgtypeRel);
}
