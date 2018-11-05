package cn.ffcs.uoo.core.dictionary.dao;

import cn.ffcs.uoo.core.dictionary.entity.TbDictionary;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 字典定义 Mapper 接口
 * </p>
 *
 * @author zhanglu
 * @since 2018-10-22
 */
public interface TbDictionaryMapper extends BaseMapper<TbDictionary> {
    /**
     *  保存
     * @param tbDictionary
     * @return
     */
    void save(TbDictionary tbDictionary);

    /**
     * 失效该记录
     * @param tbDictionary
     */
    void remove(TbDictionary tbDictionary);

    List<TbDictionary> selectDicList(TbDictionary tbDictionary);
}
