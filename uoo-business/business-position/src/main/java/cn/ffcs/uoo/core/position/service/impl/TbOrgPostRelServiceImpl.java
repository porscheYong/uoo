package cn.ffcs.uoo.core.position.service.impl;

import cn.ffcs.uoo.base.common.tool.util.DateUtils;
import cn.ffcs.uoo.core.position.constant.StatusEnum;
import cn.ffcs.uoo.core.position.entity.TbOrgPostRel;
import cn.ffcs.uoo.core.position.dao.TbOrgPostRelMapper;
import cn.ffcs.uoo.core.position.service.TbOrgPostRelService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 组织职位关系
 * 职位，不同组织树具有不同的职位 服务实现类
 * </p>
 *
 * @author zhanglu
 * @since 2018-10-30
 */
@Service
public class TbOrgPostRelServiceImpl extends ServiceImpl<TbOrgPostRelMapper, TbOrgPostRel> implements TbOrgPostRelService {

    @Override
    public int save(TbOrgPostRel tbOrgPostRel) {
        return baseMapper.save(tbOrgPostRel);
    }

    @Override
    public void remove(Long orgPostId, Long updateUser) {
        TbOrgPostRel tbOrgPostRel = new TbOrgPostRel();
        tbOrgPostRel.setOrgPostId(orgPostId);
        tbOrgPostRel.setExpDate(DateUtils.parseDate(DateUtils.getDateTime()));
        tbOrgPostRel.setUpdateUser(updateUser);
        tbOrgPostRel.setUpdateDate(DateUtils.parseDate(DateUtils.getDateTime()));
        // 失效状态
        tbOrgPostRel.setStatusCd(StatusEnum.INVALID.getStatus());
        tbOrgPostRel.setStatusDate(DateUtils.parseDate(DateUtils.getDateTime()));

        baseMapper.remove(tbOrgPostRel);
    }
}
