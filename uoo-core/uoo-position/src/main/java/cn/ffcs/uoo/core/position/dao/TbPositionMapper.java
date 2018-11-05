package cn.ffcs.uoo.core.position.dao;

import cn.ffcs.uoo.core.position.entity.TbPosition;
import cn.ffcs.uoo.core.position.vo.OrgPositionInfoVo;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 岗位，不同组织树具有不同的岗位 Mapper 接口
 * </p>
 *
 * @author zhanglu
 * @since 2018-10-31
 */
public interface TbPositionMapper extends BaseMapper<TbPosition> {
    /**
     * 新增岗位信息
     * @param tbPosition
     * @return
     */
    int save(TbPosition tbPosition);

    /**
     * 删除岗位信息
     * @param tbPosition
     */
    void remove(TbPosition tbPosition);

    /**
     * 查询组织岗位信息列表
     * @param orgId
     * @return
     */
    List<OrgPositionInfoVo> queryOrgPositionInfoList(Long orgId);
}
