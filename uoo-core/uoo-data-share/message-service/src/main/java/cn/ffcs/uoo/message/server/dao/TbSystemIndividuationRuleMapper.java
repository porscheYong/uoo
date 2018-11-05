package cn.ffcs.uoo.message.server.dao;

import cn.ffcs.uoo.message.server.pojo.TbSystemIndividuationRule;

public interface TbSystemIndividuationRuleMapper {
    int deleteByPrimaryKey(Long individuationRuleId);

    int insert(TbSystemIndividuationRule record);

    int insertSelective(TbSystemIndividuationRule record);

    TbSystemIndividuationRule selectByPrimaryKey(Long individuationRuleId);

    int updateByPrimaryKeySelective(TbSystemIndividuationRule record);

    int updateByPrimaryKey(TbSystemIndividuationRule record);
}