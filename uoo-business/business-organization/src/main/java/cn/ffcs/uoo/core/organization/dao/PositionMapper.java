package cn.ffcs.uoo.core.organization.dao;

import cn.ffcs.uoo.core.organization.entity.Position;
import cn.ffcs.uoo.core.organization.vo.TreeNodeVo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 岗位，不同组织树具有不同的岗位 Mapper 接口
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-10
 */
@Component
public interface PositionMapper extends BaseMapper<Position> {

    public Long getId();

    public void delete(Position position);

    public List<Position> getOrgPositionByOrgId(@Param("orgId")Long orgId);

    public List<TreeNodeVo> selectPositionTree(@Param("pid") String pid,
                                               @Param("positionType") String positionType,
                                               @Param("positionCode") String positionCode);
}
