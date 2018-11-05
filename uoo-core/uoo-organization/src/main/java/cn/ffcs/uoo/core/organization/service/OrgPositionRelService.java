package cn.ffcs.uoo.core.organization.service;

import cn.ffcs.uoo.core.organization.entity.Org;
import cn.ffcs.uoo.core.organization.entity.OrgPositionRel;
import com.baomidou.mybatisplus.service.IService;

import java.util.Date;

/**
 * <p>
 * 岗位，不同组织树具有不同的岗位 服务类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-10
 */
public interface OrgPositionRelService extends IService<OrgPositionRel> {

    public Long getId();
    public void delete(OrgPositionRel orgPositionRel);
    public void add(OrgPositionRel orgPositionRel);
    public void update(OrgPositionRel orgPositionRel);
}
