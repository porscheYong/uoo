package cn.ffcs.uoo.core.dictionary.service;

import cn.ffcs.uoo.core.dictionary.entity.TbDictionaryItem;
import cn.ffcs.uoo.core.vo.DictionaryItemVo;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 字典项目 服务类
 * </p>
 *
 * @author zhanglu
 * @since 2018-10-22
 */
public interface TbDictionaryItemService extends IService<TbDictionaryItem> {
    /**
     * 根据字典定义id批量失效字典项目
     */
    void removeBatchByDicId(Long dictionaryId, Long updateUser);

    void save(TbDictionaryItem tbDictionaryItem);

    void remove(Long itemId, Long updateUser);

    List<TbDictionaryItem> selectDicItemList(TbDictionaryItem tbDictionaryItem);

    List<TbDictionaryItem> selectDicItemListByDicName(String dictionaryName);

    List<DictionaryItemVo> selectAllDicitemVoList();
}
