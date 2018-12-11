package cn.ffcs.uoo.core.dictionary.service.impl;

import cn.ffcs.uoo.base.common.tool.util.DateUtils;
import cn.ffcs.uoo.core.dictionary.dao.TbDictionaryItemMapper;
import cn.ffcs.uoo.core.dictionary.entity.TbDictionaryItem;
import cn.ffcs.uoo.core.dictionary.service.TbDictionaryItemService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 字典项目 服务实现类
 * </p>
 *
 * @author zhanglu
 * @since 2018-10-22
 */
@Service
public class TbDictionaryItemServiceImpl extends ServiceImpl<TbDictionaryItemMapper, TbDictionaryItem> implements TbDictionaryItemService {
    @Override
    public void removeBatchByDicId(Long dictionaryId, Long updateUser) {
        TbDictionaryItem tbDictionaryItem = new TbDictionaryItem();
        tbDictionaryItem.setDictionaryId(dictionaryId);
        // 失效状态
        tbDictionaryItem.setStatusCd("1100");
        tbDictionaryItem.setUpdateDate(DateUtils.parseDate(DateUtils.getDateTime()));
        tbDictionaryItem.setUpdateUser(updateUser);
        tbDictionaryItem.setStatusDate(DateUtils.parseDate(DateUtils.getDateTime()));
        baseMapper.removeBatchByDicId(tbDictionaryItem);
    }

    @Override
    public void save(TbDictionaryItem tbDictionaryItem) {
        baseMapper.save(tbDictionaryItem);
    }

    @Override
    public void remove(Long itemId, Long updateUser) {
        TbDictionaryItem tbDictionaryItem = new TbDictionaryItem();
        tbDictionaryItem.setItemId(itemId);
        // 失效状态
        tbDictionaryItem.setStatusCd("1100");
        tbDictionaryItem.setUpdateDate(DateUtils.parseDate(DateUtils.getDateTime()));
        tbDictionaryItem.setUpdateUser(updateUser);
        tbDictionaryItem.setStatusDate(DateUtils.parseDate(DateUtils.getDateTime()));
        baseMapper.remove(tbDictionaryItem);
    }

    @Override
    public List<TbDictionaryItem> selectDicItemList(TbDictionaryItem tbDictionaryItem) {
        return baseMapper.selectDicItemList(tbDictionaryItem);
    }

    @Override
    public List<TbDictionaryItem> selectDicItemListByDicName(String dictionaryName) {
        return baseMapper.selectDicItemListByDicName(dictionaryName);
    }
}
