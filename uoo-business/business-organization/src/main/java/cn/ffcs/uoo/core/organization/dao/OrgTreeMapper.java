package cn.ffcs.uoo.core.organization.dao;

import cn.ffcs.uoo.core.organization.entity.OrgTree;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-09-25
 */
public interface OrgTreeMapper extends BaseMapper<OrgTree> {

    public Long getId();

    public String getOrgTreeNameByOrgId(String orgId);

    public int isExistsOrgTreeRel(String refCode);

    public OrgTree getOrgTreeByRefCode(String refCode);

    public List<OrgTree> getOrgTreeList(@Param("orgTree")OrgTree orgTree);

}
