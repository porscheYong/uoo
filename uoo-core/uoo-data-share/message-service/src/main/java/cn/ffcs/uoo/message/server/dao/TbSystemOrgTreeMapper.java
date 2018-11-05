package cn.ffcs.uoo.message.server.dao;

import cn.ffcs.uoo.message.server.pojo.TbSystemOrgTree;

public interface TbSystemOrgTreeMapper {
    int deleteByPrimaryKey(Long systemOrgTreeId);

    int insert(TbSystemOrgTree record);

    int insertSelective(TbSystemOrgTree record);

    TbSystemOrgTree selectByPrimaryKey(Long systemOrgTreeId);

    int updateByPrimaryKeySelective(TbSystemOrgTree record);

    int updateByPrimaryKey(TbSystemOrgTree record);
}