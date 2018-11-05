package cn.ffcs.uoo.message.server.dao;

import cn.ffcs.uoo.message.server.pojo.TbOrgTree;

public interface TbOrgTreeMapper {
    int insert(TbOrgTree record);

    int insertSelective(TbOrgTree record);
}