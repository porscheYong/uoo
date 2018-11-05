package cn.ffcs.uoo.core.organization.dao;

import cn.ffcs.uoo.core.organization.entity.OrgtreeOrgpersonRel;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 描述该员工挂着到哪些专业树上 Mapper 接口
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-28
 */
public interface OrgtreeOrgpersonRelMapper extends BaseMapper<OrgtreeOrgpersonRel> {

    public Long getId();

    public void delete(OrgtreeOrgpersonRel orgtreeOrgpersonRel);

}
