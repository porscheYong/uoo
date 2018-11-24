package cn.ffcs.uoo.core.organization.dao;

import cn.ffcs.uoo.core.organization.entity.OrgRelType;
import cn.ffcs.uoo.core.organization.vo.TreeNodeVo;
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
public interface OrgRelTypeMapper extends BaseMapper<OrgRelType> {

    public Long getId();

    public OrgRelType getOrgRelType(@Param("orgRootId")String orgRootId);

    public List<TreeNodeVo> selectOrgRelTypeTree();

    public List<TreeNodeVo> selectTarOrgRelTypeTree(@Param("refCode")String refCode);
}
