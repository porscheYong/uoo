package cn.ffcs.uoo.core.organization.dao;

import cn.ffcs.uoo.core.organization.entity.Org;
import cn.ffcs.uoo.core.organization.entity.PoliticalLocation;
import cn.ffcs.uoo.core.organization.vo.AreaCodeVo;
import cn.ffcs.uoo.core.organization.vo.OrgVo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-09-25
 */
@Component
public interface OrgMapper extends BaseMapper<Org> {

    public Long getId();

    public void delete(Org org);

    /**
     * 查询组织信息
     * @param id
     * @return
     */
    public Org queryOrgById(Long id);


    /**
     * 组织总条数
     * @param org
     * @return
     */
    public int queryOrgCount(@Param("org")Org org);

    /**
     * 组织查询
     * @param org
     * @return
     */
    public List<Org> getOrgList(Org org);


    /**
     * 组织编码
     * @return
     */
    public Long getOrgCodeSeq();


    /**
     * 组织关系翻页查询
     * @param page
     * @param orgVo
     * @return
     */
    public List<OrgVo> selectOrgRelPage(Pagination page,@Param("orgVo")OrgVo orgVo);
    /**
     * 组织翻页查询
     * @param page
     * @param orgVo
     * @return
     */
    public List<OrgVo> selectOrgPage(Pagination page,@Param("orgVo")OrgVo orgVo);

    /**
     * 获取系统路径
     * @param orgTreeId
     * @param orgId
     * @return
     */
    public String getSysFullName(@Param("orgTreeId")String orgTreeId,@Param("orgId")String orgId);

    /**
     * 新增组织
     */
    //public void insertByObj(@Param("orgVo")OrgVo orgVo);

    /**
     * 查询组织
     * @param orgId
     * @return
     */
    public OrgVo selectOrgByOrgId(@Param("orgId")String orgId,@Param("orgTreeId")String orgTreeId);

    /**
     * 查询组织行政管理区域
     * @param orgId
     * @return
     */
    public List<PoliticalLocation> getOrgLoc(@Param("orgId")String orgId);

    /**
     *
     * @param orgId
     * @return
     */
    public List<OrgVo> getFullOrgList(@Param("orgTreeId")String orgTreeId,@Param("orgId")String orgId);

    /**
     * 获取区域信息
     * @param orgId
     * @return
     */
    public List<AreaCodeVo> getOrgAreaCode(@Param("orgId")String orgId);


    /**
     * 组织归属哪些组织树
     * @param orgId
     * @return
     */
    public String getAppOrgTrees(@Param("orgId")String orgId);


    /**
     * 组织下级
     * @param orgTreeId
     * @param orgId
     * @return
     */
    public List<OrgVo> getLowOrgs(@Param("orgTreeId")String orgTreeId, @Param("orgId")String orgId);
    /**
     * 组织下级
     * @param refCode
     * @param orgId
     * @return
     */
    public List<OrgVo> getLowOrgsByRefCode(@Param("refCode")String refCode, @Param("orgId")String orgId);

    /**
     * 渠道标识
     * @param orgId
     * @return
     */
    public String getChannelSign(@Param("orgId")String orgId);

    /**
     * 渠道编码
     * @param orgId
     * @return
     */
    public String getChannelNBR(@Param("orgId")String orgId);

    /**
     * 查询渠道组织翻页
     * @param page
     * @param orgVo
     * @return
     */
    public List<OrgVo> selectChannelOrgPage(Pagination page,@Param("orgVo")OrgVo orgVo);

    /**
     * 渠道组织数量
     * @return
     */
    public int getChannelOrgCount();

    /**
     * 渠道组织已经挂载数量
     * @param refCode
     * @return
     */
    public int getChannelOrgLoaderCount(@Param("refCode")String refCode,@Param("orgId")String orgId);
}
