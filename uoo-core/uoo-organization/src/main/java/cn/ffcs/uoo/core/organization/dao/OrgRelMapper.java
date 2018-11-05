package cn.ffcs.uoo.core.organization.dao;

import cn.ffcs.uoo.core.organization.entity.Org;
import cn.ffcs.uoo.core.organization.entity.OrgRel;
import cn.ffcs.uoo.core.organization.vo.OrgRefTypeVo;
import cn.ffcs.uoo.core.organization.vo.OrgVo;
import cn.ffcs.uoo.core.organization.vo.PsonOrgVo;
import cn.ffcs.uoo.core.organization.vo.TreeNodeVo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-09-25
 */
@Component
public interface OrgRelMapper extends BaseMapper<OrgRel> {

    public Long getId();

    public void delete(OrgRel orgRel);

    public List<OrgRel> selectOrgRel(@Param("org")Org org);

    public List<Org> selectFuzzyOrgRel(@Param("search")String search,@Param("orgRootId")String orgRootId);

    public int selectFuzzyOrgRelCount(@Param("search")String search, @Param("orgRootId")String orgRootId);

    public List<OrgVo> selectFuzzyOrgRelPage(Pagination page,@Param("orgVo")OrgVo orgVo);

    public List<TreeNodeVo> selectFuzzyOrgRelTree(@Param("orgleafId")String orgleafId,@Param("orgRootId")String orgRootId);

    public List<TreeNodeVo> selectFuzzyFullOrgRelTree(@Param("orgleafId")String orgleafId,@Param("orgRootId")String orgRootId);

    public List<TreeNodeVo> queryOrgTreeRoot(@Param("orgRootId")String orgRootId);

    public List<TreeNodeVo> queryOrgTreeChilden(@Param("orgRootId")String orgRootId,@Param("pid")String pid);

    public List<TreeNodeVo> isLeaf(@Param("orgRootId")String orgRootId,@Param("pid")String pid);

    public List<OrgRefTypeVo> getOrgRelType(@Param("org")Org org);

    public List<OrgRefTypeVo> selectOrgRelTypePage(Pagination page,@Param("orgRefTypeVo")OrgRefTypeVo orgRefTypeVo);

    public List<OrgRel> getOrgRel(@Param("orgTreeId")String orgTreeId, @Param("orgId")String orgId);

}
