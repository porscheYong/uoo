package cn.ffcs.uoo.core.organization.service;

import cn.ffcs.uoo.core.organization.entity.OrgOrgtreeRel;
import cn.ffcs.uoo.core.organization.vo.OrgVo;
import com.baomidou.mybatisplus.service.IService;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-21
 */
public interface OrgOrgtreeRelService extends IService<OrgOrgtreeRel> {
    /**
     * 获取seq
     * @return
     */
    public Long getId();

    /**
     * 失效状态
     * @param orgOrgtreeRel
     */
    public void delete(OrgOrgtreeRel orgOrgtreeRel);

    /**
     *
     * @param orgOrgtreeRef
     */
    public void add(OrgOrgtreeRel orgOrgtreeRef);
    /**
     *
     * @param orgOrgtreeRef
     */
    public void update(OrgOrgtreeRel orgOrgtreeRef);

    /**
     * 获取组织全路径列表
     * @param orgTreeId
     * @param orgId
     * @return
     */
    public List<OrgOrgtreeRel> getFullBizOrgList(String orgTreeId, String orgId);

    /**
     * 获取组织名称全路
     * @param orgTreeId
     * @param orgId
     * @return
     */
    public String getFullBizOrgNameList(String orgTreeId,String orgId,String split);

    /**
     * 获取组织Id全路
     * @param orgTreeId
     * @param orgId
     * @return
     */
    public String getFullBizOrgIdList(String orgTreeId,String orgId,String split);

}
