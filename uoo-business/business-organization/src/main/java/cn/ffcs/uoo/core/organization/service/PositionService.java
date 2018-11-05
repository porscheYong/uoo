package cn.ffcs.uoo.core.organization.service;

import cn.ffcs.uoo.core.organization.entity.OrgType;
import cn.ffcs.uoo.core.organization.entity.Position;
import cn.ffcs.uoo.core.organization.vo.TreeNodeVo;
import com.baomidou.mybatisplus.service.IService;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 岗位，不同组织树具有不同的岗位 服务类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-10
 */
public interface PositionService extends IService<Position> {


    /**
     * 获取seq
     * @return
     */
    public Long getId();

    /**
     * 失效
     * @param position
     */
    public void delete(Position position);


    public void add(Position position);


    public void update(Position position);
    /**
     * 查询岗位列表
     * @param orgId
     * @return
     */
    public List<Position> getOrgPositionByOrgId(Long orgId);

    /**
     * 查询岗位树
     * @param pid
     * @param positionType
     * @param positionCode
     * @param isRoot
     * @return
     */
    public List<TreeNodeVo> selectPositionTree(String pid,String positionType,String positionCode, boolean isRoot);

    /**
     * 判断是否存在子节点
     * @param pid
     * @param positionType
     * @param positionCode
     * @return
     */
    public List<TreeNodeVo> isleaf(String pid,String positionType,String positionCode);



}
