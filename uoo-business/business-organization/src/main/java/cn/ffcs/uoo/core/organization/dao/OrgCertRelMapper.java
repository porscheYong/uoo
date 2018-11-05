package cn.ffcs.uoo.core.organization.dao;

import cn.ffcs.uoo.core.organization.entity.OrgCertRel;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 解决智慧BSS组织机构接入过程中的法人信息问题 Mapper 接口
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-22
 */
public interface OrgCertRelMapper extends BaseMapper<OrgCertRel> {

    public Long getId();

    public void delete(OrgCertRel orgCertRel);
}
