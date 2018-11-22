package cn.ffcs.uoo.core.position.service;

import cn.ffcs.uoo.core.position.entity.TbPosition;
import cn.ffcs.uoo.core.position.vo.OrgPositionInfoVo;
import cn.ffcs.uoo.core.position.vo.PositionNodeVo;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 岗位，不同组织树具有不同的岗位 服务类
 * </p>
 *
 * @author zhanglu
 * @since 2018-10-31
 */
public interface TbPositionService extends IService<TbPosition> {
    /**
     * 新增岗位信息
     * @param tbPosition
     * @return
     */
    int save(TbPosition tbPosition);

    /**
     *  删除岗位信息
     * @param positionId
     * @param updateUser
     */
    void remove(Long positionId, Long updateUser);

    /**
     * 查询组织岗位信息列表
     * @param orgId
     * @return
     */
    List<OrgPositionInfoVo> queryOrgPositionInfoList(Long orgId);

    /**
     * 获取岗位信息列表
     * @return
     */
    List<PositionNodeVo> getAllPositionNodeVo();
}
