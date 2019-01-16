package cn.ffcs.uoo.core.organization.dao;

import cn.ffcs.uoo.core.organization.entity.OrgPersonRel;
import cn.ffcs.uoo.core.organization.entity.Post;
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

    public List<PsonOrgVo> selectOrgRelsByPerIdPage(Pagination page, @Param("psonOrgVo")PsonOrgVo psonOrgVo);

    public List<PsonOrgVo> selectAllPerOrgRelPage(Pagination page, @Param("psonOrgVo")PsonOrgVo psonOrgVo);

    public List<PsonOrgVo> selectAllUserOrgRelPage(Pagination page, @Param("psonOrgVo")PsonOrgVo psonOrgVo);

    public List<PsonOrgVo> selectUserOrgRelPage(Pagination page, @Param("psonOrgVo")PsonOrgVo psonOrgVo);

    public List<PsonOrgVo> selectPerOrOrgRelPage(Pagination page, @Param("psonOrgVo")PsonOrgVo psonOrgVo);

    public List<PsonOrgVo> selectFuzzyOrgPsnPage(Pagination page, @Param("psonOrgVo")PsonOrgVo psonOrgVo);

    public List<OrgPersonRel> getOrgPsnByOrgTreeAndPsnId(@Param("orgTreeId")String orgTreeId,
                                                         @Param("personnelId")String personnelId,
                                                         @Param("orgId")String orgId);

    public List<OrgPersonRel> getOrgPsnRel(@Param("orgTreeId")String orgTreeId,@Param("orgId")String orgId);

    //EXT
    //获取职位
    public Post getPost(@Param("postId")String postId);
    //获取手机号码
    public String getMobile(@Param("personnelId")String personnelId);
    //获取身份证
    public String getCert(@Param("personnelId")String personnelId);

}
