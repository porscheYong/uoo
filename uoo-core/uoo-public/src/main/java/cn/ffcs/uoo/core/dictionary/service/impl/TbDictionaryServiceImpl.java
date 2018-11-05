package cn.ffcs.uoo.core.dictionary.service.impl;

import cn.ffcs.uoo.base.common.tool.util.DateUtils;
import cn.ffcs.uoo.core.dictionary.dao.TbDictionaryMapper;
import cn.ffcs.uoo.core.dictionary.entity.TbDictionary;
import cn.ffcs.uoo.core.dictionary.service.TbDictionaryService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 字典定义 服务实现类
 * </p>
 *
 * @author zhanglu
 * @since 2018-10-22
 */
@Service
public class TbDictionaryServiceImpl extends ServiceImpl<TbDictionaryMapper, TbDictionary> implements TbDictionaryService {
    @Override
    public void saveDictionary(TbDictionary tbDictionary) {
        baseMapper.save(tbDictionary);
    }

    @Override
    public List<TbDictionary> selectDicList(TbDictionary tbDictionary) {
        return baseMapper.selectDicList(tbDictionary);
    }

    @Override
    public void remove(Long dictionaryId, Long updateUser) {
        TbDictionary tbDictionary = new TbDictionary();
        tbDictionary.setDictionaryId(dictionaryId);
        // 失效状态
        tbDictionary.setStatusCd("1100");
        tbDictionary.setUpdateDate(DateUtils.parseDate(DateUtils.getDateTime()));
        tbDictionary.setUpdateUser(updateUser);
        tbDictionary.setStatusDate(DateUtils.parseDate(DateUtils.getDateTime()));
        baseMapper.remove(tbDictionary);
    }
}
