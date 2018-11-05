package cn.ffcs.uoo.core.organization.service;

import cn.ffcs.uoo.core.organization.entity.OrgTree;
import com.baomidou.mybatisplus.service.IService;

import java.util.Date;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-09-25
 */
public interface OrgTreeService extends IService<OrgTree> {

    public Long getId();

    public void delete(OrgTree orgTree);

    public void add(OrgTree orgTree);

    public void update(OrgTree orgTree);

    public String judgeOrgTreeParams(OrgTree orgTree);



}
