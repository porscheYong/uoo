package cn.ffcs.uoo.core.dictionary.service;

import cn.ffcs.uoo.core.dictionary.entity.TbDictionary;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 字典定义 服务类
 * </p>
 *
 * @author zhanglu
 * @since 2018-10-22
 */
public interface TbDictionaryService extends IService<TbDictionary> {
    /**
     * 保存字典定义
     * @param tbDictionary
     * @return
     */
    void saveDictionary(TbDictionary tbDictionary);

    /**
     * 查询字典定义列表
     * @param tbDictionary
     * @return
     */
    List<TbDictionary> selectDicList(TbDictionary tbDictionary);

    /**
     * 失效该条数据
     * @param dictionaryId
     * @param updateUser
     */
    void remove(Long dictionaryId, Long updateUser);
}
