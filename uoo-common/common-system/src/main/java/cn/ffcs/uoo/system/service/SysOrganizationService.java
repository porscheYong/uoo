package cn.ffcs.uoo.system.service;

import cn.ffcs.uoo.system.entity.SysOrganization;
import cn.ffcs.uoo.system.entity.SysOrganization;
import cn.ffcs.uoo.system.entity.SysPosition;
import cn.ffcs.uoo.system.vo.SysOrganizationVo;
import cn.ffcs.uoo.system.vo.TreeNodeVo;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-12-20
 */
public interface SysOrganizationService extends IService<SysOrganization> {

    /**
     * 获取seq
     * @return
     */
    public Long getId();

    /**
     * 失效状态
     * @param sysOrganization
     */
    public void delete(SysOrganization sysOrganization);

    /**
     * 新增
     * @param sysOrganization
     */
    public void add(SysOrganization sysOrganization);

    /**
     * 更新
     * @param sysOrganization
     */
    public void update(SysOrganization sysOrganization);


    /**
     * 获取组织树
     * @param id
     * @return
     */
    public List<TreeNodeVo> selectOrgTree(String id);


    /**
     * 获取全量组织树
     */
    public List<TreeNodeVo> selectOrgTree();


    /**
     * 检索组织树
     * @param vo
     * @return
     */
    public Page<SysOrganizationVo> selectFuzzyOrgRelPage(SysOrganizationVo vo);

    /**
     * 获取组织树
     * @param vo
     * @return
     */
    public List<TreeNodeVo> getRestructOrgRelTree(SysOrganizationVo vo);


    public Page<SysOrganizationVo> getOrgRelPage(SysOrganizationVo vo);


    public SysOrganizationVo getOrg(String orgCode);

}
