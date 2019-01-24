package cn.ffcs.uoo.core.dictionary.dao;

import cn.ffcs.uoo.core.dictionary.entity.TbDictionaryItem;
import cn.ffcs.uoo.core.vo.DictionaryItemVo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

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

    List<TbDictionaryItem> selectDicItemListByDicName(@Param("dictionaryName") String dictionaryName);

    /**
     * 查询所有字典项值对象列表
     * @return
     */
    List<DictionaryItemVo> selectAllDicitemVoList();
}
