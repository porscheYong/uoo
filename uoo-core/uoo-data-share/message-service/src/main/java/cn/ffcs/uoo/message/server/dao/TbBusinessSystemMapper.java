package cn.ffcs.uoo.message.server.dao;

import cn.ffcs.uoo.message.server.pojo.TbBusinessSystem;

public interface TbBusinessSystemMapper {
    int deleteByPrimaryKey(Long businessSystemId);

    int insert(TbBusinessSystem record);

    int insertSelective(TbBusinessSystem record);

    TbBusinessSystem selectByPrimaryKey(Long businessSystemId);

    int updateByPrimaryKeySelective(TbBusinessSystem record);

    int updateByPrimaryKey(TbBusinessSystem record);
}