package cn.ffcs.uoo.system.dao;

import cn.ffcs.uoo.system.entity.SysPosition;
import cn.ffcs.uoo.system.entity.SysPosition;
import cn.ffcs.uoo.system.vo.SysPositionVo;
import cn.ffcs.uoo.system.vo.SysRoleDTO;
import cn.ffcs.uoo.system.vo.TreeNodeVo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
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

    public Long getId();

    public int isleaf(@Param("id")String id);

    public List<TreeNodeVo> getTreeRoot();

    public List<TreeNodeVo> getTreeChild(@Param("positionId") String positionId);

    public List<TreeNodeVo> selectPositionTree();

    public List<SysPositionVo> getPositionRelPage(Pagination page, @Param("vo")SysPositionVo vo);

    public String getRolesByPositionId(@Param("positionId")String positionId);

    public List<SysRoleDTO> getPositionRoles(@Param("positionCode")String positionCode);

    public SysPositionVo getPosition(@Param("positionId")String positionId);

    public List<SysPositionVo> getSysOrgPosition(@Param("orgCode")String orgCode);

//    public List<SysPositionVo> selectFuzzyPositionPage(Pagination page, @Param("vo")SysPositionVo vo);

}
