package cn.ffcs.uoo.system.service;

import cn.ffcs.uoo.system.entity.SysPosition;
import cn.ffcs.uoo.system.vo.SysPositionVo;
import cn.ffcs.uoo.system.vo.TreeNodeVo;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 根据不同部门的工作性质、责任轻重、难易程度和所需资格条件等进行分类，在平台上，不对职位进行过细的区分 服务类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-12-20
 */
public interface SysPositionService extends IService<SysPosition> {

    /**
     * 获取seq
     * @return
     */
    public Long getId();

    /**
     * 失效状态
     * @param sysPosition
     */
    public void delete(SysPosition sysPosition);

    /**
     * 新增
     */
    public void add(SysPosition sysPosition);

    /**
     * 更新
     */
    public void update(SysPosition sysPosition);



    public List<TreeNodeVo> selectPositionTree(String positionId);

    public List<TreeNodeVo> selectPositionTree();


    public Page<SysPositionVo> getPositionRelPage(SysPositionVo vo);

    public String getRolesByPositionId(String positionId);

    public SysPositionVo getPosition(String positionId);

    public List<SysPositionVo> getSysOrgPosition(String orgCode);


    public int getPositionUserRefCount(String positionCode);

    public int getPositionDepRefCount(String positionCode);

    public int getPositionRoleRefCount(String positionCode);
}
