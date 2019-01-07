package cn.ffcs.uoo.system.dao;


import cn.ffcs.uoo.system.entity.SysOrganization;
import cn.ffcs.uoo.system.entity.SysUser;
import cn.ffcs.uoo.system.vo.SysOrganizationVo;
import cn.ffcs.uoo.system.vo.TreeNodeVo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-12-20
 */
public interface SysOrganizationMapper extends BaseMapper<SysOrganization> {

    /**
     * 获取seq
     * @return
     */
    public Long getId();

    /**
     * 获取根节点
     * @return
     */
    public List<TreeNodeVo> getTreeRoot();

    /**
     * 获取子节点
     * @param id
     * @return
     */
    public List<TreeNodeVo> getTreeChild(@Param("id") String id);

    /**
     * 叶子节点
     * @param id
     * @return
     */
    public int isleaf(@Param("id") String id);

    /**
     * 获取全量组织树
     * @return
     */
    public List<TreeNodeVo> selectOrgTree();


    /**
     * 检索组织树
     * @param vo
     * @return
     */
    public List<SysOrganizationVo> selectFuzzyOrgRelPage(Pagination page, @Param("vo")SysOrganizationVo vo);


    public List<TreeNodeVo> getRestructOrgRelTree(@Param("vo")SysOrganizationVo vo);

    public List<SysOrganizationVo> getOrgRelPage(Pagination page,@Param("vo")SysOrganizationVo vo);

    public SysOrganizationVo getOrg(@Param("orgCode")String orgCode);

    public int getOrgUserCount(@Param("orgCode")String orgCode);

    public int getOrgRoleCount(@Param("orgCode")String orgCode);
}
