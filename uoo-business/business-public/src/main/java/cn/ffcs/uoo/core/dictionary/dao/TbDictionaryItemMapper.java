package cn.ffcs.uoo.core.dictionary.dao;

import cn.ffcs.uoo.core.dictionary.entity.TbDictionaryItem;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 字典项目 Mapper 接口
 * </p>
 *
 * @author zhanglu
 * @since 2018-10-22
 */
public interface TbDictionaryItemMapper extends BaseMapper<TbDictionaryItem> {
    /**
     * 根据字典定义id批量失效字典项目
     */
    void removeBatchByDicId(TbDictionaryItem tbDictionaryItem);

    /**
     * 保存字典项
     * @param tbDictionaryItem
     */
    void save(TbDictionaryItem tbDictionaryItem);

    void remove(TbDictionaryItem tbDictionaryItem);

    List<TbDictionaryItem> selectDicItemList(TbDictionaryItem tbDictionaryItem);
}
