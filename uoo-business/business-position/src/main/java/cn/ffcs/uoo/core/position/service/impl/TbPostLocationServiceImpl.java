package cn.ffcs.uoo.core.position.service.impl;

import cn.ffcs.uoo.base.common.tool.util.DateUtils;
import cn.ffcs.uoo.core.position.constant.StatusEnum;
import cn.ffcs.uoo.core.position.entity.TbPostLocation;
import cn.ffcs.uoo.core.position.dao.TbPostLocationMapper;
import cn.ffcs.uoo.core.position.service.TbPostLocationService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 职位行政区域 服务实现类
 * </p>
 *
 * @author zhanglu
 * @since 2018-10-30
 */
@Service
public class TbPostLocationServiceImpl extends ServiceImpl<TbPostLocationMapper, TbPostLocation> implements TbPostLocationService {

    @Override
    public int save(TbPostLocation tbPostLocation) {
        return baseMapper.save(tbPostLocation);
    }

    @Override
    public void remove(Long postLocationId, Long updateUser) {
        TbPostLocation tbPostLocation = new TbPostLocation();
        tbPostLocation.setPostLocationId(postLocationId);
        // 失效状态
        tbPostLocation.setStatusCd(StatusEnum.INVALID.getStatus());
        tbPostLocation.setExpDate(DateUtils.parseDate(DateUtils.getDateTime()));
        tbPostLocation.setUpdateDate(DateUtils.parseDate(DateUtils.getDateTime()));
        tbPostLocation.setUpdateUser(updateUser);
        tbPostLocation.setStatusDate(DateUtils.parseDate(DateUtils.getDateTime()));

        baseMapper.remove(tbPostLocation);
    }
}
