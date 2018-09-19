package cn.ffcs.uoo.rabbitmq.manage.dao;

import org.apache.ibatis.annotations.Select;

import cn.ffcs.uoo.rabbitmq.manage.pojo.NodeInfo;
import cn.ffcs.uoo.rabbitmq.manage.vo.NodeVo;

public interface NodeInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(NodeInfo record);

    int insertSelective(NodeInfo record);

    NodeInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(NodeInfo record);

    int updateByPrimaryKey(NodeInfo record);
    
    
    @Select("select * from (select ip,port,username,password from node_info,rabbitmq_user_info order by dbms_random.value ) where rownum = 1")
	NodeVo getRandNodeInfo();
}