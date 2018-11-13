package cn.ffcs.uoo.core.organization.dao;

import cn.ffcs.uoo.core.organization.entity.OrgPersonRel;
import cn.ffcs.uoo.core.organization.vo.PsonOrgVo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-21
 */
public interface OrgPersonRelMapper extends BaseMapper<OrgPersonRel> {

    public Long getId();

    public void delete(OrgPersonRel orgPersonRel);

    public List<PsonOrgVo> getPerOrgRelList(@Param("psonOrgVo")PsonOrgVo psonOrgVo);

    public List<PsonOrgVo> selectPerOrgRelPage(Pagination page, @Param("psonOrgVo")PsonOrgVo psonOrgVo);

    public List<PsonOrgVo> selectPerOrOrgRelPage(Pagination page, @Param("psonOrgVo")PsonOrgVo psonOrgVo);

    public List<PsonOrgVo> selectFuzzyOrgPsnPage(Pagination page, @Param("psonOrgVo")PsonOrgVo psonOrgVo);

}
