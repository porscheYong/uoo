package cn.ffcs.uoo.core.organization.service;

import cn.ffcs.uoo.core.organization.entity.OrgTree;
import com.baomidou.mybatisplus.service.IService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

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

    /**
     * 获取组织的其他信息：组织全路径、组织归属哪些树
     * @param orgId
     * @return
     */
    public String getOrgTreeNameByOrgId(String orgId);

    /**
     * 是否存在组织树组织关系
     * @return
     */
    public boolean isExistsOrgTreeRel(String refCode);

    /**
     * 根据关系编码变动
     * @param refCode
     * @return
     */
    public OrgTree getOrgTreeByRefCode(String refCode);
}
