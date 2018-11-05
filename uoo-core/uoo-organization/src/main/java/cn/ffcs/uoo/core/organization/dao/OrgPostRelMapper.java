package cn.ffcs.uoo.core.organization.dao;

import cn.ffcs.uoo.core.organization.entity.OrgPostRel;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 职位，不同组织树具有不同的职位 Mapper 接口
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-25
 */
public interface OrgPostRelMapper extends BaseMapper<OrgPostRel> {

    public Long getId();

    public void delete(OrgPostRel orgPostRel);
}
