package cn.ffcs.uoo.core.expando.service.impl;

import cn.ffcs.uoo.base.common.tool.util.DateUtils;
import cn.ffcs.uoo.core.constant.StatusEnum;
import cn.ffcs.uoo.core.expando.entity.TbSystemtable;
import cn.ffcs.uoo.core.expando.dao.TbSystemtableMapper;
import cn.ffcs.uoo.core.expando.service.TbSystemtableService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统表登记
 * 所有业务表都要登记，扩展表需要登记,非扩展表无须定义 服务实现类
 * </p>
 *
 * @author zhanglu
 * @since 2018-10-25
 */
@Service
public class TbSystemtableServiceImpl extends ServiceImpl<TbSystemtableMapper, TbSystemtable> implements TbSystemtableService {
    @Override
    public int save(TbSystemtable tbSystemtable) {
        return baseMapper.save(tbSystemtable);
    }

    @Override
    public void remove(Long tableId, Long updateUser) {
        TbSystemtable tbSystemtable = new TbSystemtable();
        tbSystemtable.setTableId(tableId);
        // 失效状态
        tbSystemtable.setStatusCd(StatusEnum.INVALID.getValue());
        tbSystemtable.setUpdateDate(DateUtils.parseDate(DateUtils.getDateTime()));
        tbSystemtable.setUpdateUser(updateUser);
        tbSystemtable.setStatusDate(DateUtils.parseDate(DateUtils.getDateTime()));
        baseMapper.remove(tbSystemtable);
    }
}
