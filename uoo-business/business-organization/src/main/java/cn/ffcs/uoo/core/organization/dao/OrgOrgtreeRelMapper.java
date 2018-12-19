package cn.ffcs.uoo.core.organization.dao;

import cn.ffcs.uoo.core.organization.entity.OrgOrgtreeRel;
import com.baomidou.mybatisplus.mapper.BaseMapper;
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
public interface OrgOrgtreeRelMapper extends BaseMapper<OrgOrgtreeRel> {

    public Long getId();

    public void delete(OrgOrgtreeRel org);

    public List<OrgOrgtreeRel> getFullBizOrgList(@Param("orgTreeId")String orgTreeId,@Param("orgId") String orgId);

}
