package cn.ffcs.uoo.core.organization.dao;

import cn.ffcs.uoo.core.organization.entity.OrgTree;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-09-25
 */
public interface OrgTreeMapper extends BaseMapper<OrgTree> {

    public Long getId();

}