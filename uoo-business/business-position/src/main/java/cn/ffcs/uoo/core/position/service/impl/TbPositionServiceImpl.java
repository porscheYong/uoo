package cn.ffcs.uoo.core.position.service.impl;

import cn.ffcs.uoo.base.common.tool.util.DateUtils;
import cn.ffcs.uoo.core.position.constant.StatusEnum;
import cn.ffcs.uoo.core.position.entity.TbPosition;
import cn.ffcs.uoo.core.position.dao.TbPositionMapper;
import cn.ffcs.uoo.core.position.service.TbPositionService;
import cn.ffcs.uoo.core.position.vo.OrgPositionInfoVo;
import cn.ffcs.uoo.core.position.vo.PositionNodeVo;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 岗位，不同组织树具有不同的岗位 服务实现类
 * </p>
 *
 * @author zhanglu
 * @since 2018-10-31
 */
@Service
public class TbPositionServiceImpl extends ServiceImpl<TbPositionMapper, TbPosition> implements TbPositionService {
    @Override
    public int save(TbPosition tbPosition) {
        return baseMapper.save(tbPosition);
    }

    @Override
    public void remove(Long positionId, Long updateUser) {
        TbPosition tbPosition = new TbPosition();
        tbPosition.setExpDate(DateUtils.parseDate(DateUtils.getDateTime()));
        tbPosition.setPositionId(positionId);
        tbPosition.setUpdateUser(updateUser);
        tbPosition.setUpdateDate(DateUtils.parseDate(DateUtils.getDateTime()));
        // 失效状态
        tbPosition.setStatusCd(StatusEnum.INVALID.getStatus());
        tbPosition.setStatusDate(DateUtils.parseDate(DateUtils.getDateTime()));
        baseMapper.remove(tbPosition);
    }

    @Override
    public List<OrgPositionInfoVo> queryOrgPositionInfoList(Long orgId) {
        return baseMapper.queryOrgPositionInfoList(orgId);
    }

    @Override
    public List<PositionNodeVo> getAllPositionNodeVo() {
        return baseMapper.getAllPositionNodeVo();
    }
}
