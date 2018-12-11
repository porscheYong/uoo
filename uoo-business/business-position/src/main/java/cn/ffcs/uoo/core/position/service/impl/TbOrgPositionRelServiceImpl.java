package cn.ffcs.uoo.core.position.service.impl;

import cn.ffcs.uoo.base.common.tool.util.DateUtils;
import cn.ffcs.uoo.core.position.entity.TbOrgPositionRel;
import cn.ffcs.uoo.core.position.dao.TbOrgPositionRelMapper;
import cn.ffcs.uoo.core.position.service.TbOrgPositionRelService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 岗位组织关系，不同组织树具有不同的岗位 服务实现类
 * </p>
 *
 * @author zhanglu
 * @since 2018-11-01
 */
@Service
public class TbOrgPositionRelServiceImpl extends ServiceImpl<TbOrgPositionRelMapper, TbOrgPositionRel> implements TbOrgPositionRelService {

    @Override
    public int save(TbOrgPositionRel tbOrgPositionRel) {
        return baseMapper.save(tbOrgPositionRel);
    }

    @Override
    public void remove(Long orgPositionId, Long updateUser) {
        TbOrgPositionRel tbOrgPositionRel = new TbOrgPositionRel();
        tbOrgPositionRel.setOrgPositionId(orgPositionId);
        tbOrgPositionRel.setUpdateDate(DateUtils.parseDate(DateUtils.getDateTime()));
        tbOrgPositionRel.setUpdateUser(updateUser);
        tbOrgPositionRel.setStatusDate(DateUtils.parseDate(DateUtils.getDateTime()));
        // 失效状态
        tbOrgPositionRel.setStatusCd("1100");
        baseMapper.remove(tbOrgPositionRel);
    }
}
