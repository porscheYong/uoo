package cn.ffcs.uoo.rabbitmq.manage.dao;

import org.apache.ibatis.annotations.Param;

import cn.ffcs.uoo.rabbitmq.manage.pojo.SystemQueueRela;

public interface SystemQueueRelaMapper {
    int deleteByPrimaryKey(String queueName);

    int insert(SystemQueueRela record);

    int insertSelective(SystemQueueRela record);

    SystemQueueRela selectByPrimaryKey(String queueName);

    int updateByPrimaryKeySelective(SystemQueueRela record);

    int updateByPrimaryKey(SystemQueueRela record);

	int checkSystemQueueRela(@Param("systemName") String systemName,@Param("doubleName") String doubleName,@Param("queueName") String queueName,@Param("status") String status);
}