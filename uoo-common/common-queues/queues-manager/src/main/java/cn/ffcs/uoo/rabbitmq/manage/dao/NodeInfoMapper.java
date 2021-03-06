package cn.ffcs.uoo.rabbitmq.manage.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import cn.ffcs.uoo.rabbitmq.manage.pojo.NodeInfo;
import cn.ffcs.uoo.rabbitmq.manage.vo.NodeVo;

public interface NodeInfoMapper extends BaseMapper<NodeInfo> {

//    int deleteByPrimaryKey(Long id);
//
//    int insert(NodeInfo record);
//
//    int insertSelective(NodeInfo record);
//
//    NodeInfo selectByPrimaryKey(Long id);
//
//    int updateByPrimaryKeySelective(NodeInfo record);
//
//    int updateByPrimaryKey(NodeInfo record);
    
    
    @Select("select * from (select ip,port,username,password from node_info,(select id,username,password from rabbitmq_user_info where id = (select max(id) from rabbitmq_user_info )) order by dbms_random.value ) where rownum = 1")
	NodeVo getRandNodeInfo();

    int test();
}