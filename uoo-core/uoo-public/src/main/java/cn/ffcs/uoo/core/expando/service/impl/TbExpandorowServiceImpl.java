package cn.ffcs.uoo.core.expando.service.impl;

import cn.ffcs.uoo.base.common.tool.util.DateUtils;
import cn.ffcs.uoo.core.expando.entity.TbExpandorow;
import cn.ffcs.uoo.core.expando.dao.TbExpandorowMapper;
import cn.ffcs.uoo.core.expando.service.TbExpandorowService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 保留，如通讯号码就对应多个 服务实现类
 * </p>
 *
 * @author zhanglu
 * @since 2018-10-25
 */
@Service
public class TbExpandorowServiceImpl extends ServiceImpl<TbExpandorowMapper, TbExpandorow> implements TbExpandorowService {

    @Override
    public int save(TbExpandorow tbExpandorow) {
        return baseMapper.save(tbExpandorow);
    }

    @Override
    public void remove(Long rowId, Long updateUser) {
        TbExpandorow tbExpandorow = new TbExpandorow();
        tbExpandorow.setRowId(rowId);
        //  失效状态
        tbExpandorow.setStatusCd("1100");
        tbExpandorow.setUpdateDate(DateUtils.parseDate(DateUtils.getDateTime()));
        tbExpandorow.setUpdateUser(updateUser);
        tbExpandorow.setStatusDate(DateUtils.parseDate(DateUtils.getDateTime()));
        baseMapper.remove(tbExpandorow);
    }
}
