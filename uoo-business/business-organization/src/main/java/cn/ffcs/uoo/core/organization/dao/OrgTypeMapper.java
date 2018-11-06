package cn.ffcs.uoo.core.organization.dao;

import cn.ffcs.uoo.core.organization.entity.OrgType;
import cn.ffcs.uoo.core.organization.vo.TreeNodeVo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
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
public interface OrgTypeMapper extends BaseMapper<OrgType> {

    public List<OrgType> getOrgTypeByOrgId(@Param("orgId") Long orgId);

    public List<TreeNodeVo> selectOrgTypeTree(@Param("supOrgTypeId")String supOrgTypeId,@Param("orgTypeCode")String orgTypeCode);

    public List<TreeNodeVo> isLeaf(@Param("supOrgTypeId")Long supOrgTypeId);
}