package cn.ffcs.uoo.core.organization.dao;

import cn.ffcs.uoo.core.organization.entity.OgtOrgReltypeConf;
import cn.ffcs.uoo.core.organization.entity.OgtOrgReltypeConf;
import cn.ffcs.uoo.core.organization.entity.OgtOrgtypeConf;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-22
 */
public interface OgtOrgReltypeConfMapper extends BaseMapper<OgtOrgReltypeConf> {

    public Long getId();

    public void delete(OgtOrgReltypeConf ogtOrgReltypeConf);

}
