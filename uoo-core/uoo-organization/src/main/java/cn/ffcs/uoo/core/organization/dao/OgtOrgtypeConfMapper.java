package cn.ffcs.uoo.core.organization.dao;

import cn.ffcs.uoo.core.organization.entity.OgtOrgtypeConf;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-09-25
 */
public interface OgtOrgtypeConfMapper extends BaseMapper<OgtOrgtypeConf> {

    public Long getId();

    public void delete(OgtOrgtypeConf ogtOrgtypeConf);
}
