package cn.ffcs.uoo.core.organization.service;

import cn.ffcs.uoo.core.organization.entity.OrgPostRel;
import com.baomidou.mybatisplus.service.IService;

import java.util.Date;

/**
 * <p>
 * 职位，不同组织树具有不同的职位 服务类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-25
 */
public interface OrgPostRelService extends IService<OrgPostRel> {

    public Long getId();

    public void delete(OrgPostRel orgPostRel);


    public void add(OrgPostRel orgPostRel);

    public void update(OrgPostRel orgPostRel);
}
