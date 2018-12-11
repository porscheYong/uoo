package cn.ffcs.uoo.core.expando.service.impl;

import cn.ffcs.uoo.base.common.tool.util.DateUtils;
import cn.ffcs.uoo.core.constant.StatusEnum;
import cn.ffcs.uoo.core.expando.entity.TbExpandocolumn;
import cn.ffcs.uoo.core.expando.dao.TbExpandocolumnMapper;
import cn.ffcs.uoo.core.expando.service.TbExpandocolumnService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 扩展列 服务实现类
 * </p>
 *
 * @author zhanglu
 * @since 2018-10-25
 */
@Service
public class TbExpandocolumnServiceImpl extends ServiceImpl<TbExpandocolumnMapper, TbExpandocolumn> implements TbExpandocolumnService {

    @Override
    public int save(TbExpandocolumn tbExpandocolumn) {
        return baseMapper.save(tbExpandocolumn);
    }

    @Override
    public void remove(Long columnId, Long updateUser) {
        TbExpandocolumn tbExpandocolumn = new TbExpandocolumn();
        tbExpandocolumn.setColumnId(columnId);
        tbExpandocolumn.setUpdateUser(updateUser);
        tbExpandocolumn.setUpdateDate(DateUtils.parseDate(DateUtils.getDateTime()));
        // 失效状态
        tbExpandocolumn.setStatusCd(StatusEnum.INVALID.getValue());
        tbExpandocolumn.setStatusDate(DateUtils.parseDate(DateUtils.getDateTime()));
        baseMapper.remove(tbExpandocolumn);
    }

    @Override
    public List<TbExpandocolumn> queryColumnList(Long tableId, String resourceId) {
        return baseMapper.queryColumnList(tableId, resourceId);
    }
}
