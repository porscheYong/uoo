package cn.ffcs.uoo.system.dao;

import cn.ffcs.uoo.system.entity.SysPosition;
import cn.ffcs.uoo.system.entity.SysPosition;
import cn.ffcs.uoo.system.vo.SysPositionVo;
import cn.ffcs.uoo.system.vo.TreeNodeVo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 根据不同部门的工作性质、责任轻重、难易程度和所需资格条件等进行分类，在平台上，不对职位进行过细的区分 Mapper 接口
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-12-20
 */
public interface SysPositionMapper extends BaseMapper<SysPosition> {

    public List<TreeNodeVo> getPositionTree(@Param("positionId") String positionId);

    public List<SysPositionVo> getPositionRel(@Param("positionId")String positionId,@Param("isSearchlower") String isSearchlower);

}
